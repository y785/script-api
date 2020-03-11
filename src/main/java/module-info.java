module moe.maple.script.api {
    requires slf4j.api;

    exports moe.maple.api.script.helper;
    exports moe.maple.api.script.logic;
    exports moe.maple.api.script.model.object;
    exports moe.maple.api.script.model.object.data.provider;
    exports moe.maple.api.script.model.object.data.safety;
    exports moe.maple.api.script.model.object.field;
    exports moe.maple.api.script.model.object.user;
    exports moe.maple.api.script.model.helper;
    exports moe.maple.api.script.util;
    exports moe.maple.api.script.util.triple;
    exports moe.maple.api.script.util.tuple;
}