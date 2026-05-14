package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Energy Splash soul with random HP and Energy Splash weakness.
 */
public class EnergySplashSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconDEnergySplashSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates an Energy Splash soul with randomized HP.
     */
    public EnergySplashSoul() {
        super(randomizeEnergySoulHP() , PotionType.ENERGY_SPLASH, "EnergySplashSoul.png");
    }

    // Methods
    /**
     * Generates HP for an Energy Splash soul.
     *
     * @return random HP in the Energy Splash range
     */
    public static int randomizeEnergySoulHP() {
        int energyHP = (randomizer.nextInt(5) * 20) + 20 ;
        return energyHP;
    }
}
