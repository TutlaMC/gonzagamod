package net.tutla.gonzagamod.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.tutla.gonzagamod.Gonzagamod;
import net.tutla.gonzagamod.client.screen.UpdateScreen;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class AutoUpdater {
    private static final String identifier = "gonzagamod";
    private static final String REPO = "TutlaMC/"+identifier;
    private static boolean showUpdateScreen = false;
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

                // parse tag_name and assets download url
                String latestVersion = extractJson(response, "tag_name").replace("v", "");
                String downloadUrl = extractDownloadUrl(response);

                if (!latestVersion.equals(Gonzagamod.version)) {
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
        String search = "\"browser_download_url\":\"";
        int start = json.indexOf(search) + search.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static void downloadUpdate(String downloadUrl) throws Exception {
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
        showUpdateScreen = true;
        System.out.println("Update downloaded! Restart to apply.");

    }

    public static void showUpdateScreen(){
        if(showUpdateScreen){
            showUpdateScreen=false;
            MinecraftClient.getInstance().setScreen(new UpdateScreen());
        }
    }
}