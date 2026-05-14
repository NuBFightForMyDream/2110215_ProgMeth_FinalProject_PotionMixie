package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Starlove Charm soul with random HP and Starlove Charm weakness.
 */
public class StarloveCharmSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconStarloveCharmSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates a Starlove Charm soul with randomized HP.
     */
    public StarloveCharmSoul() {
        super(randomizeStarloveSoulHP() , PotionType.STARLOVE_CHARM, "StarLoveCharmSoul.png");
    }

    // Methods
    /**
     * Generates HP for a Starlove Charm soul.
     *
     * @return random HP in the Starlove Charm range
     */
    public static int randomizeStarloveSoulHP() {
        int soothingHP = (randomizer.nextInt(6) * 40) + 40 ;
        return soothingHP ;
    }
}
