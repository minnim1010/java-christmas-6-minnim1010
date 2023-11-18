package christmas.controller;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.reservation.Reservation;
import christmas.view.ErrorView;

public class MainController {
    private final ReservationController reservationController;
    private final PromotionApplyController promotionApplyController;
    private final ErrorView errorView;

    public MainController(ReservationController reservationController,
                          PromotionApplyController promotionApplyController,
                          ErrorView errorView) {
        this.reservationController = reservationController;
        this.promotionApplyController = promotionApplyController;
        this.errorView = errorView;
    }

    public void run() {
        try {
            Reservation reservation = reservationController.makeReservation();
            promotionApplyController.applyPromotion(reservation);
        } catch (Exception e) {
            errorView.outputError(e.getMessage());
        } finally {
            Console.close();
        }
    }
}
