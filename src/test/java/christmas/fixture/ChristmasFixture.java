package christmas.fixture;

import christmas.domain.base.Money;
import christmas.domain.constants.MenuItem;
import christmas.domain.reservation.OrderMenuItem;
import java.util.Iterator;
import java.util.List;

public class ChristmasFixture {

    private ChristmasFixture() {
    }

    public static OrderMenuItem createOrderMenuItem(MenuItem menuItem, int count) {
        return new OrderMenuItem(menuItem, count);
    }

    public static Money calculatePrice(List<MenuItem> menuItems, List<Integer> counts) {
        if (menuItems.size() != counts.size()) {
            throw new IllegalArgumentException("메뉴 이름과 메뉴 개수 리스트는 같은 개수여야 합니다.");
        }
        Iterator<MenuItem> menuItemsIt = menuItems.iterator();
        Iterator<Integer> countsIt = counts.iterator();

        Money price = Money.ZERO;
        while (menuItemsIt.hasNext()) {
            price = price.add(menuItemsIt.next().getPrice().times(countsIt.next()));
        }
        return price;
    }

}
