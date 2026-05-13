package gameelement.soul;

import gameelement.potion.BasePotion;
import gameelement.potion.DreamMistPotion;
import gameelement.potion.EnergySplashPotion;
import gameelement.potion.NovaSparkPotion;
import gameelement.potion.PassionPopPotion;
import gameelement.potion.PotionType;
import gameelement.potion.SoothingLovePotion;
import gameelement.potion.StarloveCharmPotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoulTest {

    @ParameterizedTest
    @MethodSource("souls")
    void soulHasExpectedMetadataAndHpRange(
            Supplier<BaseSoul> factory,
            PotionType expectedWeakness,
            String expectedImagePath,
            int minimumHp,
            int maximumHp,
            int hpStep
    ) {
        for (int run = 0; run < 100; run++) {
            BaseSoul soul = factory.get();

            assertEquals(expectedWeakness, soul.getWeaknessType());
            assertEquals(expectedImagePath, soul.getImagePath());
            assertTrue(soul.getSoulHP() >= minimumHp);
            assertTrue(soul.getSoulHP() <= maximumHp);
            assertEquals(0, soul.getSoulHP() % hpStep);
            assertInstanceOf(BeAttackedAble.class, soul);
        }
    }

    @ParameterizedTest
    @MethodSource("souls")
    void matchingPotionDamagesSoul(
            Supplier<BaseSoul> factory,
            PotionType expectedWeakness,
            String expectedImagePath,
            int minimumHp,
            int maximumHp,
            int hpStep
    ) {
        BaseSoul soul = factory.get();
        BasePotion potion = matchingPotion(expectedWeakness);
        soul.setSoulHP(100);

        assertTrue(soul.canbeHitByPotion(potion));
        soul.takeDamageFromPotion(potion);

        assertEquals(100 - potion.getPotionPower(), soul.getSoulHP());
    }

    @ParameterizedTest
    @MethodSource("souls")
    void nonMatchingPotionDoesNotDamageSoul(
            Supplier<BaseSoul> factory,
            PotionType expectedWeakness,
            String expectedImagePath,
            int minimumHp,
            int maximumHp,
            int hpStep
    ) {
        BaseSoul soul = factory.get();
        BasePotion potion = nonMatchingPotion(expectedWeakness);
        soul.setSoulHP(100);

        assertFalse(soul.canbeHitByPotion(potion));
        soul.takeDamageFromPotion(potion);

        assertEquals(100, soul.getSoulHP());
    }

    @Test
    void soulHpCannotBeNegative() {
        BaseSoul soul = new DreamMistSoul();

        soul.setSoulHP(-1);

        assertEquals(0, soul.getSoulHP());
    }

    @Test
    void soulHpStopsAtZeroWhenDamageIsHigherThanCurrentHp() {
        BaseSoul soul = new DreamMistSoul();
        soul.setSoulHP(10);

        soul.takeDamageFromPotion(new DreamMistPotion());

        assertEquals(0, soul.getSoulHP());
    }

    private static Stream<Arguments> souls() {
        return Stream.of(
                Arguments.of((Supplier<BaseSoul>) DreamMistSoul::new, PotionType.DREAM_MIST, "DreamMistSoul.png", 20, 80, 20),
                Arguments.of((Supplier<BaseSoul>) EnergySplashSoul::new, PotionType.ENERGY_SPLASH, "EnergySplashSoul.png", 20, 100, 20),
                Arguments.of((Supplier<BaseSoul>) NovaSparkSoul::new, PotionType.NOVA_SPARK, "NovaSparkSoul.png", 20, 120, 20),
                Arguments.of((Supplier<BaseSoul>) PassionPopSoul::new, PotionType.PASSION_POP, "PassionPopSoul.png", 40, 160, 40),
                Arguments.of((Supplier<BaseSoul>) SoothingLoveSoul::new, PotionType.SOOTHING_LOVE, "SoothingLoveSoul.png", 40, 200, 40),
                Arguments.of((Supplier<BaseSoul>) StarloveCharmSoul::new, PotionType.STARLOVE_CHARM, "StarLoveCharmSoul.png", 40, 240, 40)
        );
    }

    private static BasePotion matchingPotion(PotionType potionType) {
        return switch (potionType) {
            case DREAM_MIST -> new DreamMistPotion();
            case ENERGY_SPLASH -> new EnergySplashPotion();
            case NOVA_SPARK -> new NovaSparkPotion();
            case PASSION_POP -> new PassionPopPotion();
            case SOOTHING_LOVE -> new SoothingLovePotion();
            case STARLOVE_CHARM -> new StarloveCharmPotion();
        };
    }

    private static BasePotion nonMatchingPotion(PotionType potionType) {
        if (potionType == PotionType.DREAM_MIST) {
            return new EnergySplashPotion();
        }
        return new DreamMistPotion();
    }
}
