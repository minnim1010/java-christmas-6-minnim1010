package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import java.util.Map;

public class PromotionAppliedResult {
    private final Map<String, Money> discountBenefits;
    private final Map<String, MenuItem> giveawayBenefits;
    private final Money totalDiscountPrice;
    private final Money totalBenefitPrice;

    public PromotionAppliedResult(Map<String, Money> discountBenefits,
                                  Map<String, MenuItem> giveawayBenefits) {
        this.discountBenefits = discountBenefits;
        this.giveawayBenefits = giveawayBenefits;
        this.totalDiscountPrice = calculateTotalDiscountPrice(discountBenefits);
        this.totalBenefitPrice = calculateTotalBenefitPrice(totalDiscountPrice);
    }

    private Money calculateTotalDiscountPrice(Map<String, Money> discountBenefits) {
        int price = discountBenefits.values().stream()
                .map(Money::getValue)
                .mapToInt(Integer::intValue)
                .sum();
        return Money.valueOf(price);
    }

    private Money calculateTotalBenefitPrice(Money totalDiscountPrice) {
        if (giveawayBenefits == null) {
            return totalDiscountPrice;
        }

        int giveawayBenefitsAmount = giveawayBenefits.values().stream()
                .map(menuItem -> menuItem.menu().getPrice().times(menuItem.count()).getValue())
                .mapToInt(Integer::intValue)
                .sum();
        return totalDiscountPrice.add(Money.valueOf(giveawayBenefitsAmount));
    }

    public Map<String, Money> getDiscountBenefits() {
        return discountBenefits;
    }

    public Map<String, MenuItem> getGiveawayBenefits() {
        return giveawayBenefits;
    }

    public Money getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public Money getTotalBenefitPrice() {
        return totalBenefitPrice;
    }
}
