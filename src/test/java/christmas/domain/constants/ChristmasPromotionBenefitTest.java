package christmas.domain.constants;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.policy.ChampagneGiveawayPolicy;
import christmas.domain.benefit.policy.ChristmasDDayDiscountPolicy;
import christmas.domain.benefit.policy.SpecialDiscountPolicy;
import christmas.domain.benefit.policy.WeekdayDiscountPolicy;
import christmas.domain.benefit.policy.WeekendDiscountPolicy;
import christmas.domain.promotion.ChristmasPromotionBenefit;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasPromotionBenefitTest {

    static Stream<Arguments> getFindChristmasPromotionBenefitArgument() {
        return Stream.of(
                Arguments.of(new ChristmasDDayDiscountPolicy(), ChristmasPromotionBenefit.CHRISTMAS_D_DAY_DISCOUNT),
                Arguments.of(new WeekdayDiscountPolicy(2023), ChristmasPromotionBenefit.WEEKDAY_DISCOUNT),
                Arguments.of(new WeekendDiscountPolicy(2023), ChristmasPromotionBenefit.WEEKEND_DISCOUNT),
                Arguments.of(new SpecialDiscountPolicy(), ChristmasPromotionBenefit.SPECIAL_DISCOUNT),
                Arguments.of(new ChampagneGiveawayPolicy(), ChristmasPromotionBenefit.GIVEAWAY)
        );
    }

    @MethodSource("getFindChristmasPromotionBenefitArgument")
    @ParameterizedTest
    void 각혜택에_알맞은_혜택타입을_찾는다(Benefit benefit, ChristmasPromotionBenefit expected) {
        //given
        //when
        ChristmasPromotionBenefit result = ChristmasPromotionBenefit.findByBenefit(benefit);
        //then
        assertThat(result).isEqualTo(expected);
    }
}