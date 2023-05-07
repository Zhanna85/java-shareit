package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingState.valueOfIgnoreCase;
import static ru.practicum.shareit.booking.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.BookingStatus.REJECTED;
import static ru.practicum.shareit.utils.Message.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private User validUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + userId));
    }

    private Item validItemById(Long itemId) {
         return itemRepository.findById(itemId)
                 .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + itemId));
    }

    private void validStartEndEndDate(BookingDto bookingDto) {
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ValidationException(INVALID_DATE.getMessage());
        }
        if (bookingDto.getEnd().isEqual(bookingDto.getStart())) {
            throw new ValidationException(INVALID_DATE.getMessage());
        }
    }

    @Override
    public BookingDto addNewBooking(Long userId, BookingDto bookingDto) {
        User user = validUserById(userId);
        Item item = validItemById(bookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new ValidationException(NOT_AVAILABLE.getMessage());
        }
        validStartEndEndDate(bookingDto);
        Booking booking = bookingRepository.save(BookingMapper.mapToBooking(user, item, bookingDto));
        log.info(ADD_MODEL.getMessage(), booking);
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public BookingDto updateBooking(Long userId, Long bookingId, Boolean approved) {
        bookingRepository.findByIdAndItemOwnerId(bookingId, userId)
                .orElseThrow(() -> new ValidationException(INVALID_USER_REQUEST_APPROVED.getMessage()));
        BookingStatus status;
        if (approved) {
            status = APPROVED;
        } else {
            status = REJECTED;
        }

        return BookingMapper.mapToBookingDto(bookingRepository.saveById(status, bookingId));
    }

    @Override
    public BookingDto getBookingByID(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findByIdAndBookerIdOrItemOwnerId(userId, bookingId)
                .orElseThrow(() -> new NotFoundException(MODEL_NOT_FOUND.getMessage() + bookingId));
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingByUserId(Long userId, String state) {
        validUserById(userId);
        List<Booking> bookingList;
        LocalDateTime currentMoment = LocalDateTime.now();
        BookingState status = valueOfIgnoreCase(state);

        switch (status) {
            case ALL:
                bookingList = bookingRepository.findByBookerIdOrderByStartDesc(userId);
                break;
            case PAST:
                bookingList = bookingRepository.findByBookerIdAndEndBeforeOrderByStartDesc(userId, currentMoment);
                break;
            case FUTURE:
                bookingList = bookingRepository.findByBookerIdAndStartAfterOrderByStartDesc(userId, currentMoment);
                break;
            case CURRENT:
                bookingList = bookingRepository.findByBookerIdAndCurrentMomentBetweenStartAndEnd(userId, currentMoment);
                break;
            case WAITING:
                bookingList = bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, status);
                break;
            case REJECTED:
                bookingList = bookingRepository.findByBookerIdAndStatusRejected(userId);
                break;
            default:
                throw new ValidationException(UNKNOWN_STATE.getMessage());
        }
        return bookingList.stream()
                .map(BookingMapper :: mapToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingByItemUser(Long userId, String state) {
        validUserById(userId);
        List<Booking> bookingList;
        LocalDateTime currentMoment = LocalDateTime.now();
        BookingState status = valueOfIgnoreCase(state);

        switch (status) {
            case ALL:
                bookingList = bookingRepository.findByItemOwnerIdOrderByStartDesc(userId);
                break;
            case PAST:
                bookingList = bookingRepository.findByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, currentMoment);
                break;
            case FUTURE:
                bookingList = bookingRepository.findByItemOwnerIdAndStartAfterOrderByStartDesc(userId, currentMoment);
                break;
            case CURRENT:
                bookingList = bookingRepository.findByItemOwnerIdAndCurrentMomentBetweenStartAndEnd(userId, currentMoment);
                break;
            case WAITING:
                bookingList = bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(userId, status);
                break;
            case REJECTED:
                bookingList = bookingRepository.findByItemOwnerIdAndStatusRejected(userId);
                break;
            default:
                throw new ValidationException(UNKNOWN_STATE.getMessage());
        }
        return bookingList.stream()
                .map(BookingMapper :: mapToBookingDto)
                .collect(Collectors.toList());
    }
}
