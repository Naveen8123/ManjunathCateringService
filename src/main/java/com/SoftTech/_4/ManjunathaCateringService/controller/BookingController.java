package com.SoftTech._4.ManjunathaCateringService.controller;

import com.SoftTech._4.ManjunathaCateringService.entity.APIResponse;
import com.SoftTech._4.ManjunathaCateringService.entity.BookingSlot;
import com.SoftTech._4.ManjunathaCateringService.entity.BookingStatus;
import com.SoftTech._4.ManjunathaCateringService.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookingSlots")
    public ResponseEntity<BookingSlot> createBooking(@RequestBody @Valid BookingSlot bookingSlot) {
        BookingSlot booking = bookingService.createBooking(bookingSlot);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<BookingSlot> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status) {
        try {
            BookingSlot updatedBooking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    @GetMapping("/allBookingList")
//    public List<BookingSlot> getPendingBookings() {
//        return bookingService.findAllBookingList();
//    }
// using pagination
    @GetMapping("/allBookingList")
    public APIResponse<List<BookingSlot>> getPendingBookings() {
        List<BookingSlot> allBookingList = bookingService.findAllBookingList();
        return new APIResponse<>(allBookingList.size(),allBookingList);
    }
//using sorting
    @GetMapping("/allBookingWithSort/{field}")
    public APIResponse<List<BookingSlot>> getAllBookingWithSort(@PathVariable String field) {
        List<BookingSlot> allBookingList = bookingService.findBookingsWithSorting(field);
        return new APIResponse<>(allBookingList.size(),allBookingList);
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public APIResponse<Page<BookingSlot>> getAllBookingWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<BookingSlot> allBookingList = bookingService.findBookingWithPagination(offset, pageSize);
        return new APIResponse<>(allBookingList.getSize(), allBookingList);
    }

    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    public APIResponse<Page<BookingSlot>> getAllBookingWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<BookingSlot> allBookingList = bookingService.findBookingWithPaginationAndSorting(offset, pageSize, field);
        return new APIResponse<>(allBookingList.getSize(), allBookingList);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingSlot> findById(@PathVariable Long bookingId) {
        return bookingService.findByid(bookingId)
                .map(bookingSlot -> new ResponseEntity<>(bookingSlot, HttpStatus.OK)) // If found, return 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // If not found, return 404 Not Found
    }

    @GetMapping("mobile/{mobileNo}")
    public ResponseEntity<List<BookingSlot>> findByMobileNo(@PathVariable String mobileNo) {
        List<BookingSlot> byMobileNo = bookingService.findByMobileNo(mobileNo);
        return new ResponseEntity<>(byMobileNo,HttpStatus.OK);
        // If not found, return 404 Not Found
    }


}
