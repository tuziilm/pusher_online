package com.zhanghui.pusher.service;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.DateUtils;
import com.zhanghui.pusher.common.RedisKeys;
import com.zhanghui.pusher.common.TaskType;
import com.zhanghui.pusher.domain.Task;
import com.zhanghui.pusher.persistence.TaskMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.TransactionBlock;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

@Service
public class TaskService extends ObjectBasedGroupCacheSupportService<Task> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String LIST_SINGLE_LINK_KEY="list_single_link_key";
    private final static String LIST_SINGLE_APP_KEY="list_single_app_key";
    private final static String LIST_MULTI_APP_KEY="list_multi_app_key";
    private final static String LIST_PROMPT_KEY="list_prompt_key";
    private final static String LIST_TEST_PROMPT_KEY="list_test_prompt_key";
    private final static String LIST_TEST_SINGLE_LINK_KEY="list_test_single_link_key";
    private final static String LIST_TEST_SINGLE_APP_KEY="list_test_single_app_key";
    private final static String LIST_TEST_MULTI_APP_KEY="list_test_multi_app_key";
    private final static String MAP_ALL_KEY="map_all_key";
    public final static int EXPIRE_TIME_LIMIT_REQUEST_FOR_TASK=25 * 60 * 60;//1天1个小时
    public final static int EXPIRE_TIME_LIMIT_REQUEST_FOR_USER=5 * 24 * 60 * 60;//5天
	@Autowired
	public void setTaskMapper(TaskMapper taskMapper) {
		this.mapper = taskMapper;
	}

    @Override
    public String[] cacheGroupKeys() {
        return new String[]{LIST_PROMPT_KEY, LIST_TEST_PROMPT_KEY,LIST_ALL_KEY,LIST_SINGLE_LINK_KEY, LIST_SINGLE_APP_KEY, LIST_MULTI_APP_KEY, MAP_ALL_KEY, LIST_TEST_MULTI_APP_KEY, LIST_TEST_SINGLE_APP_KEY, LIST_TEST_SINGLE_LINK_KEY};
    }

    @Override
    public Object newObject(String cacheGroupKey) {
        if(cacheGroupKey.startsWith("map")){
            return new HashMap();
        }else{
            return new ArrayList();
        }
    }

    @Override
    public void updateCacheList(Map<String, Object> update, Task task) {
        ((List<Task>)update.get(LIST_ALL_KEY)).add(task);
        ((Map<Integer, Task>)update.get(MAP_ALL_KEY)).put(task.getId(), task);
        Calendar cal = DateUtils.beginningOfTheDate();
        if(task.getEndDate().getTime()>=cal.getTimeInMillis()) {
            boolean isForTesting=task.getPriority()==0;
            ((List<Task>) update.get(isForTesting?LIST_TEST_PROMPT_KEY:LIST_PROMPT_KEY)).add(task);
            if (task.getType() == TaskType.SINGLE_LINK) {
                ((List<Task>) update.get(isForTesting?LIST_TEST_SINGLE_LINK_KEY:LIST_SINGLE_LINK_KEY)).add(task);
            } else if (task.getType() == TaskType.SINGLE_APP) {
                ((List<Task>) update.get(isForTesting?LIST_TEST_SINGLE_APP_KEY:LIST_SINGLE_APP_KEY)).add(task);
            } else if (task.getType() == TaskType.MULTI_APP) {
                ((List<Task>) update.get(isForTesting?LIST_TEST_MULTI_APP_KEY:LIST_MULTI_APP_KEY)).add(task);
            }
        }
    }

    public List<Task> getPromptTasksCache(boolean testing){
        return (List<Task>)getCache(testing?LIST_TEST_PROMPT_KEY:LIST_PROMPT_KEY);
    }

    public List<Task> getSingleLinkTasksCache(boolean testing){
        return (List<Task>)getCache(testing?LIST_TEST_SINGLE_LINK_KEY:LIST_SINGLE_LINK_KEY);
    }

    public List<Task> getSingleAppTasksCache(boolean testing){
        return (List<Task>)getCache(testing?LIST_TEST_SINGLE_APP_KEY:LIST_SINGLE_APP_KEY);
    }

    public List<Task> getMultiAppTasksCache(boolean testing){
        return (List<Task>)getCache(testing?LIST_TEST_MULTI_APP_KEY:LIST_MULTI_APP_KEY);
    }

    public List<Task> getAllTasksCache(){
        return (List<Task>)getCache(LIST_ALL_KEY);
    }

    public Map<Integer, Task> getAllTasksMapCache(){
        return (Map<Integer, Task>)getCache(MAP_ALL_KEY);
    }

    public Map<Integer, Integer> getUsedReqCountMap(List<Task> tasks){
        if(tasks==null || tasks.isEmpty()){
            return null;
        }
        final Map<Integer, Integer> usedReqCountMap=new HashMap<>(tasks.size());
        final List<String> ids=new ArrayList<>(tasks.size());
        for(Task task : tasks){
            if(task.getMaxReqCount()!=null){
                if(task.getMaxReqCount()>1){
                    ids.add(task.getId().toString());
                }
            }
        }
        if(!ids.isEmpty()) {
            final String hkey = RedisKeys.TASK_REQUEST_LIMIT_HKEY_PREFIX+ DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            final int len = ids.size();
            redisSupport.doWithRedis(new RedisSupport.JedisHandler<Object>() {
                @Override
                public Object handle(Jedis jedis) {
                    List<String> counts = jedis.hmget(hkey, ids.toArray(new String[len]));
                    for (int i = 0; i < len; i++) {
                        String count = counts.get(i);
                        usedReqCountMap.put(Integer.valueOf(ids.get(i)), count == null ? 0 : Integer.valueOf(count));
                    }
                    return null;
                }
            },RedisSupport.DATABASE_1);
        }
        return usedReqCountMap;
    }

    /**
     * 自增受限请求次数
     * @param taskId
     * @return
     */
    public void incrLimitRequestForTask(final Integer taskId){
        if(taskId==null || taskId<1){
            return;
        }
        Task task = getAllTasksMapCache().get(taskId);
        if(task==null || task.getMaxReqCount() == null || task.getMaxReqCount()<1){
            return;
        }
        final String idString= task.getId().toString();
        final String hkey = RedisKeys.TASK_REQUEST_LIMIT_HKEY_PREFIX+ DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
            @Override
            public Boolean handle(final Jedis jedis) {
                Long result = jedis.hincrBy(hkey, idString, 1);
                if (result == 1) {
                    jedis.expire(hkey, EXPIRE_TIME_LIMIT_REQUEST_FOR_TASK);
                    return true;
                }
                return true;
            }
        }, RedisSupport.DATABASE_1, false, 1);
    }
    /**
     * 是否请求受限
     * @param task
     * @return
     */
    public boolean limitRequestForTask(final Task task){
        if(task==null || task.getMaxReqCount() == null || task.getMaxReqCount()<1){
            return false;
        }
        final String idString= task.getId().toString();
        final String hkey = RedisKeys.TASK_REQUEST_LIMIT_HKEY_PREFIX+ DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        return redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
            @Override
            public Boolean handle(final Jedis jedis) {
                String result = jedis.hget(hkey, idString);
                long count = result==null?0L:Long.valueOf(result);
                return count >= task.getMaxReqCount();
            }
        }, RedisSupport.DATABASE_1, false,1);
    }

    /**
     * 指定时间内对单用户不下发任务
     * @param uid
     * @param tasks
     */
    public void flagUserAccessTask(final String uid, final List<Task> tasks){
        if(Strings.isNullOrEmpty(uid) || tasks==null || tasks.isEmpty()){
            return;
        }
        redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
            @Override
            public Boolean handle(final Jedis jedis) {
                jedis.multi(new TransactionBlock() {
                    @Override
                    public void execute() throws JedisException {
                        for(Task task: tasks){
                            String key = RedisKeys.taskRequestLimitForUserKey(uid, task.getId());
                            set(key, "1");
                            expire(key, EXPIRE_TIME_LIMIT_REQUEST_FOR_USER);
                        }
                    }
                });
                return true;
            }
        }, RedisSupport.DATABASE_1, false, 1);
    }

    /**
     * 任务是否在周期内对用户进行限制
     * @param uid
     * @param taskId
     * @return true:有限制， false:无限制
     */
    public boolean taskLimitForUser(final String uid, final Integer taskId){
        if(Strings.isNullOrEmpty(uid) || taskId == null){
            return true;
        }
        Boolean result = redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
            @Override
            public Boolean handle(final Jedis jedis) {
                String key = RedisKeys.taskRequestLimitForUserKey(uid, taskId);
                return jedis.exists(key);
            }
        }, RedisSupport.DATABASE_1, false, 1);
        return result==null || result;
    }
}
