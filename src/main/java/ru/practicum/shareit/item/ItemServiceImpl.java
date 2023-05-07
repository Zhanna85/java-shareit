package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.mapper.CommentMapper.mapToCommentDtoResponse;
import static ru.practicum.shareit.item.mapper.CommentMapper.mapToNewComment;
import static ru.practicum.shareit.item.mapper.ItemMapper.toItemDto;
import static ru.practicum.shareit.item.mapper.ItemMapper.toNewItem;
import static ru.practicum.shareit.utils.Message.MODEL_NOT_FOUND;
import static ru.practicum.shareit.utils.Message.NAME_MAY_NOT_CONTAIN_SPACES;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    //private final ItemStorage itemStorage;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    private User validUser(Long id) {
        return userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + id));
    }

    private void dataValidator(String name) {
        if (name.isEmpty()) {
            log.error(NAME_MAY_NOT_CONTAIN_SPACES.getMessage());
            throw new ValidationException(NAME_MAY_NOT_CONTAIN_SPACES.getMessage());
        }
    }

    @Override
    public Collection<ItemDto> getAllItemsByIdUser(long userId) {
        validUser(userId);
       /* return itemStorage.getAllItemsByIdUser(userId);*/
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper ::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(long userId, long itemId) {
       /* return itemStorage.getItemByID(userId, itemId);*/
        validUser(userId);
        return itemRepository.findById(itemId)
                .map(ItemMapper ::toItemDto)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + itemId));
    }

    @Override
    public Collection<ItemDto> search(long userId, String text) {
        /*return itemStorage.searchItem(userId, text);*/
        validUser(userId);
        if (text.isEmpty()) {
            return Collections.emptyList();
        }

        return itemRepository.search(text).stream()
                .map(ItemMapper ::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        User user = validUser(userId);
        dataValidator(itemDto.getName());
        Item item = itemRepository.save(toNewItem(user, itemDto));
        return toItemDto(item);
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        User user = validUser(userId);
        Item item = itemRepository.findByIdAndOwnerId(itemId, userId)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + itemId));
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        Boolean available = itemDto.getAvailable();

        if (description != null) {
            item.setDescription(description);
        }
        if (name != null) {
            dataValidator(name);
            item.setName(name);
        }
        if (available != null) {
            item.setAvailable(available);
        }

        return toItemDto(itemRepository.save(item));
        /*return itemStorage.updateItem(userId, itemId, itemDto);*/
    }

    @Override
    public CommentDtoResponse saveComment(Long userId, Long itemId, CommentDto commentDto) {
        User user = validUser(userId);
        Item item = bookingRepository.findItemByIdAndBookerIdAndStatusAPPROVEDAndEndBefore(itemId, userId,
                commentDto.getCreated()).orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + itemId));
        Comment comment = commentRepository.save(mapToNewComment(user, item, commentDto));

        return mapToCommentDtoResponse(comment);
    }
}