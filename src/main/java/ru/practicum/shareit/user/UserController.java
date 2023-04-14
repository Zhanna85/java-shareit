package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;

import java.util.List;

import static ru.practicum.shareit.utils.Message.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        log.info(ADD_MODEL.getMessage(), userDto);
        return userService.addModel(userDto);
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable long userId, @RequestBody UserDto user) {
        log.info(UPDATED_MODEL.getMessage(), userId, user);
        return userService.updateModel(userId, user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        log.info(REQUEST_BY_ID.getMessage(), userId);
        return userService.findModelById(userId);
    }

    @GetMapping
    public List<User> getListUsers() {
        log.info(REQUEST_ALL.getMessage());
        return userService.getAllModels();
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable long userId) {
        log.info(DELETE_MODEL.getMessage(), userId);
        userService.deleteModelById(userId);
    }
}