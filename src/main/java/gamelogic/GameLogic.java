package gamelogic;

import gameelement.element.BaseElement;
import gameelement.potion.BasePotion;
import gameelement.potion.DreamMistPotion;
import gameelement.potion.EnergySplashPotion;
import gameelement.potion.NovaSparkPotion;
import gameelement.potion.PassionPopPotion;
import gameelement.potion.SoothingLovePotion;
import gameelement.potion.StarloveCharmPotion;
import gameelement.soul.BaseSoul;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Main gameplay controller for soul generation, timer, attacks, and merging.
 */
public class GameLogic {

    // TODO : Attributes

    // ---- 1st part : Soul Generator ----
    private final int level;
    private final int beltSize;

    private final SoulGenerator orderGenerator; // This is how we generate soul
    private final Queue<BaseSoul> soulBelt; // This will be storage (belt) for gameplay

    private int defeatedSoulCount; // this will count total defeated soul
    private int totalSoulRequiredForEachLevel ; // This will get total required souls for each level

    private BaseSoul currentSoul ;
    // ---- END of 1st part ----

    // ---- 2nd part : Time Left Manager ----
    private final TimeLeftManager timer;
    private boolean gameOver ;
    // ---- END of 2nd part ----

    // ---- 3rd part : Merging System ----
    private static final int MAX_MERGE_SLOTS = 2;
    private List<BaseElement> mergeSlots;
    private List<BasePotion> potionPool;
    // ---- END of 3rd part ----


    // TODO : Constructors
    /**
     * Creates gameplay state for the selected level.
     *
     * @param level level number used for time, soul pool, and win condition
     */
    public GameLogic(int level) {
        this.level = level;
        this.beltSize = 8;

        // set timeLeftManager & gameOver checker
        this.timer = new TimeLeftManager(level) ; // This will represent timer based on each level
        this.gameOver = false ;

        // Set orderGenerator & Belt storage
        this.orderGenerator = new SoulGenerator();
        this.soulBelt = new LinkedList<>(); // Queue can't be create directly , so create with LinkedList instead

        this.defeatedSoulCount = 0; // this will count total defeated soul
        this.totalSoulRequiredForEachLevel = getTotalSoulRequiredByLevel(level); // This will get total required souls for each level

        // handle merging system
        mergeSlots = new ArrayList<>();

        potionPool = new ArrayList<>();

        potionPool.add(new DreamMistPotion());
        potionPool.add(new EnergySplashPotion());
        potionPool.add(new NovaSparkPotion());
        potionPool.add(new PassionPopPotion());
        potionPool.add(new SoothingLovePotion());
        potionPool.add(new StarloveCharmPotion());

        // This Algorithm will generate soul until belt got full
        fillSoulBeltAlgorithm();
    }

    // -------------- Methods ---------------

