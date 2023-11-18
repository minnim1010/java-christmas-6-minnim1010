package christmas.domain.benefit.policy;

import christmas.domain.reservation.Reservation;

public abstract class Policy {
    protected final String name;

    protected Policy(String name) {
        this.name = name;
    }

    public abstract boolean isSatisfiedBy(Reservation reservation);

    public String getName() {
        return name;
    }
}
