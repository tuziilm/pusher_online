package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.domain.SysUser;

/**
 * ibatis操作系统用户表的Mapper接口
 *
 */
public interface SysUserMapper extends BaseMapper<SysUser>{

	SysUser getByUsername(String username);
}