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
 * A UnparkedCallEvent is triggered when a channel that has been parked is
 * resumed.<p>
 * It is implemented in <code>res/res_features.c</code><p>
 * Available since Asterisk 1.2
 * 
 * @author srt
 * @version $Id: UnparkedCallEvent.java,v 1.1 2010/09/19 19:11:33 yusmery Exp $
 * @since 0.2
 */
public class UnparkedCallEvent extends AbstractParkedCallEvent
{
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = -7437833328723536814L;

    private String from;

    /**
     * @param source
     */
    public UnparkedCallEvent(Object source)
    {
        super(source);
    }

    /**
     * Returns the name of the channel that parked the call.
     */
    public String getFrom()
    {
        return from;
    }

    /**
     * Sets the name of the channel that parked the call.
     */
    public void setFrom(String from)
    {
        this.from = from;
    }
}
