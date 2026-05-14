package gameelement.potion;

import gameelement.element.*;

import java.util.List;

/**
 * Potion made from Heart Berry and Dew Drop.
 */
public class SoothingLovePotion extends BasePotion implements MergeAble {

    // Constructors
    /**
     * Creates a Soothing Love potion with its recipe, type, and power.
     */
    public SoothingLovePotion() {
        super("Soothing Love Potion", "SoothingLove.png");
        setPotionRecipe( List.of(HeartBerryElement.class , DewDropElement.class) );
        setPotionType(PotionType.SOOTHING_LOVE);
        setPotionPower(40);
    }

    // Methods
    /**
     * Checks whether the selected elements can merge into Soothing Love.
     *
     * @param elements selected elements from the merge station
     * @return true when Heart Berry and Dew Drop are selected
     */
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof DewDropElement) ||
                  (elements.get(0) instanceof DewDropElement && elements.get(1) instanceof HeartBerryElement) ) ;
    }

}