    // -------- TODO 1 : Methods for SoulGenerator ---------
    /**
     * Gets the number of defeated souls required for a level.
     *
     * @param level level number
     * @return required defeated soul count
     */
    private int getTotalSoulRequiredByLevel(int level) {
        return switch (level) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 20;
            case 4 -> 30;
            case 5 -> 67;
            default -> 10;
        };
    }

    /**
     * Fills the soul belt until it reaches capacity or the level is complete.
     */
    private void fillSoulBeltAlgorithm() {
        // This method will check if belt is not full & level isn't complete
        while (soulBelt.size() < beltSize && !isLevelComplete()) {
            soulBelt.add(orderGenerator.generateSoul(level));
        }
    }

    /**
     * Attacks the first soul in the belt.
     *
     * @param potion potion used for the attack
     */
    public void attackFirstSoul(BasePotion potion) {
        attackSoulAt(0, potion);
    }

    /**
     * Attacks a soul at the given belt index and refills the belt if defeated.
     *
     * @param soulIndex index of the target soul
     * @param potion potion used for the attack
     * @return true when a valid target was attacked
     */
    public boolean attackSoulAt(int soulIndex, BasePotion potion) {
        // Case 1 : Level Complete , Won't attack soul
        if (isLevelComplete()) {
            gameOver = true; return false ;
        }

        // Case 2 : Game is running , Looking for First Soul (in queue) to be attack
        List<BaseSoul> souls = getSoulBeltAsList();

        // Case 2.1 : No first soul (No Soul in belt) , do nothing
        if (soulIndex < 0 || soulIndex >= souls.size()) {
            return false;
        }

        // Case 2.2 : Soul in Belt , attack soul from potion then check if that soul is dead already
        BaseSoul targetSoul = souls.get(soulIndex);
        targetSoul.takeDamageFromPotion(potion);

        if (targetSoul.getSoulHP() == 0) {
            // if soul dead , remove target soul then fillSoulBelt
            soulBelt.remove(targetSoul);
            defeatedSoulCount++;
            if (isLevelComplete()) {
                gameOver = true; return true;
            }
            fillSoulBeltAlgorithm();
        }
        return true;
    }

    // Getters for SoulGenerator
    /**
     * Gets the first soul waiting in the belt.
     *
     * @return first soul, or null if the belt is empty
     */
    public BaseSoul getFirstSoul() {
        return soulBelt.peek();
    }

    /**
     * Gets a copy of the current soul belt.
     *
     * @return list copy of souls in belt order
     */
    public List<BaseSoul> getSoulBeltAsList() {
        return new ArrayList<>(soulBelt);
    }

    /**
     * Gets the number of souls defeated in this level.
     *
     * @return defeated soul count
     */
    public int getDefeatedSoulCount() {
        return defeatedSoulCount;
    }

    /**
     * Gets the total souls required to complete this level.
     *
     * @return required soul count
     */
    public int getTotalSoulRequired() {
        return totalSoulRequiredForEachLevel ;
    }
    // -------- TODO 1 END : Methods for SoulGenerator ---------


    // -------- TODO 2 : Methods for TimeLeftManager ---------
    /**
     * Decreases the timer and ends the game when time reaches zero.
     */
    public void updateTimer() {
        // Case 1 : GameOver , do nothing
        if (gameOver) return;

        // Case 2 : Game Running , decrease time (Note that UI will update in Thread)
        timer.decreaseTime();
        if (timer.isTimeUp()) {
            gameOver = true;
        }
    }

    /**
     * Checks whether the player has defeated enough souls for the level.
     *
     * @return true when the level objective is complete
     */
    public boolean isLevelComplete() {
        // This method will check if that level is complete or not
        return defeatedSoulCount >= totalSoulRequiredForEachLevel ;
    }

    /**
     * Checks whether the game has ended.
     *
     * @return true when time is up or the level is complete
     */
    public boolean isGameOver() { return gameOver ; }

    /**
     * Gets the current remaining time.
     *
     * @return remaining time in seconds
     */
    public int getTimeLeft() {
        return timer.getTimeLeft();
    }

    /**
     * Checks whether the timer has reached zero.
     *
     * @return true when no time remains
     */
    public boolean isTimeUp() {
        return timer.isTimeUp();
    }

    /**
     * Gets the current level number.
     *
     * @return level number
     */
    public int getLevel() {
        return level;
    }
    // -------- TODO 2 END : Methods for TimeLeftManager ---------

    // -------- TODO 3 : Methods for handling Merging System --------
    /**
     * Adds an element to the merge slots and returns a potion when a recipe matches.
     *
     * @param element selected element
     * @return created potion, or null when no potion is created
     */
    public BasePotion addElementToMerge(BaseElement element) {
        if (mergeSlots.size() >= MAX_MERGE_SLOTS) {
            clearMergeSlots();
        }

        mergeSlots.add(element);
        BasePotion potion = tryAutoMerge();
        if (potion != null) {
            clearMergeSlots();
            return potion;
        }

        if (mergeSlots.size() == MAX_MERGE_SLOTS) {
            clearMergeSlots();
        }

        return null;
    }

    /**
     * Attempts to match the current merge slots with any potion recipe.
     *
     * @return matched potion, or null when no recipe matches
     */
    private BasePotion tryAutoMerge() {
        for (BasePotion potion : potionPool) {
            if (potion.potionMatchesRecipe(mergeSlots)) {
                return potion;
            }
        }
        return null;
    }

    /**
     * Clears all currently selected merge elements.
     */
    public void clearMergeSlots() {
        mergeSlots.clear();
    }

    /**
     * Gets the current merge slot contents.
     *
     * @return selected merge elements
     */
    public List<BaseElement> getMergeSlots() {
        return mergeSlots;
    }

    // -------- TODO 3 END : Methods for handling Merging System --------
}
