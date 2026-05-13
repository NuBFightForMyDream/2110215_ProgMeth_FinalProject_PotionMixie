package gameelement.element;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementTest {

    @ParameterizedTest
    @MethodSource("elements")
    void elementHasExpectedNameAndImage(Supplier<BaseElement> factory, String expectedName, String expectedImagePath) {
        BaseElement element = factory.get();

        assertEquals(expectedName, element.getName());
        assertEquals(expectedImagePath, element.getImagePath());
    }

    private static Stream<Arguments> elements() {
        return Stream.of(
                Arguments.of((Supplier<BaseElement>) HeartBerryElement::new, "Heart Berry", "HeartBerry.png"),
                Arguments.of((Supplier<BaseElement>) StarDustElement::new, "Star Dust", "StarDust.png"),
                Arguments.of((Supplier<BaseElement>) SparkEmberElement::new, "Spark Ember", "SparkEmber.png"),
                Arguments.of((Supplier<BaseElement>) DewDropElement::new, "Dew Drop", "DewDrop.png")
        );
    }
}
