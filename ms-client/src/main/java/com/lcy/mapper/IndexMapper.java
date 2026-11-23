package com.lcy.mapper;

import com.lcy.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface IndexMapper {

    int userRegister(UserEntity userEntity);

    int checkAccountExist(@Param("account") String account);

    UserEntity queryUserByAccountAndPassword(UserEntity userEntity);

    UserEntity queryUserById(@Param("id") String id);

    int editUserInfo(UserEntity userEntity);
}
