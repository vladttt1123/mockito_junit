package com.mockitotutorial.happyhotel.booking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.internal.MockedStaticImpl;
import org.mockito.junit.jupiter.MockitoExtension;


public class Test16FinalMethods {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        //given teach the mock to return list with 1 room when getAvailabelRooms Method is called
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 5)));
        int expected = 5;
        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

    }


}
