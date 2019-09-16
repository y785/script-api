package moe.maple.api.script.test;

import moe.maple.api.script.util.tuple.Tuple;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created on 9/14/2019.
 */
public class TupleTest {

    Logger log = LoggerFactory.getLogger(TupleTest.class);

    @Test
    public void simpleTupleTest() {
        List<Tuple<Integer, String>> tuples = Tuple.listOf(1, "one", 2, "two", 3, "three", 4, "four");
        Set<Integer> leftSet = Set.of(1, 2, 3, 4);
        Set<String> rightSet = Set.of("one", "two", "three", "four");
        AtomicInteger errorCount = new AtomicInteger(0);
        Tuple.forEach(tuples, (l, r)->{
            log.debug("Left: {}, Right: {}", l ,r);
            if(!leftSet.contains(l)) {
                log.debug("Oh no {}", l);
            }
            if(!rightSet.contains(r)) {
                log.debug("Oh my {}", r);
            }
        });
        assertTrue(errorCount.get() == 0);
        log.debug("All is well with Tuples.");
    }
}
