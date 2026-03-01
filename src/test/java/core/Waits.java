package core;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public final class Waits {

    private Waits() {
    }

    public static boolean exists(AndroidDriver driver, By locator, Duration timeout) {
        try {
            return new FluentWait<>(driver)
                    .withTimeout(timeout)
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(Exception.class)
                    .until(d -> !d.findElements(locator).isEmpty());
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> T until(AndroidDriver driver, Duration timeout,
                              java.util.function.Function<AndroidDriver, T> condition) {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class)
                .until(condition);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}