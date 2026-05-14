package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Passion Pop soul with random HP and Passion Pop weakness.
 */
public class PassionPopSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconPassionPopSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates a Passion Pop soul with randomized HP.
     */
    public PassionPopSoul() {
        super(randomizePassionSoulHP() , PotionType.PASSION_POP, "PassionPopSoul.png");
    }

    // Methods
    /**
     * Generates HP for a Passion Pop soul.
     *
     * @return random HP in the Passion Pop range
     */
    public static int randomizePassionSoulHP() {
        int passionHP = (randomizer.nextInt(4) * 40) + 40 ;
        return passionHP ;
    }
}
