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

package moe.maple.api.script.model;

import moe.maple.api.script.model.object.user.QuestObject;

public abstract class QuestScript extends NpcScript {

    protected QuestObject<?> self;

    public QuestScript() {
        super();
    }

    @Override
    public void setQuestObject(QuestObject quest) {
        this.self = quest;
        super.setQuestObject(quest);
    }

    /**
     * Attempts to start the quest {@link #self}
     * @return true if the quest has been started successfully.
     */
    public boolean startQuest() {
        return self.start();
    }

    /**
     * Attempts to complete the quest {@link #self}
     * @return true if the quest has been completed successfully.
     */
    public boolean completeQuest() {
        return self.complete();
    }

    /**
     * @return the quest's WZ identifier
     */
    public int getQuestId() {
        return self.getId();
    }

}
