package gamelogic;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.HeartBerryElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;
import gameelement.potion.BasePotion;
import gameelement.potion.DreamMistPotion;
import gameelement.potion.EnergySplashPotion;
import gameelement.potion.NovaSparkPotion;
import gameelement.potion.PassionPopPotion;
import gameelement.potion.PotionType;
import gameelement.potion.SoothingLovePotion;
import gameelement.potion.StarloveCharmPotion;
import gameelement.soul.BaseSoul;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests game logic construction, merging, attacks, timer, and belt behavior.
 */
class GameLogicTest {

    /**
     * Tests that the GameLogic constructor initializes level rules and starting state.
     */
    @ParameterizedTest
    @CsvSource({
            "1, 5, 90",
            "2, 10, 180",
            "3, 20, 175",
            "4, 30, 300",
            "5, 67, 450",
            "6, 10, 90"
    })
    void constructorInitializesLevelRulesAndSoulBelt(int level, int expectedRequiredSouls, int expectedTime) {
        GameLogic logic = new GameLogic(level);

        assertEquals(level, logic.getLevel());
        assertEquals(expectedRequiredSouls, logic.getTotalSoulRequired());
        assertEquals(expectedTime, logic.getTimeLeft());
        assertEquals(0, logic.getDefeatedSoulCount());
        assertFalse(logic.isGameOver());
        assertFalse(logic.isLevelComplete());
        assertEquals(8, logic.getSoulBeltAsList().size());
        assertNotNull(logic.getFirstSoul());
    }

    /**
     * Tests that adding matching elements returns the expected potion.
     */
    @ParameterizedTest
    @MethodSource("mergeRecipes")
    void addElementToMergeReturnsPotionForMatchingRecipe(
            Supplier<BaseElement> firstElement,
            Supplier<BaseElement> secondElement,
            Class<? extends BasePotion> expectedPotionType
    ) {
        GameLogic logic = new GameLogic(1);

        BasePotion potion = logic.addElementToMerge(firstElement.get());
        assertNull(potion);

        potion = logic.addElementToMerge(secondElement.get());

        assertInstanceOf(expectedPotionType, potion);
        assertTrue(logic.getMergeSlots().isEmpty());
    }

    /**
     * Tests that merge recipes work when ingredients are selected in reverse order.
     */
    @ParameterizedTest
    @MethodSource("mergeRecipes")
    void addElementToMergeAcceptsRecipeInReverseOrder(
            Supplier<BaseElement> firstElement,
            Supplier<BaseElement> secondElement,
            Class<? extends BasePotion> expectedPotionType
    ) {
        GameLogic logic = new GameLogic(1);

        assertNull(logic.addElementToMerge(secondElement.get()));
        BasePotion potion = logic.addElementToMerge(firstElement.get());

        assertInstanceOf(expectedPotionType, potion);
        assertTrue(logic.getMergeSlots().isEmpty());
    }

    /**
     * Tests that an invalid two-element merge clears the selection.
     */
    @Test
    void addElementToMergeClearsSlotsWhenTwoElementsDoNotMatchAnyRecipe() {
        GameLogic logic = new GameLogic(1);

        assertNull(logic.addElementToMerge(new HeartBerryElement()));
        BasePotion potion = logic.addElementToMerge(new HeartBerryElement());

        assertNull(potion);
        assertTrue(logic.getMergeSlots().isEmpty());
    }

    /**
     * Tests that clearMergeSlots removes the current element selection.
     */
    @Test
    void clearMergeSlotsRemovesCurrentSelection() {
        GameLogic logic = new GameLogic(1);
        logic.addElementToMerge(new HeartBerryElement());

        logic.clearMergeSlots();

        assertTrue(logic.getMergeSlots().isEmpty());
    }

    /**
     * Tests that attacking with an invalid soul index is rejected.
     */
    @Test
    void attackSoulAtRejectsInvalidIndex() {
        GameLogic logic = new GameLogic(1);

        assertFalse(logic.attackSoulAt(-1, new DreamMistPotion()));
        assertFalse(logic.attackSoulAt(100, new DreamMistPotion()));
        assertEquals(0, logic.getDefeatedSoulCount());
    }

