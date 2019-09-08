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

    boolean isDone();
    boolean isPaused();

    boolean isNextResponseSet();
    boolean isNextActionSet();

    // =================================================================================================================

    // void work();

    void start();
    void end();

    /**
     * Resets the script back to its default state.
     */
    void reset();
    default void resetAndStart() { reset(); start(); }

    void resume(Number type, Number action, Object response);

    // =================================================================================================================

    void setScriptAction(ScriptAction action);
    void setScriptResponse(ScriptResponse response);

    void addStartEvent(ScriptEvent event);
    void addEndEvent(ScriptEvent event);

    /**
     *  After run is called before {@link #work()} and {@link #resume(Number, Number, Object)}
     */
    void addAfterRunEvent(ScriptEvent event);

    /**
     *  see {@link #addAfterRunEvent(ScriptEvent)}
     */
    void addBeforeRunEvent(ScriptEvent event);

    // =================================================================================================================

    void setInventorySlotObject(InventorySlotObject itemSlot);
    void setServerObject(ServerObject object);
    void setFieldObject(FieldObject object);
    void setFieldSetObject(FieldSetObject object);
    void setNpcObject(NpcObject object);
    void setPortalObject(PortalObject object);
    void setQuestObject(QuestObject object);
    void setReactorObject(ReactorObject object);
    void setUserObject(UserObject object);

    Optional<InventorySlotObject> getInventorySlotObject();
    Optional<ServerObject> getServerObject();
    Optional<FieldObject> getFieldObject();
    Optional<FieldSetObject> getFieldSetObject();
    Optional<NpcObject> getNpcObject();
    Optional<PortalObject> getPortalObject();
    Optional<QuestObject> getQuestObject();
    Optional<ReactorObject> getReactorObject();
    Optional<UserObject> getUserObject();

    /**
     * Mainly used for packets. Maybe scripts will use this in say prompts or messages.
     * If a script is going to speak, it needs a template id. Not all will speak, but this is useful for reasons.
     * Can be overridden on a per-script basis.
     * @return the NpcObject's template id or 2007, which is the id for maple administrator (I hope)
     */
    default int getSpeakerTemplateId() { return getNpcObject().map(NpcObject::getTemplateId).orElse(2007); }

}
