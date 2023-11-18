package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;

public class WeekdayRule implements Rule {

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return !reservationDate.isWeekend();
    }
}
