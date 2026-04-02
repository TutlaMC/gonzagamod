package net.tutla.gonzagamod.downloader;

public class AutoSetup {
    public static volatile boolean done = false;
    public static volatile String messageContent;
    public static volatile String title;

    private static final AutoUpdater updater = new AutoUpdater();
    private static final ModrinthDependencyDownloader modDownloader = new ModrinthDependencyDownloader();

    public static void run() {

        Thread thread = new Thread(() -> {
            updater.checkAndUpdate();
            modDownloader.downloadAll();

            setMessage();
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static void setMessage(){
        boolean d = true;
        if (!updater.done && modDownloader.isDoneDownloading()){
            title = "Mods Updated!";
            messageContent = modDownloader.messageString;
        } else if (updater.done && !modDownloader.isDoneDownloading()){
            title = "GonzagaMod Updated";
            messageContent = updater.updateContent;
        } else if (updater.done && modDownloader.isDoneDownloading()){
            title = "Mods & GonzagaMod Updated!";
            messageContent = updater.updateContent + "\n\n" + modDownloader.messageString;
        } else {
            d = false;
        }

        done = d;
    }
}
