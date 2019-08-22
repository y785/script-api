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

import moe.maple.api.script.model.object.user.UserObject;

/**
 * Created on 8/22/2019.
 */
public interface GuildObject<Guild> extends ScriptObject<Guild> {

    boolean isMaster(long userId);
    default boolean isMaster(UserObject userObject) { return isMaster(userObject.getId()); }

    boolean isSubMaster(long userId);
    default boolean isSubMaster(UserObject userObject) { return isSubMaster(userObject.getId()); }

    boolean isMember(long userId);
    default boolean isMember(UserObject userObject) { return isMember(userObject.getId()); }

    boolean isMarkExist();

    int getCapacity();

    boolean increaseCapacity(int amount, int cost);
    default boolean increaseCapacity(int amount) { return increaseCapacity(amount, 0); }

    boolean setMark(int cost);
    default boolean setGuildMark() { return setMark(0); }

    boolean removeMark(int cost);
    default boolean removeGuildMark() { return removeMark(0); }

    // =================================================================================================================

    /** BMS Missing:
     * 	system function(integer)		isGuildQuestRegistered;
     * 	system function(integer)		canEnterGuildQuest;
     * 	system function				clearGuildQuest;
     * 	system function				incGuildPoint( integer );
     */
}
