package christmas.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import christmas.domain.menu.constants.Menu;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
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
            Map<String, Money> discountBenefits = new LinkedHashMap<>();
            discountBenefits.put("할인1", Money.valueOf(1200));
            discountBenefits.put("할인2", Money.valueOf(2023));
            discountBenefits.put("할인3", Money.valueOf(1000));
            Map<String, MenuItem> giveawayBenefits = new LinkedHashMap<>();
            giveawayBenefits.put("증정 이벤트", new MenuItem(Menu.CHAMPAGNE, 1));
            //when
            PromotionAppliedResult result = new PromotionAppliedResult(discountBenefits, giveawayBenefits);
            //then
            assertThat(result.getTotalDiscountPrice()).isEqualTo(Money.valueOf(4223));
            assertThat(result.getTotalBenefitPrice()).isEqualTo(Money.valueOf(29223));
        }

        @Test
        void 증정메뉴가_없는_경우() {
            //given
            Map<String, Money> discountBenefits = new LinkedHashMap<>();
            discountBenefits.put("할인1", Money.valueOf(1400));
            discountBenefits.put("할인2", Money.valueOf(2023));
            discountBenefits.put("할인3", Money.valueOf(1000));
            Map<String, MenuItem> giveawayBenefits = Collections.EMPTY_MAP;
            //when
            PromotionAppliedResult result = new PromotionAppliedResult(discountBenefits, giveawayBenefits);
            //then
            assertThat(result.getTotalDiscountPrice()).isEqualTo(Money.valueOf(4423));
            assertThat(result.getTotalBenefitPrice()).isEqualTo(Money.valueOf(4423));
        }
    }
}