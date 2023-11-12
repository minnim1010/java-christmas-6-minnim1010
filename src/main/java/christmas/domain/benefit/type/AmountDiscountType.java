package christmas.domain.benefit.type;

import christmas.domain.base.Money;
import christmas.domain.reservation.Reservation;

public interface AmountDiscountType {

    Money getDiscountAmount(Reservation reservation);
}
