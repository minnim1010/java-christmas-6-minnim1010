package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
import java.time.LocalDate;

public class PeriodRule implements Rule {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PeriodRule(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isBetween(startDate, endDate);
    }
}
