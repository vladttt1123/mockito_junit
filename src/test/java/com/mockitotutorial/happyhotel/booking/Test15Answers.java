package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.internal.MockedStaticImpl;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Test15Answers {

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
    void should_calculate_CorrectPrice() {
        try(MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)){

            // given
            BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020,01, 01)
                    ,LocalDate.of(2020, 01, 05), 2, false);

            double expected = 400.0 * 0.8;

            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenAnswer(inv -> (double) inv.getArgument(0) * 0.8);

            //When
            double actual = bookingService.calculatePriceEuro(bookingRequest);

            //Then
            assertEquals(expected, actual);



        }

    }

}
