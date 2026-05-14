package gameelement.element;

/**
 * Base model for every selectable element used in potion recipes.
 */
public abstract class BaseElement {
    // This class will be base element of all element in game
    // But in real game , this element doesn't exist
    // Note that This game consists of 4 elements : HeartBerry , StarDust , SparkEmber , DewDrop

    // Attributes
    /** Element display name. */
    protected String name ;
    /** Element image resource path. */
    protected String imagePath ;

    // Constructors
    /**
     * Creates an element with its display name and image resource path.
     *
     * @param name element name shown by the game
     * @param imagePath resource file used for the element image
     */
    public BaseElement(String name, String imagePath) {
        this.name = name ;
        this.imagePath = imagePath ;
    }

    /**
     * Gets the element display name.
     *
     * @return element name
     */
    public String getName() {
        return this.name ;
    }

    /**
     * Gets the image resource path for this element.
     *
     * @return image file path
     */
    public String getImagePath() {
        return this.imagePath ;
    }
}
