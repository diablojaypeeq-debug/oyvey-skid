package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class MaceTrigger extends Module {
    private final Setting<Boolean> maceOnly = register(new Setting<>("MaceOnly", true));
    private final Setting<Float> range = register(new Setting<>("Range", 3.8f, 1.0f, 6.0f));

    public MaceTrigger() {
        super("MaceTrigger", "Attacks when looking at a target with a Mace", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (maceOnly.getValue() && mc.player.getMainHandStack().getItem() != Items.MACE) return;

        HitResult hit = mc.crosshairTarget;
        if (hit instanceof EntityHitResult entityHit) {
            if (mc.player.distanceTo(entityHit.getEntity()) <= range.getValue()) {
                if (mc.player.getAttackCooldownProgress(0) >= 1.0f) {
                    mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
                    mc.player.swingHand(mc.player.getActiveHand());
                }
            }
        }
    }
}
