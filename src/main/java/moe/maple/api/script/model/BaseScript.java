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

import moe.maple.api.script.logic.ScriptAPI;
import moe.maple.api.script.logic.action.BasicScriptAction;
import moe.maple.api.script.logic.action.IntegerScriptAction;
import moe.maple.api.script.logic.action.ScriptAction;
import moe.maple.api.script.logic.action.StringScriptAction;
import moe.maple.api.script.model.object.*;
import moe.maple.api.script.model.object.field.NpcObject;
import moe.maple.api.script.model.object.field.PortalObject;
import moe.maple.api.script.model.object.field.ReactorObject;
import moe.maple.api.script.model.object.user.InventorySlotObject;
import moe.maple.api.script.model.object.user.QuestObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.logic.response.ScriptResponse;
import moe.maple.api.script.logic.event.ScriptEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class BaseScript implements MoeScript {

    private static final Logger log = LoggerFactory.getLogger( BaseScript.class );

    protected ScriptAction nextAction;
    protected ScriptResponse nextResponse;
    protected List<ScriptEvent> startScriptEvents;
    protected List<ScriptEvent> endScriptEvents;
    protected List<ScriptEvent> beforeRunEvents;
    protected List<ScriptEvent> afterRunEvents;

    protected ServerObject server;
    protected FieldObject field;
    protected FieldSetObject fieldset;
    protected NpcObject npc;
    protected PortalObject portal;
    protected QuestObject quest;
    protected ReactorObject reactor;
    protected InventorySlotObject item;
    protected UserObject user;

    private boolean done;

    public BaseScript() {
        this.startScriptEvents = new ArrayList<>();
        this.endScriptEvents = new ArrayList<>();
        this.beforeRunEvents = new ArrayList<>();
        this.afterRunEvents = new ArrayList<>();
    }

    @Override
    public String name() {
        var annotation = this.getClass().getAnnotation(Script.class);
        if (annotation == null) return "Unnamed Script: "+this.getClass().getName();
        return annotation.name()[0];
    }

    protected abstract void work();

    // =================================================================================================================

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isPaused() {
        return !isDone() && (nextResponse != null || nextAction != null);
    }

    @Override
    public boolean isNextActionSet() {
        return nextAction != null;
    }

    @Override
    public boolean isNextResponseSet() {
        return nextResponse != null;
    }

    // =================================================================================================================

    private void doEvents(List<ScriptEvent> events) {
        var iter = events.iterator();
        while (iter.hasNext()) {
            var next = iter.next();
            next.act(this);

            if (next.isSingleUse())
                iter.remove();
        }
    }

    // =================================================================================================================

    private void startMaybeException() {
        doEvents(beforeRunEvents);
        work();
        doEvents(afterRunEvents);
    }

    @Override
    public void start() {
        log.debug("Script is starting: {}", name());
        this.done = false;
        this.nextResponse = null;
        this.nextAction = null;
        doEvents(startScriptEvents);

        if (ScriptAPI.INSTANCE.getPreferences().shouldCatchExceptions()) {
            try {
                startMaybeException();
            } catch (Exception e) {
                log.error("Oh no! A script({}) threw an exception during start.", name(), e);
                this.end();
            }
        } else {
            startMaybeException();
        }
        if (!isNextResponseSet() && !isNextActionSet())
            end();
    }

    @Override
    public void end() {
        if (done) {
            log.debug("Script is already done: {}", name());
            return;
        }
        log.debug("Script has ended: {}", name());
        if (done)
            return;
        doEvents(endScriptEvents);
        this.nextResponse = null;
        this.nextAction = null;
        this.done = true;
    }

    @Override
    public void reset() {
        end();

        beforeRunEvents.clear(); // should these be reset?
        afterRunEvents.clear();

        this.done = false;
    }

    /**
     * Don't look at me! I'm ugly! :c
     */
    private void resumeMaybeException(Number type, Number action, Object response) {
        var act = nextAction;
        var resp = nextResponse;

        if (resp != null) {
            doEvents(beforeRunEvents);
            resp.response(type, action, response);
            doEvents(afterRunEvents);
        } else {
            doEvents(beforeRunEvents);
            if (act instanceof BasicScriptAction) {
                ((BasicScriptAction) act).act();
            } else if (act instanceof IntegerScriptAction) {
                ((IntegerScriptAction) act).act((Integer) response);
            } else if (act instanceof StringScriptAction) {
                ((StringScriptAction) act).act((String) response);
            } else {
                log.error("Couldn't process action: {}", act.getClass().getSimpleName());
                this.nextResponse = null;
            }
            doEvents(afterRunEvents); // Should we still run if action is missing?

            if (nextResponse == null)
                this.end();
        }
    }

    @Override
    public void resume(Number type, Number action, Object response) {
        var act = nextAction;
        var resp = nextResponse;
        if (act != null || resp != null) {
            if (ScriptAPI.INSTANCE.getPreferences().shouldCatchExceptions()) {
                try {
                    resumeMaybeException(type, action, response);
                } catch (Exception e) {
                    log.error("Oh no! A script({}) threw an exception during resume.", name(), e);
                    this.end();
                }
            } else {
                resumeMaybeException(type, action, response);
            }
        } else {
            end();
        }
    }

    // =================================================================================================================

    @Override
    public void setScriptAction(ScriptAction action) {
        this.nextAction = action;
    }

    @Override
    public void setScriptResponse(ScriptResponse response) {
        this.nextResponse = response;
    }

    @Override
    public void addStartEvent(ScriptEvent event) {
        this.startScriptEvents.add(event);
    }

    @Override
    public void addEndEvent(ScriptEvent event) {
        this.endScriptEvents.add(event);
    }

    @Override
    public void addAfterRunEvent(ScriptEvent event) {
        this.afterRunEvents.add(event);
    }

    @Override
    public void addBeforeRunEvent(ScriptEvent event) {
        this.beforeRunEvents.add(event);
    }

    // =================================================================================================================


    @Override
    public void setServerObject(ServerObject object) {
        this.server = object;
    }

    @Override
    public void setFieldObject(FieldObject field) {
        this.field = field;
    }

    @Override
    public void setFieldSetObject(FieldSetObject fieldset) {
        this.fieldset = fieldset;
    }

    @Override
    public void setNpcObject(NpcObject npc) {
        this.npc = npc;
    }

    @Override
    public void setPortalObject(PortalObject portal) {
        this.portal = portal;
    }

    @Override
    public void setQuestObject(QuestObject quest) {
        this.quest = quest;
    }

    @Override
    public void setInventorySlotObject(InventorySlotObject item) {
        this.item = item;
    }

    @Override
    public void setReactorObject(ReactorObject reactor) {
        this.reactor = reactor;
    }

    @Override
    public void setUserObject(UserObject user) {
        this.user = user;
    }

    @Override
    public Optional<InventorySlotObject> getInventorySlotObject() {
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<ServerObject> getServerObject() {
        return Optional.ofNullable(server);
    }

    @Override
    public Optional<FieldObject> getFieldObject() {
        return Optional.ofNullable(field);
    }

    @Override
    public Optional<FieldSetObject> getFieldSetObject() {
        return Optional.ofNullable(fieldset);
    }

    @Override
    public Optional<NpcObject> getNpcObject() {
        return Optional.ofNullable(npc);
    }

    @Override
    public Optional<PortalObject> getPortalObject() {
        return Optional.ofNullable(portal);
    }

    @Override
    public Optional<QuestObject> getQuestObject() {
        return Optional.ofNullable(quest);
    }

    @Override
    public Optional<ReactorObject> getReactorObject() {
        return Optional.ofNullable(reactor);
    }

    @Override
    public Optional<UserObject> getUserObject() {
        return Optional.ofNullable(user);
    }
}
