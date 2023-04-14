package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {

    User add(UserDto user);

    User update(long id, UserDto user);

    User findById(long id);

    List<User> getAll();

    void deleteById(long id);
}