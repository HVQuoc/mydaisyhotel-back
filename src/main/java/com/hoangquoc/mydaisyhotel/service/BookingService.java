package com.hoangquoc.mydaisyhotel.service;

import com.hoangquoc.mydaisyhotel.exception.InvalidBookingRequestException;
import com.hoangquoc.mydaisyhotel.model.BookedRoom;
import com.hoangquoc.mydaisyhotel.model.Room;
import com.hoangquoc.mydaisyhotel.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date.");
        }
        Room room = roomService.getById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean isRoomAvailable = isRoomAvailable(bookingRequest, existingBookings);

        if (isRoomAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, this room is not available for the selected date.");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoom findBookingByConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    private boolean isRoomAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream().noneMatch(existingBooking ->

                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckInDate().equals(bookingRequest.getCheckOutDate()))

        );
    }

}
