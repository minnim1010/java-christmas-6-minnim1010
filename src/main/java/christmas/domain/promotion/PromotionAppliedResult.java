package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.constants.MenuItem;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PromotionAppliedResult {
    private final EnumMap<ChristmasPromotionBenefit, Money> discountBenefits;
    private final MenuItem giveaway;
    private final Money totalDiscountPrice;
    private final Money totalBenefitPrice;

    public PromotionAppliedResult(Map<ChristmasPromotionBenefit, Money> discountBenefits,
                                  Optional<MenuItem> giveaway) {
        this.discountBenefits = (EnumMap<ChristmasPromotionBenefit, Money>) discountBenefits;
        this.giveaway = giveaway.orElse(null);
        this.totalDiscountPrice = calculateTotalDiscountPrice(discountBenefits);
        this.totalBenefitPrice = calculateTotalBenefitPrice(totalDiscountPrice, this.giveaway);
    }

    private Money calculateTotalDiscountPrice(Map<ChristmasPromotionBenefit, Money> discountBenefits) {
        int price = discountBenefits.values().stream()
                .map(Money::getValue)
                .mapToInt(Integer::intValue)
                .sum();
        return Money.valueOf(price);
    }

    private Money calculateTotalBenefitPrice(Money totalDiscountPrice, MenuItem giveaway) {
        if (giveaway == null) {
            return totalDiscountPrice;
        }
        return totalDiscountPrice.add(giveaway.getPrice());
    }

    public Map<ChristmasPromotionBenefit, Money> getDiscountBenefits() {
        return discountBenefits;
    }

    public MenuItem getGiveaway() {
        return giveaway;
    }

    public Money getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public Money getTotalBenefitPrice() {
        return totalBenefitPrice;
    }
}
