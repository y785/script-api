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
 * @param <T> your implementation of quest.
 */
public interface QuestObject<T> extends ScriptObject<T> {

    T get();

    int getId();

    /**
     * This should return 0-2 depending on a quests current status
     * 0 - NotStarted
     * 1 - In Progress (AKA 'Perform' in Nexon language)
     * 2 - Complete
     * @return int representing quest status
     */
    int getState();

    /**
     * @param state the state, see above
     * @return true if the quest state was set
     */
    boolean setState(int state);

    default boolean isInProgress() { return getState() == 1; }
    default boolean isComplete() { return getState() == 2; }

    default boolean start() { return setState(1); }
    default boolean complete() { return setState(2); }

    /**
     * @return an empty string if the quest doesn't have the key
     */
    String getEx(String key);

    boolean setEx(String key, String value);
}
