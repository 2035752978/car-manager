package cn.manager.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 分布式ID生成器，使用雪花算法
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
public class IdGenerateUtils {
    private static IdGenerateUtils IdGenerateUtils;
    
    /**
     * 生成参数，不需要人为设置
     */
    private static final long TWEPOCH = 1525104000000L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    private static final long TEST_NUM = 2000000L;

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId
     * @param datacenterId
     */
    private IdGenerateUtils(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long result = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
        return Math.abs(result);
    }

    /**
     * 下一个毫秒
     *
     * @param lastTimestamp
     * @return
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取MAC地址
     *
     * @return
     */
    private static long getMacAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(localhost).getHardwareAddress();
            long macSum = 0L;
            for (int i = 0; i < mac.length; i++) {
                macSum += (mac[i] & 0xFF);
            }
            return macSum;
        } catch (Exception e) {
            System.err.println("getMacAddress err");
            return rangeRandom(1536, 2046);
        }
    }

    static Random rand = new Random();

    /**
     * 随机数
     */
    public static int rangeRandom(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    /**
     * 获取当前线程ID
     */
    private static long getThreadId() {
        return Thread.currentThread().getId();
    }

    static {
        IdGenerateUtils = new IdGenerateUtils(getMacAddress() % MAX_WORKER_ID, getThreadId() % MAX_DATACENTER_ID);
    }

    public static IdGenerateUtils getInstance() {
        return IdGenerateUtils;
    }

    /**
     * 使用与测试
     */
    public static void main(String[] args) {
        System.out.println("getMacAddress: " + getMacAddress());
        System.out.println("MAX_WORKER_ID = " + MAX_WORKER_ID+ "\nworkerid = "+ getMacAddress() % MAX_WORKER_ID);
        System.out.println("MAX_DATACENTER_ID = " + MAX_DATACENTER_ID+ "\ndatacenterid = "+ getThreadId() % MAX_WORKER_ID);

        IdGenerateUtils idWorker = IdGenerateUtils.getInstance();
        long id = idWorker.nextId();
        System.out.println(id);
        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < TEST_NUM; i++) {
            long id1 = idWorker.nextId();
            if (ids.contains(id1)) {
                System.out.println("存在重复了");
                break;
            } else {
                ids.add(id1);
            }
        }
        System.out.println(ids.size());
        System.out.println("不存在重复数据");

        for (int i = 0; i < 10; i++) {
            long nextId = idWorker.nextId();
            System.out.println(nextId);
        }
    }
}
