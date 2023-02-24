package com.aakin.sbrepeat.dto.convert;

import com.aakin.sbrepeat.dto.UserDto;
import com.aakin.sbrepeat.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public UserDto convertDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
