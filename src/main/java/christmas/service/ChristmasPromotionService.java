package christmas.service;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import christmas.domain.promotion.ChristmasPromotion;
import christmas.domain.promotion.PromotionAppliedResult;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import java.util.Collections;
import java.util.Map;

public class ChristmasPromotionService {
    private final ChristmasPromotion christmasPromotion;

    public ChristmasPromotionService(ChristmasPromotion christmasPromotion) {
        this.christmasPromotion = christmasPromotion;
    }

    public PromotionAppliedResult applyPromotion(Reservation reservation) {
        if (!christmasPromotion.isSatisfiedBy(reservation)) {
            return new PromotionAppliedResult(
                    Collections.emptyMap(), Collections.emptyMap());
        }

        Map<String, Money> discountBenefits = christmasPromotion.applyDiscountBenefit(reservation);
        Map<String, MenuItem> giveawayBenefits = christmasPromotion.applyGiveawayPolicy(reservation);
        return new PromotionAppliedResult(discountBenefits, giveawayBenefits);
    }

    public EventBadge getEventBadge(Reservation reservation, Money totalBenefitPrice) {
        if (!christmasPromotion.isSatisfiedBy(reservation)) {
            return EventBadge.NOT_APPLICABLE;
        }
        return christmasPromotion.giveEventBadge(totalBenefitPrice);
    }
}
