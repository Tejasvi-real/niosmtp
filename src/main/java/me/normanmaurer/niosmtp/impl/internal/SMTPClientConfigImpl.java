package me.normanmaurer.niosmtp.impl.internal;

import java.net.InetSocketAddress;

import me.normanmaurer.niosmtp.SMTPClientConfig;

/**
 * Simple {@link SMTPClientConfig} implementation which allows
 * to handle the config via a POJO.
 * 
 * @author Norman Maurer
 *
 */
public class SMTPClientConfigImpl implements SMTPClientConfig {

    private String heloName = "localhost";
    private int timeout = 60;
    private InetSocketAddress localAddress = null;
    private boolean usePipelining = true;
    
    public SMTPClientConfigImpl() {
    }
    
    public void setHeloName(String heloName) {
        this.heloName = heloName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see me.normanmaurer.niosmtp.SMTPClientConfig#getHeloName()
     */
    public String getHeloName() {
        return heloName;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    /*
     * (non-Javadoc)
     * @see me.normanmaurer.niosmtp.SMTPClientConfig#getTimeout()
     */
    public int getTimeout() {
        return timeout;
    }
    

    /*
     * (non-Javadoc)
     * @see me.normanmaurer.niosmtp.SMTPClientConfig#getLocalAddress()
     */
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }
    
    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    /*
     * (non-Javadoc)
     * @see me.normanmaurer.niosmtp.SMTPClientConfig#usePipelining()
     */
    public boolean usePipelining() {
        return usePipelining;
    }
    
    public void setUsePipelining(boolean usePipelining) {
        this.usePipelining = usePipelining;
    }

}