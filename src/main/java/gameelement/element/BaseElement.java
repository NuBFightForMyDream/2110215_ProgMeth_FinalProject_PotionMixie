package gameelement.element;

public abstract class BaseElement {
    // This class will be base element of all element in game
    // But in real game , this element doesn't exist
    // Note that This game consists of 4 elements : HeartBerry , StarDust , SparkEmber , DewDrop

    // Attributes
    protected String name ;
    protected String imagePath ;

    // Constructors
    public BaseElement(String name, String imagePath) {
        this.name = name ;
        this.imagePath = imagePath ;
    }

    public String getName() {
        return this.name ;
    }

    public String getImagePath() {
        return this.imagePath ;
    }
}
