package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class DreamMistSoul extends BaseSoul implements BeAttackedAble {

    // Attributes
    private int iconDreamMistSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public DreamMistSoul() {
        super(randomizeDreamSoulHP() , PotionType.DREAM_MIST, "DreamMistSoul.png");
    }

    // Methods
    public static int randomizeDreamSoulHP() {
        int dreamHP = (randomizer.nextInt(4) * 20) + 20 ;
        return dreamHP ;
    }

}
