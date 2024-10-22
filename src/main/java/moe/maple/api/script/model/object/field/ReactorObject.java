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

package moe.maple.api.script.model.object.field;

import moe.maple.api.script.model.object.field.FieldedObject;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * This is a script proxy for reactor objects.
 * @param <T>
 */
public interface ReactorObject<T> extends FieldedObject<T> {

    int getState();

    int getTemplateId();

    boolean isOnLastState();

    boolean createMob(int mobTemplateId, int xPos, int yPos);

    /**
     * @return the last User to interact with this reactor.
     */
    Optional<? extends UserObject> getLastUser();

    /**
     * Triggers a 'drop' action for the reactor.
     * @return true if the drop was successful.
     */
    boolean drop();

    /**
     * Drops items from the reactor's position
     * @param itemAndCount - Left is the item's .wz id
     *                     - Right is the count
     * @return true if items were dropped
     */
    default boolean dropItems(Tuple<Integer, Integer>... itemAndCount) {
        return dropItems(Arrays.asList(itemAndCount));
    }
    /**
     * Drops items from the reactor's position
     * @param itemAndCount - Left is the item's .wz id
     *                     - Right is the count
     * @return true if items were dropped
     */
    boolean dropItems(Collection<Tuple<Integer, Integer>> itemAndCount);

}
