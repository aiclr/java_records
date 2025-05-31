package cn.aiclr.jvm.juc.pc;

/**
 * 生产者负责进货
 */
public class ProductorLock implements Runnable {

    private final ClerkLock clerk;

    public ProductorLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            clerk.get();
        }
    }
}
