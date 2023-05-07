package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //User saveUser(User user);

    User saveUserById(long id, User user);

    @Query("SELECT u FROM User WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);

    //User findUserById(long id);

    //List<User> findAllUser();

    //void deleteByUserId(long id);
}