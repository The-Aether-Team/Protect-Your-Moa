package com.aetherteam.protect_your_moa.client.gui.screen.inventory;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.inventory.menu.MoaInventoryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * [CODE COPY] - {@link net.minecraft.client.gui.screens.inventory.HorseInventoryScreen}.
 */
public class MoaInventoryScreen extends AbstractContainerScreen<MoaInventoryMenu> {
    private static final ResourceLocation MOA_INVENTORY_LOCATION = new ResourceLocation(ProtectYourMoa.MODID, "textures/gui/container/moa.png");
    /** The EntityHorse whose inventory is currently being accessed. */
    private final Moa moa;
    /** The mouse x-position recorded during the last rendered frame. */
    private float xMouse;
    /** The mouse y-position recorded during the last rendered frame. */
    private float yMouse;

    public MoaInventoryScreen(MoaInventoryMenu menu, Inventory playerInventory, Moa moa) {
        super(menu, playerInventory, moa.getDisplayName());
        this.moa = moa;
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(MOA_INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        MoaArmor.get(this.moa).ifPresent(moaArmor -> {
            if (moaArmor.hasChest()) {
                guiGraphics.blit(MOA_INVENTORY_LOCATION, i + 79, j + 17, 0, this.imageHeight, 7 * 18, 54);
            }
        });

        if (this.moa.isSaddleable()) {
            guiGraphics.blit(MOA_INVENTORY_LOCATION, i + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        guiGraphics.blit(MOA_INVENTORY_LOCATION, i + 7, j + 35, 0, this.imageHeight + 54, 18, 18);

        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 51, j + 63, 17, (float)(i + 51) - this.xMouse, (float)(j + 75 - 47) - this.yMouse, this.moa);
    }

    /**
     * Renders the graphical user interface (GUI) element.
     * @param guiGraphics the GuiGraphics object used for rendering.
     * @param mouseX the x-coordinate of the mouse cursor.
     * @param mouseY the y-coordinate of the mouse cursor.
     * @param partialTick the partial tick time.
     */
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
