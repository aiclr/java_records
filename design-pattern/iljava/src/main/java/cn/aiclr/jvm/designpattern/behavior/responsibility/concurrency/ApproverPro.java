package cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency;

import cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverCollege;
import cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverDepartment;
import cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverSchoolMaster;
import cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverViceSchoolMaster;
import cn.aiclr.jvm.designpattern.behavior.responsibility.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * <pre>Chain of Responsibility Pattern 责任链模式
 *
 * 为请求创建一个接收者对象的链:
 * {@link ApproverDepartment} -> {@link ApproverCollege} -> {@link ApproverViceSchoolMaster} -> {@link ApproverSchoolMaster} -> {@link ApproverDepartment}
 *
 * 对请求的发送者 {@link PurchaseRequest} 和接收者 {@link ApproverPro} 进行解耦
 *
 * 责任链模式：通常每个接收者 {@link ApproverPro} 都包含对另一个接收者 {@link ApproverPro} 的引用
 * 如果一个接收者 {@link ApproverPro} 不能处理该请求,那么它会把相同的请求传给下一个接收者,依次类推
 *
 * 问题：
 * 当链比较长的时候,需要控制链中的节点数量
 * 一般在接收者里设置一个最大节点数量,在指定下一节点的时候,判断是否超过阈值
 * 避免出现超长链,影响系统性能
 */
public abstract class ApproverPro implements Callable<Boolean> {
    protected static Logger log = LoggerFactory.getLogger(ApproverPro.class);

    protected CountDownLatch begin;
    protected CountDownLatch end;
    protected PurchaseRequest purchaseRequest;

    public ApproverPro(CountDownLatch begin, CountDownLatch end, PurchaseRequest purchaseRequest) {
        this.begin = begin;
        this.end = end;
        this.purchaseRequest = purchaseRequest;
    }

}
