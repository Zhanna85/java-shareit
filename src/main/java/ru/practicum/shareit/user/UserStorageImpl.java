package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.shareit.utils.Message.*;

@Component
@Slf4j
public class UserStorageImpl implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 0L;

    private void validationContain(long id) {
        if(!users.containsKey(id)) {
            log.error(MODEL_NOT_FOUND.getMessage() + id);
            throw new NotFoundException(MODEL_NOT_FOUND.getMessage() + id);
        }
    }

    private void validationDuplicate(UserDto user) {
        if (users.containsValue(user)) {
            log.error(DUPLICATE.getMessage());
            throw new ValidationException(DUPLICATE.getMessage());
        }
    }

    @Override
    public User add(UserDto userDto) {
        validationDuplicate(userDto);
        id++;
        User user = UserMapper.toUser(id, userDto);
        users.put(id, user);
        log.info(ADD_MODEL.getMessage(), user);
        return user;
    }

    @Override
    public User update(long id, UserDto user) {
        validationContain(id);
        User userUpdate = users.get(id);
        if (user.getEmail() != null && !userUpdate.equals(user)) {
            validationDuplicate(user);
            userUpdate.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            userUpdate.setName(user.getName());
        }
        users.put(id, userUpdate);
        return userUpdate;
    }

    @Override
    public User findById(long id) {
        validationContain(id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteById(long id) {
        validationContain(id);
        users.remove(id);
    }
}