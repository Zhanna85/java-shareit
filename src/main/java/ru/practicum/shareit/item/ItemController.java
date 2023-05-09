package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfo;

import javax.validation.Valid;
import java.util.Collection;

import static ru.practicum.shareit.utils.Message.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    private void validateUserId(Long userId) {
        if (userId == null) {
            log.error(INVALID_USER_ID.getMessage());
            throw new NotFoundException(INVALID_USER_ID.getMessage());
        }
    }

    @GetMapping
    public Collection<ItemDto> getAllItemsByIdUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        validateUserId(userId);
        log.info(REQUEST_ALL.getMessage());
        return itemService.getAllItemsByIdUser(userId);
    }

    @GetMapping("/{itemId}")
    public ItemInfo getItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @PathVariable Long itemId) {
        validateUserId(userId);
        log.info(REQUEST_BY_ID.getMessage(), itemId);
        return itemService.getItem(userId, itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @RequestParam String text) {
        validateUserId(userId);
        log.info(SEARCH.getMessage());
        return itemService.search(userId,text);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody ItemDto itemDto) {
        validateUserId(userId);
        log.info(ADD_MODEL.getMessage(), itemDto);
        return itemService.create(userId,itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable Long itemId,
                          @RequestBody ItemDto itemDto) {
        validateUserId(userId);
        log.info(UPDATED_MODEL.getMessage(), itemId, itemDto);
        return itemService.update(userId, itemId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long itemId,
                                         @Valid @RequestBody CommentDto commentDto) {
        validateUserId(userId);
        log.info(ADD_MODEL.getMessage(), commentDto);
        return itemService.saveComment(userId, itemId, commentDto);
    }
}