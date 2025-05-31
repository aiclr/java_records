package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 当期望的读数和遍历远远大于列表的更新数时，优于同步的 java.util.ArrayList，每次写入时都会复制一份集合，所以没有并发修改异常
 * CopyOnWriteArrayList 不适合添加操作多的，每次添加都会复制开销非常大
 * 适合并发迭代器操作
 */
public class CopyOnWriteArrayListForString implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CopyOnWriteArrayListForString.class);

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        //每条线程的 iterator 都是同一个
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {//iterator 是旧list的，所以不会死循环
            logger.info("{} {} {} {}", Thread.currentThread().getName(), iterator.next(), iterator.hashCode(), list.hashCode());
            //每次 add 都会复制一份list 作为新的 list
            list.add("DD");
        }
    }

    public void show() {
        logger.info("{} {}", Thread.currentThread().getName(), list);
    }
}
