/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.manager.event;

/**
 * A QueueMemberRemovedEvent is triggered when a queue member is removed from a
 * queue.<p>
 * It is implemented in <code>apps/app_queue.c</code>.<p>
 * Available since Asterisk 1.2
 * 
 * @author srt
 * @version $Id: QueueMemberRemovedEvent.java,v 1.1 2010/09/19 19:11:29 yusmery Exp $
 * @since 0.2
 */
public class QueueMemberRemovedEvent extends AbstractQueueMemberEvent
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 2108033737226142194L;

    public QueueMemberRemovedEvent(Object source)
    {
        super(source);
    }
}
