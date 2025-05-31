package cn.aiclr.jvm.juc.pc;

/**
 * 生产者负责进货
 */
public class Productor implements Runnable {

    private final Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            clerk.get();
        }
    }
}
