package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class EnergySplashSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconDEnergySplashSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public EnergySplashSoul() {
        super(randomizeEnergySoulHP() , PotionType.ENERGY_SPLASH, "EnergySplashSoul.png");
    }

    // Methods
    public static int randomizeEnergySoulHP() {
        int energyHP = (randomizer.nextInt(5) * 20) + 20 ;
        return energyHP;
    }
}
