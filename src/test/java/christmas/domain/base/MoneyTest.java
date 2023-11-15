package christmas.domain.base;

import static christmas.common.exception.ErrorMessage.INVALID_MONEY_RANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("금액")
class MoneyTest {

    @Test
    void 두_금액을_더한_결과를_계산한다() {
        //given
        Money money1 = Money.valueOf(500);
        Money money2 = Money.valueOf(700);
        //when
        Money result = money1.add(money2);
        //then
        assertThat(result.getValue()).isEqualTo(1200);
    }

    @CsvSource({
            "1000, 300, 700",
            "400, 500, 0"
    })
    @ParameterizedTest
    void 두금액을_뺀_결과를_계산한다(int value1, int value2, int expected) {
        //given
        Money money1 = Money.valueOf(value1);
        Money money2 = Money.valueOf(value2);
        //when
        Money result = money1.sub(money2);
        //then
        assertThat(result.getValue()).isEqualTo(expected);
    }

    @Test
    void 금액을_주어진횟수만큼_곱한_결과를_계산한다() {
        //given
        Money money = Money.valueOf(500);
        //when
        Money result = money.times(3);
        //then
        assertThat(result.getValue()).isEqualTo(1500);
    }

    @Test
    void 주어진금액이_크거나_같은지_판별한다() {
        //given
        Money money1 = Money.valueOf(1000);
        Money money2 = Money.valueOf(500);
        //when
        boolean result = money1.isGreaterOrEqual(money2);
        //then
        assertThat(result).isTrue();
    }

    @Test
    void 주어진_금액이_0인지_확인한다() {
        //given
        Money money = Money.valueOf(0);
        //when
        boolean result = money.isZero();
        //then
        assertThat(result).isTrue();
    }

    @Nested
    class 생성_시 {

        @Test
        void 생성된다() {
            //given
            int value = 10_000;
            //when
            Money money = Money.valueOf(value);
            //then
            assertThat(money.getValue()).isEqualTo(value);
        }

        @ValueSource(ints = {-1, 2_000_000_001})
        @ParameterizedTest
        void 금액이_0미만이거나_20억초과라면_예외가_발생한다(int value) {
            //given
            //when
            assertThatThrownBy(() -> Money.valueOf(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_MONEY_RANGE.getMessage());
        }
    }
}