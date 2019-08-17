package moe.maple.api.script.example;

import moe.maple.api.script.model.FunScript;
import moe.maple.api.script.model.ScriptAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleSayAndAsk extends FunScript {
    private static final Logger log = LoggerFactory.getLogger( ExampleSayAndAsk.class );

    @Override
    public void work() {
        ScriptAPI.say(this, "Message 1", "Message 2", "Message 3").andThen(() -> {
            ScriptAPI.askYesNo(this, "Do you want to go?", () -> {
                log.debug("Fun begins! :D");
            });
        });
    }
}
