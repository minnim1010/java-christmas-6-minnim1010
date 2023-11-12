package christmas.domain.promotion.constants;

import christmas.domain.base.Money;
import java.util.Arrays;

public enum EventBadge {
    SANTA("산타", Money.valueOf(20_000)),
    TREE("트리", Money.valueOf(10_000)),
    STAR("별", Money.valueOf(5_000)),
    NOT_APPLICABLE("없음", Money.valueOf(0));

    private final String name;
    private final Money requiredBadgeAmount;

    EventBadge(String name, Money requiredBadgeAmount) {
        this.name = name;
        this.requiredBadgeAmount = requiredBadgeAmount;
    }

    public static EventBadge findReceivableBadge(Money totalBenefitPrice) {
        return Arrays.stream(values())
                .filter(badge -> totalBenefitPrice.isGreaterOrEqual(badge.requiredBadgeAmount))
                .findFirst()
                .orElse(EventBadge.NOT_APPLICABLE);
    }

    public String getName() {
        return name;
    }
}
