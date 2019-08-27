package moe.maple.api.script.test;

import moe.maple.api.script.util.ListCursorIterator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 8/26/2019.
 */
public class UtilTest {

    private static final Logger log = LoggerFactory.getLogger( ApiTest.class );

    @Test
    public void testCursorIterator() {
        var cursor = new ListCursorIterator<Integer>(new ArrayList<>());
        cursor.add(69);
        cursor.next();
        assertEquals(1, cursor.size());
        assertEquals(0, cursor.cursor());
        assertFalse(cursor.hasNext());
        assertFalse(cursor.hasPrev());
        cursor.add(22);
        cursor.next();
        assertFalse(cursor.hasNext());
        assertTrue(cursor.hasPrev());
        assertEquals(69, (int) cursor.prev());
        assertFalse(cursor.hasPrev());
        assertTrue(cursor.hasNext());
        assertEquals(22, (int) cursor.next());
        assertFalse(cursor.hasNext());
        log.info("All is well with cursors.");
    }

}
