package net.deechael.concentration.forge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.deechael.concentration.ConcentrationConstants;
import net.deechael.concentration.FullscreenMode;
import net.deechael.concentration.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class ConcentrationConfigForge implements Config {

    public final static ConcentrationConfigForge INSTANCE = new ConcentrationConfigForge();

    public static final ForgeConfigSpec SPECS;
    public static final ForgeConfigSpec.BooleanValue CUSTOMIZED;
    public static final ForgeConfigSpec.BooleanValue RELATED;
    public static final ForgeConfigSpec.IntValue X;
    public static final ForgeConfigSpec.IntValue Y;
    public static final ForgeConfigSpec.IntValue WIDTH;
    public static final ForgeConfigSpec.IntValue HEIGHT;
    public static final ForgeConfigSpec.EnumValue<FullscreenMode> FULLSCREEN;

    private static boolean loaded = false;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("concentration");

        CUSTOMIZED = builder.comment("Whether the window size and pos is customized")
                .define("customized", false);
        RELATED = builder.comment("Whether the window pos should related to the monitor")
                .define("related", false);

        X = builder.comment("X coordinate")
                .defineInRange("x", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Y = builder.comment("Y coordinate")
                .defineInRange("y", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        WIDTH = builder.comment("Width")
                .defineInRange("width", 800, 1, Integer.MAX_VALUE);
        HEIGHT = builder.comment("Height")
                .defineInRange("height", 600, 1, Integer.MAX_VALUE);
        FULLSCREEN = builder.comment("Fullscreen mode")
                .defineEnum("fullscreen", FullscreenMode.BORDERLESS);

        builder.pop();

        SPECS = builder.build();
    }

    public static ConcentrationConfigForge ensureLoaded() {
        if (!loaded) {
            ConcentrationConstants.LOGGER.info("Loading Concentration Config");

            Path path = FMLPaths.CONFIGDIR.get().resolve("concentration-client.toml");
            CommentedFileConfig config = CommentedFileConfig.builder(path)
                    .sync()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();
            config.load();

            SPECS.setConfig(config);

            loaded = true;
        }
        return INSTANCE;
    }

    private ConcentrationConfigForge() {
    }

    @Override
    public FullscreenMode getFullscreenMode() {
        return FULLSCREEN.get();
    }

    @Override
    public void setFullscreenMode(FullscreenMode fullscreenMode) {
        FULLSCREEN.set(fullscreenMode);
    }

    @Override
    public void save() {
        SPECS.save();
    }

}
