package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.benefit.policy.ChampagneGiveawayPolicy;
import christmas.domain.benefit.policy.ChristmasDDayDiscountPolicy;
import christmas.domain.benefit.policy.DiscountPolicy;
import christmas.domain.benefit.policy.GiveawayPolicy;
import christmas.domain.benefit.policy.SpecialDiscountPolicy;
import christmas.domain.benefit.policy.WeekdayDiscountPolicy;
import christmas.domain.benefit.policy.WeekendDiscountPolicy;
import christmas.domain.constants.EventBadge;
import christmas.domain.constants.MenuItem;
import christmas.domain.reservation.Reservation;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChristmasPromotion {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;
    private static final int CHRISTMAS_DAY = 25;
    private static final Money REQUIRED_TOTAL_PRICE = Money.valueOf(10_000);

    private final List<DiscountPolicy> discountPolicies;
    private final GiveawayPolicy giveawayPolicy;

    public ChristmasPromotion() {
        discountPolicies = List.of(new ChristmasDDayDiscountPolicy(), new SpecialDiscountPolicy(),
                new WeekdayDiscountPolicy(YEAR), new WeekendDiscountPolicy(YEAR));
        giveawayPolicy = new ChampagneGiveawayPolicy();
    }

    public boolean isSatisfiedBy(Reservation reservation) {
        Money totalPrice = reservation.getTotalPrice();
        return totalPrice.isGreaterOrEqual(REQUIRED_TOTAL_PRICE);
    }

    public Map<ChristmasPromotionBenefit, Money> applyDiscountBenefit(Reservation reservation) {
        EnumMap<ChristmasPromotionBenefit, Money> discountBenefits = new EnumMap<>(ChristmasPromotionBenefit.class);

        discountPolicies.stream()
                .filter(discountPolicy -> discountPolicy.isSatisfiedBy(reservation))
                .forEach(discountPolicy -> {
                    Money discountAmount = discountPolicy.getDiscountAmount(reservation);
                    discountBenefits.put(ChristmasPromotionBenefit.findByBenefit(discountPolicy), discountAmount);
                });

        return discountBenefits;
    }

    public Optional<MenuItem> applyGiveawayPolicy(Reservation reservation) {
        if (giveawayPolicy.isSatisfiedBy(reservation)) {
            return Optional.of(giveawayPolicy.getGiveaway(reservation));
        }
        return Optional.empty();
    }

    public EventBadge giveEventBadge(Money totalBenefitPrice) {
        return EventBadge.findReceivableBadge(totalBenefitPrice);
    }
}
