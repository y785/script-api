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

package moe.maple.api.script.model.object;

import java.util.Optional;

/**
 * This is a script proxy for user/character objects.
 * <T> should be your implementation of user/character.
 * @param <T>
 */
public interface UserObject<T> extends FieldedObject {

    T getUser();

    /**
     * The user's id, typically their database key or sn
     * @return user id
     */
    long getId();

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

    /**
     * @return a user's current job id
     */
    int getJobId();

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
     * Teach the user the skill provided.
     * @param skillId - Skill id
     * @param level   - Skill level
     * @param mastery - Skill mastery
     * @return true if user learned the targeted skill, level, & mastery.
     */
    boolean learnSkill(int skillId, int level, int mastery);
    default boolean learnSkill(int skillId, int level) { return learnSkill(skillId, level, 1); }
    default boolean learnSkill(int skillId) { return learnSkill(skillId, 1, 1); }

    // =================================================================================================================

    int itemCount(int itemId);
    default boolean hasItem(int itemId, int count) { return itemCount(itemId) > count; }
    default boolean hasItem(int itemId) { return hasItem(itemId, 1); }

    // =================================================================================================================

    /**
     * This should return 0-2 depending on a quests current status
     * 0 - NotStarted
     * 1 - Started
     * 2 - Completed
     * These are officially from QR_STATE_ enum in BMS
     * @param questId the quest
     * @return int representing quest status
     */
    int getQuestState(int questId);

    /**
     * @param questId the quest
     * @param state the state, see above
     * @return true if the quest state was set
     */
    boolean setQuestState(int questId, int state);

    default boolean isQuestStarted(int questId) { return getQuestState(questId) == 1; }
    default boolean isQuestCompleted(int questId) { return getQuestState(questId) == 2; }

    default boolean startQuest(int questId) { return setQuestState(questId, 1); }
    default boolean completeQuest(int questId) { return setQuestState(questId, 2); }

    /**
     * @return an empty string if the quest doesn't have the key
     */
    String getQuestEx(int questId, String key);
    boolean setQuestEx(int questId, String key, String value);

    // =================================================================================================================

    /*
     * Aran and evan quest tutors.
     * hire/destroy should add/remove the tutor skill from the respective beginner job.
     * These are used in the beginner quests for legend/noblesse.
     * Packet: UserHireTutor / UserTutorMsg
     */

    boolean hireTutor();
    boolean destroyTutor();

    void tutorMessage(int value, int duration);
    default void tutorMessage(int value) { tutorMessage(value, 7000); }

    void tutorMessage(String value);
    void tutorMessage(String value, int width, int duration);

    // =================================================================================================================

    /*
     * Packet: SetStandAloneMode
     */
    void lockUI();
    void unlockUI();

    // =================================================================================================================

    /**
     * Packet: UserEffectLocal | UserEffect.AvatarOriented
     * @param path - The Effect.wz UOL, example: Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow1
     * @param durationInSeconds - The duration of the effect in seconds -- This was removed in later versions.
     */
    void avatarOriented(String path, int durationInSeconds);

    /**
     * Packet: UserEffectLocal | UserEffect.ReservedEffect
     * This is commonly called showEffect/showIntro/playScene in odin-based sources.
     * @param path - The Effect.wz UOL, example: Effect/Direction1/aranTutorial/Child
     */
    void reservedEffect(String path);

    /**
     * Packet: UserEffectLocal | UserEffect.PlayPortalSE
     * See: UserEffect.PlayPortalSE
     */
    void playPortalSE();

    // =================================================================================================================
}
