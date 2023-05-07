package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ItemInfo {

    private Long id; // уникальный идентификатор вещи;
    private String name; // краткое название;
    private String description; // развёрнутое описание;
    private Boolean available; // статус о том, доступна или нет вещь для аренды;
    private LocalDateTime lastBooking; // последнее бронирование;
    private LocalDateTime nextBooking; // ближайшее следующее бронирование;
    private Set<CommentDtoResponse> comments = new HashSet<>();
}
