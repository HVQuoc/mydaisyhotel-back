package com.hoangquoc.mydaisyhotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name="check_in")
    private Locale checkInDate;
    @Column(name="check_out")
    private Locale checkOutDate;
    @Column(name="guest_full_name")
    private String guestFullName;
    @Column(name="guest_email")
    private String guestEmail;
    @Column(name="total_guest")
    private int totalNumOfGuest;
    @Column(name="children")
    private int numOfChildren;
    @Column(name="adults")
    private int numOfAdults;
    @Column(name="confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    public void calTotalNumOfGuest() {
        this.totalNumOfGuest = this.numOfChildren + this.numOfAdults;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        this.calTotalNumOfGuest();
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        this.calTotalNumOfGuest();
    }
}
