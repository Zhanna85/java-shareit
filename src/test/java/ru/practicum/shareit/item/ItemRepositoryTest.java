package ru.practicum.shareit.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private UserRepository userRepository;
    private PageRequest page;

    @BeforeEach
    void setUp() {
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

        userRepository.save(user);
        repository.save(item);
        repository.save(item2);

        int from = 0;
        int size = 10;
        page = PageRequest.of(from > 0 ? from / size : 0, size);
    }

    @Test
    void searchByTextThenReturnedItem() {
        String text = "краска";

        List<Item> items = repository.search(text, page).toList();

        assertEquals(1, items.size(), "Размер списка не равен 1");
        assertEquals("Краска для обуви", items.get(0).getName(), "Значения не равны");
    }

    @Test
    void searchByTextThenReturnedEmptyList() {
        String text = "дрель";

        List<Item> items = repository.search(text, page).toList();

        assertTrue(items.isEmpty());
    }

    @AfterEach
    void deleteAll() {
        repository.deleteAll();
        userRepository.deleteAll();
    }
}