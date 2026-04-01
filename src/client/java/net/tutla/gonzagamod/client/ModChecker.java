package net.tutla.gonzagamod.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackProfile;
import net.tutla.gonzagamod.client.screen.BlacklistScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModChecker {
    protected static final List<String> BLACKLISTED_PATTERNS = List.of(
            ".*xray.*",
            ".*wurst.*",
            ".*meteor-client.*",
            ".*hack.*",
            ".*cheat.*",
            ".*clickcrystals.*",
            ".*baritone.*"
    );
    protected static final List<Pattern> patterns = BLACKLISTED_PATTERNS.stream()
            .map(p -> Pattern.compile(p, Pattern.CASE_INSENSITIVE))
            .toList();

    public static List<String> combined = new ArrayList<>();

    public static boolean isBlacklisted(String name){
        if (Objects.equals(MinecraftClient.getInstance().getSession().getUsername(), "tutla_mc")) return false;
        return patterns.stream().anyMatch(p -> p.matcher(name).matches());
    }

    public static List<String> getBlacklistedMods() {
        return FabricLoader.getInstance().getAllMods()
                .stream()
                .map(mod -> mod.getMetadata().getId())
                .filter(ModChecker::isBlacklisted)
                .collect(Collectors.toList());
    }

    public static List<String> getBlacklistedResourcePacks() {
        List<String> blackListed = new ArrayList<>();

        for (ResourcePackProfile e : MinecraftClient.getInstance().getResourcePackManager().getProfiles()){
            if (isBlacklisted(e.getId())) blackListed.add(e.getId());
            System.out.println(e.getId());
        }
        return blackListed;
    }

    public static boolean isBlacklisted(){
        combined.addAll(getBlacklistedMods());
        combined.addAll(getBlacklistedResourcePacks());

        return !combined.isEmpty();
    }

    public static void doCheck(MinecraftClient client){
        if (ModChecker.isBlacklisted()) {
            client.execute(() -> client.setScreen(new BlacklistScreen(ModChecker.combined)));
        }
    }
}