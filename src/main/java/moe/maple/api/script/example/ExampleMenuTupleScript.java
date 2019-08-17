package moe.maple.api.script.example;

import moe.maple.api.script.model.FunScript;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.util.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMenuTupleScript extends FunScript {

    private static final Logger log = LoggerFactory.getLogger( ExampleMenuTupleScript.class );

    @Override
    public void work() {
        ScriptAPI.askMenu(this, "Example Menu Script",
        Tuple.of("Option 1", () -> {
            log.debug("Option 1 met.");
        }), Tuple.of("Option 2", () -> {
            log.debug("Option 2 met.");
        }), Tuple.of("Option 3", () -> {
            log.debug("Option 3 met.");
        }), Tuple.of("Option 4", () -> {
            log.debug("Option 4 met.");
        }), Tuple.of("Option 5", () -> {
            log.debug("Option 5 met.");
        }));
    }
}
