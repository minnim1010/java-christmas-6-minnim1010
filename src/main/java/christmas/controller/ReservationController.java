package christmas.controller;

import christmas.domain.base.ReservationDate;
import christmas.domain.menu.OrderMenu;
import christmas.domain.reservation.Reservation;
import christmas.util.Parser;
import christmas.view.ErrorView;
import christmas.view.ReservationView;

public class ReservationController {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;

    private final ReservationView reservationView;
    private final ErrorView errorView;

    public ReservationController(ReservationView reservationView, ErrorView errorView) {
        this.reservationView = reservationView;
        this.errorView = errorView;
    }

    public Reservation makeReservation() {
        greet();
        ReservationDate reservationDate = getReservationDate();
        OrderMenu orderMenu = getOrderMenu();
        return Reservation.valueOf(reservationDate, orderMenu);
    }

    private void greet() {
        reservationView.greet();
    }

    private ReservationDate getReservationDate() {
        while (true) {
            try {
                String reservationDayInput = reservationView.inputReservationDay();
                int reservationDay = Parser.parseDate(reservationDayInput);
                return ReservationDate.valueOf(YEAR, MONTH, reservationDay);
            } catch (IllegalArgumentException e) {
                errorView.outputError(e.getMessage());
            }
        }
    }

    private OrderMenu getOrderMenu() {
        while (true) {
            try {
                String orderMenuInput = reservationView.inputOrderMenu();
                return OrderMenu.valueOf(Parser.parseOrderMenuItemList(orderMenuInput));
            } catch (IllegalArgumentException e) {
                errorView.outputError(e.getMessage());
            }
        }
    }
}
