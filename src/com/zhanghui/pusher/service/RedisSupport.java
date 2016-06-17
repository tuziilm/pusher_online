package com.zhanghui.pusher.service;

import com.zhanghui.pusher.common.TryUtils;
import com.zhanghui.pusher.exception.NeedRetryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * Á¬½ÓRedis
 *
 */
@Component
public class RedisSupport {
    private final Logger log=LoggerFactory.getLogger(getClass());
    public final static int DEFAULT_DATABASE = 0;
    public final static int DATABASE_0 = DEFAULT_DATABASE;
    public final static int DATABASE_1 = 1;
    public final static int DATABASE_2 = 2;
    @Resource
    private JedisPool jedisPool0;
    @Resource
    private JedisPool jedisPool1;
    @Resource
    private JedisPool jedisPool2;
    private JedisPool[] jedisPools;
    @PostConstruct
    public void init(){
        jedisPools=new JedisPool[]{jedisPool0, jedisPool1, jedisPool2};
    }

    public static interface JedisHandler<T>{
        public T handle(Jedis jedis);
    }

    public <T> T try2DoWithRedisWithNoException(final JedisHandler<T> handler, int database){
        return try2DoWithRedis(handler, database, false);
    }

    public <T> T try2DoWithRedisWithNoException(final JedisHandler<T> handler){
        return try2DoWithRedis(handler, false);
    }

    public <T> T try2DoWithRedis(final JedisHandler<T> handler, int database){
        return try2DoWithRedis(handler, database, true);
    }

    public <T> T try2DoWithRedis(final JedisHandler<T> handler){
        return try2DoWithRedis(handler, true);
    }

    public <T> T try2DoWithRedis(final JedisHandler<T> handler, final boolean throwEx){
        return try2DoWithRedis(handler, DEFAULT_DATABASE, throwEx);
    }

    public <T> T try2DoWithRedis(final JedisHandler<T> handler, final int database, final boolean throwEx){
        return try2DoWithRedis(handler, database, throwEx, TryUtils.MAX_TRY_TIMES);
    }
    public <T> T try2DoWithRedis(final JedisHandler<T> handler, final int database, final boolean throwEx, int times){
        return TryUtils.doTry(new TryUtils.Func<T>() {
            @Override
            public T run() {
                Jedis jedis = null;
                try{
                    jedis=jedisPools[database].getResource();
                    return handler.handle(jedis);
                }catch(JedisException e){
                    throw new NeedRetryException(e);
                }finally{
                    if(jedis!=null)
                        jedisPools[database].returnResource(jedis);
                }
            }

            @Override
            public void callbackWhenRetry(int times, NeedRetryException e) {
                log.warn("failure to handler jedis on database {}, try times {}. ex:{}", database, (times+1), e.getMessage());
            }

            @Override
            public void callbackWhenFail(int times, NeedRetryException e) {
                if(throwEx){
                    throw e;
                }else{
                    log.error(String.format("failure to handler jedis on database {}, try times %d.", database, (times+1)), e);
                }
            }
        }, times);
    }

    public <T> T doWithRedis(JedisHandler<T> handler){
        return doWithRedis(handler, DEFAULT_DATABASE);
    }

    public <T> T doWithRedis(JedisHandler<T> handler, int database){
        Jedis jedis=null;
        try{
            jedis=jedisPools[database].getResource();
            return handler.handle(jedis);
        }finally{
            if(jedis!=null)
                jedisPools[database].returnResource(jedis);
        }
    }
}

