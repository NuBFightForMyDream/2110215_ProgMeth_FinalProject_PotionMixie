package gameelement.potion;
import gameelement.element.BaseElement;

import java.util.List;

public abstract class BasePotion {
    // Attributes
    protected String potionName ;
    protected PotionType potionType ;
    protected int potionPower ;
    protected String imagePath ;
    protected List<Class<? extends BaseElement>> potionRecipe;

    // Constructors
    public BasePotion(String potionName, String imagePath) {
        setPotionName(potionName);
        setImagePath(imagePath);
    }
    // Methods
    public String getPotionName() {
        return this.potionName ;
    }
    public String getImagePath() {
        return this.imagePath ;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath ;
    }
    public List<Class<? extends BaseElement>> getPotionRecipe() {
        return potionRecipe ;
    }
    public void setPotionRecipe(List<Class<? extends BaseElement>> potionRecipe){
        this.potionRecipe = potionRecipe ;
    }
    public abstract boolean potionMatchesRecipe(List<BaseElement> elements);

    // Getter - Setters
    public void setPotionName(String potionName) {
        this.potionName = potionName;
    }
    public PotionType getPotionType() { return this.potionType; }
    public void setPotionType(PotionType type) {
        this.potionType = type ;
    }
    public int getPotionPower() {
        return potionPower;
    }
    public void setPotionPower(int potionPower) {
        if (potionPower < 0) this.potionPower = 0 ;
        else this.potionPower = potionPower;
    }


}
