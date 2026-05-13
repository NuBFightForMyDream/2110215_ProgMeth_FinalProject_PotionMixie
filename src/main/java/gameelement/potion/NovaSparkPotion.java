package gameelement.potion;

import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;

import java.util.List;

public class NovaSparkPotion extends BasePotion implements MergeAble {

    // Constructors
    public NovaSparkPotion() {
        super("Nova Spark Potion", "NovaSpark.png");
        setPotionRecipe( List.of(StarDustElement.class , SparkEmberElement.class) );
        setPotionType(PotionType.NOVA_SPARK);
        setPotionPower(20);
    }

    // Methods
    @Override
    public boolean potionMatchesRecipe(List<BaseElement> elements) {
        return elements.size() == 2 &&
                ( (elements.get(0) instanceof StarDustElement && elements.get(1) instanceof SparkEmberElement) ||
                  (elements.get(0) instanceof SparkEmberElement && elements.get(1) instanceof StarDustElement) ) ;
    }

}
