package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PromotionAppliedResult {
    private final EnumMap<ChristmasPromotionBenefit, Money> discountBenefits;
    private final Optional<MenuItem> giveawayBenefit;
    private final Money totalDiscountPrice;
    private final Money totalBenefitPrice;

    public PromotionAppliedResult(Map<ChristmasPromotionBenefit, Money> discountBenefits,
                                  Optional<MenuItem> giveawayBenefit) {
        this.discountBenefits = (EnumMap<ChristmasPromotionBenefit, Money>) discountBenefits;
        this.giveawayBenefit = giveawayBenefit;
        this.totalDiscountPrice = calculateTotalDiscountPrice(discountBenefits);
        this.totalBenefitPrice = calculateTotalBenefitPrice(totalDiscountPrice);
    }

    private Money calculateTotalDiscountPrice(Map<ChristmasPromotionBenefit, Money> discountBenefits) {
        int price = discountBenefits.values().stream()
                .map(Money::getValue)
                .mapToInt(Integer::intValue)
                .sum();
        return Money.valueOf(price);
    }

    private Money calculateTotalBenefitPrice(Money totalDiscountPrice) {
        if (giveawayBenefit.isEmpty()) {
            return totalDiscountPrice;
        }

        MenuItem menuItem = giveawayBenefit.get();
        return totalDiscountPrice.add(menuItem.menu().getPrice().times(menuItem.count()));
    }

    public Map<ChristmasPromotionBenefit, Money> getDiscountBenefits() {
        return discountBenefits;
    }

    public Optional<MenuItem> getGiveawayBenefit() {
        return giveawayBenefit;
    }

    public Money getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public Money getTotalBenefitPrice() {
        return totalBenefitPrice;
    }
}
