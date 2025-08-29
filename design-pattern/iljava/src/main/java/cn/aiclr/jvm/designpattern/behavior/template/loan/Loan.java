package cn.aiclr.jvm.designpattern.behavior.template.loan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 申请贷款流程 传统模板方法 实现方案
 */
public abstract class Loan {
    protected static final Logger log = LoggerFactory.getLogger(Loan.class.getSimpleName());

    public void check() throws ApplicationDenied {
        checkIdentity();
        checkIncomeHistory();
        checkCreditHistory();
        reportFindings();
    }

    protected abstract void checkIdentity() throws ApplicationDenied;

    protected abstract void checkIncomeHistory() throws ApplicationDenied;

    protected abstract void checkCreditHistory() throws ApplicationDenied;

    protected void reportFindings() {
        log.info("通过");
    }
}

