package com.lcy.mapper;

import com.lcy.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexMapper {

    int userRegister(UserEntity userEntity);

    int checkAccountExist(String account);
}
