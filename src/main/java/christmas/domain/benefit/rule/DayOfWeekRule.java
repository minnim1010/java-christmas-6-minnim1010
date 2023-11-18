package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
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
