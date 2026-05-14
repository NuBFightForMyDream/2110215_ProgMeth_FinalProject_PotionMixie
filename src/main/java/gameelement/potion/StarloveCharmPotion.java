package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.HeartBerryElement;
import gameelement.element.StarDustElement;

import java.util.List;

/**
 * Potion made from Heart Berry and Star Dust.
 */
public class StarloveCharmPotion extends BasePotion implements MergeAble {
    // Attributes
    /** Extra charm value reserved for Starlove Charm behavior. */
    protected int charmPower ;

    // Constructors
    /**
     * Creates a Starlove Charm potion with its recipe, type, and power.
     */
    public StarloveCharmPotion() {
        super("Starlove Charm Potion", "StarLoveCharm.png");
        setPotionRecipe( List.of(HeartBerryElement.class , StarDustElement.class) );
        setPotionType(PotionType.STARLOVE_CHARM);
        setPotionPower(40);
    }

    // Methods
    /**
     * Checks whether the selected elements can merge into Starlove Charm.
     *
     * @param elements selected elements from the merge station
     * @return true when Heart Berry and Star Dust are selected
     */
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ((elements.get(0) instanceof HeartBerryElement && elements.get(1) instanceof StarDustElement) ||
                 (elements.get(0) instanceof StarDustElement && elements.get(1) instanceof HeartBerryElement));
    }

}
