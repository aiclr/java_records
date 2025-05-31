# juc

### 多线程

多线程可以使1个CPU几乎在同一时间运行更多的任务。 在指定的时间单位内运行更多的任务，
其实就是大幅度提高运行效率，让软件运行更流畅，处理的数据更多，以提升使用软件时的用户体验

### 并发

java.util.concurrent并发包提供了绝大多数常用的功能。 concurrent并发包是对多线程技术的封装，使用并发包中的类可以大幅度降低多线程代码的复杂度

- 并发包可以限制访问的流量、线程间的数据交流
- 在同步处理时使用更加方便和高效率的锁（Lock）对象、读写锁对象
- 以及可以提高运行效率的线程池
- 支持异步及回调接口
- 支持计划任务
- 支持fork-join分治编程
- 而且还提供了并发集合框架

### 分段锁机制

> java5.0 java.util.concurrent 包中提供了多种并发容器类来改进同步容器性能，还提供了设计用于多线程上下文中的 Collection
> 实现：
> > java.util.concurrent.ConcurrentHashMap 多线程表现优于同步的 HashMap
> > > java5 增加的线程安全的哈希表，对于多线程的操作，介于 java.util.HashMap 和 java.util.Hashtable 之间，
> > > 内部采用分段锁机制替代 java.util.Hashtable 的独占锁。
> > > java8 取消分段锁，采用 CAS 无锁机制。
> >
> > java.util.concurrent.ConcurrentSkipListMap 优于同步的TreeMap
> >
> > java.util.concurrent.ConcurrentSkipListSet
> >
> > java.util.concurrent.CopyOnWriteArrayList 当期望的读数和遍历远远大于列表的更新数时，优于同步的
> > java.util.ArrayList，每次写入时都会复制一份集合，所以没有并发修改异常
> >
> > java.util.concurrent.CopyOnWriteArraySet
> >
> > java.util.Collections.synchronizedList() 可以将集合方法全部转换为同步方法

### CAS 算法

CAS算法是硬件对于并发操作共享数据的支持\
CAS(Compare-And-Swap)是一种无锁算法，它包含三个操作数：

1. 内存位置(V)：需要读取和更新的变量地址。
2. 预期原值(A)：希望内存位置具有的当前值。
3. 新值(B)：如果当前值等于预期值，则将内存位置更新为这个新值。

CAS操作逻辑：如果内存位置V的值等于预期原值A，则将位置V的值更新为新值B，否则不进行任何操作。

在 Java 中，可以使用 java.util.concurrent.atomic 包中的 AtomicInteger 类来通过 CAS(Compare-And-Swap) 算法实现对 int
值的原子性修改。

#### 实际应用场景

1. 计数器
2. 非阻塞算法实现
3. 乐观锁
4. 并发数据结构

#### 注意事项

1. ABA问题：CAS只检查值是否变化，不检查变化过程。如果值从A变成B又变回A，CAS会认为没有变化。
    1. 解决方法：使用 AtomicStampedReference 或 AtomicMarkableReference
2. 自旋时间长可能导致CPU资源浪费
3. 只能保证一个共享变量的原子操作

java5 后 java.util.concurrent.atomic 包下提供常用的原子变量 AtomicInteger、AtomicLong等类都使用了CAS算法来保证原子性操作，比使用
synchronized 有更好的性能表现。

1. volatile保证内存可见性
2. CAS（compare and swap）算法保证原子性，比synchronized

#### volatile

volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。相较于synchronized是一种较为轻量级的同步策略

1. volatile不具有互斥性，所有线程都可以访问共享数据，synchronized就是互斥锁
2. volatile不能保证变量的原子性。原子性：volatile能保证内存可见，但是不能保证多步操作一起执行

```
i=i++;
底层： int temp=i; 读
      i=i+1;      改
      temp=i;     写
```

### 线程八锁 ThreadMonitors

明确概念：java对象，实例对象 和 Class对象

- “java对象”指的是类的实例或任何继承自java.lang.Object类的实体。所有在Java中创建的东西，如果它是基于一个类定义的，那么它就是一个对象。对象可以拥有属性（字段/成员变量）和行为（方法）。
- “实例对象”特指通过使用new关键字根据类定义创建的具体对象。每个实例对象都独立于其他实例对象，并且拥有自己的状态（即成员变量的值）
- 每个类都有一个对应的Class对象，它包含了该类的所有信息，比如类的名字、父类、实现的接口、构造器、字段、方法等。这个Class对象是在类加载到JVM时由JVM自动创建的
- 一个类只对应一个 Class 对象，可以对应多个实例对象
- 每个类都有一个 Class 对象，每当 jvm 加载一个新类就会产生一个 Class 对象
- 每个类的运行时的类型信息是使用 Class 对象表示，包含了与类有关的信息， 可以理解为：实例对象 通过 Class对象 来创建。

八种锁竞争情况

1. 一个对象 instance，两个普通同步方法，两个线程，打印one two
    - 使用同一个锁 this （instance），one会先抢到锁，two等待
2. 一个对象 instance，getOne2()方法Thread.sleep(200),等待3秒打印 one two
    - 使用同一个锁 this （instance），one会先抢到锁，two等待one释放锁
3. 一个对象 instance，新增普通非同步方法 three3()，与 one2()、two2()一起，先打印three 再打印 one two
    - one2()、two2()使用同一个锁 this （instance），one会先抢到锁，two等待one释放锁，three不需要锁 不用等待one释放锁 直接执行
4. 两个对象instance1、instance2，两个普通同步方法，打印 two one
    - 每个对象对应一个锁 instance1、instance2，instance2 的 two 不会去竞争 instance1 的 one 的锁，即普通同步方法的锁是对象实例持有
5. 一个对象 instance，getOne5()为静态同步方法，getTwo5为普通同步方法，打印 two one
    - 静态同步方法one的锁 Class 实例，普通同步方法two的锁为 instance
6. 一个对象 instance，getOne6()和getTwo6()都为静态同步方法，打印 one two
    - 静态同步方法之间会使用同一把锁 Class实例，静态方法one获取锁，静态方法two等待one释放锁
7. 两个对象instance1、instance2，instance1 调用普通同步方法，instance2 调用静态同步方法，打印 two one
    - two 普通同步方法锁在 instance1 对象里，one 静态方法锁 Class 实例，不竞争同一把锁
8. 两个对象instance1、instance2，两个静态同步方法，打印 one two
    - 静态同步方法之间会使用同一把锁 Class 实例，one获取锁，two等待one释放锁

结论：

- 某一时刻只有一个线程持有锁，无论几个方法
- 非静态方法的锁默认为 this
- 静态方法的锁为类对应的 Class 实例