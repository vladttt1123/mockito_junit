package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class Test10ArgumentCaptors {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

    //initialize the field in the set up method

    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);

        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
        this.bookingRequestCaptor = ArgumentCaptor.forClass(BookingRequest.class);
    }

    @Test
    void should_PayCorrectPrice_When_InputOk() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, true);

        //When
        bookingService.makeBooking(bookingRequest);

        //Then
        verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());

        double capturedArgument = doubleCaptor.getValue();

        System.out.println(capturedArgument);

        assertEquals(400.0, capturedArgument);

    }

    @Test
    void should_PayCorrectPrice_When_MultipleCalls() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, true);

        BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 02), 2, true);
        List<Double> expectedValues = Arrays.asList(400.0, 100.0);

        //When
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        //Then
        verify(paymentServiceMock, times(2)).pay(any(),  doubleCaptor.capture());

        List<Double> capturedArguments = doubleCaptor.getAllValues();

        System.out.println(capturedArguments);

        assertEquals(expectedValues, capturedArguments);

    }


}
