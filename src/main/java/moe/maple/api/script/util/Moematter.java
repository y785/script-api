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

package moe.maple.api.script.util;

import moe.maple.api.script.model.object.FieldObject;
import moe.maple.api.script.model.object.ScriptObject;
import moe.maple.api.script.model.object.field.MobObject;
import moe.maple.api.script.model.object.field.NpcObject;
import moe.maple.api.script.model.object.user.ItemObject;
import moe.maple.api.script.model.object.user.UserObject;
import org.slf4j.helpers.MessageFormatter;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *  Generic class for MessageFormatter.
 *  Just because slf4j is a dependency and I trust nothing.
 */
public class Moematter {

    public static String format(String format, Object object) {
        return MessageFormatter.format(format, object).getMessage();
    }

    public static String format(String format, Object... objects) {
        return MessageFormatter.arrayFormat(format, objects).getMessage();
    }

    // =================================================================================================================

    protected static NumberFormat commaFormatter = NumberFormat.getNumberInstance(Locale.getDefault());

    /**
     * Formats a number using the server's Locale
     * Ex. (Locale.US) 1000 = 1,000
     * @param hopefullyANumber HOPEFULLY
     * @return A formatted number as a {@link String}
     */
    public static String formatWithLocale(Number hopefullyANumber) {
        return commaFormatter.format(hopefullyANumber);
    }

    // =================================================================================================================

    public static String bold(Object... objects) { return format("#e{}#n", objects); }
    public static String bold(Object object) { return String.format("#e%s#n", object); }
    public static String black(Object... objects) { return format("#k{}", objects); }
    public static String black(Object objects) { return String.format("#k%s", objects.toString()); }
    public static String blue(Object... objects) { return format("#b{}#k", objects); }
    public static String blue(Object object) { return String.format("#b%s#k", object.toString()); }
    public static String green(Object... objects) { return format("#g{}#k", objects); }
    public static String green(Object object) { return String.format("#g%s#k", object.toString()); }
    public static String purple(Object... objects) { return format("#d{}#k", objects); }
    public static String purple(Object object) { return String.format("#d%s#k", object.toString()); }
    public static String red(Object... objects) { return format("#r{}#k", objects); }
    public static String red(Object object) { return String.format("#r%s#k", object.toString()); }

    /**
     * Shows the item icon.
     * todo: #i{id}:#, #z{id}#, #t{id}#, #v{id}#
     * @param itemWzId - the item id
     * @return         - the formatted name
     */
    public static String item(Number itemWzId) { return String.format("#i%d#", itemWzId.intValue()); }
    public static String item(ItemObject object) { return item(object.getId()); }

    public static String itemName(Number itemWzId) { return String.format("#z%d#", itemWzId.intValue()); }
    public static String itemName(ItemObject object) { return itemName(object.getId()); }

    /**
     * Shows the map name.
     * @param mapWzId - the map id
     * @return        - the formatted name
     */
    public static String map(Number mapWzId) { return String.format("#m%d#", mapWzId.intValue()); }
    public static String map(FieldObject object) { return map(object.getId()); }
    public static String field(Number fieldWzId) { return map(fieldWzId); }
    public static String field(FieldObject object) { return map(object); }

    /**
     * Shows the mob name.
     * @param mobWzId - the mob id
     * @return        - the formatted name
     */
    public static String mob(Number mobWzId) { return String.format("#o%d#", mobWzId.intValue()); }
    public static String mob(MobObject object) { return mob(object.getId()); }

    /**
     * Shows the npc name.
     * @param npcWzId - the npc id
     * @return        - the formatted name
     */
    public static String npc(Number npcWzId) { return String.format("#p%d#", npcWzId.intValue()); }
    public static String npc(NpcObject object) { return npc(object.getTemplateId()); }

    /**
     * Shows an icon of the skill.
     * @param skillWzId - the skill id
     * @return          - the formatted name
     */
    public static String skill(Number skillWzId) { return String.format("#s%d#", skillWzId.intValue()); }

    /**
     * Shows the name of the skill.
     * @param skillWzId - the skill id
     * @return          - the formatted name
     */
    public static String skillName(Number skillWzId) { return String.format("#q%d#", skillWzId.intValue()); }


    /**
     * Converts objects into their wz names, if possible.
     * Otherwise defaults to {@link #toString()}
     * @param object - the object to convert
     * @return       - the formatted name
     */
    public static String name(ScriptObject object) {
        if (object instanceof ItemObject)
            return itemName(((ItemObject)object));
        else if (object instanceof UserObject)
            return ((UserObject)object).getName();
        else if (object instanceof FieldObject)
            return map(((FieldObject)object));
        else if (object instanceof MobObject)
            return mob((MobObject)object);
        else if (object instanceof NpcObject)
            return npc((NpcObject)object);
        else
            return object.toString();
    }
}
