/*
 * Copyright (C) 2019, y785, http://github.com/y785
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package moe.maple.api.script.model.object.field;

/**
 * Created on 8/19/2019.
 */
public interface LifeObject<T> extends FieldedObject<T> {

    long getHealthCurrent();

    int getManaCurrent();

    long getHealthMax();

    int getManaMax();

    /**
     * This should NOT increase an object's maximum health pool.
     * This INCREASES the CURRENT health by an amount.
     * This is a HEAL.
     * @param amountToHeal - The amount to increase the current value by
     * @return true if field allows healing/user can be healed.
     */
    boolean increaseHealth(int amountToHeal);

    /**
     * This should NOT increase an object's maximum mana pool.
     * This INCREASES the CURRENT mana by an amount.
     * This is a HEAL.
     * @param amountToHeal - The amount to increase the current value by
     * @return true if field allows healing/user can be healed.
     */
    boolean increaseMana(int amountToHeal);

    boolean decreaseHealth(int amountToReduce);
    boolean decreaseMana(int amountToReduce);

    /**
     * This INCREASES an object's maximum health pool.
     * This DOES NOT HEAL.
     * @param amountToIncrease - The amount to increase the current value by
     * @return true if was able to increase
     */
    boolean increaseHealthMax(int amountToIncrease);

    /**
     * This INCREASES an object's maximum mana pool.
     * This DOES NOT HEAL.
     * @param amountToIncrease - The amount to increase the current value by
     * @return true if was able to increase
     */
    boolean increaseManaMax(int amountToIncrease);

    /**
     * Heals an object to max health/mana.
     * @return true if both were healed.
     */
    default boolean heal() { return increaseHealth(Integer.MAX_VALUE) && increaseMana(Integer.MAX_VALUE); }

    // =================================================================================================================
}
