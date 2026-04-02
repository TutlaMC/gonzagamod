package net.tutla.gonzagamod.downloader;

import net.tutla.gonzagamod.Gonzagamod;

public class AutoUpdater {
    private static final String identifier = Gonzagamod.MOD_ID;
    private static final String REPO = "TutlaMC/"+identifier;
    public static volatile boolean done = false;
    public static volatile String updateContent;
    public static void checkAndUpdate() {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Updating.... fr");
                Fetcher fetcher = new Fetcher();
                String response = fetcher.fetch("https://api.github.com/repos/" + REPO + "/releases/latest", identifier+"-updater");

                updateContent = fetcher.extractJson(response, "body");

                String latestVersion = fetcher.extractJson(response, "tag_name").replace("v", "");
                String downloadUrl = extractDownloadUrl(fetcher, response);
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

    private static String extractDownloadUrl(Fetcher fetcher, String json) {
        return fetcher.extractJson(json, "browser_download_url");
    }

    private static void downloadUpdate(String downloadUrl) throws Exception {
        Downloader downloader = new Downloader();
        downloader.findAndDelete(identifier);
        downloader.download(downloadUrl,identifier + "-latest.jar");
        System.out.println("Update downloaded! Restart to apply.");
        done = true;
    }


}