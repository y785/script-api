/*
 * Copyright (C) 2019, y785, http://github.com/y785
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package moe.maple.api.script.model.object.user;

import moe.maple.api.script.model.object.GuildObject;
import moe.maple.api.script.model.object.PartyObject;
import moe.maple.api.script.model.object.field.FieldedObject;
import moe.maple.api.script.model.object.field.LifeObject;
import moe.maple.api.script.util.builder.ScriptFormatter;
import org.slf4j.helpers.MessageFormatter;

import java.util.Optional;

/**
 * This is a script proxy for user/character objects.
 * <T> should be your implementation of user/character.
 * @param <T>
 */
public interface UserObject<T> extends LifeObject<T>, InventoryHolderObject<T>, QuestHolderObject<T> {

    /**
     * The user's id, typically their database key or sn
     * @return user id
     */
    long getId();

    /**
     * @return the user's name, duh
     */
    String getName();

    // =================================================================================================================

    Optional<GuildObject> getGuild();
    Optional<PartyObject> getParty();

    default boolean isInGuild() { return getGuild().isPresent(); }
    default boolean isInParty() { return getParty().isPresent(); }

    boolean isCreateGuildPossible(int cost);
    default boolean isCreateGuildPossible() { return isCreateGuildPossible(0); }

    boolean createNewGuild(int cost);
    default boolean createNewGuild() { return createNewGuild(0); }

    boolean removeGuild(int cost);
    default boolean removeGuild() { return removeGuild(0); }

    int getBuddyCapacity();

    boolean increaseBuddyCapacity(int amount, int cost);
    default boolean increaseBuddyCapacity(int amount) { return increaseBuddyCapacity(amount, 0); }

    // =================================================================================================================

    /**
     * Script variables are special values that are stored on a per-user
     * basis. They are used cross-script for various purposes. Examples include
     * Free Market portals and World Trip. The relationship is a String:String map.
     * If you don't wish to store like that, you should shove the data into an unused
     * quest like nexon does.
     * @param key
     * @return the value stored
     */
    String getScriptVariable(String key);
    default String getScriptVariable(int value) { return getScriptVariable(Integer.toString(value)); }

    boolean setScriptVariable(String key, String value);
    default boolean setScriptVariable(String key, Integer value) { return setScriptVariable(key, Integer.toString(value)); }
    default boolean setScriptVariable(String key, String format, Object... objects) { return setScriptVariable(key, ScriptFormatter.format(format, objects)); }

    // =================================================================================================================

    /**
     * This doesn't necessarily have to be immediate. Recommended
     * procedure is to add an on-end-event for current script, to go to the next script.
     * @param scriptName the script to chain to
     */
    void talkTo(String scriptName);

    /**
     * @param npcId - The NPC used to grab the script name.
     */
    void talkTo(int npcId);

    /**
     * See above for implementation recommendations.
     * @param shopId the shop id to load
     */
    void openShop(int shopId);

    // =================================================================================================================

    /**
     * This doesn't necessarily have to be an immediate transfer.
     * You can register an on-script-end event to transfer after script has finished.
     * @param fieldId the map/field to transfer to
     * @return true if transfer was successful
     */
    boolean transferField(int fieldId);

    /**
     * @param spawnPoint Portal Id
     */
    boolean transferField(int fieldId, int spawnPoint);

    /**
     * @param spawnPoint Portal name
     */
    boolean transferField(int fieldId, String spawnPoint);

    /**
     * Teach the user the skill provided.
     * @param skillId - Skill id
     * @param level   - Skill level
     * @param mastery - Skill mastery
     * @return true if user learned the targeted skill, level, & mastery.
     */
    boolean learnSkill(int skillId, int level, int mastery);

    /**
     * @param skillId - Skill id
     * @return true if the skill was forgotten/removed.
     */
    boolean forgetSkill(int skillId);

    default boolean learnSkill(int skillId, int level) { return learnSkill(skillId, level, 1); }
    default boolean learnSkill(int skillId) { return learnSkill(skillId, 1, 1); }

