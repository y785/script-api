package moe.maple.api.script.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Created on 9/30/2019.
 */
public class ListOf {

    public static <L, R, T> List<T> tuples(BiFunction<L, R, T> constructor, Object... args) {
        if ((args.length & 1) != 0) {
            throw new InternalError("length is odd");
        }
        List<T> list = new ArrayList<>(args.length / 2);
        for (int i = 0; i < args.length; i += 2) {
            @SuppressWarnings("unchecked")
            L left = Objects.requireNonNull((L)args[i]);
            @SuppressWarnings("unchecked")
            R right = Objects.requireNonNull((R)args[i+1]);
            list.add(constructor.apply(left, right));
        }
        return list;
    }
}
