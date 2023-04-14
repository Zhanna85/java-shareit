package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static User toUser(long id, UserDto userDto) {
        return new User(
                id,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}