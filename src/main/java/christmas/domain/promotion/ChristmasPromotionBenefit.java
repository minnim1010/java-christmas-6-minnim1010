package christmas.domain.promotion;

import christmas.domain.base.Money;
import christmas.domain.benefit.policy.DiscountPolicy;
import christmas.domain.benefit.policy.GiveawayPolicy;
import christmas.domain.benefit.rule.AnyMatchRule;
import christmas.domain.benefit.rule.DayOfWeekRule;
import christmas.domain.benefit.rule.PeriodRule;
import christmas.domain.benefit.rule.SpecialDateRule;
import christmas.domain.benefit.rule.TotalPriceAboveThresholdRule;
import christmas.domain.benefit.rule.WeekdayRule;
import christmas.domain.benefit.rule.WeekendRule;
import christmas.domain.benefit.type.DailyIncreasingAmountDiscount;
import christmas.domain.benefit.type.FixedAmountDiscount;
import christmas.domain.benefit.type.MenuCategoryAmountDiscount;
import christmas.domain.benefit.type.MenuGiveaway;
import christmas.domain.menu.constants.Menu;
import christmas.domain.menu.constants.MenuCategory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChristmasPromotionBenefit {
    private static final int CHRISTMAS_DAY = 25;
    private static final int CHRISTMAS_D_DAY_DISCOUNT_BASE_AMOUNT = 1000;
    private static final int CHRISTMAS_D_DAY_DISCOUNT_INCREASE_AMOUNT = 100;
    private static final int SPECIAL_DISCOUNT_AMOUNT = 1000;
    private static final int CHAMPAGNE_GIVEAWAY_THRESHOLD_PRICE = 120_000;
    private static final int CHAMPAGNE_GIVEAWAY_COUNT = 1;
    private final int year;
    private final LocalDate promotionStartDate;
    private final LocalDate christmasDate;
    private final List<DiscountPolicy> discountBenefits = new ArrayList<>();
    private final List<GiveawayPolicy> giveawayBenefits = new ArrayList<>();

    private enum BenefitName {
        CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인"),
        WEEKDAY_DISCOUNT("평일 할인"),
        WEEKEND_DISCOUNT("주말 할인"),
        SPECIAL_DISCOUNT("특별 할인"),
        CHAMPAGNE_GIVEAWAY("증정 이벤트");

        private final String value;

        BenefitName(String value) {
            this.value = value;
        }
    }

    public ChristmasPromotionBenefit(int year, int month, LocalDate promotionStartDate) {
        this.year = year;
        this.promotionStartDate = promotionStartDate;
        this.christmasDate = LocalDate.of(year, month, CHRISTMAS_DAY);

        discountBenefits.addAll(createDiscountPolicies());
        giveawayBenefits.addAll(createGiveawayPolicies());
    }

    private List<DiscountPolicy> createDiscountPolicies() {
        return List.of(
                new DiscountPolicy(BenefitName.CHRISTMAS_D_DAY_DISCOUNT.value,
                        new PeriodRule(promotionStartDate, christmasDate),
                        new DailyIncreasingAmountDiscount(Money.valueOf(CHRISTMAS_D_DAY_DISCOUNT_BASE_AMOUNT),
                                Money.valueOf(CHRISTMAS_D_DAY_DISCOUNT_INCREASE_AMOUNT), promotionStartDate)),
                new DiscountPolicy(BenefitName.WEEKDAY_DISCOUNT.value,
                        new WeekdayRule(),
                        new MenuCategoryAmountDiscount(MenuCategory.DESSERT, Money.valueOf(year))),
                new DiscountPolicy(BenefitName.WEEKEND_DISCOUNT.value,
                        new WeekendRule(),
                        new MenuCategoryAmountDiscount(MenuCategory.MAIN_COURSE, Money.valueOf(year))),
                new DiscountPolicy(BenefitName.SPECIAL_DISCOUNT.value,
                        new AnyMatchRule(new DayOfWeekRule(DayOfWeek.SUNDAY), new SpecialDateRule(christmasDate)),
                        new FixedAmountDiscount(Money.valueOf(SPECIAL_DISCOUNT_AMOUNT)))
        );
    }

    private List<GiveawayPolicy> createGiveawayPolicies() {
        return List.of(new GiveawayPolicy(BenefitName.CHAMPAGNE_GIVEAWAY.value,
                new TotalPriceAboveThresholdRule(Money.valueOf(CHAMPAGNE_GIVEAWAY_THRESHOLD_PRICE)),
                new MenuGiveaway(Menu.CHAMPAGNE, CHAMPAGNE_GIVEAWAY_COUNT)));
    }

    public List<DiscountPolicy> getDiscountBenefits() {
        return discountBenefits;
    }

    public List<GiveawayPolicy> getGiveawayBenefits() {
        return giveawayBenefits;
    }
}
