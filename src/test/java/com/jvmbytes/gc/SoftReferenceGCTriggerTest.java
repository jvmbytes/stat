package com.jvmbytes.gc;

import com.jvmbytes.stat.GcStat;
import com.jvmbytes.stat.JvmStat;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SoftReferenceGCTriggerTest {
    public static void main(String[] args) throws Exception {
        List<SoftReference<Object>> list = new ArrayList<SoftReference<Object>>();
        long preGcCount = 0;

        while (true) {
            GcStat gcStat = JvmStat.getGcStat();
            System.out.println(gcStat);
            System.out.println(JvmStat.getMemoryStat());
            System.out.println(JvmStat.getThreadStat());

            if (gcStat.getOldGcCount() > preGcCount) {
                System.out.println("--------------- old gc happens");
                Thread.sleep(1000);
                preGcCount = gcStat.getOldGcCount();
            }

            byte[] b = new byte[1024 * 1024 * 100];
            SoftReference soft = new SoftReference(b);
            list.add(soft);
            Thread.sleep(500);
        }
    }
}
