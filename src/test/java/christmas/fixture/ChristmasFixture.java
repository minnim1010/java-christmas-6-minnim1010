package christmas.fixture;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.OrderMenuItem;
import christmas.domain.menu.constants.Menu;
import christmas.domain.reservation.Reservation;
import christmas.util.Parser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChristmasFixture {

    private ChristmasFixture() {
    }

    public static ReservationDate createReservationDate(int year, int month, int day) {
        return ReservationDate.valueOf(year, month, day);
    }

    public static OrderMenuItem createOrderMenuItem(Menu menu, int count) {
        return new OrderMenuItem(menu, count);
    }

    public static Money calculatePrice(List<Menu> menus, List<Integer> counts) {
        if (menus.size() != counts.size()) {
            throw new IllegalArgumentException("메뉴 이름과 메뉴 개수 리스트는 같은 개수여야 합니다.");
        }
        Iterator<Menu> menuItemsIt = menus.iterator();
        Iterator<Integer> countsIt = counts.iterator();

        Money price = Money.ZERO;
        while (menuItemsIt.hasNext()) {
            price = price.add(menuItemsIt.next().getPrice().times(countsIt.next()));
        }
        return price;
    }

    public static Reservation createReservation(int day, List<Menu> menus, List<Integer> counts) {
        ReservationDate reservationDate = createReservationDate(2023, 12, day);

        if (menus.size() != counts.size()) {
            throw new IllegalArgumentException("메뉴 이름과 메뉴 개수 리스트는 같은 개수여야 합니다.");
        }
        Iterator<Menu> menuItemsIt = menus.iterator();
        Iterator<Integer> countsIt = counts.iterator();

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();
        while (menuItemsIt.hasNext()) {
            orderMenuItems.add(OrderMenuItem.valueOf(menuItemsIt.next().getName(), countsIt.next()));
        }
        OrderMenu orderMenu = OrderMenu.valueOf(orderMenuItems);

        return Reservation.valueOf(reservationDate, orderMenu);
    }

    public static Reservation createReservation(int day, String orderMenuInput) {
        ReservationDate reservationDate = createReservationDate(2023, 12, day);

        List<OrderMenuItem> orderMenuItems = Parser.parseOrderMenuItemList(orderMenuInput);
        OrderMenu orderMenu = OrderMenu.valueOf(orderMenuItems);

        return Reservation.valueOf(reservationDate, orderMenu);
    }
}
