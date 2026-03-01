package pages;

import core.Waits;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.time.Duration;

public class VkStartPage {

    private final AndroidDriver driver;

    private final By skipAuth = By.id("com.vk.vkvideo:id/fast_login_tertiary_btn");

    private final By googleCancel = By.id("com.google.android.gms:id/cancel");

    private final By closeButtonByText =
            By.xpath("//*[@class='android.widget.Button' and (@text='Close' or @content-desc='Close')]");
    private final By closeButtonGeneric =
            By.xpath("//*[@content-desc='Close' or @content-desc='Dismiss' or @text='Dismiss']");

    private final By notNow =
            By.xpath("//*[@text='Not now' or @content-desc='Not now']");
    private final By continueBtn =
            By.xpath("//*[@text='Continue' or @content-desc='Continue']");

    public VkStartPage(AndroidDriver driver) {
        this.driver = driver;
    }


    public void passGateIfPresent() {
        Waits.sleep(1200);

        if (Waits.exists(driver, googleCancel, Duration.ofSeconds(2))) {
            driver.findElement(googleCancel).click();
            Waits.sleep(700);
        }

        if (Waits.exists(driver, notNow, Duration.ofSeconds(2))) {
            driver.findElement(notNow).click();
            Waits.sleep(700);
        } else if (Waits.exists(driver, continueBtn, Duration.ofSeconds(2))) {
            driver.findElement(continueBtn).click();
            Waits.sleep(700);
        }

        if (Waits.exists(driver, skipAuth, Duration.ofSeconds(3))) {
            driver.findElement(skipAuth).click();
            Waits.sleep(900);
        }

        if (Waits.exists(driver, closeButtonByText, Duration.ofSeconds(2))) {
            driver.findElement(closeButtonByText).click();
            Waits.sleep(700);
        } else if (Waits.exists(driver, closeButtonGeneric, Duration.ofSeconds(2))) {
            driver.findElement(closeButtonGeneric).click();
            Waits.sleep(700);
        }
    }
}