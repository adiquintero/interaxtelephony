/*
 *  Copyright 2005-2006 Stefan Reuter
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

/**
 * Indicates that the channel is not available on the Asterisk server.
 * 
 * @author srt
 * @version $Id: NoSuchChannelException.java,v 1.1 2010/09/19 19:11:27 yusmery Exp $
 */
public class NoSuchChannelException extends LiveException
{
    /**
     * Serial version identifier 
     */
    private static final long serialVersionUID = -6751088178882822091L;

    /**
     * Creates a new instance with the given message.
     * 
     * @param message the message
     */
    public NoSuchChannelException(String message)
    {
        super(message);
    }
}
