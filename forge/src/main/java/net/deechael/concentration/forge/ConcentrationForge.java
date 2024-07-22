package net.deechael.concentration.forge;

import net.deechael.concentration.Concentration;
import net.deechael.concentration.ConcentrationConstants;
import net.deechael.concentration.config.ConcentrationConfigScreen;
import net.deechael.concentration.forge.compat.EmbeddiumCompat;
import net.deechael.concentration.forge.config.ConcentrationConfigForge;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Mod entrance for NeoForge of Concentration
 *
 * @author DeeChael
 */
@Mod(value = ConcentrationConstants.MOD_ID)
public class ConcentrationForge {

    public ConcentrationForge() {
        Concentration.init();

        ConcentrationConfigForge.ensureLoaded();

        if (ModList.get().isLoaded("embeddium")) {
            EmbeddiumCompat.init();
        }

        if (FMLEnvironment.dist.isClient()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
                    (minecraft, parent) -> new ConcentrationConfigScreen(Component.literal(ConcentrationConstants.MOD_NAME), parent) {

                        @Override
                        public void save() {
                            ConcentrationConfigForge.ensureLoaded().save();
                            Concentration.toggleFullScreenMode(minecraft.options, minecraft.options.fullscreen().get());
                        }

                        @Override
                        public void addElements() {
                            addOption(OptionInstance.createBoolean("concentration.config.customization.enabled",
                                    ConcentrationConfigForge.CUSTOMIZED.get(),
                                    ConcentrationConfigForge.CUSTOMIZED::set));
                            addOption(OptionInstance.createBoolean("concentration.config.customization.related",
                                    ConcentrationConfigForge.RELATED.get(),
                                    ConcentrationConfigForge.RELATED::set));

                            addIntField(Component.translatable("concentration.config.customization.x"),
                                    ConcentrationConfigForge.X,
                                    ConcentrationConfigForge.X::set);
                            addIntField(Component.translatable("concentration.config.customization.y"),
                                    ConcentrationConfigForge.Y,
                                    ConcentrationConfigForge.Y::set);
                            addIntField(Component.translatable("concentration.config.customization.width"),
                                    ConcentrationConfigForge.WIDTH,
                                    ConcentrationConfigForge.WIDTH::set);
                            addIntField(Component.translatable("concentration.config.customization.height"),
                                    ConcentrationConfigForge.HEIGHT,
                                    ConcentrationConfigForge.HEIGHT::set);
                        }
                    }
            ));
        }
    }

}