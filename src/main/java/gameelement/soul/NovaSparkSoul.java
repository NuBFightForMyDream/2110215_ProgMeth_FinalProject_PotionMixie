package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

public class NovaSparkSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconNovaSparkSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    public NovaSparkSoul() {
        super(randomizeNovaSoulHP() , PotionType.NOVA_SPARK, "NovaSparkSoul.png");
    }

    // Methods
    public static int randomizeNovaSoulHP() {
        int novaHP = (randomizer.nextInt(6) * 20) + 20 ;
        return novaHP ;
    }
}
