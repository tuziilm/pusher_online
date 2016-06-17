package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.domain.User;

public interface UserMapper extends BaseMapper<User> {
    User selectByIdentity(String identity);
}
