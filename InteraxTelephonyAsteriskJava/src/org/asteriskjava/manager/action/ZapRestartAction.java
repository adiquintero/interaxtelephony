package org.asteriskjava.manager.action;

/**
 * Fully restarts all zaptel channels and terminates any calls on Zap
 * interfaces.
 * <p>
 * Available since Asterisk 1.4.
 * 
 * @author srt
 * @since 0.3
 * @version $Id: ZapRestartAction.java,v 1.1 2010/09/19 19:10:49 yusmery Exp $
 */
public class ZapRestartAction extends AbstractManagerAction
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 5899540386204706397L;

    /**
     * Creates a new ZapRestartAction.
     */
    public ZapRestartAction()
    {

    }

    @Override
    public String getAction()
    {
        return "ZapRestart";
    }
}
