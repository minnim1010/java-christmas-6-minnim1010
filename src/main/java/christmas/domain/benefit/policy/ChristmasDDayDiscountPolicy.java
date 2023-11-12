package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.reservation.Reservation;
import java.time.LocalDate;

public class ChristmasDDayDiscountPolicy implements DiscountPolicy {
    private final Money baseAmount = Money.valueOf(1_000);
    private final Money increaseAmountPerDay = Money.valueOf(100);

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isBetween(LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 25));
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        int increaseCount = reservationDate.getDay() - 1;
        return baseAmount.add(increaseAmountPerDay.times(increaseCount));
    }
}
