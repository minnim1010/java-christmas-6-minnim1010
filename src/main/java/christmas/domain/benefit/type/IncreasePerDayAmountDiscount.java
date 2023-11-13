package christmas.domain.benefit.type;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.reservation.Reservation;
import java.time.LocalDate;

public class IncreasePerDayAmountDiscount implements AmountDiscountType {
    private final Money baseAmount;
    private final Money discountAmountPerDay;
    private final LocalDate startDate;

    public IncreasePerDayAmountDiscount(Money baseAmount, Money discountAmountPerDay, LocalDate startDate) {
        this.baseAmount = baseAmount;
        this.discountAmountPerDay = discountAmountPerDay;
        this.startDate = startDate;
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        int betweenDays = reservationDate.getDaysBetween(startDate);

        return baseAmount.add(discountAmountPerDay.times(betweenDays));
    }
}
