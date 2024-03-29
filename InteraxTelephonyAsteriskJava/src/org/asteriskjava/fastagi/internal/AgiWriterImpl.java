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
package org.asteriskjava.fastagi.internal;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiNetworkException;
import org.asteriskjava.fastagi.command.AgiCommand;
import org.asteriskjava.util.SocketConnectionFacade;


/**
 * Default implementation of the AGIWriter interface.
 * 
 * @author srt
 * @version $Id: AgiWriterImpl.java,v 1.1 2010/09/19 19:11:36 yusmery Exp $
 */
class AgiWriterImpl implements AgiWriter
{
    private final SocketConnectionFacade socket;

    AgiWriterImpl(SocketConnectionFacade socket)
    {
        this.socket = socket;
    }

    public void sendCommand(AgiCommand command) throws AgiException
    {
        try
        {
            socket.write(command.buildCommand() + "\n");
            socket.flush();
        }
        catch (IOException e)
        {
            throw new AgiNetworkException(
                    "Unable to send command to Asterisk: " + e.getMessage(), e);
        }
    }
}
