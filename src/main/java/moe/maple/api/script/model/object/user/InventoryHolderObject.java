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

package moe.maple.api.script.model.object.user;

import moe.maple.api.script.model.object.ScriptObject;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An object that has a single inventory or a collection of inventories.
 * Typically a user/character.
 */
public interface InventoryHolderObject<T> extends ScriptObject<T> {

    /**
     * This is an inventory transaction that only applies changes <strong>if
     * all actions can be completed</strong>.
     * @param money     - The +/- meso amount to increase/decrease by
     * @param itemId    - The .wz id of the item to add or remove
     * @param itemCount - The +/- amount to increase/decrease by
     * @return true if all actions were completed
     */
    boolean exchange(int money, int itemId, int itemCount);

    /**
     * This is an inventory transaction that only applies changes <strong>if
     * all actions can be completed</strong>.
     * @param money                     - The +/- meso amount to increase/decrease by
     * @param itemTemplateIdAndCount    - An array of Tuples. Left parameter should
     *                                    be the .wz id of the item. Right parameter
     *                                    should be the amount to add or remove.
     * @return true if all actions were completed
     */
    boolean exchange(int money, Tuple<Integer, Integer>... itemTemplateIdAndCount);

    // =================================================================================================================

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

    /**
     * Remove <strong>ALL ITEMS</strong> at the specified slot position.
     * @param tab An Inventory index as an int
     * @param position A {@link Short} slot position
     * @return true if the item was removed successfully.
     */
    boolean removeSlot(int tab, short position);

    default boolean removeSlotEquip(short position) {
        return removeSlot(1, position);
    }

    default boolean removeSlotUse(short position) {
        return removeSlot(2, position);
    }

    default boolean removeSlotSetup(short position) {
        return removeSlot(3, position);
    }

    default boolean removeSlotEtc(short position) {
        return removeSlot(4, position);
    }

    default boolean removeSlotCash(short position) {
        return removeSlot(5, position);
    }

    // =================================================================================================================

    /**
     * @param itemTemplateId - The .wz id of the item
     * @return the total count of the itemTemplateId
     */
    int getItemCount(int itemTemplateId);


    /**
     * Retrieves the # of non-empty slots in an inventory.
     * @param inventoryType @see {@link #increaseSlotCount(int, int)}
     * @return # of filled slots
     */
    int getHoldCount(int inventoryType);

    /**
     * Retrieves the # of accessible slots in an inventory. (Default is typically 24)
     * @param inventoryType @see {@link #increaseSlotCount(int, int)}
     * @return # of filled slots
     */
    int getSlotCount(int inventoryType);


    /**
     * @return true if this object holds the total count
     */
    default boolean hasItem(int itemTemplateId, int count) { return getItemCount(itemTemplateId) >= count; }
    default boolean hasItem(int itemTemplateId) { return hasItem(itemTemplateId, 1); }

    // =================================================================================================================

    /**
     * Increases the # of usable slots in an inventory tab based on an Inventory Type enumeration.
     * Valid inventory types for incrementation are 1 through 5 (EQUIP, CONSUME, INSTALL, ETC, CASH respectively).
     * @param inventoryType Inventory Type as an integer.
     * @param howMany How many slots to add.
     * @return true if the operation was successful.
     */
    boolean increaseSlotCount(int inventoryType, int howMany);

    default boolean increaseSlotCountForEquip(int howMany) {
        return increaseSlotCount(1, howMany);
    }

    default boolean increaseSlotCountForUse(int howMany) {
        return increaseSlotCount(2, howMany);
    }

    default boolean increaseSlotCountForSetup(int howMany) {
        return increaseSlotCount(3, howMany);
    }

    default boolean increaseSlotCountForEtc(int howMany) {
        return increaseSlotCount(4, howMany);
    }

    default boolean increaseSlotCountForCash(int howMany) {
        return increaseSlotCount(5, howMany);
    }

    // =================================================================================================================

    /**
     * @param inventoryType Inventory Type as an integer, {@link #increaseSlotCount(int, int)}
     * @return the inventory's item collection
     */
    Collection<InventorySlotObject> getItems(int inventoryType);

    default Stream<InventorySlotObject> streamItems(int inventoryType) { return getItems(inventoryType).stream(); }
    default Stream<InventorySlotObject> streamItems(int inventoryType, Predicate<InventorySlotObject> filter) { return streamItems(inventoryType).filter(filter); }

    default Collection<InventorySlotObject> getItemsEquip() { return getItems(1); }
    default Collection<InventorySlotObject> getItemsConsume() { return getItems(2); }
    default Collection<InventorySlotObject> getItemsInstall() { return getItems(3); }
    default Collection<InventorySlotObject> getItemsEtc() { return getItems(4); }
    default Collection<InventorySlotObject> getItemsCash() { return getItems(5); }

    default Stream<InventorySlotObject> streamItemsEquipped() { return streamItems(1).filter(i -> i.getSlotId() < 0); }
    default Collection<InventorySlotObject> getItemsEquipped() { return streamItemsEquipped().collect(Collectors.toList()); }
}
