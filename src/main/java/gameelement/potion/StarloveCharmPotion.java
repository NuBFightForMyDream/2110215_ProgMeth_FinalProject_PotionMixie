package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.HeartBerryElement;
import gameelement.element.StarDustElement;

import java.util.List;

public class StarloveCharmPotion extends BasePotion implements MergeAble {
    // Attributes
    protected int charmPower ;

    // Constructors
    public StarloveCharmPotion() {
        super("Starlove Charm Potion", "StarLoveCharm.png");
        setPotionRecipe( List.of(HeartBerryElement.class , StarDustElement.class) );
        setPotionType(PotionType.STARLOVE_CHARM);
        setPotionPower(40);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ((elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof StarDustElement) ||
                 (elements.get(0) instanceof StarDustElement && elements.get(1) instanceof HeartBerryElement));
    }

}
