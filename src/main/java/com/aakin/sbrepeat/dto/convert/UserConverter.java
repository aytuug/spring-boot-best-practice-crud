package com.aakin.sbrepeat.dto.convert;

import com.aakin.sbrepeat.dto.UserDto;
import com.aakin.sbrepeat.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertUser(UserDto userdto){
        return User.builder()
                .id(userdto.getId())
                .firstName(userdto.getFirstName())
                .lastName(userdto.getLastName())
                .email(userdto.getEmail())
                .build();
    }
}
