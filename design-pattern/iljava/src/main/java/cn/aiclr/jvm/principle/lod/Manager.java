package cn.aiclr.jvm.principle.lod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接朋友{@link Employee} {@link ManagerCollege}
 */
public class Manager {

    private static final Logger logger = LoggerFactory.getLogger(Manager.class);

    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            list.add(employee);
        }
        return list;
    }

    /**
     * {@link ManagerCollege} 自己处理输出逻辑，只暴露结果方法 {@link ManagerCollege#printAllEmployee()} 出来
     */
    public void printAllEmployee(ManagerCollege cm) {
        cm.printAllEmployee();
        logger.info("------");
        List<Employee> employeeList = this.getAllEmployee();
        for (Employee e : employeeList) {
            logger.info("{}", e.getId());
        }
    }
}
