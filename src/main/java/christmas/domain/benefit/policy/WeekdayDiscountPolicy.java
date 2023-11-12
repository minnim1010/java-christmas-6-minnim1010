package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.constants.MenuCategory;
import christmas.domain.reservation.Reservation;

public class WeekdayDiscountPolicy implements DiscountPolicy {
    private final Money discountAmountPerDessertMenu;

    public WeekdayDiscountPolicy(int discountAmountPerDessertMenu) {
        this.discountAmountPerDessertMenu = Money.valueOf(discountAmountPerDessertMenu);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return !reservationDate.isWeekend();
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        OrderMenu orderMenu = reservation.getOrderMenu();
        int totalCount = orderMenu.getTotalCountByCategory(MenuCategory.DESSERT);
        return discountAmountPerDessertMenu.times(totalCount);
    }
}
