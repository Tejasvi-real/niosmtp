package me.normanmaurer.niosmtp.impl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.normanmaurer.niosmtp.DeliveryResult;
import me.normanmaurer.niosmtp.SMTPClientFuture;
import me.normanmaurer.niosmtp.SMTPClientFutureListener;

import org.jboss.netty.channel.Channel;

public class SMTPClientFutureImpl implements SMTPClientFuture{

    private volatile boolean isReady = false;
    private volatile boolean isCancelled = false;
    private final List<SMTPClientFutureListener> listeners = Collections.synchronizedList(new ArrayList<SMTPClientFutureListener>());
    private volatile Channel channel;
    private DeliveryResult result;
    protected synchronized void setDeliveryStatus(DeliveryResult result) {
        if (!isReady) {
            this.result = result;
            isReady = true;
            notify();

            
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).operationComplete(result);
            }
        } else {
            notify();
        }
    }

    
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (isCancelled || isDone()) {
            return false;
        } else {
           if (channel != null) {
               channel.close();
           }
           isCancelled = true;
           return true;
        }
    }

    /**
     * Set the {@link Channel} which will be used for the {@link #cancel(boolean)} operation later
     * 
     * @param channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    private synchronized void checkReady() throws InterruptedException {
        while (!isReady) {
            wait();

        }
    }

    private synchronized void checkReady(long timeout) throws InterruptedException {
        while (!isReady) {
            wait(timeout);
        }
    }
    
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public boolean isDone() {
        return isReady;
    }

    @Override
    public void addListener(SMTPClientFutureListener listener) {
        listeners.add(listener);
        if (isReady) {
            listener.operationComplete(result);
        }
    }

    @Override
    public void removeListener(SMTPClientFutureListener listener) {
        listeners.remove(listener);
    }


    @Override
    public DeliveryResult get() throws InterruptedException, ExecutionException {
        checkReady();
        return result;
    }



    @Override
    public DeliveryResult get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        checkReady(unit.toMillis(timeout));
        if (isDone()) {
            return result;
        } else {
            return null;
        }
    }

}