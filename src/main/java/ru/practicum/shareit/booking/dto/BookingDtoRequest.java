package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingStatus.WAITING;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoRequest {

    @NotNull
    private Long itemId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime start; // дата и время начала бронирования;

    @NotNull
    @FutureOrPresent
    private LocalDateTime end; // дата и время конца бронирования;
    private final BookingStatus status = WAITING; // статус бронирования.
}