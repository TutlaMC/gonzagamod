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
    // ai generated all the patterns btw
    protected static final List<String> BLACKLISTED_PATTERNS = List.of(
            ".*xray.*",
            ".*x[-_]ray.*",
            ".*xrayclient.*",

            ".*wurst.*",
            ".*wurstclient.*",
            ".*wurst[-_]client.*",

            // === Meteor ===
            ".*meteor[-_]?client.*",
            ".*meteorclient.*",

            // === Baritone (pathfinding) ===
            ".*baritone.*",
            ".*cabaletta.*",           // Baritone's dev alias

            // === Generic hack/cheat terms ===
            ".*hack.*",
            ".*cheat.*",
            ".*cheats.*",
            ".*hacked.*",
            ".*hacking.*",
            ".*hax.*",                 // Common leet spelling
            ".*h4x.*",
            ".*exploit.*",
            ".*exploits.*",

            // === Well-known cheat clients ===
            ".*liquidbounce.*",
            ".*liquid[-_]bounce.*",
            ".*impact.*client.*",
            ".*impactclient.*",
            ".*aristois.*",
            ".*hyperium.*",
            ".*sigma.*client.*",
            ".*sigmaclient.*",
            ".*wolfram.*",             // Wolfram cheat client
            ".*nodus.*",               // Legacy Minecraft cheat client
            ".*killaura.*",
            ".*kill[-_]aura.*",
            ".*forgehax.*",
            ".*labymod.*hack.*",
            ".*inertia.*client.*",
            ".*inertiaclient.*",
            ".*future.*client.*",
            ".*futureclient.*",
            ".*rusherhack.*",
            ".*rusher.*hack.*",
            ".*phobos.*",              // Phobos cheat client
            ".*entropy.*client.*",
            ".*pandora.*client.*",
            ".*rise.*client.*",        // Rise client
            ".*riseclient.*",
            ".*exodus.*client.*",
            ".*novoline.*",
            ".*anima.*client.*",

            // === Autoclickers and input manipulation ===
            ".*autoclicker.*",
            ".*auto[-_]clicker.*",
            ".*clickgui.*",
            ".*click[-_]gui.*",

            // === Aimbots, combat cheats ===
            ".*aimbot.*",
            ".*aim[-_]bot.*",
            ".*triggerbot.*",
            ".*trigger[-_]bot.*",
            ".*criticals.*cheat.*",
            ".*reach.*hack.*",
            ".*antikb.*",              // Anti-knockback
            ".*anti[-_]kb.*",
            ".*antiknockback.*",
            ".*speedhack.*",
            ".*speed[-_]hack.*",
            ".*bhop.*",                // Bunny hop exploit

            // === ESP / visual cheats ===
            ".*wallhack.*",
            ".*wall[-_]hack.*",
            ".*esp[-_]hack.*",
            ".*entityesp.*",
            ".*entity[-_]esp.*",
            ".*playeresp.*",
            ".*chestfinder.*",
            ".*chest[-_]finder.*",
            ".*tracers.*cheat.*",

            // === Movement exploits ===
            ".*fly[-_]?hack.*",
            ".*noclip.*",
            ".*no[-_]clip.*",
            ".*scaffold[-_]?hack.*",
            ".*nofall.*",
            ".*no[-_]fall.*",
            ".*step[-_]?hack.*",

            // === Obfuscation/evasion attempts ===
            ".*ch3at.*",
            ".*h4ck.*",
            ".*1llegal.*",             // "illegal" with leet
            ".*ch34t.*",
            ".*haxor.*",
            ".*haxxor.*",

            // === Common injection/loader patterns ===
            ".*injector.*",
            ".*classloader.*cheat.*",
            ".*mixin.*cheat.*",
            ".*agent.*cheat.*",
            ".*patcher.*cheat.*",

            // === Macro tools commonly used for cheating ===
            ".*macromod.*",
            ".*macro[-_]mod.*",
            ".*easymap.*",

            // === Utilities commonly bundled with cheats ===
            ".*raidersb.*",
            ".*minecraftcheat.*",
            ".*mccheats.*",
            ".*mccheat.*"
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