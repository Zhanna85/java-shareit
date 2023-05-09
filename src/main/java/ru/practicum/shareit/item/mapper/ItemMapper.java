package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfo;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    /*public static Item toItem(long id, long userId, ItemDto itemDto) {
        return new Item(
                id,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                userId,
                itemDto.getRequest()
        );
    }*/

    public static Item toNewItem(User user, ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(user);

        return item;
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static ItemInfo toGetItem(Item item, Booking lastBooking, Booking nextBooking) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setId(item.getId());
        itemInfo.setName(item.getName());
        itemInfo.setDescription(item.getDescription());
        itemInfo.setAvailable(item.getAvailable());
        if (lastBooking != null) {
            itemInfo.setLastBooking(new BookingDtoForItem(lastBooking.getId(),
                    lastBooking.getStart(), lastBooking.getEnd(), lastBooking.getBooker().getId()));
        }
        if (nextBooking != null) {
            itemInfo.setNextBooking(new BookingDtoForItem(nextBooking.getId(), nextBooking.getStart(),
                    nextBooking.getEnd(), nextBooking.getBooker().getId()));
        }

        return itemInfo;
    }
}