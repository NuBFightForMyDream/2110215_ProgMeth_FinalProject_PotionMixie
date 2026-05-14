package gamelogic;

/**
 * Tracks remaining game time for each level.
 */
public class TimeLeftManager {
    // Attributes (for each level)
    private static final int DEFAULT_TIME_LEFT = 90;
    private static final int[] TOTAL_TIME_BY_LEVEL = {
            DEFAULT_TIME_LEFT,
            90, // Level 1 : 90 Seconds (75 Real , 15 Bonus Time)
            180, // Level 2 : 180 Seconds (150 Real , 30 Bonus Time)
            175, // Level 3 : 240 Sec (12 Sec / Potion)
            300, // Level 4 : 300 Sec (10 Sec / Potion)
            450 // Level 5 : 450 Sec (6.7 Sec / Potion)
    };

    private int totalTimeLeft ;

    // Constructor
    /**
     * Creates a timer using the configured time for the selected level.
     *
     * @param level level number
     */
    public TimeLeftManager(int level) {
        // set totalTime
        this.totalTimeLeft = getTotalTimeFromLevel(level);
    }

    // Methods
    /**
     * Decreases remaining time by one second, stopping at zero.
     */
    public void decreaseTime() {
        // This method will check if time can be decreased (game not over)
        if (totalTimeLeft > 0) totalTimeLeft--;
    }

    /**
     * Gets the starting time configured for a level.
     *
     * @param level level number
     * @return starting time in seconds
     */
    public int getTotalTimeFromLevel(int level) {
        if (level >= 1 && level < TOTAL_TIME_BY_LEVEL.length) {
            return TOTAL_TIME_BY_LEVEL[level];
        }
        return DEFAULT_TIME_LEFT ;
    }

    /**
     * Checks whether time has run out.
     *
     * @return true when no time remains
     */
    public boolean isTimeUp() {
        return this.totalTimeLeft <= 0 ;
    }

    /**
     * Gets the current remaining time.
     *
     * @return remaining time in seconds
     */
    public int getTimeLeft() {
        return this.totalTimeLeft ;
    }
}
