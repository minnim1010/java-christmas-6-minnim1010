package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;

public interface Rule {

    boolean isSatisfiedBy(Reservation reservation);
}
