package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.menu.constants.MenuItem;
import christmas.domain.reservation.Reservation;

public class ChampagneGiveawayPolicy implements GiveawayPolicy {
    private final Money threshold = Money.valueOf(120_000);

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        Money totalPrice = reservation.getTotalPrice();
        return totalPrice.isGreaterOrEqual(threshold);
    }

    @Override
    public MenuItem getGiveaway(Reservation reservation) {
        return MenuItem.CHAMPAGNE;
    }
}