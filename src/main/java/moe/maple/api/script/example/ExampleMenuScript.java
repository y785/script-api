package moe.maple.api.script.example;

import moe.maple.api.script.model.FunScript;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.util.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMenuScript extends FunScript {
    private static final Logger log = LoggerFactory.getLogger( ExampleMenuScript.class );
    @Override
    public void work() {
        var menuItems = new String[]{"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
        ScriptAPI.askMenu(this, "Example Menu Script", menuItems).andThen(idx -> {
            var selection = menuItems[idx.intValue()];
            log.debug("Selected: {}", selection);
        });
    }
}
