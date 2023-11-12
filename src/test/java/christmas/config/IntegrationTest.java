package christmas.config;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;

public abstract class IntegrationTest extends NsTest {
    protected static final String ERROR_MESSAGE = "[ERROR]";

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
