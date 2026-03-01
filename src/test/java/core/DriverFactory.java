package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.time.Duration;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static AndroidDriver create() {
        String appiumUrl = System.getenv().getOrDefault("APPIUM_URL", "http://127.0.0.1:4723/");
        String deviceName = System.getenv().getOrDefault("DEVICE_NAME", "emulator-5554");
        String udid = System.getenv().getOrDefault("UDID", "emulator-5554");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName)
                .setUdid(udid)
                .setAutoGrantPermissions(true)
                .amend("noReset", true)
                .amend("newCommandTimeout", 180);

        try {
            AndroidDriver driver = new AndroidDriver(new URL(appiumUrl), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return driver;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create AndroidDriver for VK Video. APPIUM_URL=" + appiumUrl, e);
        }
    }
}