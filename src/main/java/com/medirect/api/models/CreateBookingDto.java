package com.medirect.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookingDto {
    public int bookingid;
    public BookingDto booking;
}
