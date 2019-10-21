package org.asteriskjava.manager.event;

import org.asteriskjava.manager.action.QueueSummaryAction;

/**
 * A QueueSummaryCompleteEvent is triggered after the summary for all requested
 * queues has been reported in response to a QueueSummaryAction.
 * 
 * @see QueueSummaryAction
 * @see QueueSummaryEvent
 * @author srt
 * @version $Id: QueueSummaryCompleteEvent.java,v 1.1 2010/09/19 19:11:29 yusmery Exp $
 * @since 0.3
 */
public class QueueSummaryCompleteEvent extends ResponseEvent
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = -5044247858568827143L;

    public QueueSummaryCompleteEvent(Object source)
    {
        super(source);
    }
}
