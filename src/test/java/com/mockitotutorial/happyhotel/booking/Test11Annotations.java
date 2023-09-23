package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Test11Annotations {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Captor
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

    //initialize the field in the set up method



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
