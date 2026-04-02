package net.tutla.gonzagamod;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class AutoUpdater {
    private static final String identifier = Gonzagamod.MOD_ID;
    private static final String REPO = "TutlaMC/"+identifier;
    public static volatile boolean done = false;
    public static volatile String updateContent;
    public static void checkAndUpdate() {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Updating.... fr");
                URL url = new URL("https://api.github.com/repos/" + REPO + "/releases/latest");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("User-Agent", identifier+"-updater");

                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                String latestVersion = extractJson(response, "tag_name").replace("v", "");
                String downloadUrl = extractDownloadUrl(response);

                updateContent =extractJson(response, "body");

                if (!latestVersion.equals(Gonzagamod.getVersion())) {
                    downloadUpdate(downloadUrl);
                }

            } catch (Exception e) {
                System.out.println("Update check failed: " + e.getMessage());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static String extractJson(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search) + search.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String extractDownloadUrl(String json) {
        return extractJson(json, "browser_download_url");
    }

    private static void downloadUpdate(String downloadUrl) throws Exception {
        // TODO: Make everything modular & introduce download installer

        // find current mod jar
        Path modsDir = FabricLoader.getInstance().getGameDir().resolve("mods");

        // find and delete old jar
        Files.list(modsDir)
                .filter(p -> p.getFileName().toString().startsWith(identifier))
                .forEach(p -> {
                    try { Files.delete(p);  }
                    catch (Exception e) { System.out.println("failed here lol"); }
                });

        // download new jar
        Path newJar = modsDir.resolve(identifier + "-latest.jar");
        URL url = new URL(downloadUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, newJar, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Update downloaded! Restart to apply.");
        done = true;
    }


}