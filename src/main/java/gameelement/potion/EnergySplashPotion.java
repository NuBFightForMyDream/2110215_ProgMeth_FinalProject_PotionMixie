package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;

import java.util.List;

public class EnergySplashPotion extends BasePotion implements MergeAble {

    // Constructors
    public EnergySplashPotion() {
        super("Energy Splash Potion", "EnergySplash.png");
        setPotionRecipe( List.of(SparkEmberElement.class , DewDropElement.class) );
        setPotionType(PotionType.ENERGY_SPLASH);
        setPotionPower(20);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ((elements.get(0) instanceof SparkEmberElement && elements.get(1) instanceof DewDropElement) ||
                        (elements.get(0) instanceof DewDropElement && elements.get(1) instanceof SparkEmberElement));
    }

}
