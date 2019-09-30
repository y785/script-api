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

import moe.maple.api.script.logic.ScriptAPI;
import moe.maple.api.script.logic.action.BasicScriptAction;
import moe.maple.api.script.logic.chain.BasicActionChain;
import moe.maple.api.script.model.helper.Exchange;
import moe.maple.api.script.model.object.ScriptObject;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An object that has a single inventory or a collection of inventories.
 * Typically a user/character.
 */
public interface InventoryHolderObject<T> extends ScriptObject<T> {

    boolean exchange(Exchange exchange);

    /**
     * This is an inventory transaction that only applies changes <strong>if
     * all actions can be completed</strong>.
     * @param money     - The +/- meso amount to increase/decrease by
     * @param itemId    - The .wz id of the item to add or remove
     * @param itemCount - The +/- amount to increase/decrease by
     * @return true if all actions were completed
     */
    default boolean exchange(int money, int itemId, int itemCount) {
        return exchange(new Exchange(money, itemId, itemCount));
    }

    default void exchange(BasicScriptAction onTrue, BasicScriptAction onFalse, int money, int itemId, int itemCount) {
        if (exchange(money, itemId, itemCount)) onTrue.act();
        else onFalse.act();
    }

    default void exchange(BasicScriptAction onFalse, int money, int itemId, int itemCount) {
        exchange(() -> { }, onFalse, money, itemId, itemCount);
    }

    default void exchange(BasicScriptAction onTrue, BasicScriptAction onFalse, Exchange exchange) {
        if (exchange(exchange)) onTrue.act();
        else onFalse.act();
    }

    default void exchange(BasicScriptAction onFalse, Exchange exchange) {
        exchange(() -> { }, onFalse, exchange);
    }

    /**
     * This is an inventory transaction that only applies changes <strong>if
     * all actions can be completed</strong>.
     * @param money                     - The +/- meso amount to increase/decrease by
     * @param itemTemplateIdAndCount    - An array of Tuples. Left parameter should
     *                                    be the .wz id of the item. Right parameter
     *                                    should be the amount to add or remove.
     * @return true if all actions were completed
     */
    default boolean exchange(int money, Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return exchange(money, List.of(itemTemplateIdAndCount));
    }

    default boolean exchange(int money, List<Tuple<Integer, Integer>> itemTemplateIdAndCount) {
        return exchange(new Exchange(money, itemTemplateIdAndCount));
    }

    // =================================================================================================================

    default boolean exchange(int money, int... itemTemplateIdAndCount) {
        return exchange(new Exchange(money, itemTemplateIdAndCount));
    }

    default void exchange(BasicScriptAction onTrue, BasicScriptAction onFalse, int money, int... itemIdAndCount) {
        if (exchange(money, itemIdAndCount)) onTrue.act();
        else onFalse.act();
    }

    default void exchange(BasicScriptAction onFalse, int money, int... itemIdAndCount) {
        if (!exchange(money, itemIdAndCount)) onFalse.act();
    }

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
    default boolean addItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return addItemAll(Arrays.asList(itemTemplateIdAndCount));
    }
    boolean addItemAll(Collection<Tuple<Integer, Integer>> itemTemplateIdAndCount);

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
    default boolean removeItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return removeItemAll(Arrays.asList(itemTemplateIdAndCount));
    }

    boolean removeItemAll(Collection<Tuple<Integer, Integer>> itemTemplateIdAndCount);

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
    default int getHoldCount(int inventoryType) { return (int) (getSlotCount(inventoryType) - streamItems(inventoryType).count()); }

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
     * @return An immutable inventory collection
     */
    default Collection<InventorySlotObject> getItems(int inventoryType) {
        return streamItems(inventoryType).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * @param inventoryType Inventory Type as an integer, {@link #increaseSlotCount(int, int)}
     * @param slot the inventory slot position
     * @return an item at this slot, if it exists
     */
    Optional<InventorySlotObject> getItem(int inventoryType, short slot);
    default Optional<InventorySlotObject> getItemEquip(short slot) { return getItem(1, slot);}
    default Optional<InventorySlotObject> getItemConsume(byte slot) { return getItem(2, slot);}
    default Optional<InventorySlotObject> getItemInstall(byte slot) { return getItem(3, slot);}
    default Optional<InventorySlotObject> getItemEtc(byte slot) { return getItem(4, slot);}
    default Optional<InventorySlotObject> getItemCash(byte slot) { return getItem(5, slot);}

    /**
     * @param inventoryType Inventory Type as an integer, {@link #increaseSlotCount(int, int)}
     * @return an inventory's slot : item map
     */
    default Map<Short, InventorySlotObject> getItemsMap(int inventoryType) {
        return streamItems(inventoryType).collect(Collectors.toMap(InventorySlotObject::getPosition, Function.identity()));
    }

    default Map<Short, InventorySlotObject> getItemsEquipMap() { return getItemsMap(1);}
    default Map<Short, InventorySlotObject> getItemsConsumeMap() { return getItemsMap(2);}
    default Map<Short, InventorySlotObject> getItemsInstallMap() { return getItemsMap(3);}
    default Map<Short, InventorySlotObject> getItemsEtcMap() { return getItemsMap(4);}
    default Map<Short, InventorySlotObject> getItemsCashMap() { return getItemsMap(5);}

    default Collection<InventorySlotObject> getItemsEquip() { return getItems(1); }
    default Collection<InventorySlotObject> getItemsConsume() { return getItems(2); }
    default Collection<InventorySlotObject> getItemsInstall() { return getItems(3); }
    default Collection<InventorySlotObject> getItemsEtc() { return getItems(4); }
    default Collection<InventorySlotObject> getItemsCash() { return getItems(5); }

    /**
     * @param inventoryType Inventory Type as an integer, {@link #increaseSlotCount(int, int)}
     * @return A stream of {@link InventorySlotObject}
     */
    Stream<InventorySlotObject> streamItems(int inventoryType);
    default Stream<InventorySlotObject> streamItems(int inventoryType, Predicate<InventorySlotObject> filter) { return streamItems(inventoryType).filter(filter); }
    default Stream<InventorySlotObject> streamItemsEquipped() { return streamItems(1).filter(i -> i.getPosition() <= 0); }
    default Collection<InventorySlotObject> getItemsEquipped() { return streamItemsEquipped().collect(Collectors.toList()); }
}
