package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getAllItemsByIdUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        return null;
    }

    @GetMapping("/{itemId}")
    public Collection<ItemDto> getItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @PathVariable long itemId) {
        return null;
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @RequestParam(value = "text", required = true) String text) {
        return null;
    }
    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemDto itemDto) {
        return null;
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId,
                          @RequestBody ItemDto itemDto) {

        return null;
    }
}