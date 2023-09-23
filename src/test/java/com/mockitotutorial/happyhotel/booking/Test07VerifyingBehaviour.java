package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test07VerifyingBehaviour {

    /**
     * verify is used to verify whether certain mock were or weren't called as expected
     */

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    //initialize the field in the set up method

    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);
    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, true);

        //When
        bookingService.makeBooking(bookingRequest);

        //Then
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);

        verifyNoMoreInteractions(paymentServiceMock); // checks if any other methods from this mock were called

    }

    @Test
    void shouldNot_InvokePayment_When_NotPrepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, false);

        //When
        bookingService.makeBooking(bookingRequest);

        //Then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());

    }
}
