package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class PassionPopSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconPassionPopSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public PassionPopSoul() {
        super(randomizePassionSoulHP() , PotionType.PASSION_POP, "PassionPopSoul.png");
    }

    // Methods
    public static int randomizePassionSoulHP() {
        int passionHP = (randomizer.nextInt(4) * 40) + 40 ;
        return passionHP ;
    }
}
