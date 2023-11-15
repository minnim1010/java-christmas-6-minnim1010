package christmas.domain.benefit.policy;

import christmas.domain.benefit.rule.Rule;
import christmas.domain.benefit.type.GiveawayType;
import christmas.domain.menu.MenuItem;
import christmas.domain.reservation.Reservation;
import java.util.Optional;

public class GiveawayPolicy extends Policy {
    private final Rule rule;
    private final GiveawayType giveawayType;

    public GiveawayPolicy(String name, Rule rule, GiveawayType giveawayType) {
        super(name);
        this.rule = rule;
        this.giveawayType = giveawayType;
    }

    public boolean isSatisfiedBy(Reservation reservation) {
        return rule.isSatisfiedBy(reservation);
    }

    public Optional<MenuItem> getGiveaway() {
        return Optional.ofNullable(giveawayType.getGiveaway());
    }
}
