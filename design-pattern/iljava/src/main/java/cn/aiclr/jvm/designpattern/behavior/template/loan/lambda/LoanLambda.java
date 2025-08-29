package cn.aiclr.jvm.designpattern.behavior.template.loan.lambda;

import cn.aiclr.jvm.designpattern.behavior.template.loan.ApplicationDenied;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 申请贷款流程 传统模板方法 实现方案
 */
public class LoanLambda {
    protected static final Logger log = LoggerFactory.getLogger(LoanLambda.class);

    private final Criteria identity;
    private final Criteria incomeHistory;
    private final Criteria creditHistory;

    public LoanLambda(Criteria identity, Criteria incomeHistory, Criteria creditHistory) {
        this.identity = identity;
        this.incomeHistory = incomeHistory;
        this.creditHistory = creditHistory;
    }

    public void check() throws ApplicationDenied {
        identity.check();
        incomeHistory.check();
        creditHistory.check();
        reportFindings();
    }


    protected void reportFindings() {
        log.info("通过");
    }
}

