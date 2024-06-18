package com.hoangquoc.mydaisyhotel.service;

import com.hoangquoc.mydaisyhotel.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    void cancelBooking(Long bookingId);
    String saveBooking(Long roomId, BookedRoom bookingRequest);
    BookedRoom findBookingByConfirmationCode(String confirmationCode);
    List<BookedRoom> getAllBookings();
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);
    List<BookedRoom> getBookingsByUserEmail(String email);
}
