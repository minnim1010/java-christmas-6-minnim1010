package christmas.domain.reservation;

import static christmas.common.exception.ErrorMessage.INVALID_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.base.ReservationDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
            assertThat(reservationDate.getYear()).isEqualTo(year);
            assertThat(reservationDate.getMonth()).isEqualTo(month);
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
}