package gameelement.potion;
import gameelement.element.BaseElement;

import java.util.List;

/**
 * Base model for potions created by merging two elements.
 */
public abstract class BasePotion {
    // Attributes
    /** Potion display name. */
    protected String potionName ;
    /** Potion type used for matching soul weakness. */
    protected PotionType potionType ;
    /** Damage dealt by this potion. */
    protected int potionPower ;
    /** Potion image resource path. */
    protected String imagePath ;
    /** Element classes required to create this potion. */
    protected List<Class<? extends BaseElement>> potionRecipe;

    // Constructors
    /**
     * Creates a potion with its display name and image resource path.
     *
     * @param potionName potion name shown by the game
     * @param imagePath resource file used for the potion image
     */
    public BasePotion(String potionName, String imagePath) {
        setPotionName(potionName);
        setImagePath(imagePath);
    }
    // Methods
    /**
     * Gets the potion display name.
     *
     * @return potion name
     */
    public String getPotionName() {
        return this.potionName ;
    }

    /**
     * Gets the image resource path for this potion.
     *
     * @return image file path
     */
    public String getImagePath() {
        return this.imagePath ;
    }

    /**
     * Sets the image resource path for this potion.
     *
     * @param imagePath image file path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath ;
    }

    /**
     * Gets the two element classes required to create this potion.
     *
     * @return recipe element classes
     */
    public List<Class<? extends BaseElement>> getPotionRecipe() {
        return potionRecipe ;
    }

    /**
     * Sets the element classes required to create this potion.
     *
     * @param potionRecipe recipe element classes
     */
    public void setPotionRecipe(List<Class<? extends BaseElement>> potionRecipe){
        this.potionRecipe = potionRecipe ;
    }

    /**
     * Checks whether the selected elements match this potion recipe.
     *
     * @param elements selected elements from the merge station
     * @return true when the recipe is correct
     */
    public abstract boolean potionMatchesRecipe(List<BaseElement> elements);

    // Getter - Setters
    /**
     * Sets the potion display name.
     *
     * @param potionName potion name
     */
    public void setPotionName(String potionName) {
        this.potionName = potionName;
    }

    /**
     * Gets the potion type used for soul weakness matching.
     *
     * @return potion type
     */
    public PotionType getPotionType() { return this.potionType; }

    /**
     * Sets the potion type used for soul weakness matching.
     *
     * @param type potion type
     */
    public void setPotionType(PotionType type) {
        this.potionType = type ;
    }

    /**
     * Gets the damage power of this potion.
     *
     * @return potion damage power
     */
    public int getPotionPower() {
        return potionPower;
    }

    /**
     * Sets the potion damage power and clamps negative values to zero.
     *
     * @param potionPower potion damage power
     */
    public void setPotionPower(int potionPower) {
        if (potionPower < 0) this.potionPower = 0 ;
        else this.potionPower = potionPower;
    }


}
