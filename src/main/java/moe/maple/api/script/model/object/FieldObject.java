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

/**
 * This is a script proxy for field/map objects.
 * <T> should be your implementation of maps/fields.
 * @param <T>
 */
public interface FieldObject<T> extends ScriptObject {

    T getField();

    int getId();
    /**
     * Returns a count of the mobs in a map, by ID.
     * @param mobId the ID of the mob to count
     * @return a count of the mob specified in the map
     */
    int getMobCount(int mobId);

    /**
     * @return a count of all users in the field.
     */
    int getUserCount();

    /**
     * Nexon's comment:
     ** Returns the HP of the Mob.
     ** Valid only when there is only one Mob with a specific ID.
     ** If there is no Mob, return -1
     * @param mobId the template ID of the mob
     * @return See nexon's note.
     */
    long getMobHp(int mobId);

    int countUserInArea(String areaName);
    int countMaleInArea(String areaName);
    int countFemaleInArea(String areaName);

    /**
     * Sends a packet to the Field, enabling or disabling a portal.
     * @param portalName the name of the portal to enable.
     * @param enable TRUE if enabling, FALSE if disabling.
     * @return TRUE if the portal is now enabled, false otherwise.
     */
    boolean enablePortal(String portalName, boolean enable);
    default boolean enablePortal(String portalName) {
        return enablePortal(portalName, true);
    }
    default boolean disablePortal(String portalName) {
        return enablePortal(portalName, false);
    }

    /**
     * Summons a mob in the current field instance.
     * @param templateId The mob's template ID. OR mob sack item?
     * @param xPos the mob's proposed x position.
     * @param yPos the mob's proposed y position.
     * @return TRUE if all is well, the mob is summoned.
     */
    boolean summonMob(int templateId, int xPos, int yPos);//bySack?
}
