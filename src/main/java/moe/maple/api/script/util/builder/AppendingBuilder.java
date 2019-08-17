package moe.maple.api.script.util.builder;

/**
 * @author umbreon22
 * @param <Builder> A Builder class
 */
public interface AppendingBuilder<Builder extends AppendingBuilder<Builder>> {

    Builder get();

    Builder append(String str);

    Builder append(Object object);

    Builder append(StringBuffer sb);

    Builder append(CharSequence s);

}
