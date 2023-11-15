package christmas.domain.reservation;

import static christmas.common.exception.ErrorMessage.INVALID_DATE;
import static christmas.fixture.ChristmasFixture.createReservationDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("예약 날짜")
class ReservationDateTest {

    @Nested
    class 생성_시 {

        @Test
        void 생성한다() {
            //given
            int year = 2023;
            int month = 12;
            int day = 31;
            //when
            ReservationDate reservationDate = ReservationDate.valueOf(year, month, day);
            //then
            assertThat(reservationDate.getDay()).isEqualTo(day);
        }

        @ValueSource(ints = {0, 32})
        @ParameterizedTest
        void 예약일이_12월의_최소최대일과_맞지않는다면_예외가_발생한다(int day) {
            //given
            int year = 2023;
            int month = 12;
            //when then
            assertThatThrownBy(() -> ReservationDate.valueOf(year, month, day))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_DATE.getMessage());
        }
    }

    @CsvSource({
            "1, 25, 24",
            "10, 1, 9",
            "10, 10, 0",
    })
    @ParameterizedTest
    void 예약일이_특정일에서_얼마나_떨어져있는지_계산한다(int reservationDay, int targetDay, int gap) {
        //given
        ReservationDate reservationDate = ReservationDate.valueOf(2023, 12, reservationDay);
        LocalDate targetDate = LocalDate.of(2023, 12, targetDay);
        //when
        int remainingDays = reservationDate.getDaysBetween(targetDate);
        //then
        assertThat(remainingDays).isEqualTo(gap);
    }

    @Nested
    class 주어진기간에_예약일이_포함되는지_판별한다 {

        static Stream<Arguments> getReservationDatePeriodTestArgument() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2023, 11, 17), LocalDate.of(2023, 11, 20),
                            createReservationDate(2023, 11, 15), false),
                    Arguments.of(LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 20),
                            createReservationDate(2023, 11, 15), false),
                    Arguments.of(LocalDate.of(2023, 11, 15), LocalDate.of(2023, 11, 20),
                            createReservationDate(2023, 11, 17), true),
                    Arguments.of(LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 20),
                            createReservationDate(2023, 11, 20), true)
            );
        }

        @MethodSource("getReservationDatePeriodTestArgument")
        @ParameterizedTest
        void 예약일이_기간에_포함되는지_여부를_반환한다(LocalDate startDate, LocalDate endDate, ReservationDate reservationDate,
                                     boolean expected) {
            //given
            //when
            boolean result = reservationDate.isBetween(startDate, endDate);
            //then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        void 시작일이_마지막일_후라면_예외를_반환한다() {
            //given
            LocalDate startDate = LocalDate.of(2023, 11, 20);
            LocalDate endDate = LocalDate.of(2023, 11, 15);
            ReservationDate reservationDate = ReservationDate.valueOf(2023, 11, 17);
            //when and then
            assertThatThrownBy(() -> reservationDate.isBetween(startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 예약일이_주말인지_판별한다 {

        static Stream<Arguments> getReservationDateWeekendTestArgument() {
            return Stream.of(
                    Arguments.of(createReservationDate(2023, 11, 20), false),
                    Arguments.of(createReservationDate(2023, 11, 17), true),
                    Arguments.of(createReservationDate(2023, 11, 18), true)
            );
        }

        @MethodSource("getReservationDateWeekendTestArgument")
        @ParameterizedTest
        void 예약일이_주말인지_여부를_반환한다(ReservationDate reservationDate, boolean expected) {
            // Given
            // When
            boolean result = reservationDate.isWeekend();
            // Then
            assertThat(result).isEqualTo(expected);
        }
    }
}