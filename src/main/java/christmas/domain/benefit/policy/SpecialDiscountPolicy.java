package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class SpecialDiscountPolicy implements DiscountPolicy {
    private final Money discountAmount = Money.valueOf(1_000);

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();

        return dayOfWeek == DayOfWeek.SUNDAY || reservationDate.isSameDay(LocalDate.of(2023, 12, 25));
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        return discountAmount;
    }
}
