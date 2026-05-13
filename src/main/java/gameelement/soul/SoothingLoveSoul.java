package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class SoothingLoveSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconSoothingLoveSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public SoothingLoveSoul() {
        super(randomizeSoothingSoulHP() , PotionType.SOOTHING_LOVE, "SoothingLoveSoul.png");
    }

    // Methods
    public static int randomizeSoothingSoulHP() {
        int soothingHP = (randomizer.nextInt(5) * 40) + 40 ;
        return soothingHP ;
    }

}