    /**
     * Tests that a wrong potion type does not damage the target soul.
     */
    @Test
    void attackSoulAtWithWrongPotionDoesNotDamageSoul() {
        GameLogic logic = new GameLogic(1);
        BaseSoul target = logic.getFirstSoul();
        target.setSoulHP(100);
        BasePotion wrongPotion = nonMatchingPotion(target.getWeaknessType());

        assertTrue(logic.attackSoulAt(0, wrongPotion));

        assertEquals(100, target.getSoulHP());
        assertEquals(0, logic.getDefeatedSoulCount());
    }

    /**
     * Tests that a matching potion defeats a soul and refills the belt.
     */
    @Test
    void attackSoulAtWithMatchingPotionDefeatsSoulAndRefillsBelt() {
        GameLogic logic = new GameLogic(1);
        BaseSoul target = logic.getFirstSoul();
        BasePotion matchingPotion = matchingPotion(target.getWeaknessType());
        target.setSoulHP(matchingPotion.getPotionPower());

        assertTrue(logic.attackSoulAt(0, matchingPotion));

        assertEquals(1, logic.getDefeatedSoulCount());
        assertEquals(8, logic.getSoulBeltAsList().size());
        assertFalse(logic.isLevelComplete());
    }

    /**
     * Tests that defeating the required souls completes the level and ends the game.
     */
    @Test
    void defeatingRequiredSoulsCompletesLevelAndEndsGame() {
        GameLogic logic = new GameLogic(1);

        for (int i = 0; i < logic.getTotalSoulRequired(); i++) {
            BaseSoul target = logic.getFirstSoul();
            BasePotion potion = matchingPotion(target.getWeaknessType());
            target.setSoulHP(potion.getPotionPower());

            assertTrue(logic.attackSoulAt(0, potion));
        }

        assertTrue(logic.isLevelComplete());
        assertTrue(logic.isGameOver());
        assertEquals(5, logic.getDefeatedSoulCount());
    }

    /**
     * Tests that the timer ends the game when time reaches zero.
     */
    @Test
    void updateTimerEndsGameWhenTimeRunsOut() {
        GameLogic logic = new GameLogic(1);

        for (int i = 0; i < 90; i++) {
            logic.updateTimer();
        }

        assertEquals(0, logic.getTimeLeft());
        assertTrue(logic.isTimeUp());
        assertTrue(logic.isGameOver());
    }

    /**
     * Tests that the soul belt getter returns a copy instead of the internal queue.
     */
    @Test
    void soulBeltListIsACopy() {
        GameLogic logic = new GameLogic(1);
        List<BaseSoul> belt = logic.getSoulBeltAsList();

        belt.clear();

        assertEquals(8, logic.getSoulBeltAsList().size());
    }

    /**
     * Provides all merge recipes and expected potion classes.
     *
     * @return merge recipe test data
     */
    private static Stream<Arguments> mergeRecipes() {
        return Stream.of(
                Arguments.of((Supplier<BaseElement>) StarDustElement::new, (Supplier<BaseElement>) DewDropElement::new, DreamMistPotion.class),
                Arguments.of((Supplier<BaseElement>) SparkEmberElement::new, (Supplier<BaseElement>) DewDropElement::new, EnergySplashPotion.class),
                Arguments.of((Supplier<BaseElement>) StarDustElement::new, (Supplier<BaseElement>) SparkEmberElement::new, NovaSparkPotion.class),
                Arguments.of((Supplier<BaseElement>) HeartBerryElement::new, (Supplier<BaseElement>) SparkEmberElement::new, PassionPopPotion.class),
                Arguments.of((Supplier<BaseElement>) HeartBerryElement::new, (Supplier<BaseElement>) DewDropElement::new, SoothingLovePotion.class),
                Arguments.of((Supplier<BaseElement>) HeartBerryElement::new, (Supplier<BaseElement>) StarDustElement::new, StarloveCharmPotion.class)
        );
    }

    /**
     * Creates a potion that matches the given weakness type.
     *
     * @param potionType weakness type
     * @return matching potion instance
     */
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

    /**
     * Creates a potion that does not match the given weakness type.
     *
     * @param potionType weakness type
     * @return non-matching potion instance
     */
    private static BasePotion nonMatchingPotion(PotionType potionType) {
        if (potionType == PotionType.DREAM_MIST) {
            return new EnergySplashPotion();
        }
        return new DreamMistPotion();
    }
}
