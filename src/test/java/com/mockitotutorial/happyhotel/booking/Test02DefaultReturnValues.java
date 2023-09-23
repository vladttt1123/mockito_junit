package com.mockitotutorial.happyhotel.booking;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class Test02DefaultReturnValues {
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

        System.out.println("List Returned: " + roomServiceMock.getAvailableRooms());
        System.out.println("Object Returned: " + roomServiceMock.findAvailableRoomId(null));
        System.out.println("Primitive Returned: " + roomServiceMock.getRoomCount());
    }

    @Test
    void shouldCountAvailablePlaces(){
        //given
        int expected = 0;

        //when
        int actual = bookingService.getAvailablePlaceCount();
        //then

        assertEquals(expected, actual);
    }

}
