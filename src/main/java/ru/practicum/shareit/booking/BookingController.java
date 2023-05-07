package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.Valid;
import java.util.Collection;

import static ru.practicum.shareit.utils.Message.*;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    private void validateUserId(Long userId) {
        if (userId == null) {
            log.error(INVALID_USER_ID.getMessage(), userId);
            throw new NotFoundException(INVALID_USER_ID.getMessage() + userId);
        }
    }

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody BookingDto bookingDto) {
        validateUserId(userId);
        log.info(ADD_MODEL.getMessage(), bookingDto);
        return bookingService.addNewBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @PathVariable Long bookingId,
                             @RequestParam Boolean approved) {
        validateUserId(userId);
        log.info(UPDATED_MODEL.getMessage(), bookingId);
        return bookingService.updateBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long bookingId) {
        validateUserId(userId);
        log.info(REQUEST_BY_ID.getMessage(), bookingId);
        return bookingService.getBookingByID(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDto> getAllBookingsByIdUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                         @RequestParam(defaultValue = "ALL") String state) {
        validateUserId(userId);
        log.info(REQUEST_ALL.getMessage());
        return bookingService.getAllBookingByUserId(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingAllItemByIdUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                               @RequestParam(defaultValue = "ALL") String state) {
        validateUserId(userId);
        log.info(REQUEST_ALL.getMessage());
        return bookingService.getAllBookingByItemUser(userId, state);
    }
}