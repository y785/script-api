package moe.maple.api.script.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;
import java.util.function.Function;

@Retention(RetentionPolicy.RUNTIME)
public @interface Script {
    String name();
    String author() default "";
    String description() default "";
}
