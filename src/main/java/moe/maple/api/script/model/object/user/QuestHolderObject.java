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

import java.util.Optional;

/**
 * An object that has a collection of quests.
 * @param <T> Typically a user/character.
 */
public interface QuestHolderObject<T> extends ScriptObject<T> {

    /**
     * This should return 0-2 depending on a quests current status
     * 0 - NotStarted
     * 1 - In Progress
     * 2 - Complete
     * @return int representing quest status
     */
    int getState(int questId);

    /**
     * @param questId the quest
     * @param state the state, see above
     * @return true if the quest state was set
     */
    default boolean setState(int questId, int state) {
        return getQuest(questId).map(quest->quest.setState(state)).orElse(false);
    }

    default boolean start(int questId) {
        return getQuest(questId).map(QuestObject::start).orElse(false);
    }

    default boolean complete(int questId) {
        return getQuest(questId).map(QuestObject::complete).orElse(false);
    }

    /**
     * Forfeits the quest.
     * @param key a questId
     * @param force if TRUE, it will remove it from the quest record no matter what.
     * @return true if successful.
     */
    boolean remove(short key, boolean force);

    default boolean isInProgress(int questId) { return getState(questId) == 1; }
    default boolean isComplete(int questId) { return getState(questId) == 2; }


    /**
     * @return an empty string if the quest doesn't have the key
     */
    default String getEx(int questId, String key) {
        return getQuest(questId).map(quest->quest.getEx(key)).orElse("");
    }

    default boolean containsEx(int questId, String key, String value) {
        return getEx(questId, key).contentEquals(value);
    }

    Optional<QuestObject> getQuest(int questId);

    default boolean setEx(int questId, String key, String value) {
        return getQuest(questId).map(quest->quest.setEx(key, value)).orElse(false);
    }

}
