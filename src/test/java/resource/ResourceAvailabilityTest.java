package resource;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests that image and UML resources referenced by the game are available.
 */
class ResourceAvailabilityTest {

    /**
     * Tests that each listed resource can be loaded from the classpath.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "HomeScreenPic.png",
            "LevelSelectionScreenPic.png",
            "HowToPlayPic.png",
            "CreditScreenPic.png",
            "GameScreenPic.png",
            "HeartBerry.png",
            "StarDust.png",
            "SparkEmber.png",
            "DewDrop.png",
            "DreamMist.png",
            "EnergySplash.png",
            "NovaSpark.png",
            "PassionPop.png",
            "SoothingLove.png",
            "StarLoveCharm.png",
            "DreamMistSoul.png",
            "EnergySplashSoul.png",
            "NovaSparkSoul.png",
            "PassionPopSoul.png",
            "SoothingLoveSoul.png",
            "StarLoveCharmSoul.png",
            "CP215_FinalProject_UML_Application.puml",
            "CP215_FinalProject_UML_GameLogic.puml",
            "CP215_FinalProject_UML_GameElement.puml",
            "CP215_FinalProject_UML_GraphicalUserInterface.puml",
            "CP215_FinalProject_UML_All.puml",
            "CP215_FinalProject_UML_All.svg"
    })
    void resourceReferencedByGameExists(String resourceName) {
        assertNotNull(
                getClass().getClassLoader().getResource(resourceName),
                "Missing resource: " + resourceName
        );
    }
}
