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
package org.asteriskjava.live;

import java.io.Serializable;
import java.util.Date;

/**
 * An entry in the linked channels history of an {@link AsteriskChannel}.
 * 
 * @author srt
 * @version $Id: LinkedChannelHistoryEntry.java,v 1.1 2010/09/19 19:11:25 yusmery Exp $
 * @since 0.3
 */
public class LinkedChannelHistoryEntry implements Serializable
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 5437551192335452460L;
    private final Date dateLinked;
    private Date dateUnlinked;
    private final AsteriskChannel channel;

    /**
     * Creates a new instance.
     * 
     * @param dateLinked the date the channel was linked.
     * @param channel the channel that has been linked.
     */
    public LinkedChannelHistoryEntry(Date dateLinked, AsteriskChannel channel)
    {
        this.dateLinked = dateLinked;
        this.channel = channel;
    }

    /**
     * Returns the date the channel was linked.
     * 
     * @return the date the channel was linked.
     */
    public Date getDateLinked()
    {
        return dateLinked;
    }

    /**
     * Returns the date the channel was unlinked.
     * 
     * @return the date the channel was unlinked.
     */
    public Date getDateUnlinked()
    {
        return dateUnlinked;
    }

    /**
     * Sets the date the channel was unlinked.
     * 
     * @param dateUnlinked the date the channel was unlinked.
     */
    public void setDateUnlinked(Date dateUnlinked)
    {
        this.dateUnlinked = dateUnlinked;
    }

    /**
     * Returns the channel that has been linked.
     * 
     * @return the channel that has been linked.
     */
    public AsteriskChannel getChannel()
    {
        return channel;
    }

    @Override
   public String toString()
    {
        final StringBuffer sb;

        sb = new StringBuffer(100);
        sb.append("LinkedChannelHistoryEntry[");
        sb.append("dateLinked=" + dateLinked + ",");
        sb.append("dateUnlinked=" + dateUnlinked + ",");
        sb.append("channel=" + channel + "]");
        return sb.toString();
    }
}
