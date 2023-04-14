package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;

import static ru.practicum.shareit.utils.Message.NAME_MAY_NOT_CONTAIN_SPACES;

@Service
@Slf4j
public class ItemServiceImpl {
    /*// проверка на пробел
     if (item.getName().contains(" ")){
        log.error(NAME_MAY_NOT_CONTAIN_SPACES.getMessage());
        throw new ValidationException(NAME_MAY_NOT_CONTAIN_SPACES.getMessage());
    }*/
}