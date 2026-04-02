package net.tutla.gonzagamod.downloader;

import net.fabricmc.loader.api.FabricLoader;
import net.tutla.gonzagamod.Gonzagamod;

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
        Downloader downloader = new Downloader();
        downloader.findAndDelete(identifier);
        downloader.download(downloadUrl,identifier + "-version.jar");
        System.out.println("Update downloaded! Restart to apply.");
        done = true;
    }


}