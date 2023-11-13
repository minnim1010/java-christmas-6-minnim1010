package christmas.domain.benefit.rule;

import christmas.domain.base.ReservationDate;
import christmas.domain.reservation.Reservation;
import java.time.LocalDate;

public class SpecialDayRule implements Rule {
    private final LocalDate specialDay;

    public SpecialDayRule(LocalDate specialDay) {
        this.specialDay = specialDay;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isSameDay(specialDay);
    }
}
