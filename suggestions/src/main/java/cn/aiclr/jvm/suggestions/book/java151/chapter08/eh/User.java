package cn.aiclr.jvm.suggestions.book.java151.chapter08.eh;

/**
 * <pre>1）受检异常使接口声明脆弱
 * 着系统的开发，User 接口有了多个实现者，
 * 比如普通的用户 UserImpl、模拟用户 MockUserImpl（用作测试或系统管理）、非实体用户 NonUserImpl（如自动执行机、逻辑处理器等），
 * 此时如果发现 {@link User#changePassword(int)} 方法可能还需要抛出 RejectChangeException（拒绝修改异常，如自动执行机正在处理任务时不能修改其密码），
 * 那就需要修改 User 接口了：
 *      changePassword 方法增加抛出 RejectChangeException 异常，
 *      这会导致所有的 User 调用者都要追加对 RejectChangeException 异常问题的处理
 * 这里产生了两个问题：
 *      一是异常是主逻辑的补充逻辑，修改一个补充逻辑，就会导致主逻辑也被修改，
 *          也就是出现了实现类“逆影响”接口的情景，
 *          我们知道实现类是不稳定的，而接口是稳定的，
 *          一旦定义了异常，则增加了接口的不稳定性，这是对面向对象设计的严重亵渎；
 *      二是实现的类变更最终会影响到调用者，破坏了封装性，这也是迪米特法则所不能容忍的
 */
public interface User {

    /**
     * <pre>在声明接口时不再声明异常，
     * 而是在具体实现时根据不同的情况产生不同的非受检异常，
     * 这样持久层和逻辑层抛出的异常将会由展现层自行决定如何展示，
     * 不再受异常的规则约束了，大大简化开发工作，提高了代码的可读性
     */
    void changePassword();

    /**
     * 这会导致所有的 User 调用者都要追加对 Exception 异常问题的处理
     *
     * @param type
     * @throws Exception
     */
    void changePassword(int type) throws Exception;
}
