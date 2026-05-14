package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Dream Mist soul with random HP and Dream Mist weakness.
 */
public class DreamMistSoul extends BaseSoul implements BeAttackedAble {

    // Attributes
    private int iconDreamMistSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates a Dream Mist soul with randomized HP.
     */
    public DreamMistSoul() {
        super(randomizeDreamSoulHP() , PotionType.DREAM_MIST, "DreamMistSoul.png");
    }

    // Methods
    /**
     * Generates HP for a Dream Mist soul.
     *
     * @return random HP in the Dream Mist range
     */
    public static int randomizeDreamSoulHP() {
        int dreamHP = (randomizer.nextInt(4) * 20) + 20 ;
        return dreamHP ;
    }

}
