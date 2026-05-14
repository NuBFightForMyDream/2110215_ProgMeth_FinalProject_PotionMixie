package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Soothing Love soul with random HP and Soothing Love weakness.
 */
public class SoothingLoveSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconSoothingLoveSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates a Soothing Love soul with randomized HP.
     */
    public SoothingLoveSoul() {
        super(randomizeSoothingSoulHP() , PotionType.SOOTHING_LOVE, "SoothingLoveSoul.png");
    }

    // Methods
    /**
     * Generates HP for a Soothing Love soul.
     *
     * @return random HP in the Soothing Love range
     */
    public static int randomizeSoothingSoulHP() {
        int soothingHP = (randomizer.nextInt(5) * 40) + 40 ;
        return soothingHP ;
    }

}
