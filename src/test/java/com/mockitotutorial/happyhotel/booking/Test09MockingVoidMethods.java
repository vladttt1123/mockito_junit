package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class Test09MockingVoidMethods {
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
    void should_ThrowException_When_MailNotReady() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, false);

        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

        //When
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //Then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_NotThrowException_When_MailNotReady() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, false);

        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

        //do Nothing is default behaviour and can therefore be skipped
        doNothing().when(mailSenderMock).sendBookingConfirmation(any());

        //WhenbookingService.makeBooking(bookingRequest);

        //Then
        //no exception is thrown
    }
}
