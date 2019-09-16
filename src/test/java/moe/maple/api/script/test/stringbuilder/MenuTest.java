package moe.maple.api.script.test.stringbuilder;

import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import moe.maple.api.script.util.builder.ScriptStringBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 9/15/2019.
 */
public class MenuTest {

    private static final Logger log = LoggerFactory.getLogger( MenuTest.class );

    @Test
    public void testMenuParsing() {
        var menu = new ScriptMenuBuilder<>().append("This is a test!").newLine().appendMenu("zero", "one", "two", "three").build();
        log.debug(menu);
        var matches = ScriptMenuBuilder.matchIndices(menu);
        log.debug(matches.toString());
        assertEquals(matches, List.of("#L0#zero#l", "#L1#one#l", "#L2#two#l", "#L3#three#l"));
        var options = matches.stream().map(ScriptMenuBuilder::parseMenuIndex).collect(Collectors.toSet());
        log.debug(options.toString());
        assertEquals(options, Set.of(0, 1, 2, 3));
    }

}
