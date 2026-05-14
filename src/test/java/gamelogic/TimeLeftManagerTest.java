package gamelogic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests timer constructor defaults and countdown behavior.
 */
class TimeLeftManagerTest {

    /**
     * Tests that the constructor uses the expected starting time for each level.
     */
    @ParameterizedTest
    @CsvSource({
            "1, 90",
            "2, 180",
            "3, 175",
            "4, 300",
            "5, 450",
            "0, 90",
            "6, 90"
    })
    void constructorUsesExpectedTimeForLevel(int level, int expectedTime) {
        TimeLeftManager timer = new TimeLeftManager(level);

        assertEquals(expectedTime, timer.getTimeLeft());
        assertEquals(expectedTime, timer.getTotalTimeFromLevel(level));
    }

    /**
     * Tests that decreaseTime reduces time until it reaches zero.
     */
    @Test
    void decreaseTimeReducesTimeByOneUntilZero() {
        TimeLeftManager timer = new TimeLeftManager(1);

        timer.decreaseTime();

        assertEquals(89, timer.getTimeLeft());
        assertFalse(timer.isTimeUp());

        for (int i = 0; i < 200; i++) {
            timer.decreaseTime();
        }

        assertEquals(0, timer.getTimeLeft());
        assertTrue(timer.isTimeUp());
    }
}
