package christmas.domain.promotion;

import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.policy.ChampagneGiveawayPolicy;
import christmas.domain.benefit.policy.ChristmasDDayDiscountPolicy;
import christmas.domain.benefit.policy.SpecialDiscountPolicy;
import christmas.domain.benefit.policy.WeekdayDiscountPolicy;
import christmas.domain.benefit.policy.WeekendDiscountPolicy;
import java.util.Arrays;

public enum ChristmasPromotionBenefit {
    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", new ChristmasDDayDiscountPolicy()),
    WEEKDAY_DISCOUNT("평일 할인", new WeekdayDiscountPolicy(2023)),
    WEEKEND_DISCOUNT("주말 할인", new WeekendDiscountPolicy(2023)),
    SPECIAL_DISCOUNT("특별 할인", new SpecialDiscountPolicy()),
    GIVEAWAY("증정 이벤트", new ChampagneGiveawayPolicy());

    private final String name;
    private final Benefit benefit;

    ChristmasPromotionBenefit(String name, Benefit benefit) {
        this.name = name;
        this.benefit = benefit;
    }

    public static ChristmasPromotionBenefit findByBenefit(Benefit benefit) {
        return Arrays.stream(values())
                .filter(value -> value.benefit.getClass().equals(benefit.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 혜택은 존재하지 않습니다."));
    }

    public String getName() {
        return name;
    }
}
