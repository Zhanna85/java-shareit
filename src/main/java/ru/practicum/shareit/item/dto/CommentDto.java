package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CommentDto {

    @NotBlank
    private String text; // содержимое комментария;

    @JsonIgnore
    private LocalDateTime created = LocalDateTime.now();
}