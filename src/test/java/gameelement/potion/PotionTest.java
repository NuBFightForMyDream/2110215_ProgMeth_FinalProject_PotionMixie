package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.HeartBerryElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests potion constructors, recipes, and potion setter behavior.
 */
class PotionTest {

    /**
     * Tests that each potion constructor sets metadata, recipe, type, and power.
     */
    @ParameterizedTest
    @MethodSource("potions")
    void potionHasExpectedMetadata(
            Supplier<BasePotion> factory,
            String expectedName,
            String expectedImagePath,
            PotionType expectedType,
            int expectedPower,
            Class<? extends BaseElement> firstIngredient,
            Class<? extends BaseElement> secondIngredient
    ) {
        BasePotion potion = factory.get();

        assertEquals(expectedName, potion.getPotionName());
        assertEquals(expectedImagePath, potion.getImagePath());
        assertEquals(expectedType, potion.getPotionType());
        assertEquals(expectedPower, potion.getPotionPower());
        assertEquals(List.of(firstIngredient, secondIngredient), potion.getPotionRecipe());
        assertInstanceOf(MergeAble.class, potion);
    }

    /**
     * Tests that recipe matching accepts both ingredient orders.
     */
    @ParameterizedTest
    @MethodSource("potions")
    void potionRecipeMatchesBothIngredientOrders(
            Supplier<BasePotion> factory,
            String expectedName,
            String expectedImagePath,
            PotionType expectedType,
            int expectedPower,
            Class<? extends BaseElement> firstIngredient,
            Class<? extends BaseElement> secondIngredient
    ) {
        BasePotion potion = factory.get();
        BaseElement first = createElement(firstIngredient);
        BaseElement second = createElement(secondIngredient);

        assertTrue(potion.potionMatchesRecipe(List.of(first, second)));
        assertTrue(potion.potionMatchesRecipe(List.of(second, first)));
    }

    /**
     * Tests that recipe matching rejects missing, repeated, or extra ingredients.
     */
    @ParameterizedTest
    @MethodSource("potions")
    void potionRecipeRejectsWrongOrIncompleteIngredients(
            Supplier<BasePotion> factory,
            String expectedName,
            String expectedImagePath,
            PotionType expectedType,
            int expectedPower,
            Class<? extends BaseElement> firstIngredient,
            Class<? extends BaseElement> secondIngredient
    ) {
        BasePotion potion = factory.get();
        BaseElement first = createElement(firstIngredient);
        BaseElement wrong = createWrongElement(firstIngredient, secondIngredient);

        assertFalse(potion.potionMatchesRecipe(List.of()));
        assertFalse(potion.potionMatchesRecipe(List.of(first)));
        assertFalse(potion.potionMatchesRecipe(List.of(first, first)));
        assertFalse(potion.potionMatchesRecipe(List.of(first, wrong)));
        assertFalse(potion.potionMatchesRecipe(List.of(first, createElement(secondIngredient), wrong)));
    }

    /**
     * Tests that the potion power setter clamps negative values to zero.
     */
    @Test
    void potionPowerCannotBeNegative() {
        BasePotion potion = new DreamMistPotion();

        potion.setPotionPower(-10);

        assertEquals(0, potion.getPotionPower());
    }

    /**
     * Provides potion constructors and expected metadata.
     *
     * @return potion constructor test data
     */
    private static Stream<Arguments> potions() {
        return Stream.of(
                Arguments.of(
                        (Supplier<BasePotion>) DreamMistPotion::new,
                        "Dream Mist Potion",
                        "DreamMist.png",
                        PotionType.DREAM_MIST,
                        20,
                        StarDustElement.class,
                        DewDropElement.class
                ),
                Arguments.of(
                        (Supplier<BasePotion>) EnergySplashPotion::new,
                        "Energy Splash Potion",
                        "EnergySplash.png",
                        PotionType.ENERGY_SPLASH,
                        20,
                        SparkEmberElement.class,
                        DewDropElement.class
                ),
                Arguments.of(
                        (Supplier<BasePotion>) NovaSparkPotion::new,
                        "Nova Spark Potion",
                        "NovaSpark.png",
                        PotionType.NOVA_SPARK,
                        20,
                        StarDustElement.class,
                        SparkEmberElement.class
                ),
                Arguments.of(
                        (Supplier<BasePotion>) PassionPopPotion::new,
                        "Passion Pop Potion",
                        "PassionPop.png",
                        PotionType.PASSION_POP,
                        40,
                        HeartBerryElement.class,
                        SparkEmberElement.class
                ),
                Arguments.of(
                        (Supplier<BasePotion>) SoothingLovePotion::new,
                        "Soothing Love Potion",
                        "SoothingLove.png",
                        PotionType.SOOTHING_LOVE,
                        40,
                        HeartBerryElement.class,
                        DewDropElement.class
                ),
                Arguments.of(
                        (Supplier<BasePotion>) StarloveCharmPotion::new,
                        "Starlove Charm Potion",
                        "StarLoveCharm.png",
                        PotionType.STARLOVE_CHARM,
                        40,
                        HeartBerryElement.class,
                        StarDustElement.class
                )
        );
    }

    /**
     * Creates an element instance from its class for recipe tests.
     *
     * @param elementType element class to instantiate
     * @return new element instance
     */
    private static BaseElement createElement(Class<? extends BaseElement> elementType) {
        if (elementType == HeartBerryElement.class) {
            return new HeartBerryElement();
        }
        if (elementType == StarDustElement.class) {
            return new StarDustElement();
        }
        if (elementType == SparkEmberElement.class) {
            return new SparkEmberElement();
        }
        if (elementType == DewDropElement.class) {
            return new DewDropElement();
        }
        throw new IllegalArgumentException("Unsupported element type: " + elementType);
    }

    /**
     * Finds an element type that is not part of the expected recipe.
     *
     * @param firstIngredient first recipe ingredient
     * @param secondIngredient second recipe ingredient
     * @return element not used by the recipe
     */
    private static BaseElement createWrongElement(
            Class<? extends BaseElement> firstIngredient,
            Class<? extends BaseElement> secondIngredient
    ) {
        for (Class<? extends BaseElement> candidate : List.of(
                HeartBerryElement.class,
                StarDustElement.class,
                SparkEmberElement.class,
                DewDropElement.class
        )) {
            if (candidate != firstIngredient && candidate != secondIngredient) {
                return createElement(candidate);
            }
        }
        throw new IllegalStateException("No wrong ingredient available");
    }
}
