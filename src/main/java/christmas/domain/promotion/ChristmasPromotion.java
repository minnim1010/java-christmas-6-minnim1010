package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.benefit.rule.PeriodRule;
import christmas.domain.benefit.rule.Rule;
import christmas.domain.benefit.rule.TotalPriceAboveThresholdRule;
import christmas.domain.menu.MenuItem;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChristmasPromotion {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final LocalDate START_DATE = LocalDate.of(YEAR, MONTH, 1);
    private static final LocalDate END_DATE = LocalDate.of(YEAR, MONTH, 31);
    private static final Money REQUIRED_TOTAL_PRICE = Money.valueOf(10_000);
    private final List<Rule> christmasPromotionRules = new ArrayList<>();
    private final ChristmasPromotionBenefit christmasPromotionBenefit = new ChristmasPromotionBenefit(YEAR, MONTH,
            START_DATE);

    public ChristmasPromotion() {
        registerRules();
    }

    private void registerRules() {
        christmasPromotionRules.add(new PeriodRule(START_DATE, END_DATE));
        christmasPromotionRules.add(new TotalPriceAboveThresholdRule(REQUIRED_TOTAL_PRICE));
    }

    public boolean isSatisfiedBy(Reservation reservation) {
        return christmasPromotionRules.stream().allMatch(rule -> rule.isSatisfiedBy(reservation));
    }

    public Map<String, Money> applyDiscountBenefit(Reservation reservation) {
        Map<String, Money> discountBenefits = new LinkedHashMap<>();

        christmasPromotionBenefit.getDiscountBenefits().stream()
                .filter(discountPolicy -> discountPolicy.isSatisfiedBy(reservation))
                .forEach(discountPolicy -> {
                    Money discountAmount = discountPolicy.getDiscountAmount(reservation);
                    if (!discountAmount.isZero()) {
                        discountBenefits.put(discountPolicy.getName(), discountAmount);
                    }
                });

        return discountBenefits;
    }

    public Map<String, MenuItem> applyGiveawayPolicy(Reservation reservation) {
        Map<String, MenuItem> giveawayBenefits = new LinkedHashMap<>();

        christmasPromotionBenefit.getGiveawayBenefits().stream()
                .filter(giveawayPolicy -> giveawayPolicy.isSatisfiedBy(reservation)).forEach(
                        giveawayPolicy -> giveawayPolicy.getGiveaway()
                                .ifPresent(menuItem -> giveawayBenefits.put(giveawayPolicy.getName(), menuItem)));

        return giveawayBenefits;
    }

    public EventBadge giveEventBadge(Money totalBenefitPrice) {
        return EventBadge.findReceivableBadge(totalBenefitPrice);
    }
}
