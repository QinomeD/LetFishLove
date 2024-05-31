package com.uraneptus.letfishlove;

import net.minecraftforge.common.ForgeConfigSpec;

public class LFLConfig {
    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_HATCH_DURATION;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_HATCH_DURATION;

    public static final ForgeConfigSpec.ConfigValue<Integer> COD_HATCH_AMOUNT_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> COD_HATCH_AMOUNT_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> PUFFERFISH_HATCH_AMOUNT_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> PUFFERFISH_HATCH_AMOUNT_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> SALMON_HATCH_AMOUNT_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SALMON_HATCH_AMOUNT_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> TROPICAL_FISH_HATCH_AMOUNT_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> TROPICAL_FISH_HATCH_AMOUNT_MAX;
    public static final ForgeConfigSpec COMMON;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        MIN_HATCH_DURATION = COMMON_BUILDER.comment("The minimum duration it takes for fish roe to hatch (default = 4800)").define("min duration", 3600);
        MAX_HATCH_DURATION = COMMON_BUILDER.comment("The maximum duration it takes for fish roe to hatch (default = 8000)").define("max duration", 12000);

        COD_HATCH_AMOUNT_MIN = COMMON_BUILDER.comment("The minimum amount of cod that can hatch from cod roe (default = 2)").define("cod min amount", 2);
        COD_HATCH_AMOUNT_MAX = COMMON_BUILDER.comment("The maximum amount of cod that can hatch from cod roe (default = 4)").define("cod max amount", 4);

        PUFFERFISH_HATCH_AMOUNT_MIN = COMMON_BUILDER.comment("The minimum amount of pufferfish that can hatch from pufferfish roe (default = 1)").define("pufferfish min amount", 1);
        PUFFERFISH_HATCH_AMOUNT_MAX = COMMON_BUILDER.comment("The maximum amount of pufferfish that can hatch from pufferfish roe (default = 2)").define("pufferfish max amount", 2);

        SALMON_HATCH_AMOUNT_MIN = COMMON_BUILDER.comment("The minimum amount of salmon that can hatch from salmon roe (default = 2)").define("salmon min amount", 2);
        SALMON_HATCH_AMOUNT_MAX = COMMON_BUILDER.comment("The maximum amount of salmon that can hatch from salmon roe (default = 4)").define("salmon max amount", 4);

        TROPICAL_FISH_HATCH_AMOUNT_MIN = COMMON_BUILDER.comment("The minimum amount of tropical fish that can hatch from tropical fish roe (default = 2)").define("tropical fish min amount", 2);
        TROPICAL_FISH_HATCH_AMOUNT_MAX = COMMON_BUILDER.comment("The maximum amount of tropical fish that can hatch from tropical fish roe (default = 4)").define("tropical fish max amount", 4);

        COMMON = COMMON_BUILDER.build();
    }
}
