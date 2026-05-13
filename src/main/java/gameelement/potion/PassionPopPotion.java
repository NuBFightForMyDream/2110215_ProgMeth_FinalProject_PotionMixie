package gameelement.potion;

import gameelement.element.*;

import java.util.List;

public class PassionPopPotion extends BasePotion implements MergeAble {

    // Constructors
    public PassionPopPotion() {
        super("Passion Pop Potion", "PassionPop.png");
        setPotionRecipe( List.of(HeartBerryElement.class , SparkEmberElement.class) );
        setPotionType(PotionType.PASSION_POP);
        setPotionPower(40);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof SparkEmberElement) ||
                  (elements.get(0) instanceof SparkEmberElement && elements.get(1) instanceof HeartBerryElement) ) ;
    }

}
