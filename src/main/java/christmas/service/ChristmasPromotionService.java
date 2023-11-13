package christmas.service;

import christmas.domain.base.Money;
import christmas.domain.menu.MenuItem;
import christmas.domain.promotion.ChristmasPromotion;
import christmas.domain.promotion.PromotionAppliedResult;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import java.util.EnumMap;
import java.util.Optional;

public class ChristmasPromotionService {
    private final ChristmasPromotion christmasPromotion;

    public ChristmasPromotionService(ChristmasPromotion christmasPromotion) {
        this.christmasPromotion = christmasPromotion;
    }

    public PromotionAppliedResult applyPromotion(Reservation reservation) {
        if (!christmasPromotion.isSatisfiedBy(reservation)) {
            return new PromotionAppliedResult(
                    new EnumMap<>(ChristmasPromotionBenefit.class), null);
        }

        EnumMap<ChristmasPromotionBenefit, Money> discountBenefits =
                (EnumMap<ChristmasPromotionBenefit, Money>) christmasPromotion.applyDiscountBenefit(reservation);
        Optional<MenuItem> giveaway = christmasPromotion.applyGiveawayPolicy(reservation);
        return new PromotionAppliedResult(discountBenefits, giveaway.orElse(null));
    }

    public EventBadge getEventBadge(Reservation reservation, Money totalBenefitPrice) {
        if (!christmasPromotion.isSatisfiedBy(reservation)) {
            return EventBadge.NOT_APPLICABLE;
        }
        return christmasPromotion.giveEventBadge(totalBenefitPrice);
    }
}
