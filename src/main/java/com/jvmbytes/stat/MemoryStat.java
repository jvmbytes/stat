package com.jvmbytes.stat;

/**
 * memory stat
 *
 * @author wongoo
 */
public class MemoryStat {

    private long heapMax;

    private long heapUsed;

    /**
     * 年轻代大小 Eden Space + Survivor Spaces
     */
    private long youngUsed = 0;

    private long youngMax = 0;

    /**
     * 老年代大小 Old Gen
     */
    private long oldUsed = 0;

    private long oldMax = 0;

    /**
     * 永久代（元空间）大小
     */
    private long metaUsed = 0;

    public long getHeapUsed() {
        return heapUsed;
    }

    public void setHeapUsed(long heapUsed) {
        this.heapUsed = heapUsed;
    }

    public long getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(long heapMax) {
        this.heapMax = heapMax;
    }

    public long getYoungUsed() {
        return youngUsed;
    }

    public void setYoungUsed(long youngUsed) {
        this.youngUsed = youngUsed;
    }

    public long getOldUsed() {
        return oldUsed;
    }

    public void setOldUsed(long oldUsed) {
        this.oldUsed = oldUsed;
    }

    public long getMetaUsed() {
        return metaUsed;
    }

    public void setMetaUsed(long metaUsed) {
        this.metaUsed = metaUsed;
    }

    public long getYoungMax() {
        return youngMax;
    }

    public void setYoungMax(long youngMax) {
        this.youngMax = youngMax;
    }

    public long getOldMax() {
        return oldMax;
    }

    public void setOldMax(long oldMax) {
        this.oldMax = oldMax;
    }

    @Override
    public String toString() {
        return "MemoryStat{" +
                "heapMax=" + heapMax +
                ", heapUsed=" + heapUsed +
                ", youngUsed=" + youngUsed +
                ", youngMax=" + youngMax +
                ", oldUsed=" + oldUsed +
                ", oldMax=" + oldMax +
                ", metaUsed=" + metaUsed +
                '}';
    }
}
