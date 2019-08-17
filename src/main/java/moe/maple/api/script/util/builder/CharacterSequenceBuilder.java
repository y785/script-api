package moe.maple.api.script.util.builder;

/**
 * @author umbreon22
 * @param <Builder> A Builder class
 */
interface CharacterSequenceBuilder<Builder extends CharacterSequenceBuilder<Builder>> {

    Builder append(CharSequence text);

    /**
     * Disregard. 4000 IQ plays.
     * @return A Builder.
     */
    Builder get();

    /**
     * @see {{@link #tab()}}
     * @param howMany Appends 'howMany' tabs to the builder.
     * @return A Builder.
     */
    default Builder tab(int howMany) {
        for(int i = 0; i < howMany; i++)
            tab();
        return get();
    }

    /**
     * Appends \t to the builder.
     * @return A Builder.
     */
    default Builder tab() {
        append("\t");
        return get();
    }

    /**
     * @see {{@link #newLine()} ()}}
     * @param howMany Appends 'howMany' newLines to the builder.
     * @return A Builder.
     */
    default Builder newLine(int howMany) {
        for(int i = 0; i < howMany; i++)
            newLine();
        return get();
    }

    /**
     * Appends a '\r\n' to the builder.
     * @return A Builder.
     */
    default Builder newLine() {
        append("\r\n");
        return get();
    }

    /**
     * Appends the System's line separator to the builder.
     * This is '\r\n' on Windows systems.
     * @return A Builder.
     */
    default Builder lineSeparator() {
        append(System.lineSeparator());
        return get();
    }

    /**
     * Appends a '\r' to the builder.
     * @return A Builder.
     */
    default Builder carriageReturn() {
        append("\r");
        return get();
    }

    /**
     * Appends a '\n' to the builder.
     * @return A Builder.
     */
    default Builder lineFeed() {
        append("\n");
        return get();
    }

}
