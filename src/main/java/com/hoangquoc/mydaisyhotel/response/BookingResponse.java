package com.hoangquoc.mydaisyhotel.response;

import com.hoangquoc.mydaisyhotel.model.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Locale checkInDate;
    private Locale checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int totalNumOfGuest;
    private int numOfChildren;
    private int numOfAdults;
    private String bookingConfirmationCode;
    private RoomResponse room;

    public BookingResponse(Long bookingId, Locale checkInDate, Locale checkOutDate, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