    /**
     * Gives the user a buff based on a consumable itemId.
     * @param buffItemId a consumable itemId
     * @return true if the operation is successful.
     */
    boolean giveBuffItem(int buffItemId);

    /**
     * Gives the user a buff based on a skill id.
     * @param skillId a skill .wz id
     * @return true if the operation is successful.
     */
    boolean giveBuffSkill(int skillId);

    // =================================================================================================================

    /*
     * Aran and evan quest tutors.
     * hire/fire should add/remove the tutor skill from the respective beginner job.
     * These are used in the beginner quests for legend/noblesse.
     * Packet: UserHireTutor / UserTutorMsg
     */

    boolean hireTutor();
    boolean fireTutor();

    void tutorMessage(int value, int duration);
    default void tutorMessage(int value) { tutorMessage(value, 7000); }

    default void tutorMessage(String value) { tutorMessage(value, 200, 7000); }
    void tutorMessage(String value, int width, int duration);

    // =================================================================================================================

    /*
     * Packet: SetStandAloneMode
     */
    void setStandAloneMode(boolean set);

    /*
     * Packet: DirectionMode
     */
    void setDirectionMode(boolean set);

    // =================================================================================================================

    /**
     * Unfortunately, there are only three Genders in MapleStory #confirmed
     * 0 = MALE
     * 1 = FEMALE
     * 2 = BOTH
     * @return The user's gender represented as an integer.
     */
    int getGender();

    /**
     * Gender in 2019?
     * @return true if the User's gender is set to Male.
     */
    default boolean isMale() {
        return getGender() == 0;
    }
    /**
     * Always false, trust me. Not the return value.
     * @return true if the User's gender is set to Female.
     */
    default boolean isFemale() {
        return getGender() == 1;
    }

    /**
     * Retrieves the User's current Hair Identifier.
     * @return hairId as an integer
     */
    int getHair();

    /**
     * Retrieves the User's current Face Identifier.
     * @return faceId as an integer
     */
    int getFace();

    /**
     * Sets the user's Hair identifier.
     * @param hairId A Hair identifier
     * @return true if the operation completes successfully.
     */
    boolean setHair(int hairId);

    /**
     * Sets the user's Face identifier.
     * @param faceId A Face identifier
     * @return true if the operation completes successfully.
     */
    boolean setFace(int faceId);

    /**
     * Retrieves the User's current Skin Identifier.
     * @return skinId as an integer
     */
    int getSkin();

    /**
     * Retrieves the user's Strength stat.
     * @return The user's STR as an Integer.
     */
    int getStrength();

    /**
     * Retrieves the user's Dexterity stat.
     * @return The user's DEX as an Integer.
     */
    int getDexterity();


    /**
     * Retrieves the user's Intelligence stat.
     * @return The user's INT as an Integer.
     */
    int getIntelligence();

    /**
     * Retrieves the user's Luck stat.
     * @return The user's LUK as an Integer.
     */
    int getLuck();

    /**
     * Retrieves the user's current fame stat.
     * @return The user's current POP as an integer
     */
    int getFame();

    // =================================================================================================================


    /**
     * @return the user's current mesos/money amount
     */
    int getMoney();

    default boolean hasMoney(int amount) { return getMoney() >= Math.abs(amount); }

    /**
     * @param amount the amount to increased/decreased
     * @return if money was increased successfully
     */
    boolean increaseMoney(int amount);
    boolean decreaseMoney(int amount);

    /**
     * @return user's current level
     */
    int getLevel();

    /**
     * @return user's current experience
     */
    long getExperience();

    /**
     * Increases a user's current experience.
     * @param amount - The amount to increase by
     * @param quest  - For how the packet is shown. true should show the notice into
     *                 the chatlog. false should show it on the right status.
     *                 Most scripts will have no need to change this, but :shrug:.
     * @return true if able to increase
     */
    boolean increaseExp(int amount, boolean quest);

    default boolean increaseExp(int amount) { return increaseExp(amount, true); }

    /**
     * @return user's ability points
     */
    int getAbilityPoints();

