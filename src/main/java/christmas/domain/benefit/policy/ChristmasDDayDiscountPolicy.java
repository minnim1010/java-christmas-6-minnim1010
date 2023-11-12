package christmas.domain.benefit.policy;

import static christmas.domain.constants.ChristmasConstraints.isAfterChristmasDay;

import christmas.domain.base.Money;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;

public class ChristmasDDayDiscountPolicy implements DiscountPolicy {
    private final Money baseAmount = Money.valueOf(1_000);
    private final Money increaseAmountPerDay = Money.valueOf(100);

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return !isAfterChristmasDay(reservationDate.getDay());
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        int increaseCount = reservationDate.getDay() - 1;
        return baseAmount.add(increaseAmountPerDay.times(increaseCount));
    }
}
