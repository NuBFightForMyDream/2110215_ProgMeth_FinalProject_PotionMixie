package gameelement.soul;
import gameelement.potion.BasePotion;
import gameelement.potion.PotionType;

import java.util.Random;

/**
 * Base model for all souls that appear on the conveyor belt.
 */
public abstract class BaseSoul {
    // Attributes
    private int soulHP ;
    private PotionType weaknessType ;
    private String imagePath ;

    // Constructors
    /**
     * Creates a soul with HP, weakness type, and image resource path.
     *
     * @param soulHP starting hit points
     * @param weaknessType potion type that can damage this soul
     * @param imagePath resource file used for the soul image
     */
    public BaseSoul(int soulHP , PotionType weaknessType, String imagePath) {
        setSoulHP(soulHP);
        setWeaknessType(weaknessType);
        setImagePath(imagePath);
    }

    // Getter - Setter
    /**
     * Gets the current soul HP.
     *
     * @return current hit points
     */
    public int getSoulHP() {
        return this.soulHP;
    }

    /**
     * Sets soul HP and clamps negative values to zero.
     *
     * @param soulHP hit points to set
     */
    public void setSoulHP(int soulHP) {
        if (soulHP < 0) this.soulHP = 0 ;
        else this.soulHP = soulHP;
    }

    /**
     * Gets the potion type that can damage this soul.
     *
     * @return weakness potion type
     */
    public PotionType getWeaknessType() {
        return this.weaknessType;
    }

    /**
     * Sets the potion type that can damage this soul.
     *
     * @param weaknessType weakness potion type
     */
    public void setWeaknessType(PotionType weaknessType) {
        this.weaknessType = weaknessType;
    }

    /**
     * Gets the image resource path for this soul.
     *
     * @return image file path
     */
    public String getImagePath() {
        return this.imagePath ;
    }

    /**
     * Sets the image resource path for this soul.
     *
     * @param imagePath image file path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath ;
    }

    // Methods
    /**
     * Checks whether the potion matches this soul weakness.
     *
     * @param potion potion used for the attack
     * @return true when the potion type matches the weakness
     */
    public boolean canbeHitByPotion(BasePotion potion) {
        return potion.getPotionType() == this.weaknessType ;
    }

    /**
     * Applies potion damage when the potion matches this soul weakness.
     *
     * @param potion potion used for the attack
     */
    public void takeDamageFromPotion(BasePotion potion) {
        if (potion.getPotionType() == weaknessType) {
            this.soulHP -= potion.getPotionPower() ;
            // check if soul is vanished
            if (this.soulHP < 0) this.soulHP = 0 ;
        }
    }
}
