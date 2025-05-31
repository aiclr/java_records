package cn.aiclr.jvm.juc.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 店员
 */
public class Clerk {

    private static final Logger logger = LoggerFactory.getLogger(Clerk.class);

    //商品
    private int product = 0;

    //进货
    public synchronized void get() {
        //此处使用while，防止虚假唤醒
        while (product >= 1) {
            logger.info("{}：满仓", Thread.currentThread().getName());
            try {
                this.wait();
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
        logger.info("{}:{}", Thread.currentThread().getName(), ++product);
        this.notifyAll();

/**
 * 使用if-else控制唤醒
 * 售货：if(产品空){this.wait()} else{this.notifyAll()}
 * 进货：if(产品满){this.wait()} else{this.notifyAll()}
 * 可能出现：当消费者售货没货后，此时在if内等待，
 *         当生产者进货，满仓时，进到生产者的else内唤醒消费者
 *         消费者在if内被唤醒，并不会进到消费者的else代码内，即：不会唤醒生产者线程进行生产，所以生产者一直等待
 */
//        if (product >= 1) {
//            logger.info("{}：满仓", Thread.currentThread().getName());
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                logger.error("{}", e.getMessage(), e);
//            }
//        } else {
//            logger.info("{}:{}", Thread.currentThread().getName(), ++product);
//            this.notifyAll();
//        }

        /**
         * 2.1 改进：去掉else,解决上述问题
         * 售货：if(产品空){
         *        this.wait();
         *      }
         *      this.notifyAll();
         * 进货：if(产品满){
         *        this.wait();
         *      }
         *      this.notifyAll();
         * 可能出现问题：
         * 虚假唤醒  JDK API Object类下wait()方法有说明使用while
         */
//        if (product >= 1) {
//            logger.info("{}：满仓", Thread.currentThread().getName());
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                logger.error("{}", e.getMessage(), e);
//            }
//        }
//        logger.info("{}:{}", Thread.currentThread().getName(), ++product);
//        this.notifyAll();
    }

    //售货
    public synchronized void sale() {
        while (product <= 0) {
            logger.info("{}：缺货", Thread.currentThread().getName());
            try {
                this.wait();
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
        logger.info("{}:{}", Thread.currentThread().getName(), --product);
        this.notifyAll();
    }
}