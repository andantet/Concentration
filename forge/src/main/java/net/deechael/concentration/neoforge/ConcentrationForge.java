package net.deechael.concentration.neoforge;

import net.deechael.concentration.Concentration;
import net.deechael.concentration.ConcentrationConstants;
import net.deechael.concentration.config.ConcentrationConfigScreen;
import net.deechael.concentration.neoforge.compat.EmbeddiumCompat;
import net.deechael.concentration.neoforge.config.ConcentrationConfigNeoForge;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
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

    public ConcentrationForge(ModContainer container, IEventBus eventBus) {
        Concentration.init();

        if (ModList.get().isLoaded("embeddium")) {
            EmbeddiumCompat.init();
        }

        if (FMLEnvironment.dist.isClient()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
                    (minecraft, parent) -> new ConcentrationConfigScreen(Component.literal(ConcentrationConstants.MOD_NAME), parent) {

                        @Override
                        public void save() {
                            ConcentrationConfigNeoForge.SPECS.save();
                            Concentration.toggleFullScreenMode(minecraft.options, minecraft.options.fullscreen().get());
                        }

                        @Override
                        public void addElements() {
                            addOption(OptionInstance.createBoolean("concentration.config.customization.enabled",
                                    ConcentrationConfigNeoForge.CUSTOMIZED.get(),
                                    ConcentrationConfigNeoForge.CUSTOMIZED::set));
                            addOption(OptionInstance.createBoolean("concentration.config.customization.related",
                                    ConcentrationConfigNeoForge.RELATED.get(),
                                    ConcentrationConfigNeoForge.RELATED::set));

                            addIntField(Component.translatable("concentration.config.customization.x"),
                                    ConcentrationConfigNeoForge.X,
                                    ConcentrationConfigNeoForge.X::set);
                            addIntField(Component.translatable("concentration.config.customization.y"),
                                    ConcentrationConfigNeoForge.Y,
                                    ConcentrationConfigNeoForge.Y::set);
                            addIntField(Component.translatable("concentration.config.customization.width"),
                                    ConcentrationConfigNeoForge.WIDTH,
                                    ConcentrationConfigNeoForge.WIDTH::set);
                            addIntField(Component.translatable("concentration.config.customization.height"),
                                    ConcentrationConfigNeoForge.HEIGHT,
                                    ConcentrationConfigNeoForge.HEIGHT::set);
                        }
                    }
            ));
        }
    }

}