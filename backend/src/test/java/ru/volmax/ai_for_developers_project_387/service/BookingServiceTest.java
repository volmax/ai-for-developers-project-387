package ru.volmax.ai_for_developers_project_387.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import ru.volmax.ai_for_developers_project_387.data.BookingRequest;
import ru.volmax.ai_for_developers_project_387.entity.Booking;
import ru.volmax.ai_for_developers_project_387.entity.EventType;
import ru.volmax.ai_for_developers_project_387.repository.BookingRepository;
import ru.volmax.ai_for_developers_project_387.repository.EventTypeRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    private static final ZoneId TEST_TIMEZONE = ZoneId.of("Europe/Moscow");
    private static final Clock FIXED_CLOCK =
            Clock.fixed(
                    LocalDateTime.of(2026, 11, 16, 12, 0).atZone(TEST_TIMEZONE).toInstant(),
                    TEST_TIMEZONE);

    @Mock private BookingRepository bookingRepository;
    @Mock private EventTypeRepository eventTypeRepository;
    @Mock private SlotService slotService;

    private BookingService createService(EventType eventType) {
        return new BookingService(bookingRepository, eventTypeRepository, slotService, FIXED_CLOCK);
    }

    // --- create ---

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("should return created booking when request is valid")
        void createValidBookingShouldReturnCreatedBooking() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setGuestEmail("test@example.com");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenReturn(
                            Booking.testEntity()
                                    .id(1L)
                                    .eventTypeId("1")
                                    .eventTypeName("Консультация")
                                    .eventDurationMinutes(30)
                                    .slotStart(slotStart)
                                    .slotEnd(slotStart.plusMinutes(30))
                                    .guestName("Test Guest")
                                    .guestEmail("test@example.com")
                                    .createdAt(LocalDateTime.now(FIXED_CLOCK))
                                    .build());

            Booking booking = createService(eventType).create(request);

            assertNotNull(booking.getId());
            assertEquals("1", booking.getEventTypeId());
            assertEquals("Консультация", booking.getEventTypeName());
            assertEquals(30, booking.getEventDurationMinutes());
            assertEquals(slotStart, booking.getSlotStart());
            assertEquals(slotStart.plusMinutes(30), booking.getSlotEnd());
            assertEquals("Test Guest", booking.getGuestName());
            assertEquals("test@example.com", booking.getGuestEmail());

            verify(eventTypeRepository).findById(1L);
            verify(bookingRepository).findBySlotStartBeforeAndSlotEndForUpdate(any(), any());
            verify(bookingRepository).save(any());
            verifyNoMoreInteractions(eventTypeRepository, bookingRepository, slotService);
        }

        @Test
        @DisplayName("should save and return topic when provided")
        void createBookingWithTopicShouldSaveAndReturnTopic() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setTopic("Обсудить архитектуру проекта");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenReturn(
                            Booking.testEntity()
                                    .id(1L)
                                    .eventTypeId("1")
                                    .eventTypeName("Консультация")
                                    .eventDurationMinutes(30)
                                    .slotStart(slotStart)
                                    .slotEnd(slotStart.plusMinutes(30))
                                    .guestName("Test Guest")
                                    .topic("Обсудить архитектуру проекта")
                                    .createdAt(LocalDateTime.now(FIXED_CLOCK))
                                    .build());

            Booking booking = createService(eventType).create(request);

            assertEquals("Обсудить архитектуру проекта", booking.getTopic());
        }

        @Test
        @DisplayName("should normalize whitespace-only topic to null")
        void createBookingWithWhitespaceOnlyTopicShouldSaveNull() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setTopic("   ");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenAnswer(
                            invocation -> {
                                Booking saved = invocation.getArgument(0);
                                return Booking.testEntity()
                                        .id(1L)
                                        .eventTypeId(saved.getEventTypeId())
                                        .eventTypeName(saved.getEventTypeName())
                                        .eventDurationMinutes(saved.getEventDurationMinutes())
                                        .slotStart(saved.getSlotStart())
                                        .slotEnd(saved.getSlotEnd())
                                        .guestName(saved.getGuestName())
                                        .topic(saved.getTopic())
                                        .createdAt(saved.getCreatedAt())
                                        .build();
                            });

            Booking booking = createService(eventType).create(request);

            assertNull(booking.getTopic());
        }

        @Test
        @DisplayName("should normalize empty string topic to null")
        void createBookingWithEmptyTopicShouldSaveNull() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setTopic("");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenAnswer(
                            invocation -> {
                                Booking saved = invocation.getArgument(0);
                                return Booking.testEntity()
                                        .id(1L)
                                        .eventTypeId(saved.getEventTypeId())
                                        .eventTypeName(saved.getEventTypeName())
                                        .eventDurationMinutes(saved.getEventDurationMinutes())
                                        .slotStart(saved.getSlotStart())
                                        .slotEnd(saved.getSlotEnd())
                                        .guestName(saved.getGuestName())
                                        .topic(saved.getTopic())
                                        .createdAt(saved.getCreatedAt())
                                        .build();
                            });

            Booking booking = createService(eventType).create(request);

            assertNull(booking.getTopic());
        }

        @Test
        @DisplayName("should trim topic with leading and trailing whitespace")
        void createBookingWithSurroundingWhitespaceTopicShouldTrim() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setTopic("  Обсуждение релиза  ");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenAnswer(
                            invocation -> {
                                Booking saved = invocation.getArgument(0);
                                return Booking.testEntity()
                                        .id(1L)
                                        .eventTypeId(saved.getEventTypeId())
                                        .eventTypeName(saved.getEventTypeName())
                                        .eventDurationMinutes(saved.getEventDurationMinutes())
                                        .slotStart(saved.getSlotStart())
                                        .slotEnd(saved.getSlotEnd())
                                        .guestName(saved.getGuestName())
                                        .topic(saved.getTopic())
                                        .createdAt(saved.getCreatedAt())
                                        .build();
                            });

            Booking booking = createService(eventType).create(request);

            assertEquals("Обсуждение релиза", booking.getTopic());
        }

        @Test
        @DisplayName("should create booking without topic as before")
        void createBookingWithoutTopicShouldSaveNullTopic() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setGuestEmail("test@example.com");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            when(bookingRepository.save(any()))
                    .thenAnswer(
                            invocation -> {
                                Booking saved = invocation.getArgument(0);
                                return Booking.testEntity()
                                        .id(1L)
                                        .eventTypeId(saved.getEventTypeId())
                                        .eventTypeName(saved.getEventTypeName())
                                        .eventDurationMinutes(saved.getEventDurationMinutes())
                                        .slotStart(saved.getSlotStart())
                                        .slotEnd(saved.getSlotEnd())
                                        .guestName(saved.getGuestName())
                                        .topic(saved.getTopic())
                                        .createdAt(saved.getCreatedAt())
                                        .build();
                            });

            Booking booking = createService(eventType).create(request);

            assertNull(booking.getTopic());
        }

        @Test
        @DisplayName("should throw when booking is outside window")
        void createBookingOutsideWindowShouldThrow() {
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).minusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setGuestEmail("test@example.com");

            assertThrows(IllegalStateException.class, () -> createService(null).create(request));

            verifyNoMoreInteractions(eventTypeRepository, bookingRepository, slotService);
        }

        @Test
        @DisplayName("should throw when booking is too far in the future")
        void createBookingTooFarInFutureShouldThrow() {
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(20).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("1");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setGuestEmail("test@example.com");

            assertThrows(IllegalStateException.class, () -> createService(null).create(request));

            verifyNoMoreInteractions(eventTypeRepository, bookingRepository, slotService);
        }

        @Test
        @DisplayName("should throw when event type does not exist")
        void createBookingWithNonExistentEventTypeShouldThrow() {
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest request = new BookingRequest();
            request.setEventTypeId("999");
            request.setSlotStart(slotStart);
            request.setGuestName("Test Guest");
            request.setGuestEmail("test@example.com");

            assertThrows(IllegalStateException.class, () -> createService(null).create(request));

            verify(eventTypeRepository).findById(999L);
            verifyNoMoreInteractions(eventTypeRepository, bookingRepository, slotService);
        }

        @Test
        @DisplayName("should throw when slot is already booked")
        void createBookingWithDuplicateSlotShouldThrow() {
            EventType eventType = new EventType(1L, "Консультация", "30 min", 30);
            LocalDateTime slotStart = LocalDate.now(FIXED_CLOCK).plusDays(1).atTime(10, 0);
            BookingRequest firstRequest = new BookingRequest();
            firstRequest.setEventTypeId("1");
            firstRequest.setSlotStart(slotStart);
            firstRequest.setGuestName("First Guest");
            firstRequest.setGuestEmail("first@example.com");

            when(eventTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(eventType));
            when(bookingRepository.findBySlotStartBeforeAndSlotEndForUpdate(any(), any()))
                    .thenReturn(List.of());
            doAnswer(
                            invocation -> {
                                Booking booking = invocation.getArgument(0);
                                return Booking.testEntity()
                                        .id(1L)
                                        .eventTypeId(booking.getEventTypeId())
                                        .eventTypeName(booking.getEventTypeName())
                                        .eventDurationMinutes(booking.getEventDurationMinutes())
                                        .slotStart(booking.getSlotStart())
                                        .slotEnd(booking.getSlotEnd())
                                        .guestName(booking.getGuestName())
                                        .guestEmail(booking.getGuestEmail())
                                        .createdAt(LocalDateTime.now(FIXED_CLOCK))
                                        .build();
                            })
                    .when(bookingRepository)
                    .save(any());

            Booking firstBooking = createService(eventType).create(firstRequest);
            assertNotNull(firstBooking.getId());

            BookingRequest secondRequest = new BookingRequest();
            secondRequest.setEventTypeId("1");
            secondRequest.setSlotStart(slotStart);
            secondRequest.setGuestName("Second Guest");
            secondRequest.setGuestEmail("second@example.com");

            doThrow(new DataIntegrityViolationException("Duplicate key"))
                    .when(bookingRepository)
                    .save(any());

            assertThrows(
                    IllegalStateException.class,
                    () -> createService(eventType).create(secondRequest));

            verify(eventTypeRepository, times(2)).findById(1L);
            verify(bookingRepository, times(2))
                    .findBySlotStartBeforeAndSlotEndForUpdate(any(), any());
            verify(bookingRepository, times(2)).save(any());
            verifyNoMoreInteractions(eventTypeRepository, bookingRepository, slotService);
        }
    }

    // --- getUpcoming ---

    @Nested
    @DisplayName("getUpcoming")
    class GetUpcoming {

        @Test
        @DisplayName("should return future bookings from repository")
        void getUpcomingShouldReturnFutureBookings() {
            LocalDate today = LocalDate.now(FIXED_CLOCK);
            LocalDateTime futureSlotStart = today.plusDays(1).atTime(10, 0);

            Booking futureBooking =
                    Booking.testEntity()
                            .id(1L)
                            .eventTypeId("1")
                            .eventTypeName("Консультация")
                            .eventDurationMinutes(30)
                            .slotStart(futureSlotStart)
                            .slotEnd(futureSlotStart.plusMinutes(30))
                            .guestName("Future Guest")
                            .guestEmail("future@example.com")
                            .createdAt(LocalDateTime.now(FIXED_CLOCK))
                            .build();

            when(bookingRepository.findUpcomingBookings(any())).thenReturn(List.of(futureBooking));

            List<Booking> upcoming = createService(null).getUpcoming();

            assertNotNull(upcoming);
            assertEquals(1, upcoming.size());
            assertEquals("Future Guest", upcoming.get(0).getGuestName());

            verify(bookingRepository).findUpcomingBookings(any());
            verifyNoMoreInteractions(bookingRepository, eventTypeRepository, slotService);
        }
    }
}
