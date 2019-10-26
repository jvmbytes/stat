package com.jvmbytes.stat;

public class StatShow {
    public static void main(String[] args) {
        System.out.println(JvmStat.getGcStat());
        System.out.println(JvmStat.getMemoryStat());
        System.out.println(JvmStat.getThreadStat());
    }
}
