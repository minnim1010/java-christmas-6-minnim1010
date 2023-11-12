package christmas;

import christmas.common.config.ChristmasConfig;
import christmas.controller.MainController;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new ChristmasConfig().mainController();
        mainController.run();
    }
}
