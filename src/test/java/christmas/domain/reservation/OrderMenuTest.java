package christmas.domain.reservation;

import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static christmas.exception.ErrorMessage.INVALID_ORDER_TOTAL_COUNT_RANGE;
import static christmas.exception.ErrorMessage.ORDERED_ONLY_BEVERAGE;
import static christmas.fixture.ChristmasFixture.calculatePrice;
import static christmas.fixture.ChristmasFixture.createOrderMenuItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.base.Money;
import christmas.domain.constants.MenuItem;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("주문 메뉴")
class OrderMenuTest {

    @Nested
    class 생성_시 {

        static Stream<Arguments> getInvalidOrderMenuTotalCountTestArgument() {
            return Stream.of(
                    Arguments.of(List.of()),
                    Arguments.of(List.of(
                            createOrderMenuItem(MenuItem.BBQ_RIB, 5),
                            createOrderMenuItem(MenuItem.CHAMPAGNE, 6),
                            createOrderMenuItem(MenuItem.ZERO_COLA, 5),
                            createOrderMenuItem(MenuItem.CHRISTMAS_PASTA, 5)
                    ))
            );
        }

        @Test
        void 생성한다() {
            //given
            List<OrderMenuItem> orderMenuItems = List.of(
                    createOrderMenuItem(MenuItem.BBQ_RIB, 1),
                    createOrderMenuItem(MenuItem.CHAMPAGNE, 5),
                    createOrderMenuItem(MenuItem.ZERO_COLA, 7));
            //when
            OrderMenu orderMenu = OrderMenu.valueOf(orderMenuItems);
            //then
            assertThat(orderMenu.toString())
                    .contains("BBQ_RIB", "1", "CHAMPAGNE", "5", "ZERO_COLA", "7");
        }

        @Test
        void 음료만_주문했다면_예외가_발생한다() {
            //given
            List<OrderMenuItem> orderMenuItems = List.of(
                    createOrderMenuItem(MenuItem.CHAMPAGNE, 1),
                    createOrderMenuItem(MenuItem.RED_WINE, 1),
                    createOrderMenuItem(MenuItem.ZERO_COLA, 1));
            //when then
            assertThatThrownBy(() -> OrderMenu.valueOf(orderMenuItems))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ORDERED_ONLY_BEVERAGE.getMessage());
        }

        @MethodSource("getInvalidOrderMenuTotalCountTestArgument")
        @ParameterizedTest
        void 총메뉴개수가_1개이상_20개이하가_아니라면_예외가_발생한다(List<OrderMenuItem> orderMenuItems) {
            //given
            //when then
            assertThatThrownBy(() -> OrderMenu.valueOf(orderMenuItems))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER_TOTAL_COUNT_RANGE.getMessage());
        }

        @Test
        void 중복된메뉴가_있다면_예외가_발생한다() {
            //given
            List<OrderMenuItem> orderMenuItems = List.of(
                    createOrderMenuItem(MenuItem.CHOCO_CAKE, 1),
                    createOrderMenuItem(MenuItem.CHOCO_CAKE, 1),
                    createOrderMenuItem(MenuItem.ZERO_COLA, 1));
            //when then
            assertThatThrownBy(() -> OrderMenu.valueOf(orderMenuItems))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }
    }

    @Test
    void 총메뉴가격을_계산한다() {
        //given
        List<OrderMenuItem> orderMenuItems = List.of(
                createOrderMenuItem(MenuItem.BBQ_RIB, 1),
                createOrderMenuItem(MenuItem.CHAMPAGNE, 5),
                createOrderMenuItem(MenuItem.ZERO_COLA, 7));
        OrderMenu orderMenu = OrderMenu.valueOf(orderMenuItems);
        //when
        Money money = orderMenu.calculateTotalPrice();
        //then
        Money expected = calculatePrice(
                List.of(MenuItem.BBQ_RIB, MenuItem.CHAMPAGNE, MenuItem.ZERO_COLA),
                List.of(1, 5, 7));
        assertThat(money).isEqualTo(expected);
    }
}