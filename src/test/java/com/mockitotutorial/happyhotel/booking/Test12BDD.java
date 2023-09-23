package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Test12BDD {

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

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        //given teach the mock to return list with 1 room when getAvailabelRooms Method is called
        given(this.roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room 1", 5)));
        int expected = 5;
        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, true);

        //When
        bookingService.makeBooking(bookingRequest);

        //Then
        then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);

        verifyNoMoreInteractions(paymentServiceMock); // checks if any other methods from this mock were called

    }


}
