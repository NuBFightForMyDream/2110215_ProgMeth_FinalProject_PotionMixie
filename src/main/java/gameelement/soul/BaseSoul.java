package gameelement.soul;
import gameelement.potion.BasePotion;
import gameelement.potion.PotionType;

import java.util.Random;

public abstract class BaseSoul {
    // Attributes
    private int soulHP ;
    private PotionType weaknessType ;
    private String imagePath ;

    // Constructors
    public BaseSoul(int soulHP , PotionType weaknessType, String imagePath) {
        setSoulHP(soulHP);
        setWeaknessType(weaknessType);
        setImagePath(imagePath);
    }

    // Getter - Setter
    public int getSoulHP() {
        return this.soulHP;
    }
    public void setSoulHP(int soulHP) {
        if (soulHP < 0) this.soulHP = 0 ;
        else this.soulHP = soulHP;
    }
    public PotionType getWeaknessType() {
        return this.weaknessType;
    }
    public void setWeaknessType(PotionType weaknessType) {
        this.weaknessType = weaknessType;
    }
    public String getImagePath() {
        return this.imagePath ;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath ;
    }

    // Methods
    public boolean canbeHitByPotion(BasePotion potion) {
        return potion.getPotionType() == this.weaknessType ;
    }
    public void takeDamageFromPotion(BasePotion potion) {
        if (potion.getPotionType() == weaknessType) {
            this.soulHP -= potion.getPotionPower() ;
            // check if soul is vanished
            if (this.soulHP < 0) this.soulHP = 0 ;
        }
    }
}
