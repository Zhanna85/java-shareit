package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserStorage userStorage;

    @Override
    public User addModel(UserDto user) {
        return userStorage.add(user);
    }

    @Override
    public User updateModel(long id, UserDto user) {
        return userStorage.update(id, user);
    }

    @Override
    public User findModelById(long id) {
        return userStorage.findById(id);
    }

    @Override
    public List<User> getAllModels() {
        return userStorage.getAll();
    }

    @Override
    public void deleteModelById(long id) {
        userStorage.deleteById(id);
    }
}