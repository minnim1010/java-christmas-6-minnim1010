package christmas.common.config;

import christmas.controller.MainController;
import christmas.controller.PromotionApplyController;
import christmas.controller.ReservationController;
import christmas.domain.promotion.ChristmasPromotion;
import christmas.service.ChristmasPromotionService;
import christmas.view.ErrorView;
import christmas.view.PromotionApplyResultView;
import christmas.view.ReservationView;
import christmas.view.io.reader.ConsoleReader;
import christmas.view.io.reader.Reader;
import christmas.view.io.writer.ConsoleWriter;
import christmas.view.io.writer.Writer;

public class ChristmasConfig {

    public MainController mainController() {
        Reader reader = reader();
        Writer writer = writer();

        ReservationView reservationView = reservationView(reader, writer);
        PromotionApplyResultView promotionApplyResultView = promotionApplyResultView(writer);
        ErrorView errorView = errorView(writer);

        ChristmasPromotionService christmasPromotionService = christmasPromotionApplyService(
                christmasPromotion());
        ReservationController reservationController = reservationController(reservationView, errorView);
        PromotionApplyController promotionApplyController = promotionApplyController(promotionApplyResultView,
                christmasPromotionService);
        return new MainController(reservationController, promotionApplyController, errorView);
    }

    private Reader reader() {
        return new ConsoleReader();
    }

    private Writer writer() {
        return new ConsoleWriter();
    }

    private ReservationView reservationView(Reader reader, Writer writer) {
        return new ReservationView(reader, writer);
    }

    private PromotionApplyResultView promotionApplyResultView(Writer writer) {
        return new PromotionApplyResultView(writer);
    }

    private ErrorView errorView(Writer writer) {
        return new ErrorView(writer);
    }

    private ChristmasPromotion christmasPromotion() {
        return new ChristmasPromotion();
    }

    private ChristmasPromotionService christmasPromotionApplyService(ChristmasPromotion christmasPromotion) {
        return new ChristmasPromotionService(christmasPromotion);
    }

    private ReservationController reservationController(ReservationView reservationView, ErrorView errorView) {
        return new ReservationController(reservationView, errorView);
    }

    private PromotionApplyController promotionApplyController(PromotionApplyResultView promotionApplyResultView,
                                                              ChristmasPromotionService promotionApplyService) {
        return new PromotionApplyController(promotionApplyResultView, promotionApplyService);
    }
}