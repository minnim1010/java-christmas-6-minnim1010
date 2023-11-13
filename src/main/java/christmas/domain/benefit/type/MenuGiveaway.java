package christmas.domain.benefit.type;

import christmas.domain.menu.MenuItem;
import christmas.domain.menu.constants.Menu;

public class MenuGiveaway implements GiveawayType {
    private final Menu giveaway;
    private final int count;

    public MenuGiveaway(Menu giveaway, int count) {
        this.giveaway = giveaway;
        this.count = count;
    }

    @Override
    public MenuItem getGiveaway() {
        return new MenuItem(giveaway, count);
    }
}
