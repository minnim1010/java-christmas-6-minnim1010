package christmas.domain.reservation;

import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.menu.OrderMenuItem;
import christmas.domain.menu.constants.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주문 메뉴 항목")
class OrderMenuItemTest {

    @Nested
    class 생성_시 {

        @Test
        void 생성한다() {
            //given
            String menuItemName = "양송이수프";
            int count = 1;
            //when
            OrderMenuItem orderMenuItem = OrderMenuItem.valueOf(menuItemName, count);
            //then
            assertThat(orderMenuItem.getMenuItem()).isEqualTo(MenuItem.MUSHROOM_CREAM_SOUP);
            assertThat(orderMenuItem.getCount()).isEqualTo(count);
        }

        @Test
        void 메뉴판에_없다면_예외가_발생한다() {
            //given
            String menuItemName = "없는메뉴";
            int count = 1;
            //when then
            assertThatThrownBy(() -> OrderMenuItem.valueOf(menuItemName, count))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }

        @ValueSource(ints = {0, 21})
        @ParameterizedTest
        void 주문개수가_1개이상_20개이하가_아니라면_예외가_발생한다(int count) {
            //given
            String menuItemName = "양송이수프";
            //when then
            assertThatThrownBy(() -> OrderMenuItem.valueOf(menuItemName, count))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }
    }
}