package cn.aiclr.jvm.principle.lod;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接朋友 {@link Employee} {@link NoneManagerCollege}
 * 陌生类 {@link EmployeeCollege}
 */
public class NoneManager {

    private static final Logger logger = LoggerFactory.getLogger(NoneManager.class);

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
     * {@link EmployeeCollege} 非朋友关系 可以改进
     */
    public void printAllEmployee(NoneManagerCollege cm) {
        //EmployeeCollege  违背迪米特法则
        List<EmployeeCollege> employeeCollegeList = cm.getAllEmployee();
        for (EmployeeCollege ce : employeeCollegeList) {
            logger.info(Integer.toString(ce.getId()));
        }
        logger.info("------");
        List<Employee> employeeList = this.getAllEmployee();
        for (Employee e : employeeList) {
            logger.info("{}", e.getId());
        }
    }
}
