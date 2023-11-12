package christmas.domain.benefit.type;

import christmas.domain.menu.constants.Menu;
import christmas.domain.reservation.Reservation;

public interface GiveawayType {

    Menu getGiveaway(Reservation reservation);
}
