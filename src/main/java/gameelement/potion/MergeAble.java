package gameelement.potion;

import gameelement.element.BaseElement;

import java.util.List;

public interface MergeAble {
    // This interface will check if potion can be merged
    boolean potionMatchesRecipe(List<BaseElement> elements);
}
