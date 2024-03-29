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
 * A LinkEvent is triggered when two voice channels are linked together and voice data exchange
 * commences.<p>
 * Several Link events may be seen for a single call. This can occur when Asterisk fails to setup a
 * native bridge for the call.This is when Asterisk must sit between two telephones and perform
 * CODEC conversion on their behalf.<p>
 * It is implemented in <code>channel.c</code>
 * 
 * @author srt
 * @version $Id: LinkEvent.java,v 1.1 2010/09/19 19:11:34 yusmery Exp $
 */
public class LinkEvent extends LinkageEvent
{
    /**
     * Serializable version identifier
     */
    static final long serialVersionUID = -4023240534975776225L;

    /**
     * @param source
     */
    public LinkEvent(Object source)
    {
        super(source);
    }
}
