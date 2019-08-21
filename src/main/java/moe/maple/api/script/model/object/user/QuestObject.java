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

/**
 * This is a script proxy for quest objects.
 * <T> should be your implementation of quest.
 * @param <T>
 */
public interface QuestObject<T> extends ScriptObject {
    T getQuest();

    int getId();

    /**
     * This should return 0-2 depending on a quests current status
     * 0 - NotStarted
     * 1 - Started
     * 2 - Completed
     * These are officially from QR_STATE_ enum in BMS
     * @return int representing quest status
     */
    int getState();

    /**
     * @param state the state, see above
     * @return true if the quest state was set
     */
    boolean setState(int state);

    default boolean isStarted() { return getState() == 1; }
    default boolean isCompleted() { return getState() == 2; }

    default boolean start() { return setState(1); }
    default boolean complete() { return setState(2); }

    /**
     * Forfeits the quest.
     * @param key a questId
     * @param force if TRUE, it will remove it from the quest record no matter what.
     * @return true if successful.
     */
    boolean remove(short key, boolean force);

    /**
     * @return an empty string if the quest doesn't have the key
     */
    String getQuestEx(String key);

    boolean setQuestEx(String key, String value);
}
