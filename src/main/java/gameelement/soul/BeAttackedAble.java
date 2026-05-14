package gameelement.soul;

import gameelement.potion.BasePotion;

/**
 * Contract for souls that can be checked and damaged by potions.
 */
public interface BeAttackedAble {
    /**
     * Checks whether a potion matches this soul weakness.
     *
     * @param potion potion used for the attack
     * @return true when the potion can damage this soul
     */
    public boolean canbeHitByPotion(BasePotion potion) ;

    /**
     * Applies damage from a potion if it matches the soul weakness.
     *
     * @param potion potion used for the attack
     */
    public void takeDamageFromPotion(BasePotion potion) ;
}
