package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    private PageRequest page;

    @BeforeEach
    void before() {
        User user = new User();
        user.setName("user");
        user.setEmail("user@user.com");

        Item item = new Item();
        item.setName("Щётка для обуви");
        item.setDescription("Стандартная щётка для обуви");
        item.setAvailable(true);
        item.setOwner(user);

        Item item2 = new Item();
        item2.setName("Краска для обуви");
        item2.setDescription("Стандартная краска для обуви");
        item2.setAvailable(true);
        item2.setOwner(user);

        User userBooker = new User();
        userBooker.setName("userBooker");
        userBooker.setEmail("userBooker@user.com");

        userRepository.save(user);
        itemRepository.save(item);
        itemRepository.save(item2);
        userRepository.save(userBooker);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(2));
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item2);
        booking.setBooker(userBooker);

        repository.save(booking);

        int from = 0;
        int size = 10;
        page = PageRequest.of(from > 0 ? from / size : 0, size);
    }
    @Test
    void findByIdAndBookerIdOrItemOwnerId() {
        Optional<Booking> booking = repository.findByIdAndBookerIdOrItemOwnerId(1L, 2L);

        assertTrue(booking.isPresent());
    }

    @Test
    void findByIdAndInvalidBookerIdOrItemOwnerIdThenReturnedEmptyList() {
        Optional<Booking> booking = repository.findByIdAndBookerIdOrItemOwnerId(1L, 3L);

        assertTrue(booking.isEmpty());
    }

    @Test
    void findByBookerIdAndCurrentMomentBetweenStartAndEnd() {
        List<Booking> bookingList = repository.findByBookerIdAndCurrentMomentBetweenStartAndEnd(2L,
                LocalDateTime.now(), page).getContent();

        assertEquals(1, bookingList.size(), "Размер списка не равен 1");
        assertEquals(1L, bookingList.get(0).getId(), "Значения не равны");
    }

    @Test
    void findByInvalidBookerIdAndCurrentMomentBetweenStartAndEndThenReturnedEmptyList() {
        List<Booking> bookingList = repository.findByBookerIdAndCurrentMomentBetweenStartAndEnd(1L,
                LocalDateTime.now(), page).getContent();

        assertTrue(bookingList.isEmpty());
    }

    @Test
    void findByItemOwnerIdAndCurrentMomentBetweenStartAndEnd() {
        List<Booking> bookingList = repository.findByItemOwnerIdAndCurrentMomentBetweenStartAndEnd(1L,
                LocalDateTime.now(), page).getContent();

        assertEquals(1, bookingList.size(), "Размер списка не равен 1");
        assertEquals(1L, bookingList.get(0).getId(), "Значения не равны");
    }

    @Test
    void findByItemOwnerIdAndCurrentMomentBetweenStartAndEndThenReturnedEmptyList() {
        List<Booking> bookingList = repository.findByItemOwnerIdAndCurrentMomentBetweenStartAndEnd(2L,
                LocalDateTime.now(), page).getContent();

        assertTrue(bookingList.isEmpty());
    }

    @Test
    void getBookingDateThenReturnedList() {
        List<Booking> bookingList = repository.getBookingDate(2L,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(3));

        assertEquals(1, bookingList.size(), "Размер списка не равен 1");
        assertEquals(1L, bookingList.get(0).getId(), "Значения не равны");
    }
}