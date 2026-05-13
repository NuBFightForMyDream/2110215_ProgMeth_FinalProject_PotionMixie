package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.StarDustElement;

import java.util.List;

public class DreamMistPotion extends BasePotion implements MergeAble {

    // Constructors
    public DreamMistPotion() {
        super("Dream Mist Potion", "DreamMist.png");
        setPotionRecipe( List.of(StarDustElement.class , DewDropElement.class) );
        setPotionType(PotionType.DREAM_MIST);
        setPotionPower(20);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof StarDustElement && elements.get(1) instanceof DewDropElement) ||
                  (elements.get(0) instanceof DewDropElement && elements.get(1) instanceof StarDustElement) ) ;
    }

}
