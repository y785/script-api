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
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class BaseScript implements MoeScript {

    private static final Logger log = LoggerFactory.getLogger( BaseScript.class );

    protected String expected;

    protected ScriptAction nextAction;
    protected ScriptResponse nextResponse;
    private List<ScriptEvent> startScriptEvents;
    private List<ScriptEvent> endScriptEvents;
    private List<ScriptEvent> beforeRunEvents;
    private List<ScriptEvent> afterRunEvents;
    private List<ScriptEvent> npEvents;
    private ScriptEvent escapeEvent;

    protected ServerObject<?> server;
    protected FieldObject<?> field;
    protected FieldSetObject<?> fieldset;
    protected NpcObject<?> npc;
    protected PortalObject<?> portal;
    protected QuestObject<?> quest;
    protected ReactorObject<?> reactor;
    protected InventorySlotObject<?> item;
    protected UserObject<?> user;

    private boolean done;

    private boolean awaitingRespnse;

    public BaseScript() {
        this.expected = "";
    }

    @Override
    public String name() {
        var annotation = this.getClass().getAnnotation(Script.class);
        if (annotation == null) return "Unnamed Script: "+this.getClass().getName();
        return annotation.name()[0];
    }

    @Override
    public void setExpected(String expected) {
        this.expected = expected;
    }

    @Override
    public String getExpected() {
        return expected;
    }

    protected abstract void work();

    // =================================================================================================================

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isPaused() {
        return !isDone() && (isNextResponseSet() || isNextActionSet());
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
        if (events == null)
            return;
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
        if (!hasPermission()) {
            log.debug("Script({}/{}) doesn't have permission to run, not starting.", name(), expected);
            doEvents(npEvents);
            return;
        }
        log.debug("Script is starting: {} / {}", name(), expected);
        this.done = false;
        this.nextResponse = null;
        this.nextAction = null;
        doEvents(startScriptEvents);

        if (ScriptAPI.INSTANCE.getPreferences().shouldCatchExceptions()) {
            log.debug("Starting with exception handling...");
            With.silence(this::startMaybeException, (e) -> {
                log.error("Oh no! A script({})({}) threw an exception during start.", name(), expected, e);
                end();
            });
        } else {
            log.debug("Starting without exception handling...");
            startMaybeException();
        }

        if (!isNextResponseSet() && !isNextActionSet())
            end();
        else
            log.debug("Next actions are set, waiting for response to resume: {} / {}", isNextResponseSet(), isNextActionSet());
    }

    @Override
    public void end() {
        if (isDone()) {
            log.debug("Script is already done: {} / {}", name(), expected);
            return;
        }
        log.debug("Script has ended: {} / {}", name(), expected);
        doEvents(endScriptEvents);
        this.escapeEvent = null;
        this.nextResponse = null;
        this.nextAction = null;
        this.done = true;
    }

    @Override
    public void escape() {
        log.debug("Escape response being processed.");
        if (escapeEvent != null) {
            escapeEvent.act(this);
            if (escapeEvent.isSingleUse())
                escapeEvent = null;
        } else {
            this.end();
        }
    }

    @Override
    public void reset() {
        end();
        // Trust, but verify.
        this.escapeEvent = null;
        this.nextResponse = null;
        this.nextAction = null;
        this.beforeRunEvents = null;
        this.afterRunEvents = null;
        this.done = false;
    }

    /**
     * Most likely cause of an exception: user input.
     */
    private void resumeMaybeException(Number type, Number action, Object response) {
        var act = nextAction;
        var resp = nextResponse;

        this.awaitingRespnse = false;

        if (isNextResponseSet()) {
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
        log.debug("Resuming Script({})({}) with: {} / {} / {}", name(), expected, type, action, response);

        if (isPaused()) {
            if (ScriptAPI.INSTANCE.getPreferences().shouldCatchExceptions()) {
                With.silence(() -> resumeMaybeException(type, action, response), e -> {
                    log.error("Oh no! A script({})({}) threw an exception during resume.", name(), expected, e);
                    end();
                });
            } else {
                resumeMaybeException(type, action, response);
            }
        } else {
            log.debug("Script isn't paused, ending.");
            end();
        }
    }

    // =================================================================================================================

    @Override
    public void setScriptAction(ScriptAction action) {
        if (awaitingRespnse)
            throw new IllegalArgumentException("Already waiting for a response...");
        this.awaitingRespnse = true;
        this.nextAction = action;
    }

    @Override
    public void setScriptResponse(ScriptResponse response) {
        this.nextResponse = response;
    }

    // =================================================================================================================

    @Override
    public void setEscapeEvent(ScriptEvent event) {
        escapeEvent = event;
    }

    @Override
    public void addStartEvent(ScriptEvent event) {
        if (startScriptEvents == null)
            startScriptEvents = new ArrayList<>();
        startScriptEvents.add(event);
    }

    @Override
    public void addEndEvent(ScriptEvent event) {
        if (endScriptEvents == null)
            endScriptEvents = new ArrayList<>();
        this.endScriptEvents.add(event);
    }

    @Override
    public void addAfterRunEvent(ScriptEvent event) {
        if (afterRunEvents == null)
            afterRunEvents = new ArrayList<>();
        afterRunEvents.add(event);
    }

    @Override
    public void addBeforeRunEvent(ScriptEvent event) {
        if (beforeRunEvents == null)
            beforeRunEvents = new ArrayList<>();
        beforeRunEvents.add(event);
    }

    @Override
    public void addNoPermissionEvent(ScriptEvent event) {
        if (npEvents == null)
            npEvents = new ArrayList<>();
        npEvents.add(event);
    }

    // =================================================================================================================


    @Override
    public void setServerObject(ServerObject<?> object) {
        this.server = object;
    }

    @Override
    public void setFieldObject(FieldObject<?> field) {
        this.field = field;
    }

    @Override
    public void setFieldSetObject(FieldSetObject<?> fieldset) {
        this.fieldset = fieldset;
    }

    @Override
    public void setNpcObject(NpcObject<?> npc) {
        this.npc = npc;
    }

    @Override
    public void setPortalObject(PortalObject<?> portal) {
        this.portal = portal;
    }

    @Override
    public void setQuestObject(QuestObject<?> quest) {
        this.quest = quest;
    }

    @Override
    public void setInventorySlotObject(InventorySlotObject<?> item) {
        this.item = item;
    }

    @Override
    public void setReactorObject(ReactorObject<?> reactor) {
        this.reactor = reactor;
    }

    @Override
    public void setUserObject(UserObject<?> user) {
        this.user = user;
    }

    @Override
    public Optional<InventorySlotObject<?>> getInventorySlotObject() {
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<ServerObject<?>> getServerObject() {
        return Optional.ofNullable(server);
    }

    @Override
    public Optional<FieldObject<?>> getFieldObject() {
        return Optional.ofNullable(field);
    }

    @Override
    public Optional<FieldSetObject<?>> getFieldSetObject() {
        return Optional.ofNullable(fieldset);
    }

    @Override
    public Optional<NpcObject<?>> getNpcObject() {
        return Optional.ofNullable(npc);
    }

    @Override
    public Optional<PortalObject<?>> getPortalObject() {
        return Optional.ofNullable(portal);
    }

    @Override
    public Optional<QuestObject<?>> getQuestObject() {
        return Optional.ofNullable(quest);
    }

    @Override
    public Optional<ReactorObject<?>> getReactorObject() {
        return Optional.ofNullable(reactor);
    }

    @Override
    public Optional<UserObject<?>> getUserObject() {
        return Optional.ofNullable(user);
    }
}
