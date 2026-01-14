package com.ultramega.playershells.gui;

import com.ultramega.playershells.container.CentrifugeContainerMenu;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import static com.ultramega.playershells.PlayerShells.MODID;

public class CentrifugeScreen extends AbstractContainerScreen<CentrifugeContainerMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MODID, "textures/gui/centrifuge.png");
    private static final ResourceLocation PROGRESS_BG = new ResourceLocation(MODID, "textures/gui/sprites/widget/progress_bar_background.png");
    private static final ResourceLocation PROGRESS_FILL = new ResourceLocation(MODID, "textures/gui/sprites/widget/green_progress_bar.png");
    private static final int ENERGY_BAR_HEIGHT = 52;

    public CentrifugeScreen(final CentrifugeContainerMenu menu, final Inventory playerInventory, final Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(final GuiGraphics graphics, final int mouseX, final int mouseY, final float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(final GuiGraphics graphics, final float partialTicks, final int mouseX, final int mouseY) {
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.getXSize(), this.getYSize());

        // Draw energy bar
        graphics.blit(BACKGROUND, this.leftPos + 8, this.topPos + 18, 176, 0, 6, ENERGY_BAR_HEIGHT);
        final int energyAmount = this.getMenu().getEnergyStored();
        final int energyMaxStored = this.getMenu().getBlockEntity().energyStorage.getMaxEnergyStored();
        final int energyLevel = (int) (energyAmount * (ENERGY_BAR_HEIGHT / (float) energyMaxStored));
        graphics.blit(BACKGROUND, this.leftPos + 8, this.topPos + 18, 8, 18, 6, ENERGY_BAR_HEIGHT - energyLevel);

        // Draw progress
        final int barX = this.leftPos + 79;
        final int barY = this.topPos + 34;
        final int barWidth = 24;
        final int barHeight = 16;
        graphics.blit(PROGRESS_BG, barX - 1, barY - 1, 0, 0, barWidth + 2, barHeight + 2, 82, 18);
        final int filledWidth = Mth.clamp(Mth.ceil(this.menu.getProcessingProgress() * barWidth), 0, barWidth);
        graphics.blit(PROGRESS_FILL, barX, barY, 0, 0, filledWidth, barHeight, 80, 16);
    }

    @Override
    protected void renderTooltip(final GuiGraphics graphics, final int mouseX, final int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        if (this.isHovering(7, 18, 8, ENERGY_BAR_HEIGHT + 1, mouseX, mouseY)) {
            graphics.renderTooltip(this.font, List.of(Component.translatable("gui.playershells.energy",
                this.getMenu().getEnergyStored()).getVisualOrderText()), mouseX, mouseY);
        }
    }
}
