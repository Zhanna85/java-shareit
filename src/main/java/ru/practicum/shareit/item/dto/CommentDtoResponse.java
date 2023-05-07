package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CommentDtoResponse {

    private long id; // уникальный идентификатор комментария;
    private String text; // содержимое комментария;
    private String authorName; // автор комментария;
    private LocalDateTime created; // дата создания комментария.
}