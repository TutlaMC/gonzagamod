package net.tutla.gonzagamod.downloader;

import net.tutla.gonzagamod.downloader.util.Downloader;

import java.util.ArrayList;
import java.util.List;

public class ModrinthDependencyDownloader {
    private boolean doneDownloading = false;
    public String messageString = "";
    private final List<Dependancy> dependencies = List.of(
            new Dependancy(
                    "simple-voice-chat",
                    "https://cdn.modrinth.com/data/9eGKb6K1/versions/TE9flmQz/voicechat-fabric-1.21.11-2.6.14.jar",
                    "(?=.*voice)(?=.*chat).*"
                    )
    );
    private final List<Dependancy> doneDeps = new ArrayList<>();

    public boolean isDoneDownloading() {
        return doneDownloading;
    }

    public void doneDownloading() {
        this.doneDownloading = true;
        doneDeps.forEach((Dependancy d) -> messageString += "- " + d.name + "\n");
    }

    public void downloadAll(){
        try {
            for (Dependancy d : dependencies) {
                downloadMod(d);
            }
            if (!doneDeps.isEmpty()) {
                doneDownloading();
            }
        } catch (Exception ignored){

        }
    }

    public void downloadMod(Dependancy dep){
        try {
            Downloader downloader = new Downloader();

            if (downloader.isMatchInFolder(dep.pattern)) return;
            downloader.download(dep.url, dep.name+".jar");
            doneDeps.add(dep);
        } catch (Exception ignored) {}
    }
}
