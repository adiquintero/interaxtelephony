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
package org.asteriskjava.manager.action;

/**
 * The AgentCallbackLoginAction sets an agent as logged in with callback.<p>
 * You can pass an extentsion (and optionally a context) to specify the
 * destination of the callback.<p>
 * In contrast to the AgentCallbackLogin application that you can use within
 * Asterisk's dialplan, you don't need to know the agent's password when logging
 * in an agent.<p>
 * Available since Asterisk 1.2
 * 
 * @author srt
 * @version $Id: AgentCallbackLoginAction.java,v 1.1 2010/09/19 19:10:56 yusmery Exp $
 * @since 0.2
 */
public class AgentCallbackLoginAction extends AbstractManagerAction
{
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 5239805071977668779L;
    private String agent;
    private String exten;
    private String context;
    private Boolean ackCall;
    private Long wrapupTime;

    /**
     * Creates a new empty AgentCallbackLoginAction.
     */
    public AgentCallbackLoginAction()
    {

    }

    /**
     * Creates a new AgentCallbackLoginAction, that logs in the given agent at
     * the given callback extension.
     * 
     * @param agent the name of the agent to log in
     * @param exten the extension that is called to connect a queue member with
     *            this agent
     */
    public AgentCallbackLoginAction(String agent, String exten)
    {
        this.agent = agent;
        this.exten = exten;
    }

    /**
     * Creates a new AgentCallbackLoginAction, that logs in the given agent at
     * the given callback extension in the given context.
     * 
     * @param agent the name of the agent to log in
     * @param exten the extension that is called to connect a queue member with
     *            this agent
     * @param context the context of the extension to use for callback
     */
    public AgentCallbackLoginAction(String agent, String exten, String context)
    {
        this(agent, exten);
        this.context = context;
    }

    /**
     * Returns the name of this action, i.e. "AgentCallbackLogin".
     * 
     * @return the name of this action
     */
    @Override
   public String getAction()
    {
        return "AgentCallbackLogin";
    }

    /**
     * Returns the name of the agent to log in, for example "1002".
     * 
     * @return the name of the agent to log in
     */
    public String getAgent()
    {
        return agent;
    }

    /**
     * Sets the name of the agent to log in, for example "1002".<p>
     * This is property is mandatory.
     * 
     * @param agent the name of the agent to log in
     */
    public void setAgent(String agent)
    {
        this.agent = agent;
    }

    /**
     * Returns the extension to use for callback.
     * 
     * @return the extension to use for callback.
     */
    public String getExten()
    {
        return exten;
    }

    /**
     * Sets the extension to use for callback.<p>
     * This is property is mandatory.
     * 
     * @param exten the extension to use for callback.
     */
    public void setExten(String exten)
    {
        this.exten = exten;
    }

    /**
     * Returns the context of the extension to use for callback.
     * 
     * @return the context of the extension to use for callback.
     */
    public String getContext()
    {
        return context;
    }

    /**
     * Sets the context of the extension to use for callback.
     * 
     * @param context the context of the extension to use for callback.
     */
    public void setContext(String context)
    {
        this.context = context;
    }

    /**
     * Returns if an acknowledgement is needed when agent is called back.
     * 
     * @return Boolean.TRUE if acknowledgement by '#' is required when agent is
     *         called back, Boolean.FALSE otherwise. <code>null</code> if
     *         default should be used.
     */
    public Boolean getAckCall()
    {
        return ackCall;
    }

    /**
     * Sets if an acknowledgement is needed when agent is called back.<p>
     * This property is optional, it allows you to override the defaults defined
     * in Asterisk's configuration.
     * 
     * @param ackCall Boolean.TRUE to 'true' to require an acknowledgement by
     *            '#' when agent is called back, Boolean.FALSE otherwise.
     *            <code>null</code> if default should be used.
     */
    public void setAckCall(Boolean ackCall)
    {
        this.ackCall = ackCall;
    }

    /**
     * Returns the minimum amount of time after disconnecting before the caller
     * can receive a new call.
     * 
     * @return the minimum amount of time after disconnecting before the caller
     *         can receive a new call in milliseconds.
     */
    public Long getWrapupTime()
    {
        return wrapupTime;
    }

    /**
     * Sets the minimum amount of time after disconnecting before the caller can
     * receive a new call.<p>
     * This property is optional, it allows you to override the defaults defined
     * in Asterisk's configuration.
     * 
     * @param wrapupTime the minimum amount of time after disconnecting before
     *            the caller can receive a new call in milliseconds.
     */
    public void setWrapupTime(Long wrapupTime)
    {
        this.wrapupTime = wrapupTime;
    }
}
