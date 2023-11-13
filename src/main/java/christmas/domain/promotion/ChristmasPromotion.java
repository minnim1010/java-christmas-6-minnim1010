package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.benefit.policy.DiscountPolicy;
import christmas.domain.benefit.policy.GiveawayPolicy;
import christmas.domain.benefit.rule.AnyMatchRule;
import christmas.domain.benefit.rule.DayOfWeekRule;
import christmas.domain.benefit.rule.PeriodRule;
import christmas.domain.benefit.rule.RequireTotalPriceRule;
import christmas.domain.benefit.rule.SpecialDayRule;
import christmas.domain.benefit.rule.WeekdayRule;
import christmas.domain.benefit.rule.WeekendRule;
import christmas.domain.benefit.type.AmountDiscountByMenuCategory;
import christmas.domain.benefit.type.GiveawayMenu;
import christmas.domain.benefit.type.IncreasePerDayAmountDiscount;
import christmas.domain.benefit.type.SpecificAmountDiscount;
import christmas.domain.menu.MenuItem;
import christmas.domain.menu.constants.Menu;
import christmas.domain.menu.constants.MenuCategory;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChristmasPromotion {
    private static final int YEAR = 2023;
    private static final LocalDate START_DATE = LocalDate.of(YEAR, 12, 1);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(YEAR, 12, 25);
    private static final LocalDate END_DATE = LocalDate.of(YEAR, 12, 31);
    private static final Money REQUIRED_TOTAL_PRICE = Money.valueOf(10_000);

    private final List<DiscountPolicy> discountPolicies = new ArrayList<>();
    private final GiveawayPolicy giveawayPolicy;

    public ChristmasPromotion() {
        DiscountPolicy christmasDDayDiscount = new DiscountPolicy("크리스마스 디데이 할인",
                new PeriodRule(START_DATE, CHRISTMAS_DATE),
                new IncreasePerDayAmountDiscount(Money.valueOf(1000), Money.valueOf(100), START_DATE));

        DiscountPolicy weekDayDiscount = new DiscountPolicy("평일 할인",
                new WeekdayRule(),
                new AmountDiscountByMenuCategory(MenuCategory.DESSERT, Money.valueOf(YEAR)));

        DiscountPolicy weekendDiscount = new DiscountPolicy("주말 할인",
                new WeekendRule(),
                new AmountDiscountByMenuCategory(MenuCategory.MAIN_COURSE, Money.valueOf(YEAR)));

        DiscountPolicy specialDiscount = new DiscountPolicy("특별 할인",
                new AnyMatchRule(new DayOfWeekRule(DayOfWeek.SUNDAY), new SpecialDayRule(CHRISTMAS_DATE)),
                new SpecificAmountDiscount(Money.valueOf(1000)));

        GiveawayPolicy champagneGiveaway =
                new GiveawayPolicy("증정 이벤트", new RequireTotalPriceRule(Money.valueOf(120_000)),
                        new GiveawayMenu(Menu.CHAMPAGNE, 1));

        discountPolicies.add(christmasDDayDiscount);
        discountPolicies.add(weekDayDiscount);
        discountPolicies.add(weekendDiscount);
        discountPolicies.add(specialDiscount);

        giveawayPolicy = champagneGiveaway;
    }

    public boolean isSatisfiedBy(Reservation reservation) {
        Money totalPrice = reservation.getTotalPrice();
        return isPromotionPeriod(reservation.getReservationDate())
                && totalPrice.isGreaterOrEqual(REQUIRED_TOTAL_PRICE);
    }

    private boolean isPromotionPeriod(ReservationDate reservationDate) {
        return reservationDate.isBetween(START_DATE, END_DATE);
    }

    public Map<String, Money> applyDiscountBenefit(Reservation reservation) {
        Map<String, Money> discountBenefits = new LinkedHashMap<>();

        discountPolicies.stream()
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

        if (giveawayPolicy.isSatisfiedBy(reservation)) {
            giveawayBenefits.put(giveawayPolicy.getName(), giveawayPolicy.getGiveaway());
        }

        return giveawayBenefits;
    }

    public EventBadge giveEventBadge(Money totalBenefitPrice) {
        return EventBadge.findReceivableBadge(totalBenefitPrice);
    }
}
