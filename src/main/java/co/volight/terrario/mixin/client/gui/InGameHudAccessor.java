package co.volight.terrario.mixin.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(InGameHud.class)
@Environment(value= EnvType.CLIENT)
public interface InGameHudAccessor {
    @Accessor
    Random getRandom();

    @Accessor
    MinecraftClient getClient();

    @Invoker
    PlayerEntity callGetCameraPlayer();

    @Invoker
    TextRenderer callGetTextRenderer();

    @Accessor
    int getRenderHealthValue();

    @Accessor
    void setRenderHealthValue(int renderHealthValue);

    @Accessor
    long getLastHealthCheckTime();

    @Accessor
    void setLastHealthCheckTime(long lastHealthCheckTime);

    @Accessor
    long getHeartJumpEndTick();

    @Accessor
    void setHeartJumpEndTick(long heartJumpEndTick);

    @Accessor
    int getScaledWidth();

    @Accessor
    int getScaledHeight();

    @Accessor
    float getAutosaveIndicatorAlpha();

    @Accessor
    float getLastAutosaveIndicatorAlpha();

    @Accessor
    Map<MessageType, List<ClientChatListener>> getListeners();

    @Accessor
    float getSpyglassScale();

    @Accessor
    ItemRenderer getItemRenderer();

    @Accessor
    ChatHud getChatHud();

    @Accessor
    int getTicks();

    @Accessor
    Text getOverlayMessage();

    @Accessor
    int getOverlayRemaining();

    @Accessor
    boolean isOverlayTinted();

    @Accessor
    int getHeldItemTooltipFade();

    @Accessor
    ItemStack getCurrentStack();

    @Accessor
    DebugHud getDebugHud();

    @Accessor
    SubtitlesHud getSubtitlesHud();

    @Accessor
    SpectatorHud getSpectatorHud();

    @Accessor
    PlayerListHud getPlayerListHud();

    @Accessor
    BossBarHud getBossBarHud();

    @Accessor
    int getTitleTotalTicks();

    @Accessor
    Text getTitle();

    @Accessor
    Text getSubtitle();

    @Accessor
    int getTitleFadeInTicks();

    @Accessor
    int getTitleRemainTicks();

    @Accessor
    int getTitleFadeOutTicks();

    @Accessor
    int getLastHealthValue();

    @Accessor
    void setLastHealthValue(int lastHealthValue);

    @Invoker
    LivingEntity callGetRiddenEntity();

    @Invoker
    int callGetHeartCount(LivingEntity entity);

    @Invoker
    int callGetHeartRows(int heartCount);
}
