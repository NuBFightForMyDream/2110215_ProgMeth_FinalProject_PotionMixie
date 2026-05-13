package gamelogic;

import gameelement.soul.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SoulGenerator {
    // Attributes (for each level)
    private final Random randomizer = new Random(); // define randomizer

    // Constructors
        // No need , call via methods

    // Methods
    public BaseSoul generateSoul(int level) {
        // This method will be call based on player level
        // soul pool = unique types of souls stored
        List< Supplier<BaseSoul> > soulPool = getSoulPoolByLevel(level);
        int index = randomizer.nextInt(soulPool.size());
        return soulPool.get(index).get(); // get index => get soul type from that index
    }

    private List<Supplier<BaseSoul>> getSoulPoolByLevel(int level) {
        // Note : using Supplier for creating new object every time called
        List<Supplier<BaseSoul>> soulPool = new ArrayList<>();
        // Level 1 : Unlocking 3 base soul types
        soulPool.add(DreamMistSoul::new);
        soulPool.add(EnergySplashSoul::new);
        soulPool.add(NovaSparkSoul::new);

        if (level >= 2) {
            soulPool.add(PassionPopSoul::new);
        }
        if (level >= 3) {
            soulPool.add(SoothingLoveSoul::new);
        }
        if (level >= 4) {
            soulPool.add(StarloveCharmSoul::new);
        }
        return soulPool;
    }

}
