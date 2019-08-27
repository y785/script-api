package moe.maple.api.script.model;

/**
 * Created on 8/26/2019.
 */
public class ScriptPreferences {

    private boolean forceOkOnSay;
    public static final ScriptPreferences DEFAULT = new ScriptPreferences();
    private ScriptPreferences() {//Defaults go here.
        this.forceOkOnSay = false;
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
}
