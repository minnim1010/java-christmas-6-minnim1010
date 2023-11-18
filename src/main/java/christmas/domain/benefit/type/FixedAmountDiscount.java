package christmas.domain.benefit.type;

import christmas.domain.base.Money;
import christmas.domain.reservation.Reservation;

public class FixedAmountDiscount implements AmountDiscountType {
    private final Money discountAmount;

    public FixedAmountDiscount(Money discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        return discountAmount;
    }
}
