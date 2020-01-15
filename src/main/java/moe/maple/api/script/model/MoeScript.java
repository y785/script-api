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

package moe.maple.api.script.model;

import moe.maple.api.script.logic.action.ScriptAction;
import moe.maple.api.script.model.object.*;
import moe.maple.api.script.model.object.field.NpcObject;
import moe.maple.api.script.model.object.field.PortalObject;
import moe.maple.api.script.model.object.field.ReactorObject;
import moe.maple.api.script.model.object.user.InventorySlotObject;
import moe.maple.api.script.model.object.user.QuestObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.logic.response.ScriptResponse;
import moe.maple.api.script.logic.event.ScriptEvent;
import moe.maple.api.script.model.type.ScriptMessageType;

import java.util.Optional;

public interface MoeScript {

    String name();

    /**
     *  Scripts can have multiple names and aliases,
     *  since it is unsure which script you're aiming at,
     *  you need to set the expected value here, for clarity.
     * @param expected - The expected name for this script.
     *                 - Used to override script names, especially useful
     *                 - when the script doesn't exist.
     */
    void setExpected(String expected);

    /**
     * @return See {@link #setExpected(String)}
     */
    String getExpected();

    /**
     * Scripts have multiple states, this will return true if the
     * script has finished processing and is ready to be disposed or reset.
     * @return - true if script used {@link #start()} and reached {@link #end()}
     */
    boolean isDone();

    /**
     * Is the script waiting for a response for {@link #resume(Number, Number, Object)}?
     * @return - true if the script waiting for a response
     */
    boolean isPaused();

    boolean isNextResponseSet();
    boolean isNextActionSet();

    // =================================================================================================================

    /**
     * A check if this script has permission to run.
     * This can be overridden to check if a user is valid for this script,
     * or field, or whatever. This is called before {@link #start()}
     * @return - true if script can run
     */
    default boolean hasPermission() { return true; }

    // =================================================================================================================

    /**
     * Start the script, calling all events attached.
     */
    void start();

    /**
     * End the script, calling all events attached.
     */
    void end();

    /**
     * Resumes the scripts
     * @param type     - {@link ScriptMessageType}
     * @param action   - The action for the <code>type</code>
     * @param response - The object to respond with
     */
    void resume(Number type, Number action, Object response);

    /**
     * Called if {@link #resume(Number, Number, Object)}'s <code>action</code>
     * matches the escape event, typically -1.
     * If escape event is null, then {@link #end()} is called.
     */
    void escape();

    /**
     * Resets the script back to a default state.
     */
    void reset();
    default void resetAndStart() { reset(); start(); }

    // =================================================================================================================

    void setScriptAction(ScriptAction action);
    void setScriptResponse(ScriptResponse response);

    // =================================================================================================================

    /**
     * Called in {@link #start()}
     * @param event - the event to add
     */
    void addStartEvent(ScriptEvent event);

    /**
     * Called in {@link #end()}
     * @param event - the event to add
     */
    void addEndEvent(ScriptEvent event);

    /**
     *  Called internally after the work method.
     *  @param event - the event to add
     */
    void addAfterRunEvent(ScriptEvent event);

    /**
     *  Called internally before the work method.
     *  @param event - the event to add
     */
    void addBeforeRunEvent(ScriptEvent event);

    /**
     * Called in {@link #start()} if script fails {@link #hasPermission()}
     * @param event - the event to add
     */
    void addNoPermissionEvent(ScriptEvent event);

    /**
     * Called in {@link #resume(Number, Number, Object)} if a user issues an escape action
     * @param event - the event to add
     */
    void setEscapeEvent(ScriptEvent event);

    // =================================================================================================================

    void setInventorySlotObject(InventorySlotObject<?> itemSlot);
    void setServerObject(ServerObject<?> object);
    void setFieldObject(FieldObject<?> object);
    void setFieldSetObject(FieldSetObject<?> object);
    void setNpcObject(NpcObject<?> object);
    void setPortalObject(PortalObject<?> object);
    void setQuestObject(QuestObject<?> object);
    void setReactorObject(ReactorObject<?> object);
    void setUserObject(UserObject<?> object);

    Optional<InventorySlotObject<?>> getInventorySlotObject();
    Optional<ServerObject<?>> getServerObject();
    Optional<FieldObject<?>> getFieldObject();
    Optional<FieldSetObject<?>> getFieldSetObject();
    Optional<NpcObject<?>> getNpcObject();
    Optional<PortalObject<?>> getPortalObject();
    Optional<QuestObject<?>> getQuestObject();
    Optional<ReactorObject<?>> getReactorObject();
    Optional<UserObject<?>> getUserObject();

    /**
     * Mainly used for packets. Maybe scripts will use this in say prompts or messages.
     * If a script is going to speak, it needs a template id. Not all will speak, but this is useful for reasons.
     * Can be overridden on a per-script basis.
     * @return the NpcObject's template id or 2007, which is the id for maple administrator (I hope)
     */
    default int getSpeakerTemplateId() { return getNpcObject().map(NpcObject::getTemplateId).orElse(2007); }

}
