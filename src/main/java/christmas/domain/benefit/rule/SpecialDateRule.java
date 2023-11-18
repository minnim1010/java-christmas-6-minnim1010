package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
import java.time.LocalDate;

public class SpecialDateRule implements Rule {
    private final LocalDate specialDay;

    public SpecialDateRule(LocalDate specialDay) {
        this.specialDay = specialDay;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isSameDay(specialDay);
    }
}
