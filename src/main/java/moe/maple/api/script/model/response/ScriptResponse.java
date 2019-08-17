package moe.maple.api.script.model.response;

import moe.maple.api.script.model.type.SpeakerType;

@FunctionalInterface
public interface ScriptResponse {
    void response(SpeakerType type, Number action, Object response);
}
