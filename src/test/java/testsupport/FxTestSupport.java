package testsupport;

import javafx.application.Platform;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility methods for running JavaFX-related tests on the FX application thread.
 */
public final class FxTestSupport {
    private static final AtomicBoolean STARTED = new AtomicBoolean(false);

    /**
     * Prevents creating utility class instances.
     */
    private FxTestSupport() {
    }

    /**
     * Starts the JavaFX runtime once for all tests.
     */
    public static void startJavaFx() {
        if (STARTED.get()) {
            return;
        }

        synchronized (FxTestSupport.class) {
            if (STARTED.get()) {
                return;
            }

            CountDownLatch latch = new CountDownLatch(1);
            try {
                Platform.startup(latch::countDown);
            } catch (IllegalStateException alreadyStarted) {
                latch.countDown();
            }
            await(latch);
            STARTED.set(true);
        }
    }

    /**
     * Runs a callable on the JavaFX application thread and returns its result.
     *
     * @param action action to run on the FX thread
     * @param <T> result type
     * @return action result
     */
    public static <T> T runOnFxThread(Callable<T> action) {
        startJavaFx();

        if (Platform.isFxApplicationThread()) {
            try {
                return action.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<T> result = new AtomicReference<>();
        AtomicReference<Throwable> error = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                result.set(action.call());
            } catch (Throwable throwable) {
                error.set(throwable);
            } finally {
                latch.countDown();
            }
        });

        await(latch);

        if (error.get() != null) {
            if (error.get() instanceof AssertionError assertionError) {
                throw assertionError;
            }
            throw new RuntimeException(error.get());
        }

        return result.get();
    }

    /**
     * Runs a runnable on the JavaFX application thread.
     *
     * @param action action to run on the FX thread
     */
    public static void runOnFxThread(Runnable action) {
        runOnFxThread(() -> {
            action.run();
            return null;
        });
    }

    /**
     * Waits for a JavaFX latch and fails the test if it times out.
     *
     * @param latch latch to wait for
     */
    private static void await(CountDownLatch latch) {
        try {
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new AssertionError("Timed out waiting for JavaFX thread");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted while waiting for JavaFX thread", e);
        }
    }
}
