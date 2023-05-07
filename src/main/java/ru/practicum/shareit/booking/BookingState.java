package ru.practicum.shareit.booking;

public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState valueOfIgnoreCase(String name) {
        return BookingState.valueOf(name.toUpperCase());
    }
}