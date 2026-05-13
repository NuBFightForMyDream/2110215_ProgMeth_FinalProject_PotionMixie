package gameelement.potion;

import gameelement.element.*;

import java.util.List;

public class SoothingLovePotion extends BasePotion implements MergeAble {

    // Constructors
    public SoothingLovePotion() {
        super("Soothing Love Potion", "SoothingLove.png");
        setPotionRecipe( List.of(HeartBerryElement.class , DewDropElement.class) );
        setPotionType(PotionType.SOOTHING_LOVE);
        setPotionPower(40);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof DewDropElement) ||
                  (elements.get(0) instanceof DewDropElement && elements.get(1) instanceof HeartBerryElement) ) ;
    }

}
