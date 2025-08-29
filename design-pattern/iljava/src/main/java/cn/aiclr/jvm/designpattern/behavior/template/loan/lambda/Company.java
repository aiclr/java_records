package cn.aiclr.jvm.designpattern.behavior.template.loan.lambda;

import cn.aiclr.jvm.designpattern.behavior.template.loan.ApplicationDenied;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>具体方法实现统一放到 Company
 * 可以为不同国家 不同城市 创建不同 Company
 */
public class Company {

    protected static final Logger log = LoggerFactory.getLogger(Company.class);

    public void checkIdentityCompany() throws ApplicationDenied {
        log.info("在Companies House等注册公司数据库中查找相关信息");
    }

    public void checkProfitAndLoss() throws ApplicationDenied {
        log.info("评估公司的现有利润、损益表和资产负债表");
    }

    public void checkHistoricalDebtCompany() throws ApplicationDenied {
        log.info("查看现有的坏账和未偿债务");
    }
}
