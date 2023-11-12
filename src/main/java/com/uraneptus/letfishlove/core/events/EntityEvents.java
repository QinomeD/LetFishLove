package com.uraneptus.letfishlove.core.events;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import com.uraneptus.letfishlove.common.entity.FishBreedGoal;
import com.uraneptus.letfishlove.common.entity.FishBreedingUtil;
import com.uraneptus.letfishlove.common.entity.FishLayRoeGoal;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = LetFishLoveMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {

    @SubscribeEvent
    public static void onEnityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemInHand = player.getItemInHand(hand);
        Entity target = event.getTarget();
        Level level = event.getLevel();

        if (target instanceof AbstractFish fish) {
            String regName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType()).getPath();
            TagKey<Item> temptationItems = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/" + regName));
            if (Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).isKnownTagName(temptationItems) && itemInHand.is(temptationItems)) {
                AbstractFishCap fishCap = FishBreedingUtil.getFishCap(fish);
                if (fishCap.canFallInLove()) {
                    fishCap.setInLove(fish, player, level);
                    FishBreedingUtil.usePlayerItem(player, itemInHand);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof  AbstractFish fish) {
            //System.out.println("Is Pregnant: " + FishBreedingUtil.getFishCap(fish).isPregnant());
            //System.out.println("Can fall in love: " + FishBreedingUtil.getFishCap(fish).canFallInLove());
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractFish fish) {
            String regName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType()).getPath();
            TagKey<Item> temptationItems = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/" + regName));
            fish.goalSelector.addGoal(2, new TemptGoal(fish, 1.2D, Ingredient.of(temptationItems), false));
            fish.goalSelector.addGoal(3, new FishBreedGoal(fish, 1.0D));
            fish.goalSelector.addGoal(3, new FishLayRoeGoal(fish));
        }
    }
}
