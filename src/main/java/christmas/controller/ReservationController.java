package christmas.controller;

import static christmas.domain.constants.ChristmasConstraints.PROMOTION_MONTH;
import static christmas.domain.constants.ChristmasConstraints.PROMOTION_YEAR;

import christmas.domain.reservation.OrderMenu;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
import christmas.util.Parser;
import christmas.view.ErrorView;
import christmas.view.ReservationView;

public class ReservationController {
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
                int reservationDay = Parser.parseInt(reservationDayInput);
                return ReservationDate.valueOf(PROMOTION_YEAR, PROMOTION_MONTH, reservationDay);
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
