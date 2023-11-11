package christmas.domain.reservation;

import christmas.domain.base.Money;

public class Reservation {
    private final ReservationDate reservationDate;
    private final OrderMenu orderMenu;
    private final Money totalPrice;

    protected Reservation(ReservationDate reservationDate, OrderMenu orderMenu) {
        this.reservationDate = reservationDate;
        this.orderMenu = orderMenu;
        this.totalPrice = orderMenu.calculateTotalPrice();
    }

    public static Reservation valueOf(ReservationDate reservationDate, OrderMenu orderMenu) {
        return new Reservation(reservationDate, orderMenu);
    }

    public ReservationDate getReservationDate() {
        return reservationDate;
    }

    public OrderMenu getOrderMenu() {
        return orderMenu;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationDate=" + reservationDate +
                ", orderMenu=" + orderMenu +
                '}';
    }
}
