package christmas.domain.benefit.rule;

import christmas.domain.base.ReservationDate;
import christmas.domain.reservation.Reservation;
import java.time.DayOfWeek;

public class DayOfWeekRule implements Rule {
    private final DayOfWeek dayOfWeek;

    public DayOfWeekRule(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isSameDayOfWeek(dayOfWeek);
    }
}
