package moe.maple.api.script.model.object;

/**
 * Created on 8/19/2019.
 */
public interface LifeObject extends FieldedObject {

    // =================================================================================================================

    int getHealthCurrent();
    int getManaCurrent();

    int getHealthMax();
    int getManaMax();

    /**
     * This should NOT increase an object's maximum health pool.
     * This INCREASES the CURRENT health by an amount.
     * This is a HEAL.
     * @return true if field allows healing/user can be healed.
     */
    boolean increaseHealth(int amountToHeal);

    /**
     * This should NOT increase an object's maximum mana pool.
     * This INCREASES the CURRENT mana by an amount.
     * This is a HEAL.
     * @return true if field allows healing/user can be healed.
     */
    boolean increaseMana(int amountToHeal);

    /**
     * This INCREASES an object's maximum health pool.
     * This DOES NOT HEAL.
     * @return true if was able to increase
     */
    boolean increaseHealthMax(int amountToIncrease);

    /**
     * This INCREASES an object's maximum mana pool.
     * This DOES NOT HEAL.
     * @return true if was able to increase
     */
    boolean increaseManaMax(int amountToIncrease);

    /**
     * Heals an object to max health/mana.
     */
    default boolean heal() { return increaseHealth(Integer.MAX_VALUE) && increaseMana(Integer.MAX_VALUE); }

    // =================================================================================================================
}
