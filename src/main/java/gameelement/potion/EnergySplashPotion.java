package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;

import java.util.List;

/**
 * Potion made from Spark Ember and Dew Drop.
 */
public class EnergySplashPotion extends BasePotion implements MergeAble {

    // Constructors
    /**
     * Creates an Energy Splash potion with its recipe, type, and power.
     */
    public EnergySplashPotion() {
        super("Energy Splash Potion", "EnergySplash.png");
        setPotionRecipe( List.of(SparkEmberElement.class , DewDropElement.class) );
        setPotionType(PotionType.ENERGY_SPLASH);
        setPotionPower(20);
    }

    // Methods
    /**
     * Checks whether the selected elements can merge into Energy Splash.
     *
     * @param elements selected elements from the merge station
     * @return true when Spark Ember and Dew Drop are selected
     */
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ((elements.get(0) instanceof SparkEmberElement && elements.get(1) instanceof DewDropElement) ||
                        (elements.get(0) instanceof DewDropElement && elements.get(1) instanceof SparkEmberElement));
    }

}
