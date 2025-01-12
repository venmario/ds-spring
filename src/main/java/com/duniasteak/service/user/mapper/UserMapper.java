package com.duniasteak.service.user.mapper;

import com.duniasteak.service.user.dto.UserDto;
import com.duniasteak.service.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);

    User dtoToUser(UserDto dto);
}
