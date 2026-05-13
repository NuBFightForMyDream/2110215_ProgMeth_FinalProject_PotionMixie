package gamelogic;

import gameelement.soul.BaseSoul;
import gameelement.soul.DreamMistSoul;
import gameelement.soul.EnergySplashSoul;
import gameelement.soul.NovaSparkSoul;
import gameelement.soul.PassionPopSoul;
import gameelement.soul.SoothingLoveSoul;
import gameelement.soul.StarloveCharmSoul;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoulGeneratorTest {

    @ParameterizedTest
    @MethodSource("allowedSoulTypesByLevel")
    void generateSoulOnlyUsesTypesUnlockedForLevel(int level, Set<Class<? extends BaseSoul>> allowedTypes) {
        SoulGenerator generator = new SoulGenerator();

        for (int run = 0; run < 200; run++) {
            BaseSoul soul = generator.generateSoul(level);

            assertNotNull(soul);
            assertTrue(
                    allowedTypes.contains(soul.getClass()),
                    "Unexpected soul type at level " + level + ": " + soul.getClass().getSimpleName()
            );
        }
    }

    private static Stream<Arguments> allowedSoulTypesByLevel() {
        Set<Class<? extends BaseSoul>> levelOne = Set.of(
                DreamMistSoul.class,
                EnergySplashSoul.class,
                NovaSparkSoul.class
        );
        Set<Class<? extends BaseSoul>> levelTwo = Set.of(
                DreamMistSoul.class,
                EnergySplashSoul.class,
                NovaSparkSoul.class,
                PassionPopSoul.class
        );
        Set<Class<? extends BaseSoul>> levelThree = Set.of(
                DreamMistSoul.class,
                EnergySplashSoul.class,
                NovaSparkSoul.class,
                PassionPopSoul.class,
                SoothingLoveSoul.class
        );
        Set<Class<? extends BaseSoul>> levelFourOrFive = Set.of(
                DreamMistSoul.class,
                EnergySplashSoul.class,
                NovaSparkSoul.class,
                PassionPopSoul.class,
                SoothingLoveSoul.class,
                StarloveCharmSoul.class
        );

        return Stream.of(
                Arguments.of(0, levelOne),
                Arguments.of(1, levelOne),
                Arguments.of(2, levelTwo),
                Arguments.of(3, levelThree),
                Arguments.of(4, levelFourOrFive),
                Arguments.of(5, levelFourOrFive)
        );
    }
}
