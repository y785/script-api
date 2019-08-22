package moe.maple.api.script.model.object.data.safety;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author umbreon22
 * Created on 8/21/2019.
 */
public interface DataValidator<Key> {

    boolean isValid(Key keys);

    default boolean isValid(Collection<Key> keys) {
        return keys.stream().allMatch(this::isValid);
    }

    default boolean isValid(Key[] keys) {
        return Arrays.stream(keys).allMatch(this::isValid);
    }

    default Set<Key> collectValidKeys(Collection<Key> keys) {
        return keys.stream().filter(this::isValid).collect(Collectors.toSet());
    }

    default Set<Key> collectValidKeys(Key[] keys) {
        return Arrays.stream(keys).filter(this::isValid).collect(Collectors.toSet());
    }


}

