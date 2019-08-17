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

import java.util.Optional;

/**
 * This is a script proxy for user/character objects.
 * <T> should be your implementation of user/character.
 * @param <T>
 */
public interface UserObject<T> extends FieldedObject {

    T getUser();

    /*
     * Normally these would be classified as a constant and thrown into
     * a static method, so that it could be called whenever. However, since
     * these change based on version,  it's important that you have to ability
     * to override these as nexon changes jobs. So, for now, some simple implementations.
     * Based on v92 ~ v95.
     */
    default boolean isAdmin() {
        var nJob = getJobId();
        return nJob % 1000 / 100 == 9;
    }
    default boolean isAran() {
        var nJob = getJobId();
        return nJob / 100 == 21 || nJob == 2000;
    }
    default boolean isBeginner() {
        var nJob = getJobId();
        return (nJob % 1000 == 0) || nJob == 2001;
    }
    default boolean isBattleMage() {
        var nJob = getJobId();
        return nJob / 100 == 32;
    }
    default boolean isCygnus() {
        var nJob = getJobId();
        return nJob / 1000 == 1;
    }
    default boolean isDualBlade() {
        var nJob = getJobId();
        return nJob / 10 == 43;
    }
    default boolean isExtendedSPJob() {
        var nJob = getJobId();
        return nJob / 1000 == 3 || nJob / 100 == 22 || nJob == 2001;
    }
    default boolean isEvan() {
        var nJob = getJobId();
        return nJob / 100 == 22 || nJob == 2001;
    }
    default boolean isManager() {
        var nJob = getJobId();
        return nJob % 1000 / 100 == 8;
    }
    default boolean isMechanic() {
        var nJob = getJobId();
        return nJob / 100 == 35;
    }
    default boolean isResistance() {
        var nJob = getJobId();
        return nJob / 1000 == 3;
    }
    default boolean isWildHunter() {
        var nJob = getJobId();
        return nJob / 100 == 33;
    }

    boolean hasMoney();
    boolean increaseMoney(int amount);
    boolean decreaseMoney(int amount);

    int getId();
    int getJobId();

    /*
     * Inventory related things.
     */
    int itemCount(int itemId);
    default boolean hasItem(int itemId, int count) { return itemCount(itemId) > count; }
    default boolean hasItem(int itemId) { return hasItem(itemId, 1); }
}
