package net.tutla.gonzagamod.downloader.util;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Downloader {
    private final Path mainDir = FabricLoader.getInstance().getGameDir();
    private final Path modsDir = mainDir.resolve("mods");

    public void download(String downloadUrl, String newFilename) throws Exception {
        Path newFile = modsDir.resolve(newFilename);
        URL url = new URL(downloadUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void findAndDelete(String findString) throws IOException {
        Files.list(modsDir)
                .filter(p -> p.getFileName().toString().startsWith(findString))
                .forEach(p -> {
                    try { Files.delete(p);  }
                    catch (Exception e) { System.out.println("failed here lol"); }
                });
    }

    public boolean isMatchInFolder(String pattern) throws IOException {
        return Files.list(modsDir)
                .anyMatch(p -> p.getFileName().toString().matches(pattern));
    }
}
