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
 * Server Objects have the ability to generate other
 * script objects.
 */
public interface ServerObject<T> extends ScriptObject<T> {

    /**
     * This should return the current state of the contimove.
     * ContiState.Wait is used for boarding, etc.
     * ContiState:
     * 0 - Dormant
     * 1 - Wait
     * 2 - Start
     * 3 - Move
     * 4 - Mob Gen
     * 5 - Mob Destroy
     * 6 - End
     * ... and so on
     * @param fieldIdStart - The start of the contimove
     * @return 0 if contimove doesn't exist, otherwise it's current state
     */
    int getContiState(int fieldIdStart);

    /**
     * @param fieldSetName - The name of the FieldSet
     * @return the fieldset if it exists
     */
    Optional<FieldSetObject> getFieldSet(String fieldSetName);

    /**
     * @param fieldId - The .wz id of the field
     * @return the fieldobject if it exists
     */
    Optional<FieldObject> getField(int fieldId);
}
