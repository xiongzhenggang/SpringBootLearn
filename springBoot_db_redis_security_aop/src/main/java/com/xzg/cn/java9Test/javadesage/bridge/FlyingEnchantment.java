package com.xzg.cn.java9Test.javadesage.bridge;

import java.util.logging.Logger;

public class FlyingEnchantment implements Enchantment {

    private static final Logger LOGGER = Logger.getLogger(FlyingEnchantment.class.getName());
    @Override
    public void onActivate() {
        LOGGER.info("The item begins to glow faintly.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item flies and strikes the enemies finally returning to owner's hand.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("The item's glow fades.");
    }
}
