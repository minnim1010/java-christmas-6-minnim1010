package christmas.testConfig;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;

public abstract class IntegrationTestConfig extends NsTest {

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
