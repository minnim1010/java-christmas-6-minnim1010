package christmas.domain.benefit.type;

import christmas.domain.base.Money;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.constants.MenuCategory;
import christmas.domain.reservation.Reservation;

public class AmountDiscountByMenuCategory implements AmountDiscountType {
    private final MenuCategory menuCategory;
    private final Money discountAmountPerMenu;

    public AmountDiscountByMenuCategory(MenuCategory menuCategory, Money discountAmountPerMenu) {
        this.menuCategory = menuCategory;
        this.discountAmountPerMenu = discountAmountPerMenu;
    }

    @Override
    public Money getDiscountAmount(Reservation reservation) {
        OrderMenu orderMenu = reservation.getOrderMenu();
        int totalCount = orderMenu.getTotalCountByCategory(menuCategory);
        return discountAmountPerMenu.times(totalCount);
    }
}
