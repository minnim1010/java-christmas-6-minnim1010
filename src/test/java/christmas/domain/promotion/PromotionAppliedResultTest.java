package christmas.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import java.util.EnumMap;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("프로모션 적용 결과")
class PromotionAppliedResultTest {

    @Nested
    class 생성_시_총할인금액과_총혜택금액을_올바르게_계산한다 {

        @Test
        void 증정메뉴가_있는_경우() {
            //given
            EnumMap<ChristmasPromotionBenefit, Money> discountBenefits = new EnumMap<>(ChristmasPromotionBenefit.class);
            discountBenefits.put(ChristmasPromotionBenefit.CHRISTMAS_D_DAY_DISCOUNT, Money.valueOf(1200));
            discountBenefits.put(ChristmasPromotionBenefit.WEEKDAY_DISCOUNT, Money.valueOf(2023));
            discountBenefits.put(ChristmasPromotionBenefit.SPECIAL_DISCOUNT, Money.valueOf(1000));
            Optional<MenuItem> giveawayBenefit = Optional.of(new MenuItem(Menu.CHAMPAGNE, 1));
            //when
            PromotionAppliedResult result = new PromotionAppliedResult(discountBenefits, giveawayBenefit);
            //then
            assertThat(result.getTotalDiscountPrice()).isEqualTo(Money.valueOf(4223));
            assertThat(result.getTotalBenefitPrice()).isEqualTo(Money.valueOf(29223));
        }

        @Test
        void 증정메뉴가_없는_경우() {
            //given
            EnumMap<ChristmasPromotionBenefit, Money> discountBenefits = new EnumMap<>(ChristmasPromotionBenefit.class);
            discountBenefits.put(ChristmasPromotionBenefit.CHRISTMAS_D_DAY_DISCOUNT, Money.valueOf(1400));
            discountBenefits.put(ChristmasPromotionBenefit.WEEKEND_DISCOUNT, Money.valueOf(2023));
            discountBenefits.put(ChristmasPromotionBenefit.SPECIAL_DISCOUNT, Money.valueOf(1000));
            Optional<MenuItem> giveawayBenefit = Optional.empty();
            //when
            PromotionAppliedResult result = new PromotionAppliedResult(discountBenefits, giveawayBenefit);
            //then
            assertThat(result.getTotalDiscountPrice()).isEqualTo(Money.valueOf(4423));
            assertThat(result.getTotalBenefitPrice()).isEqualTo(Money.valueOf(4423));
        }
    }
}