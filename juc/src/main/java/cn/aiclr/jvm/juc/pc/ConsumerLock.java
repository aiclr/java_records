package cn.aiclr.jvm.juc.pc;

/**
 * 消费者负责售货
 */
public class ConsumerLock implements Runnable {
    private final ClerkLock clerk;

    public ConsumerLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            clerk.sale();
        }
    }
}
