package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.domain.SysUser;

/**
 * ibatis����ϵͳ�û����Mapper�ӿ�
 *
 */
public interface SysUserMapper extends BaseMapper<SysUser>{

	SysUser getByUsername(String username);
}