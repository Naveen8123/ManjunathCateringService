package com.SoftTech._4.ManjunathaCateringService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_slot")
public class BookingSlot {

    @Id
    @SequenceGenerator(name = "booking_slot_seq", sequenceName = "booking_slot_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_slot_seq")
    private Long bookingId;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    private String customerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNo;

    @NotBlank(message = "Address is required")
    @Column(length = 500)
    private String address;

    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
