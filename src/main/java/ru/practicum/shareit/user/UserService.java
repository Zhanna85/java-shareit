package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User addModel(UserDto userDto);

    User updateModel(long id, UserDto userDto);

    User findModelById(long id);

    List<User> getAllModels();

    void deleteModelById(long id);
}