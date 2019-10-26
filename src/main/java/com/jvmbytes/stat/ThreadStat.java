package com.jvmbytes.stat;

/**
 * thread stat
 *
 * @author wongoo
 */
public class ThreadStat {
    /**
     * 线程总数
     */
    private int threadCount;
    /**
     * 守护线程数量
     */
    private int daemonThreadCount;

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(int daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    @Override
    public String toString() {
        return "ThreadStat{" +
                "threadCount=" + threadCount +
                ", daemonThreadCount=" + daemonThreadCount +
                '}';
    }
}
