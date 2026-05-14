package gameelement.potion;

import gameelement.element.BaseElement;

import java.util.List;

/**
 * Contract for potions that can validate a two-element merge recipe.
 */
public interface MergeAble {
    // This interface will check if potion can be merged
    /**
     * Checks whether the given elements match this potion recipe.
     *
     * @param elements selected elements from the merge station
     * @return true when the recipe is correct
     */
    boolean potionMatchesRecipe(List<BaseElement> elements);
}
