package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.responsibility.Approver;
import cn.aiclr.jvm.designpattern.behavior.responsibility.PurchaseRequest;
import cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency.ApproverCollege;
import cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency.ApproverDepartment;
import cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency.ApproverSchoolMaster;
import cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency.ApproverViceSchoolMaster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@DisplayName("责任链模式")
class ResponsibilityTest {

    private static final Logger log = LoggerFactory.getLogger(ResponsibilityTest.class);

    @DisplayName("普通责任链模式")
    @Test
    void responsibilityTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        PurchaseRequest request = new PurchaseRequest(1, 20000.0f, 100);
        Approver department = new cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverDepartment("教学主任");
        Approver college = new cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverCollege("院长");
        Approver viceSchoolMaster = new cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverViceSchoolMaster("副校长");
        Approver schoolMaster = new cn.aiclr.jvm.designpattern.behavior.responsibility.ApproverSchoolMaster("校长");

        department.setApprover(college);
        college.setApprover(viceSchoolMaster);
        viceSchoolMaster.setApprover(schoolMaster);
        schoolMaster.setApprover(department);

        schoolMaster.processRequest(request);
        log.info("{}ms", (System.currentTimeMillis() - start));
    }

    @DisplayName("并发改造-非责任链模式")
    @Test
    void asyncTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        int number = 4;
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(number);
        try (ExecutorService executorService = Executors.newFixedThreadPool(number)) {

            PurchaseRequest purchaseRequest = new PurchaseRequest(1, 20000.0f, 100);

            List<Future<Boolean>> futureList = new ArrayList<>();

            futureList.add(executorService.submit(new ApproverCollege(begin, end, purchaseRequest)));
            futureList.add(executorService.submit(new ApproverDepartment(begin, end, purchaseRequest)));
            futureList.add(executorService.submit(new ApproverSchoolMaster(begin, end, purchaseRequest)));
            futureList.add(executorService.submit(new ApproverViceSchoolMaster(begin, end, purchaseRequest)));

            begin.countDown();
            end.await();

            for (Future<Boolean> future : futureList) {
                if (future.get()) {
                    log.info("yes");
                } else {
                    log.info("no");
                }
            }
            log.info("并发：{}ms", (System.currentTimeMillis() - start));
            executorService.shutdown();
        }
    }
}
