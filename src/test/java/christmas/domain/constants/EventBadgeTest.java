package christmas.domain.constants;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.domain.base.Money;
import christmas.domain.promotion.constants.EventBadge;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventBadgeTest {

    static Stream<Arguments> getFindReceivableBadgeArgument() {
        return Stream.of(
                Arguments.of(1000, EventBadge.NOT_APPLICABLE),
                Arguments.of(4999, EventBadge.NOT_APPLICABLE),
                Arguments.of(5000, EventBadge.STAR),
                Arguments.of(9999, EventBadge.STAR),
                Arguments.of(10000, EventBadge.TREE),
                Arguments.of(19999, EventBadge.TREE),
                Arguments.of(20000, EventBadge.SANTA)
        );
    }

    @MethodSource("getFindReceivableBadgeArgument")
    @ParameterizedTest
    void 총혜택금액에_알맞은_배지를_찾는다(int totalBenefitAmount, EventBadge expected) {
        //given
        Money totalBenefitPrice = Money.valueOf(totalBenefitAmount);
        //when
        EventBadge receivableBadge = EventBadge.findReceivableBadge(totalBenefitPrice);
        //then
        assertThat(receivableBadge).isEqualTo(expected);
    }
}