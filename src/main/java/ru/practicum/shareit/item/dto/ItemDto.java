package ru.practicum.shareit.item.dto;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
public class ItemDto {
    private long id; // уникальный идентификатор вещи;
    @NotBlank
    private String name; // краткое название;
    @NotBlank
    private String description; // развёрнутое описание;
    @NotBlank
    private boolean available; // статус о том, доступна или нет вещь для аренды;
    private String request; // ссылка на соответствующий запрос (заполняется только если вещь создана по запросу).
}