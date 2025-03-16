package com.medirect.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;
    public DatesDto datesDto;
    public String additionalneeds;
}
