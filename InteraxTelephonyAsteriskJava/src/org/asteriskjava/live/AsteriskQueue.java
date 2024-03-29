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

import java.util.List;

/**
 * An Asterisk ACD queue.
 * 
 * @author srt
 * @version $Id: AsteriskQueue.java,v 1.1 2010/09/19 19:11:26 yusmery Exp $
 */
public interface AsteriskQueue
{
    /**
     * Returns the name of this queue as defined in Asterisk's
     * <code>queues.conf</code>.
     * 
     * @return the name of this queue.
     */
    String getName();

    /**
     * Returns the maximum number of people allowed to wait in this queue or 0
     * for unlimited.
     * <p>
     * Corresponds to the <code>maxlen</code> option in Asterisk's
     * <code>queues.conf</code>.
     * 
     * @return the maximum number of people allowed to wait in this queue.
     */
    Integer getMax();

    /**
     * Returns the service level (in seconds) as defined by the
     * <code>servicelevel</code> setting in <code>queues.conf</code>.
     * 
     * @return the service level (in seconds).
     */
    Integer getServiceLevel();

    /**
     * Returns the weight of this queue.
     * <p>
     * A queue can be assigned a 'weight' to ensure calls waiting in a higher
     * priority queue will deliver its calls first. Only delays the lower weight
     * queue's call if the member is also in the higher weight queue.
     * <p>
     * Available since Asterisk 1.2
     * 
     * @return the weight of this queue or <code>null</code> if not supported
     *         by your version of Asterisk.
     */
    Integer getWeight();

    /**
     * Returns the list of channels currently waiting in this queue.
     * 
     * @return the list of channels currently waiting in this queue.
     */
    List<AsteriskChannel> getEntries();
    
    /**
     * Registers a new queue listener.
     * 
     * @param listener the listener to add.
     * @since 0.3
     */
    void addAsteriskQueueListener(AsteriskQueueListener listener);

    /**
     * Removes a previously registered queue listener.
     * 
     * @param listener the listener to remove.
     * @since 0.3
     */
    void removeAsteriskQueueListener(AsteriskQueueListener listener);
}
