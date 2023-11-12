package christmas.domain.benefit.policy;

import christmas.domain.base.Money;
import christmas.domain.constants.MenuCategory;
import christmas.domain.reservation.OrderMenu;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;

public class WeekendDiscountPolicy implements DiscountPolicy {
    private final Money discountAmountPerMain;

    public WeekendDiscountPolicy(int discountAmountPerMain) {
        this.discountAmountPerMain = Money.valueOf(discountAmountPerMain);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        ReservationDate reservationDate = reservation.getReservationDate();
        return reservationDate.isWeekend();
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        OrderMenu orderMenu = reservation.getOrderMenu();
        int totalCount = orderMenu.getTotalCountByCategory(MenuCategory.MAIN_COURSE);
        return discountAmountPerMain.times(totalCount);
    }
}