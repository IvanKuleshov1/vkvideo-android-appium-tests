package pages;

import core.Gestures;
import core.Waits;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.Duration;

public class VkVideoPage {

    private final AndroidDriver driver;

    private final By loader = By.id("com.vk.vkvideo:id/progress_view");

    public VkVideoPage(AndroidDriver driver) {
        this.driver = driver;
    }


    public void openAnyVideoFromFeed() {
        Waits.sleep(2500);

        int w = driver.manage().window().getSize().width;
        int h = driver.manage().window().getSize().height;

        int tapX = (int) (w * 0.30);
        int tapY = (int) (h * 0.33);

        Gestures.tap(driver, tapX, tapY);
        Waits.sleep(2500);
    }


    public boolean waitForLoader(int seconds) {
        return Waits.exists(driver, loader, Duration.ofSeconds(seconds));
    }


    public boolean confirmPlaybackWithinSeconds(int seconds) {
        long deadline = System.currentTimeMillis() + Duration.ofSeconds(seconds).toMillis();

        String prevProgress = null;

        while (System.currentTimeMillis() < deadline) {
            if (!driver.findElements(loader).isEmpty()) {
                String now = safeText(driver.findElement(loader));
                if (prevProgress != null && now != null && !now.equals(prevProgress)) {
                    return true;
                }
                prevProgress = now;
            }

            if (isFrameChanging()) return true;

            Waits.sleep(1200);
        }

        return false;
    }

    private String safeText(WebElement el) {
        try {
            String t = el.getText();
            return (t == null) ? "" : t.trim();
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isFrameChanging() {
        try {
            byte[] s1 = driver.getScreenshotAs(OutputType.BYTES);
            Waits.sleep(1200);
            byte[] s2 = driver.getScreenshotAs(OutputType.BYTES);

            BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(s1));
            BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(s2));
            if (img1 == null || img2 == null) return false;

            int width = img1.getWidth();
            int height = img1.getHeight();

            int cropY = (int) (height * 0.18);
            int cropH = (int) (height * 0.55);

            BufferedImage a = img1.getSubimage(0, cropY, width, cropH);
            BufferedImage b = img2.getSubimage(0, cropY, width, cropH);

            long diff = 0;
            int step = 6;

            for (int y = 0; y < a.getHeight(); y += step) {
                for (int x = 0; x < a.getWidth(); x += step) {
                    if (a.getRGB(x, y) != b.getRGB(x, y)) diff++;
                }
            }

            double total = (double) ((a.getWidth() / step) * (a.getHeight() / step));
            double pct = (diff / total) * 100.0;

            return pct > 0.35;
        } catch (Exception e) {
            return false;
        }
    }
}