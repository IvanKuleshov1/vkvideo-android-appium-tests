package tests;

import core.DeviceState;
import core.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.VkStartPage;
import pages.VkVideoPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VkVideoPlaybackTest {

    private AndroidDriver driver;

    @AfterEach
    void tearDown() {
        try {
            DeviceState.setNetworkEnabled(true);
        } catch (Exception ignored) {
        }
        if (driver != null) driver.quit();
    }

    @Test
    void videoPlaybackPositiveAndNegative() {
        DeviceState.setNetworkEnabled(true);
        DeviceState.clearApp("com.vk.vkvideo");

        driver = DriverFactory.create();
        driver.activateApp("com.vk.vkvideo");

        VkStartPage start = new VkStartPage(driver);
        VkVideoPage video = new VkVideoPage(driver);

        // POSITIVE
        start.passGateIfPresent();
        video.openAnyVideoFromFeed();

        boolean playedOnline = video.confirmPlaybackWithinSeconds(45);
        assertTrue(playedOnline, "Playback was not confirmed while network is enabled");

        // NEGATIVE
        driver.activateApp("com.vk.vkvideo");
        start.passGateIfPresent();
        video.openAnyVideoFromFeed();

        DeviceState.setNetworkEnabled(false);

        boolean loaderAppeared = video.waitForLoader(90);
        assertTrue(loaderAppeared, "Loader progress_view did not appear after disabling network");

        boolean playedOffline = video.confirmPlaybackWithinSeconds(60);
        assertFalse(playedOffline, "Playback unexpectedly confirmed while network is disabled");
    }
}