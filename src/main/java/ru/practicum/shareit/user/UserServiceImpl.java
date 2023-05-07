package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationExceptionOnDuplicate;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.mapper.UserMapper.toUserDto;
import static ru.practicum.shareit.utils.Message.*;
import static ru.practicum.shareit.utils.Message.DUPLICATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    //private final UserStorage userStorage;
    private final UserRepository userRepository;

    @Override
    public UserDto addModel(UserDto userDto) {
      /*  //return userStorage.add(user);
        Optional<User> userOptional = userRepository.findUserByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            log.error(DUPLICATE.getMessage());
            throw new ValidationExceptionOnDuplicate(DUPLICATE.getMessage());
        }
*/
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info(ADD_MODEL.getMessage(), user);
        return toUserDto(user);
    }

    @Override
    public UserDto updateModel(long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + id));
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        //return userStorage.update(id, user);
        return toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findModelById(long id) {
        //return userStorage.findById(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + id));
        return toUserDto(user);
    }

    @Override
    public List<UserDto> getAllModels() {
        //return userStorage.getAll();
        return userRepository.findAll().stream()
                .map(UserMapper :: toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteModelById(long id) {
        //userStorage.deleteById(id);
        userRepository.deleteById(id);
    }
}