package christmas.domain.benefit.rule;

import christmas.domain.base.Money;
import christmas.domain.reservation.Reservation;

public class TotalPriceAboveThresholdRule implements Rule {
    private final Money requireTotalPrice;

    public TotalPriceAboveThresholdRule(Money requireTotalPrice) {
        this.requireTotalPrice = requireTotalPrice;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        Money totalPrice = reservation.getTotalPrice();
        return totalPrice.isGreaterOrEqual(requireTotalPrice);
    }
}
