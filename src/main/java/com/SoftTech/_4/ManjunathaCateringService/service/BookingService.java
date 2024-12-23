package com.SoftTech._4.ManjunathaCateringService.service;

import com.SoftTech._4.ManjunathaCateringService.entity.BookingSlot;
import com.SoftTech._4.ManjunathaCateringService.entity.BookingStatus;
import com.SoftTech._4.ManjunathaCateringService.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingSlot createBooking(BookingSlot bookingSlot){
        bookingSlot.setStatus(BookingStatus.PENDING);
        bookingRepository.save(bookingSlot);
        return bookingSlot;
    }

    public List<BookingSlot> findAllBookingList() {
        List<BookingSlot> allBookingList = bookingRepository.findAll();
        //Collections.sort(allBookingList,(o1, o2) ->(int) (o2.getBookingId() - o1.getBookingId()));
       // List<BookingSlot> pendingOrders = allBookingList.stream().filter(booking->booking.getStatus()==BookingStatus.PENDING).sorted((o1, o2) -> Math.toIntExact(o2.getBookingId() - o1.getBookingId())).collect(Collectors.toList());
        //List<BookingSlot> pendingOrders = allBookingList.stream().filter(booking -> booking.getStatus() == BookingStatus.PENDING).collect(Collectors.toList());
//        Collections.sort(employees,(o1, o2) -> (int) (o1.getSalary()- o2.getSalary()));
        //return pendingOrders;

        return allBookingList;

    }
  //sorting data with perticular field like bookingdate, cusName, bookingid etc
    public List<BookingSlot> findBookingsWithSorting(String field){
        return bookingRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    public Page<BookingSlot> findBookingWithPagination(int offset, int pageSize){
        Page<BookingSlot> all = bookingRepository.findAll(PageRequest.of(offset, pageSize));
        return all;
    }
    public Page<BookingSlot> findBookingWithPaginationAndSorting(int offset, int pageSize, String field){
        Page<BookingSlot> all = bookingRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return all;
    }

    public BookingSlot updateBookingStatus(Long id,BookingStatus bookingStatus) {
        Optional<BookingSlot> optionalBookingSlot = bookingRepository.findById(id);

        if (optionalBookingSlot.isPresent()) {
            BookingSlot bookingSlot = optionalBookingSlot.get();
            bookingSlot.setStatus(bookingStatus); // Update the status
            return bookingRepository.save(bookingSlot); // Persist the changes and return the updated entity
        } else {
            throw new RuntimeException("Booking with id " + id + " not found");
        }
    }

    public Optional<BookingSlot> findByid(Long bookingId) {

        return bookingRepository.findById(bookingId);
    }

    public List<BookingSlot> findByMobileNo(String mobileNo) {
       return bookingRepository.findByMobileNo(mobileNo);
    }
}
