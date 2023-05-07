package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingStatus.WAITING;

/**
 * TODO Sprint add-bookings.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;  // уникальный идентификатор бронирования;

    @FutureOrPresent
    private LocalDateTime start; // дата и время начала бронирования;

    @FutureOrPresent
    private LocalDateTime end; // дата и время конца бронирования;
    private BookingStatus status = WAITING; // статус бронирования.

    @NotBlank
    private Long itemId; // вещь, которую пользователь бронирует;
    private Long bookerId; // пользователь, который осуществляет бронирование;
    private String itemName;
}
