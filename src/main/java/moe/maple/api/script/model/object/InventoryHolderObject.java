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

package moe.maple.api.script.model.object;

import moe.maple.api.script.util.tuple.Tuple;

/**
 * An object that has a single inventory or a collection of inventories.
 * Typically a user/character.
 */
public interface InventoryHolderObject<T> extends ScriptObject {

    /**
     * Adds an item into this objects inventory.
     * @param itemTemplateId - The .wz id of the item
     * @param count          - The amount to add.
     * @return true if the item and the correct amount were added.
     */
    boolean addItem(int itemTemplateId, int count);

    /**
     * Adds a single item into this object's inventory.
     * @param itemTemplateId - The .wz id of the item
     * @return true if single item was added
     */
    default boolean addItem(int itemTemplateId) { return addItem(itemTemplateId, 1); }

    /**
     * Adds ALL ITEMS in the array.
     * This should <strong>ONLY ADD ITEMS IF ALL CAN BE ADDED</strong>.
     * @param itemTemplateId
     * @return true if ALL ITEMS were added
     */
    boolean addItemAll(int... itemTemplateId);

    /**
     * Add ALL ITEMS in the array with their count.
     * This should <strong>ONLY ADD ITEMS IF ALL CAN BE ADDED</strong>.
     * @param itemTemplateIdAndCount - An array of Tuples. Left parameter should
     *                                 be the .wz id of the item. Right parameter
     *                                 should be the amount to add.
     * @return true if ALL ITEMS were added
     */
    boolean addItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount);

    // =================================================================================================================

    /**
     * Remove <strong>ALL INSTANCES</strong> where an item matches the item id.
     * @param itemTemplateId - The .wz id of the item
     * @return true if at least one was removed
     */
    boolean removeItem(int itemTemplateId);

    /**
     * Removes an amount of items where the item matches the item id.
     * This should <strong>ONLY REMOVE AN ITEM</strong> if you can remove
     * <strong>THE CORRECT AMOUNT</strong>.
     * @param itemTemplateId - The .wz id of the item
     * @param count
     * @return true if THE COUNT WAS REMOVED
     */
    boolean removeItem(int itemTemplateId, int count);

    /**
     * Remove ALL INSTANCES where an item matches the item id.
     * This should <strong>ONLY REMOVE AN ITEM IF ALL CAN BE REMOVED</strong>.
     * @param itemTemplateId - The .wz id of the item
     * @return true if <strong>ALL ITEMS WERE REMOVED</strong>
     */
    boolean removeItemAll(int... itemTemplateId);

    /**
     * Removes <strong>ALL ITEMS</strong> in the array with their count.
     * This should <strong>ONLY ADD ITEMS IF ALL CAN BE REMOVED</strong>.
     * @param itemTemplateIdAndCount - An array of Tuples. Left parameter should
     *                                 be the .wz id of the item. Right parameter
     *                                 should be the amount to add.
     * @return true if <strong>ALL WERE REMOVED</strong>
     */
    boolean removeItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount);

    // =================================================================================================================

    /**
     * @param itemTemplateId - The .wz id of the item
     * @return the total count of the itemTemplateId
     */
    int getItemCount(int itemTemplateId);

    /**
     * @return true if this object holds the total count
     */
    default boolean hasItem(int itemTemplateId, int count) { return getItemCount(itemTemplateId) >= count; }
    default boolean hasItem(int itemTemplateId) { return hasItem(itemTemplateId, 1); }

    // =================================================================================================================
}