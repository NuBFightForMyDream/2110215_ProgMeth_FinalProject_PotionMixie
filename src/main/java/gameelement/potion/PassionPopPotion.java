package gameelement.potion;

import gameelement.element.*;

import java.util.List;

/**
 * Potion made from Heart Berry and Spark Ember.
 */
public class PassionPopPotion extends BasePotion implements MergeAble {

    // Constructors
    /**
     * Creates a Passion Pop potion with its recipe, type, and power.
     */
    public PassionPopPotion() {
        super("Passion Pop Potion", "PassionPop.png");
        setPotionRecipe( List.of(HeartBerryElement.class , SparkEmberElement.class) );
        setPotionType(PotionType.PASSION_POP);
        setPotionPower(40);
    }

    // Methods
    /**
     * Checks whether the selected elements can merge into Passion Pop.
     *
     * @param elements selected elements from the merge station
     * @return true when Heart Berry and Spark Ember are selected
     */
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof SparkEmberElement) ||
                  (elements.get(0) instanceof SparkEmberElement && elements.get(1) instanceof HeartBerryElement) ) ;
    }

}
