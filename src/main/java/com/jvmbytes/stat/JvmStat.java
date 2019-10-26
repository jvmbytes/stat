package com.jvmbytes.stat;


import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wongoo
 */
public final class JvmStat {
    private static List<String> youngGenCollectorNames = new ArrayList<String>();
    private static List<String> oldGenCollectorNames = new ArrayList<String>();

    static {
        youngGenCollectorNames.add("Copy");
        youngGenCollectorNames.add("ParNew");
        youngGenCollectorNames.add("PS Scavenge");
        youngGenCollectorNames.add("G1 Young Generation");

        oldGenCollectorNames.add("MarkSweepCompact");
        oldGenCollectorNames.add("PS MarkSweep");
        oldGenCollectorNames.add("ConcurrentMarkSweep");
        oldGenCollectorNames.add("G1 Old Generation");
    }

    /**
     * gc次数和时间
     */
    public static GcStat getGcStat() {
        long oldCount = 0;
        long oldTime = 0;
        long youngCount = 0;
        long youngTime = 0;
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : gcs) {
            if (youngGenCollectorNames.contains(gc.getName())) {
                youngCount += gc.getCollectionCount();
                youngTime += gc.getCollectionTime();
            } else if (oldGenCollectorNames.contains(gc.getName())) {
                oldCount += gc.getCollectionCount();
                oldTime += gc.getCollectionTime();
            }
        }

        GcStat stat = new GcStat();
        stat.setYoungGcCount(youngCount);
        stat.setYoungGcTime(youngTime);
        stat.setOldGcCount(oldCount);
        stat.setOldGcTime(oldTime);
        return stat;
    }

    /**
     * jvm内存空间大小
     * 包括年轻代、老年代、永久代（元空间）
     * <p>
     * (metaspace)元空间的特点：
     * - 充分利用了Java语言规范中的好处：类及相关的元数据的生命周期与类加载器的一致。
     * - 每个加载器有专门的存储空间
     * - 只进行线性分配
     * - 不会单独回收某个类
     * - 省掉了GC扫描及压缩的时间
     * - 元空间里的对象的位置是固定的
     * - 如果GC发现某个类加载器不再存活了，会把相关的空间整个回收掉
     * <p>
     * 使用-XX:MaxMetaspaceSize参数可以设置元空间的最大值，默认是没有上限的，也就是说你的系统内存上限是多少它就是多少。
     * -XX:MetaspaceSize选项指定的是元空间的初始大小，如果没有指定的话，元空间会根据应用程序运行时的需要动态地调整大小。
     * <p>
     * 类指针压缩空间（Compressed Class Pointer Space）
     * 只有是64位平台上启用了类指针压缩才会存在这个区域。
     * 对于64位平台，为了压缩JVM对象中的_klass指针的大小，引入了类指针压缩空间（Compressed Class Pointer Space）.
     * 类指针压缩空间只包含类的元数据，比如InstanceKlass, ArrayKlass;
     * 仅当打开了UseCompressedClassPointers选项才生效 为了提高性能，Java中的虚方法表也存放到这里.
     */
    public static MemoryStat getMemoryStat() {
        MemoryStat stat = new MemoryStat();

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        stat.setHeapMax(heapMemoryUsage.getMax());
        stat.setHeapUsed(heapMemoryUsage.getUsed());

        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean bean : memoryPoolMXBeans) {
            String name = bean.getName();
            MemoryUsage usage = bean.getUsage();
            // jdk8及以后为Metaspace，jdk7及更早的为PermGen
            if (name.endsWith("Metaspace") || name.endsWith("Perm Gen")) {
                stat.setMetaUsed(usage.getUsed());
            } else if (name.endsWith("Survivor Space") || name.endsWith("Eden Space")) {
                stat.setYoungUsed(stat.getYoungUsed() + usage.getUsed());
                stat.setYoungMax(stat.getYoungMax() + usage.getMax());
            } else if (name.endsWith("Old Gen")) {
                stat.setOldUsed(usage.getUsed());
                stat.setOldMax(usage.getMax());
            }
        }
        return stat;
    }

    /**
     * 线程数量（总数和守护线程数）
     */
    public static ThreadStat getThreadStat() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadStat stat = new ThreadStat();
        stat.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());
        stat.setThreadCount(threadMXBean.getThreadCount());
        return stat;
    }

}
