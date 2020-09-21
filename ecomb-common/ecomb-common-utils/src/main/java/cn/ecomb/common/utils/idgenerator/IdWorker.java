package cn.ecomb.common.utils.idgenerator;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * 分布式id分成器
 * workerId是机器ID，datacenterId是数据中心ID或机房ID。 这都是为分布式而设置的，workerId每台机器肯定不一样，最大值由maxWorkerId限制。
 *
 * @URL: https://gitee.com/properfect/IdWorker
 */
@Slf4j
public class IdWorker {

    private long workerId;
    private long datacenterId;
    private long sequence;

    public IdWorker(long sequence) {
        //计算workerId
        try {
            byte[] bytes = InetAddress.getLocalHost().getAddress();
            workerId = Long.valueOf((bytes[3] & 0xFF)) % maxWorkerId;
        } catch (Exception e) {
            log.warn("IdWorker计算workerId出错：{}", e.getMessage(), e);
            workerId = 1;
        }

        //计算datacenterId
        try {
            byte[] bytes = InetAddress.getLocalHost().getAddress();
            datacenterId = Long.valueOf((bytes[2] & 0xFF)) % maxDatacenterId;
        } catch (Exception e) {
            log.warn("IdWorker计算datacenterId出错：{}", e.getMessage(), e);
            datacenterId = 1;
        }


        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (log.isInfoEnabled()) {
            log.info("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                    timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        }

        this.sequence = sequence;
    }

    public IdWorker(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (log.isInfoEnabled()) {
            log.info("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                    timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    private static final IdWorker ID_WORDER = new IdWorker(2);

    public static long nextId() {
        return ID_WORDER.nextId0();
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    private synchronized long nextId0() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    //---------------测试---------------
    public static void main(String[] args) {
        System.out.println(ID_WORDER.workerId);
        System.out.println(ID_WORDER.datacenterId);
        System.out.println(nextId());
//        IdWorker worker = new IdWorker(1, 1, 2);
        for (int i = 0; i < 30; i++) {
//            System.out.println(worker.nextId0());
            System.out.println(IdWorker.nextId());
        }
    }
}
