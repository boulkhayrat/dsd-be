package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}