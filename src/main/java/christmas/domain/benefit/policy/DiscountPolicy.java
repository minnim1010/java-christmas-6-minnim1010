package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.benefit.rule.Rule;
import christmas.domain.benefit.type.AmountDiscountType;
import christmas.domain.reservation.Reservation;

public class DiscountPolicy extends Policy {
    private final Rule rule;
    private final AmountDiscountType amountDiscountType;

    public DiscountPolicy(String name, Rule rule, AmountDiscountType amountDiscountType) {
        super(name);
        this.rule = rule;
        this.amountDiscountType = amountDiscountType;
    }

    public boolean isSatisfiedBy(Reservation reservation) {
        return rule.isSatisfiedBy(reservation);
    }

    public Money getDiscountAmount(Reservation reservation) {
        return amountDiscountType.getDiscountAmount(reservation);
    }
}
