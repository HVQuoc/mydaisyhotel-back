package com.hoangquoc.mydaisyhotel.controller;

import com.hoangquoc.mydaisyhotel.exception.ResourceNotFoundException;
import com.hoangquoc.mydaisyhotel.exception.InvalidBookingRequestException;
import com.hoangquoc.mydaisyhotel.model.BookedRoom;
import com.hoangquoc.mydaisyhotel.model.Room;
import com.hoangquoc.mydaisyhotel.response.BookingResponse;
import com.hoangquoc.mydaisyhotel.response.RoomResponse;
import com.hoangquoc.mydaisyhotel.service.IBookingService;
import com.hoangquoc.mydaisyhotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bkResponse = getBookingResponse(booking);
            bookingResponses.add(bkResponse);
        }

        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedRoom booking = bookingService.findBookingByConfirmationCode(confirmationCode);
            BookingResponse bkResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bkResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest) {
        try {
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room has been booked successfully. Your confirmation code " + confirmationCode);
        } catch (InvalidBookingRequestException ex ) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getById(booking.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice()
        );
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(),
                roomResponse
        );
    }
}
