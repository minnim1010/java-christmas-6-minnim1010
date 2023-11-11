package christmas.util;

import static christmas.exception.ErrorMessage.INVALID_NUMERIC_INPUT;
import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.constants.MenuItem;
import christmas.domain.reservation.OrderMenuItem;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParserTest {

    @Nested
    class 입력값을_int형으로_바꿀시 {

        @ValueSource(strings = {"123", "1", "-1"})
        @ParameterizedTest
        void 성공한다(String input) {
            //given
            //when
            int result = Parser.parseInt(input);
            //then
            assertThat(result).isEqualTo(Integer.parseInt(input));
        }

        @ValueSource(strings = {" ", "  ", "\t", "\n", "\r"})
        @ParameterizedTest
        void 공백만_존재한다면_예외가_발생한다(String input) {
            //given
            //when then
            assertThatThrownBy(() -> Parser.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_NUMERIC_INPUT.getMessage());
        }

        @ValueSource(strings = {"a1", "1a", "a"})
        @ParameterizedTest
        void 숫자이외문자가_존재한다면_예외가_발생한다(String input) {
            //given
            //when then
            assertThatThrownBy(() -> Parser.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_NUMERIC_INPUT.getMessage());
        }
    }

    @Nested
    class 입력값을_OrderMenuItem형으로_바꿀시 {

        @CsvSource(value = {
                "시저샐러드-1, 시저샐러드, 1",
                "티본스테이크-5, 티본스테이크, 5",
                "해산물파스타-3, 해산물파스타, 3"
        })
        @ParameterizedTest
        void 성공한다(String input, String menuItemName, int count) {
            //given
            //when
            OrderMenuItem orderMenuItem = Parser.parseOrderMenuItem(input);
            //then
            assertThat(orderMenuItem.getMenuItem().getName()).isEqualTo(menuItemName);
            assertThat(orderMenuItem.getCount()).isEqualTo(count);
        }

        @ValueSource(strings = {" ", "  ", "\t", "\n", "\r"})
        @ParameterizedTest
        void 공백만_존재한다면_예외가_발생한다(String input) {
            //given
            //when then
            assertThatThrownBy(() -> Parser.parseOrderMenuItem(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }

        @ValueSource(strings = {"시저샐러드 1", "티본스테이크", "12345"})
        @ParameterizedTest
        void 입력값형식과_맞지않는다면_예외가_발생한다(String input) {
            //given
            //when then
            assertThatThrownBy(() -> Parser.parseOrderMenuItem(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }
    }

    @Nested
    class 입력값을_OrderMenuItemList형으로_바꿀시 {

        static Stream<Arguments> getSuccessTestArgument() {
            return Stream.of(
                    Arguments.of("시저샐러드-1,티본스테이크-5",
                            List.of(Tuple.tuple(MenuItem.CAESAR_SALAD, 1),
                                    Tuple.tuple(MenuItem.T_BONE_STEAK, 5))),
                    Arguments.of("시저샐러드-1,티본스테이크-5,아이스크림-10",
                            List.of(Tuple.tuple(MenuItem.CAESAR_SALAD, 1),
                                    Tuple.tuple(MenuItem.T_BONE_STEAK, 5),
                                    Tuple.tuple(MenuItem.ICE_CREAM, 10)))
            );
        }

        @MethodSource("getSuccessTestArgument")
        @ParameterizedTest
        void 성공한다(String input, List<Tuple> expected) {
            //given
            //when
            List<OrderMenuItem> orderMenuItems = Parser.parseOrderMenuItemList(input);
            //then
            assertThat(orderMenuItems).hasSize(expected.size())
                    .extracting("menuItem", "count")
                    .containsExactlyElementsOf(expected);
        }

        @ValueSource(strings = {
                "시저샐러드-1/티본스테이크-5/시저샐러드-3",
                "바비큐립-3 크리스마스파스타-1 초코케이크-10"
        })
        @ParameterizedTest
        void 입력형식과_맞지않는다면_예외가_발생한다(String input) {
            //given
            //when then
            assertThatThrownBy(() -> Parser.parseOrderMenuItemList(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER.getMessage());
        }
    }
}