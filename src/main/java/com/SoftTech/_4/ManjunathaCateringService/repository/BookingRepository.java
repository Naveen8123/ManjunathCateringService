package com.SoftTech._4.ManjunathaCateringService.repository;

import com.SoftTech._4.ManjunathaCateringService.entity.BookingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingSlot,Long> {
    List<BookingSlot> findByMobileNo(String mobileNo);
}
