package core;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public final class Gestures {

    private Gestures() {
    }

    public static void tap(AndroidDriver driver, int x, int y) {

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence tap = new Sequence(finger, 1);

        tap.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        x,
                        y
                )
        );

        tap.addAction(
                finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())
        );

        tap.addAction(
                finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())
        );

        driver.perform(Collections.singletonList(tap));
    }
}