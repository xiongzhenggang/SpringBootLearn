package com.xzg.cn.java9Test.javadesage.bridge;

import java.util.logging.Logger;

public class SoulEatingEnchantment implements Enchantment {

    private static final Logger LOGGER = Logger.getLogger(SoulEatingEnchantment.class.getName());
    @Override
    public void onActivate() {
        LOGGER.info("The item spreads bloodlust.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item eats the soul of enemies.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("Bloodlust slowly disappears.");
    }
}