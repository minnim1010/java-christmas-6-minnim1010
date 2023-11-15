package christmas.controller;

import christmas.domain.menu.OrderMenu;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.ReservationDate;
import christmas.util.Parser;
import christmas.view.ErrorView;
import christmas.view.ReservationView;
import java.util.function.Function;
import java.util.function.Supplier;

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
        return getValidInput(
                reservationView::inputReservationDay,
                input -> ReservationDate.valueOf(YEAR, MONTH, Parser.parseDate(input))
        );
    }

    private OrderMenu getOrderMenu() {
        return getValidInput(
                reservationView::inputOrderMenu,
                input -> OrderMenu.valueOf(Parser.parseOrderMenuItemList(input))
        );
    }

    private <T> T getValidInput(Supplier<String> inputSupplier, Function<String, T> parser) {
        while (true) {
            try {
                String input = inputSupplier.get();
                return parser.apply(input);
            } catch (IllegalArgumentException e) {
                errorView.outputError(e.getMessage());
            }
        }
    }
}