    /**
     * @param amount - The amount to increase by
     * @return true if able to increase
     */
    boolean increaseAbilityPoints(int amount);

    /**
     * @param amount - The amount to increase by
     * @return true if able to decrease
     */
    boolean decreaseAbilityPoints(int amount);

    /**
     * Returns the user's skill points based on tier.
     * If your version doesn't have skill point tiers, just return
     * the current skill point amount.
     * @param tier
     */
    int getSkillPoints(int tier);
    int getSkillPoints();

    /**
     * Increases a user's available skill points.
     * @param amount - The amount to increase by
     * @param tier   - The tier to increase. Used for jobs that have split
     *                 skill points. Evan & Dual Blade. Later versions split all
     *                 skills, while lower don't.
     */
    boolean increaseSkillPoints(int amount, int tier);

    /**
     * Increases a user's available skill points.
     * If your user has tiered skill points, you should default to
     * the current active tier.
     * @param amount - The amount to increase by
     */
    boolean increaseSkillPoints(int amount);

    // =================================================================================================================

    /**
     * This is intended to be used for job advancement, but doesn't have to be.
     * @param jobCode A {@link Short} job identifier.
     * @param isJobAdvancement if true, the user's stats should increase as they would per job advancement.
     * @return true if the operation completes successfully.
     */
    boolean setJob(short jobCode, boolean isJobAdvancement);

    /**
     * @see #setJob(short, boolean)
     * @param jobCode A {@link Short} job identifier.
     * @return true if the operation completes successfully.
     */
    default boolean setJob(short jobCode) {
        return setJob(jobCode, false);
    }

    /**
     * @return a user's current job id
     */
    int getJobId();

    // =================================================================================================================

    /*
     * Normally these would be classified as a constant and thrown into
     * a static method, so that it could be called whenever. However, since
     * these change based on version,  it's important that you have to ability
     * to override these as nexon changes jobs. So, for now, some simple implementations.
     * Based on v92 ~ v95.
     */
    default boolean isAdmin() {
        var nJob = getJobId();
        return nJob % 1000 / 100 == 9;
    }
    default boolean isAran() {
        var nJob = getJobId();
        return nJob / 100 == 21 || nJob == 2000;
    }
    default boolean isBeginner() {
        var nJob = getJobId();
        return (nJob % 1000 == 0) || nJob == 2001;
    }
    default boolean isBattleMage() {
        var nJob = getJobId();
        return nJob / 100 == 32;
    }
    default boolean isCygnus() {
        var nJob = getJobId();
        return nJob / 1000 == 1;
    }
    default boolean isDualBlade() {
        var nJob = getJobId();
        return nJob / 10 == 43;
    }
    default boolean isExtendedSPJob() {
        var nJob = getJobId();
        return nJob / 1000 == 3 || nJob / 100 == 22 || nJob == 2001;
    }
    default boolean isEvan() {
        var nJob = getJobId();
        return nJob / 100 == 22 || nJob == 2001;
    }
    default boolean isManager() {
        var nJob = getJobId();
        return nJob % 1000 / 100 == 8;
    }
    default boolean isMechanic() {
        var nJob = getJobId();
        return nJob / 100 == 35;
    }
    default boolean isResistance() {
        var nJob = getJobId();
        return nJob / 1000 == 3;
    }
    default boolean isWildHunter() {
        var nJob = getJobId();
        return nJob / 100 == 33;
    }

    // =================================================================================================================

    /**
     * Resets the User's AP in all 4 primary stats (STR, DEX, LUK, INT)
     * @param remain How much AP should remain after resetting (typically min of 4)
     * @return The # of AP reset.
     */
    int resetAp(int remain);

    /**
     * Calls {@link #resetAp(int)} with a value of 4, the standard default minimum AP per stat.
     * @return The # of AP reset.
     */
    default int resetAp() {
        return resetAp(4);
    }

    void openSkillGuide();

    void openClassCompetitionPage();

    /**
     * @return The user's current channelId.
     */
    int getChannelId();

}
