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

package moe.maple.api.script.model.type;

// Enums are bad.
public enum SpeakerType {
    SAY(0x0),
    SAYIMAGE(0x1),
    ASKYESNO(0x2),
    ASKTEXT(0x3),
    ASKNUMBER(0x4),
    ASKMENU(0x5),
    ASKQUIZ(0x6),
    ASKSPEEDQUIZ(0x7),
    ASKAVATAR(0x8),
    ASKMEMBERSHOPAVATAR(0x9),
    ASKPET(0xA),
    ASKPETALL(0xB),
    SCRIPT(0xC),
    ASKACCEPT(0xD),
    ASKBOXTEXT(0xE),
    ASKSLIDEMENU(0xF),
    ASKCENTER(0x10),
    ;
    public final int value;

    SpeakerType(int value) {
        this.value = value;
    }

    public static SpeakerType getByValue(int value) {
        for (SpeakerType type : values()) {
            if (type.value == value)
                return type;
        }
        throw new IllegalArgumentException("Cant find, wololol");
    }
}
