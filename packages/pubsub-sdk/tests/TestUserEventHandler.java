import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import dev.begon.hexagonal.core.sdk.messaging.UserMessaging;

public class TestUserEventHandler implements Consumer<UserMessaging> {
    private UserMessaging lastReceivedUserMessaging;
    private CountDownLatch latch;

    public TestUserEventHandler(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void accept(UserMessaging userMessaging) {
        lastReceivedUserMessaging = userMessaging;
        latch.countDown(); // Count down the latch to signal message processing
    }

    public UserMessaging getLastReceivedUserMessaging() {
        return lastReceivedUserMessaging;
    }
}
