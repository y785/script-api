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

package moe.maple.api.script.test;

import moe.maple.api.script.model.object.FieldObject;
import moe.maple.api.script.model.object.GuildObject;
import moe.maple.api.script.model.object.PartyObject;
import moe.maple.api.script.model.object.user.InventoryItemObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.Collection;
import java.util.Optional;

public class TestUserObject implements UserObject<Integer> {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Optional<GuildObject> getGuild() {
        return Optional.empty();
    }

    @Override
    public Optional<PartyObject> getParty() {
        return Optional.empty();
    }

    @Override
    public int isCreateGuildPossible(int cost) {
        return 0;
    }

    @Override
    public void createNewGuild(int cost) {

    }

    @Override
    public boolean removeGuild(int cost) {
        return false;
    }

    @Override
    public int getBuddyCapacity() {
        return 0;
    }

    @Override
    public boolean increaseBuddyCapacity(int amount, int cost) {
        return false;
    }

    @Override
    public String getScriptVariable(String key) {
        return null;
    }

    @Override
    public boolean setScriptVariable(String key, String value) {
        return false;
    }

    @Override
    public void talkTo(String scriptName) {

    }

    @Override
    public void talkTo(int npcId) {

    }

    @Override
    public void openShop(int shopId) {

    }

    @Override
    public boolean transferField(int fieldId) {
        return false;
    }

    @Override
    public boolean transferField(int fieldId, int spawnPoint) {
        return false;
    }

    @Override
    public boolean transferField(int fieldId, String spawnPoint) {
        return false;
    }

    @Override
    public boolean learnSkill(int skillId, int level, int mastery) {
        return false;
    }

    @Override
    public boolean forgetSkill(int skillId) {
        return false;
    }

    @Override
    public boolean giveBuffItem(int buffItemId) {
        return false;
    }

    @Override
    public boolean giveBuffSkill(int skillId) {
        return false;
    }

    @Override
    public boolean hireTutor() {
        return false;
    }

    @Override
    public boolean fireTutor() {
        return false;
    }

    @Override
    public void tutorMessage(int value, int duration) {

    }

    @Override
    public void tutorMessage(String value, int width, int duration) {

    }

    @Override
    public void setStandAloneMode(boolean set) {

    }

    @Override
    public void setDirectionMode(boolean set) {

    }

    @Override
    public int getGender() {
        return 0;
    }

    @Override
    public int getHair() {
        return 0;
    }

    @Override
    public int getFace() {
        return 0;
    }

    @Override
    public boolean setHair(int hairId) {
        return false;
    }

    @Override
    public boolean setFace(int faceId) {
        return false;
    }

    @Override
    public int getSkin() {
        return 0;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getDexterity() {
        return 0;
    }

    @Override
    public int getIntelligence() {
        return 0;
    }

    @Override
    public int getLuck() {
        return 0;
    }

    @Override
    public int getFame() {
        return 0;
    }

    @Override
    public int getMoney() {
        return 0;
    }

    @Override
    public boolean increaseMoney(int amount) {
        return false;
    }

    @Override
    public boolean decreaseMoney(int amount) {
        return false;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public long getExperience() {
        return 0;
    }

    @Override
    public boolean increaseExp(int amount, boolean quest) {
        return false;
    }

    @Override
    public int getAbilityPoints() {
        return 0;
    }

    @Override
    public boolean increaseAbilityPoints(int amount) {
        return false;
    }

    @Override
    public boolean decreaseAbilityPoints(int amount) {
        return false;
    }

    @Override
    public int getSkillPoints(int tier) {
        return 0;
    }

    @Override
    public int getSkillPoints() {
        return 0;
    }

    @Override
    public boolean increaseSkillPoints(int amount, int tier) {
        return false;
    }

    @Override
    public boolean increaseSkillPoints(int amount) {
        return false;
    }

    @Override
    public boolean setJob(short jobCode, boolean isJobAdvancement) {
        return false;
    }

    @Override
    public int getJobId() {
        return 0;
    }

    @Override
    public int resetAp(int remain) {
        return 0;
    }

    @Override
    public void openSkillGuide() {

    }

    @Override
    public void openClassCompetitionPage() {

    }

    @Override
    public int getChannelId() {
        return 0;
    }

    @Override
    public long getHealthCurrent() {
        return 0;
    }

    @Override
    public int getManaCurrent() {
        return 0;
    }

    @Override
    public long getHealthMax() {
        return 0;
    }

    @Override
    public int getManaMax() {
        return 0;
    }

    @Override
    public boolean increaseHealth(int amountToHeal) {
        return false;
    }

    @Override
    public boolean increaseMana(int amountToHeal) {
        return false;
    }

    @Override
    public boolean decreaseHealth(int amountToReduce) {
        return false;
    }

    @Override
    public boolean decreaseMana(int amountToReduce) {
        return false;
    }

    @Override
    public boolean increaseHealthMax(int amountToIncrease) {
        return false;
    }

    @Override
    public boolean increaseManaMax(int amountToIncrease) {
        return false;
    }

    @Override
    public int getObjectId() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Optional<? extends FieldObject> getField() {
        return Optional.empty();
    }

    @Override
    public boolean exchange(int money, int itemId, int itemCount) {
        return false;
    }

    @Override
    public boolean exchange(int money, Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return false;
    }

    @Override
    public boolean addItem(int itemTemplateId, int count) {
        return false;
    }

    @Override
    public boolean addItemAll(int... itemTemplateId) {
        return false;
    }

    @Override
    public boolean addItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return false;
    }

    @Override
    public boolean removeItem(int itemTemplateId) {
        return false;
    }

    @Override
    public boolean removeItem(int itemTemplateId, int count) {
        return false;
    }

    @Override
    public boolean removeItemAll(int... itemTemplateId) {
        return false;
    }

    @Override
    public boolean removeItemAll(Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        return false;
    }

    @Override
    public boolean removeSlot(int tab, short position) {
        return false;
    }

    @Override
    public int getItemCount(int itemTemplateId) {
        return 0;
    }

    @Override
    public int getHoldCount(int inventoryType) {
        return 0;
    }

    @Override
    public boolean increaseSlotCount(int inventoryType, int howMany) {
        return false;
    }

    @Override
    public Collection<InventoryItemObject> getItems(int inventoryType) {
        return null;
    }

    @Override
    public int getQuestState(int questId) {
        return 0;
    }

    @Override
    public boolean setQuestState(int questId, int state) {
        return false;
    }

    @Override
    public String getQuestEx(int questId, String key) {
        return null;
    }

    @Override
    public boolean setQuestEx(int questId, String key, String value) {
        return false;
    }

    @Override
    public Integer get() {
        return null;
    }
}
