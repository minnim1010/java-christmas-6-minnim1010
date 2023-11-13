package christmas.domain.benefit.rule;

import christmas.domain.base.ReservationDate;
import christmas.domain.reservation.Reservation;

public class WeekendRule implements Rule {

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isWeekend();
    }
}
