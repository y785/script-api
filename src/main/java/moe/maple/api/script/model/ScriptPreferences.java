package moe.maple.api.script.model;

/**
 * Created on 8/26/2019.
 */
public class ScriptPreferences {

    private boolean forceOkOnSay, catchExceptions;
    public static final ScriptPreferences DEFAULT = new ScriptPreferences();
    private ScriptPreferences() {//Defaults go here.
        this.forceOkOnSay = false;
        this.catchExceptions = true;
    }

    public ScriptPreferences catchExceptions(boolean enabled) {
        this.catchExceptions = enabled;
        return this;
    }

    public ScriptPreferences forceOkOnSay(boolean enabled) {
        this.forceOkOnSay = enabled;
        return this;
    }

    /**
     * If {@link #forceOkOnSay} is true, all script 'say' calls will end with 'OK'.
     * Example: Given the pseudo script { say("0", "1", "2").andThen(()=>askMenu("pick", "one")) }
     * If true, "2" would end with Ok else "2" would be next.
     * @return Your preference.
     */
    public boolean shouldForceOkOnSay() {
        return forceOkOnSay;
    }

    /**
     * If true, scripts will catch all exceptions thrown and issue {@link MoeScript#end()}.
     * If false, scripts will throw exceptions and not call {@link MoeScript#end()}.
     */
    public boolean shouldCatchExceptions() {
        return catchExceptions;
    }
}
