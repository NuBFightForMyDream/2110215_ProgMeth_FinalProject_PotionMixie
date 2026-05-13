package gameelement.soul;

import gameelement.potion.BasePotion;

public interface BeAttackedAble {
    public boolean canbeHitByPotion(BasePotion potion) ;
    public void takeDamageFromPotion(BasePotion potion) ;
}
