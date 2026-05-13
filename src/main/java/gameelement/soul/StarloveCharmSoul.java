package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class StarloveCharmSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconStarloveCharmSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public StarloveCharmSoul() {
        super(randomizeStarloveSoulHP() , PotionType.STARLOVE_CHARM, "StarLoveCharmSoul.png");
    }

    // Methods
    public static int randomizeStarloveSoulHP() {
        int soothingHP = (randomizer.nextInt(6) * 40) + 40 ;
        return soothingHP ;
    }
}
