package moe.maple.api.script.model.object.data.provider;

import moe.maple.api.script.model.object.data.safety.DataValidator;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author umbreon22
 * Created on 8/21/2019.
 */
public interface DataProvider<Key, Value> extends DataValidator<Key> {

    /**
     * @return A stream of {@link Key}
     */
    Stream<Key> keyStream();

    /**
     * @return A stream of {@link Value}
     */
    Stream<Value> valueStream();

    /**
     * @param key A {@link Key}
     * @return An {@link Optional<Value>} from {@link Key}
     */
    Optional<Value> get(Key key);

    /**
     * @param filter A {@link Predicate<Key>} filter
     * @return A collection if {@link Value} that passes the predicate test.
     */
    default Collection<Value> getAll(Predicate<Value> filter) {
        return valueStream().filter(filter).collect(Collectors.toSet());
    }
    /**
     * @return A collection of all {@link Value}
     */
    default Collection<Value> getAll() {
        return valueStream().collect(Collectors.toSet());
    }

}
