package moe.maple.api.script;

import moe.maple.api.script.example.ExampleSayAndAsk;
import moe.maple.api.script.model.type.SpeakerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger( Main.class );
    public static void main(String... args) {
        // Goals:
        // Non-blocking / Non-locking.
        // Initialize as needed.
        // Moe.

        var script = new ExampleSayAndAsk();
        script.addStartEvent(a -> {
            log.debug("Starting script! :D");
        });
        script.addEndEvent(e -> {
            log.debug("Ending script! :(");
        });
        script.start();

        script.resume(SpeakerType.SAY, 1, null); // Forward!
        script.resume(SpeakerType.SAY, 0, null); // Back
        script.resume(SpeakerType.SAY, 1, null); // Forward!
        script.resume(SpeakerType.SAY, 1, null); // Forward!
        script.resume(SpeakerType.SAY, 1, null); // Forward!

        script.resume(SpeakerType.ASKYESNO, 1, null);


    }
}
