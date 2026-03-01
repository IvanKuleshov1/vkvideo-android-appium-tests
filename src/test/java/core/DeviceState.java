package core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DeviceState {

    public static void clearApp(String packageName) {
        run(Duration.ofSeconds(20), "adb", "shell", "pm", "clear", packageName);
    }

    public static void setNetworkEnabled(boolean enabled) {
        if (enabled) {
            run(Duration.ofSeconds(20), "adb", "shell", "svc", "wifi", "enable");
            run(Duration.ofSeconds(20), "adb", "shell", "svc", "data", "enable");
        } else {
            run(Duration.ofSeconds(20), "adb", "shell", "svc", "wifi", "disable");
            run(Duration.ofSeconds(20), "adb", "shell", "svc", "data", "disable");
        }
    }

    private static void run(Duration timeout, String... cmd) {
        try {
            Process p = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start();

            StringBuilder out = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) out.append(line).append('\n');
            }

            boolean finished = p.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
            if (!finished) {
                p.destroyForcibly();
                throw new RuntimeException("Command timed out: " + String.join(" ", cmd));
            }

            if (p.exitValue() != 0) {
                throw new RuntimeException("Command failed: " + String.join(" ", cmd) + "\n" + out);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run: " + String.join(" ", cmd), e);
        }
    }
}