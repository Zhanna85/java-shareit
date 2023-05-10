package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndItemOwnerId(Long id, Long ownerId);

    @Query("Select distinct b " +
            "From Booking AS b " +
            "join b.item AS i " +
            "Where b.id = ?1" +
            " and (b.booker.id = ?2 or i.owner.id = ?2)")
    Optional<Booking> findByIdAndBookerIdOrItemOwnerId(Long id, Long bookerId);

    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findByBookerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime currentMoment);

    List<Booking> findByBookerIdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime currentMoment);

    @Query("Select b " +
            "From Booking AS b " +
            "Where b.booker.id = ?1 " +
            "and b.start < ?2 " +
            "and b.end > ?2 " +
            "ORDER BY b.start ASC")
    List<Booking> findByBookerIdAndCurrentMomentBetweenStartAndEnd(Long bookerId, LocalDateTime currentMoment);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    @Query("Select b " +
            "From Booking AS b " +
            "Where b.booker.id = ?1 " +
            "and b.status in ('REJECTED', 'CANCELED') " +
            "ORDER BY b.start DESC")
    List<Booking> findByBookerIdAndStatusRejected(Long bookerId);

    List<Booking> findByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItemOwnerIdAndEndBeforeOrderByStartDesc(Long ownerId, LocalDateTime currentMoment);

    List<Booking> findByItemOwnerIdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime currentMoment);

    @Query("Select b " +
            "From Booking AS b " +
            "join b.item AS i " +
            "Where i.owner.id = ?1 " +
            "and b.start < ?2 " +
            "and b.end > ?2 " +
            "ORDER BY b.start DESC")
    List<Booking> findByItemOwnerIdAndCurrentMomentBetweenStartAndEnd(Long ownerId, LocalDateTime currentMoment);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status);

    @Query("Select b " +
            "From Booking AS b " +
            "join b.item AS i " +
            "Where i.owner.id = ?1 " +
            "and b.status in ('REJECTED', 'CANCELED') " +
            "ORDER BY b.start DESC")
    List<Booking> findByItemOwnerIdAndStatusRejected(Long ownerId);

    List<Booking> findByItemIdAndBookerIdAndStatusAndEndBefore(Long id, Long bookerId, BookingStatus status,
                                                                 LocalDateTime createdDate);

    @Query("Select b " +
            "From Booking AS b " +
            "Where b.item.id = ?1 " +
            "and (b.start >= ?2 and b.end <= ?3)"
    )
    List<Booking> getBookingDate(Long Id, LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartAsc(Long ownerId, BookingStatus status);

    List<Booking> findByItemIdAndStatusOrderByStartAsc(Long itemId, BookingStatus status);
}