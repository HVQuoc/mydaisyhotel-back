package com.hoangquoc.mydaisyhotel.service;

import com.hoangquoc.mydaisyhotel.model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    @Override
    public void cancelBooking(Long bookingId) {

    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        return null;
    }

    @Override
    public BookedRoom findBookingByConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return null;
    }
//    public List<BookedRoom> getAllBookingsByRoomId(Long id) {
//        return null;
//    }

}
