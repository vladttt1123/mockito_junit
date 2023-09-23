package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class Test05ThrowingExceptions {

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
    void should_ThrowException_When_NoRoomAvailable() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01, 01)
                ,LocalDate.of(2020, 01, 05), 2, false);

        when(roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);

        //When
        Executable executable = () ->bookingService.makeBooking(bookingRequest);

        //Then
        assertThrows(BusinessException.class, executable);
    }
}
