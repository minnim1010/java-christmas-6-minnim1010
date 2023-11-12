package christmas.domain.benefit.type;

import christmas.domain.menu.MenuItem;
import christmas.domain.reservation.Reservation;

public interface GiveawayType {

    MenuItem getGiveaway(Reservation reservation);
}
