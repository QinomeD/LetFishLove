package com.uraneptus.letfishlove.core.events;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import com.uraneptus.letfishlove.common.entity.FishBreedGoal;
import com.uraneptus.letfishlove.common.entity.FishBreedingUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
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
            TagKey<Item> temptationItems = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/" + regName));
            if (Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).isKnownTagName(temptationItems) && itemInHand.is(temptationItems)) {
                AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
                    if (cap.inLove <= 0) {
                        RandomSource random = fish.getRandom();
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        event.setCanceled(true);

                        cap.inLove = 600;
                        cap.loveCause = player.getUUID();
                        level.broadcastEntityEvent(fish, (byte)18);
                        for(int i = 0; i < 7; ++i) {
                            double d0 = random.nextGaussian() * 0.02D;
                            double d1 = random.nextGaussian() * 0.02D;
                            double d2 = random.nextGaussian() * 0.02D;
                            level.addParticle(ParticleTypes.HEART, fish.getRandomX(1.0D), fish.getRandomY() + 0.5D, fish.getRandomZ(1.0D), d0, d1, d2);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractFish fish) {
            Level level = fish.getLevel();

            if (FishBreedingUtil.breed) {
                AbstractFishCap.getCapOptional(fish).ifPresent(cap -> cap.inLove = 0);
            }
            /*
            System.out.println(FishBreedingUtil.isInLove(fish));
            if (entity.getLevel().isClientSide) {
                System.out.println(FishBreedingUtil.isInLove(fish));
                System.out.println(FishBreedingUtil.getLoveCause(level, fish));
            }
 */
            if (level.isClientSide()) {
                AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
                    System.out.println("On client: " + cap.inLove);
                });
            }
            if (!level.isClientSide()) {
                AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
                    System.out.println("On Server: " + cap.inLove);
                });
            }

        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractFish fish) {
            String regName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType()).getPath();
            TagKey<Item> temptationItems = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/" + regName));
            fish.goalSelector.addGoal(2, new TemptGoal(fish, 1.2D, Ingredient.of(temptationItems), false));
            fish.goalSelector.addGoal(3, new FishBreedGoal(fish, 1.0D));
        }
    }
}
