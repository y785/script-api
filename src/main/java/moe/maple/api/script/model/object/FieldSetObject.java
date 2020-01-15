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

/**
 * This is a script proxy for fieldset objects.
 * FieldSets are groups of fields, typically used for party quests or events.
 * @param <T> should be your implementation of fieldset.
 */
public interface FieldSetObject<T> extends ScriptObject<T> {

    String getName();

    //The below is reflective of BMS, not necessarily the final interface.
    String getVar(String name);
    void setVar(String name, String value);

    int getUserCount();

    /**
     * Attempts to enter the FieldSet.
     * @param characterId - the character id
     * @param fieldIndex  - the field index to enter into
     * @return            - the result code from entering
     */
    int enter(int characterId, int fieldIndex);

    int getReactorState(int fieldIndex, String name);//-1 on error

    void setReactorState(int fieldIndex, String name, int state, int delay);

    // =================================================================================================================

    int getTimeout();

    void resetQuestTime();

    long getQuestTime();

    // =================================================================================================================

    int increaseExpAll(int exp); //퀘스트 반복 횟수 Number of Quest Repeats? check BMS.

    // =================================================================================================================

    /**
     * Transfers all Users in the FieldSet to your specified Field ID.
     * @param fieldId - The field to transfer all users in the fieldset to
     * @param portal  - The portal name to transfer to
     */
    void transferFieldAll(int fieldId, String portal);

    /**
     * Sends a BroadcastMsg packet to the entire FieldSet.
     * @param type BroadcastMsg type
     * @param msg String message
     */
    void broadcastMsg(int type, String msg);

    /**
     * FALSE = Active. Restart failure.
     * @return TRUE = Successful restart.
     */
    boolean startManually();

    /**
     * "Do not use if resetQuestTime is needed. We can not guarantee what will happen." - neckson
     * @param timeMaybeCheckBMS - Unsure
     * @return successful reset
     */
    boolean resetTimeOut(int timeMaybeCheckBMS);

    void setTargetFieldID(int targetFieldID);

}
