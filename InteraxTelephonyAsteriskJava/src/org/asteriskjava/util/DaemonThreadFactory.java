package org.asteriskjava.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A ThreadFactory that creates daemon threads for use with an {@link Executor}
 * 
 * @author srt
 * @version $Id: DaemonThreadFactory.java,v 1.1 2010/09/19 19:11:12 yusmery Exp $
 * @since 0.3
 */
public class DaemonThreadFactory implements ThreadFactory
{
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    /**
     * Creates a new instance.
     */
    public DaemonThreadFactory()
    {
        namePrefix = "Asterisk-Java DaemonPool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable r)
    {
        final Thread thread;

        thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName(namePrefix + threadNumber.getAndIncrement());

        return thread;
    }
}
