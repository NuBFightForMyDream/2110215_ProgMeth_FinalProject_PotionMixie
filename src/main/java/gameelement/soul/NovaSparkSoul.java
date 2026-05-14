package gameelement.soul;

import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Nova Spark soul with random HP and Nova Spark weakness.
 */
public class NovaSparkSoul extends BaseSoul implements BeAttackedAble {
    // Attributes
    private int iconNovaSparkSoul ;
    private static final Random randomizer = new Random();

    // Constructors
    /**
     * Creates a Nova Spark soul with randomized HP.
     */
    public NovaSparkSoul() {
        super(randomizeNovaSoulHP() , PotionType.NOVA_SPARK, "NovaSparkSoul.png");
    }

    // Methods
    /**
     * Generates HP for a Nova Spark soul.
     *
     * @return random HP in the Nova Spark range
     */
    public static int randomizeNovaSoulHP() {
        int novaHP = (randomizer.nextInt(6) * 20) + 20 ;
        return novaHP ;
    }
}
