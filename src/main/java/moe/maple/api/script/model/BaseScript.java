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

import moe.maple.api.script.model.action.BasicScriptAction;
import moe.maple.api.script.model.action.IntegerScriptAction;
import moe.maple.api.script.model.action.ScriptAction;
import moe.maple.api.script.model.action.StringScriptAction;
import moe.maple.api.script.model.object.*;
import moe.maple.api.script.model.object.field.NpcObject;
import moe.maple.api.script.model.object.field.PortalObject;
import moe.maple.api.script.model.object.field.ReactorObject;
import moe.maple.api.script.model.object.user.QuestObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.event.ScriptEvent;
import moe.maple.api.script.model.type.SpeakerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Optional;

public abstract class BaseScript implements MoeScript {

    private static final Logger log = LoggerFactory.getLogger( BaseScript.class );

    protected ScriptAction nextAction;
    protected ScriptResponse nextResponse;
    protected LinkedList<ScriptEvent> startScriptEvents;
    protected LinkedList<ScriptEvent> endScriptEvents;

    protected FieldObject field;
    protected FieldSetObject fieldset;
    protected NpcObject npc;
    protected PortalObject portal;
    protected QuestObject quest;
    protected ReactorObject reactor;
    protected UserObject user;

    private boolean done;

    public BaseScript() {
        this.startScriptEvents = new LinkedList<>();
        this.endScriptEvents = new LinkedList<>();
    }

    @Override
    public String name() {
        var annotation = this.getClass().getAnnotation(Script.class);
        if (annotation == null) return "Unnamed Script: "+this.getClass().getName();
        return annotation.name();
    }

    @Override
    public abstract void work();

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
    public void start() {
        log.debug("Script is starting: {}", name());
        this.done = false;
        this.nextResponse = null;
        this.nextAction = null;
        this.startScriptEvents.forEach(event -> event.act(this));
        try {
            work();
        } catch (Exception e) {
            log.error("Oh no!", e);
        }
        if (nextResponse == null && nextAction == null)
            end();
    }

    @Override
    public void end() {
        log.debug("Script has ended: {}", name());
        if (done)
            return;
        this.nextResponse = null;
        this.nextAction = null;
        this.endScriptEvents.forEach(event -> event.act(this));
        this.done = true;
    }

    @Override
    public void reset() {
        end();

        this.done = false;
    }

    @Override
    public void resume(SpeakerType type, Number action, Object response) {
        var act = nextAction;
        var resp = nextResponse;
        if (act != null || resp != null) {
            try {
                if (resp != null) {
                    resp.response(type, action, response);
                } else {
                    if (act instanceof BasicScriptAction) {
                        ((BasicScriptAction) act).act();
                        if (nextResponse == null)
                            this.end();
                    } else if (act instanceof IntegerScriptAction) {
                        ((IntegerScriptAction) act).act((Integer) response);
                        if (nextResponse == null)
                            this.end();
                    } else if (act instanceof StringScriptAction) {
                        ((StringScriptAction) act).act((String) response);
                        if (nextResponse == null)
                            this.end();
                    } else {
                        log.error("Couldn't process action: {}", act.getClass().getSimpleName());
                        this.end();
                    }
                }
            } catch (Exception e) {
                log.error(":(", e);
                end();
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

    // =================================================================================================================

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
    public void setReactorObject(ReactorObject reactor) {
        this.reactor = reactor;
    }

    @Override
    public void setUserObject(UserObject user) {
        this.user = user;
    }

    @Override
    public Optional<FieldObject> getFieldObect() {
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
