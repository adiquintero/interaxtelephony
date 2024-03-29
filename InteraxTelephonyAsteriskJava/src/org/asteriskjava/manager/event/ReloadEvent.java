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
 * A ReloadEvent is triggerd when the <code>reload</code> console command is executed or the
 * asterisk server is started.<p>
 * It is implemented in <code>manager.c</code>
 * 
 * @author srt
 * @version $Id: ReloadEvent.java,v 1.1 2010/09/19 19:11:34 yusmery Exp $
 */
public class ReloadEvent extends ManagerEvent
{
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 7503005587022499819L;
    private String message;

    /**
     * @param source
     */
    public ReloadEvent(Object source)
    {
        super(source);
    }

    /**
     * Always returns "Reload Requested".
     */
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
