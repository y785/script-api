package moe.maple.api.script.util.builder;

import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.messenger.say.SayMessage;
import moe.maple.api.script.model.object.field.FieldedObject;
import moe.maple.api.script.model.object.field.NpcObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.type.ScriptMessageParameters;
import moe.maple.api.script.model.type.ScriptSpeakerType;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created on 9/3/2019.
 */
public class SayBuilder {

    public static final int USER_TEMPLATE = 0;//Sets the template to user object id
    public static final int RESET_TEMPLATE = -1;//Sets the template to the original speaker template id

    private final int speakerTemplate;
    private final int speakerType;
    private int replaceTemplate;
    private int parameters;

    private final int userObjectId;
    private final List<SayMessage> saying;

    public SayBuilder(MoeScript script) {
        this(script.getNpcObject().map(FieldedObject::getObjectId).orElseThrow(), script.getUserObject().map(FieldedObject::getObjectId).orElseThrow());
    }

    public SayBuilder(int speakerTemplate, int userObjectId) {
        this.speakerTemplate = speakerTemplate;
        this.userObjectId = userObjectId;
        this.saying = new LinkedList<>();
        this.speakerType = ScriptSpeakerType.NONE;//Not sure if we'll support this yet.
    }

    public SayBuilder say(String... messages) {
        for(String msg : messages) {
            this.saying.add(new SayMessage(speakerType, speakerTemplate, replaceTemplate, parameters, msg));
        }
        return this;
    }

    public <T> SayBuilder sayIf(Predicate<T> yourFace, T obj, String... messages) {
        return sayIf(yourFace.test(obj), messages);
    }

    public SayBuilder sayIf(boolean sayIf, String... messages) {
        if(sayIf) {
            for (String msg : messages) {
                this.saying.add(new SayMessage(speakerType, speakerTemplate, replaceTemplate, parameters, msg));
            }
        }
        return this;
    }

    public SayBuilder sayAsUser(String... messages) {
        int tempParameters = parameters & ~ScriptMessageParameters.NPC_REPLACED_BY_NPC | ScriptMessageParameters.NPC_REPLACED_BY_USER;//remove NPC, add User
        for(String msg : messages) {
            this.saying.add(new SayMessage(ScriptSpeakerType.USER, speakerTemplate, 0, tempParameters, msg));//not sure about the speakerType here
        }
        return this;
    }

    public SayBuilder sayAsNpc(int replaceTemplate, String... messages) {
        int tempParameters = parameters & ~ScriptMessageParameters.NPC_REPLACED_BY_USER;//remove User, add NPC
        if(replaceTemplate != 0) tempParameters |= ScriptMessageParameters.NPC_REPLACED_BY_NPC;
        else tempParameters &= ~ScriptMessageParameters.NPC_REPLACED_BY_NPC;
        for(String msg : messages) {
            this.saying.add(new SayMessage(ScriptSpeakerType.NPC, speakerTemplate, replaceTemplate, tempParameters, msg));//not sure about the speakerType here
        }
        return this;
    }

    public SayBuilder sayAsNpc(String message) {
        return sayAsNpc(replaceTemplate, message);
    }

    public SayBuilder toggleEsc() {
        this.parameters ^= ScriptMessageParameters.NO_ESC;
        return this;
    }

    public SayBuilder disableEsc() {
        this.parameters |= ScriptMessageParameters.NO_ESC;
        return this;
    }

    public SayBuilder enableEsc() {
        this.parameters &= ~ScriptMessageParameters.NO_ESC;
        return this;
    }

    public boolean allowsEsc() {
        return (this.parameters & ScriptMessageParameters.NO_ESC) == 0;
    }

    public SayBuilder flipImage() {
        this.parameters ^= ScriptMessageParameters.FLIP_IMAGE;
        return this;
    }

    public boolean isFlipped() {
        return (this.parameters & ScriptMessageParameters.FLIP_IMAGE) != 0;
    }

    private void replaceTemplate(int templateId) {
        this.parameters &= ~ScriptMessageParameters.NPC_REPLACED_BY_USER;
        this.parameters &= ~ScriptMessageParameters.NPC_REPLACED_BY_NPC;
        if(templateId == RESET_TEMPLATE) {
            this.replaceTemplate = speakerTemplate;
        } else if(templateId == USER_TEMPLATE) {
            this.parameters |= ScriptMessageParameters.NPC_REPLACED_BY_USER;
            this.replaceTemplate = userObjectId;
        } else {
            this.parameters |= ScriptMessageParameters.NPC_REPLACED_BY_NPC;
            this.replaceTemplate = templateId;
        }
    }

    public SayBuilder asNpc() {
        replaceTemplate(USER_TEMPLATE);
        return this;
    }

    public SayBuilder asUser() {
        replaceTemplate(USER_TEMPLATE);
        return this;
    }

    public SayBuilder clearParameters() {
        this.parameters = 0;
        this.replaceTemplate = 0;
        return this;
    }

    public List<SayMessage> build() {
        return saying;
    }

}
