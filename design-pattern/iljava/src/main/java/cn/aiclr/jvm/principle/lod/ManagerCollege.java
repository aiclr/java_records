package cn.aiclr.jvm.principle.lod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ManagerCollege {

    private static final Logger logger = LoggerFactory.getLogger(ManagerCollege.class);

    public List<EmployeeCollege> getAllEmployee() {
        List<EmployeeCollege> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            EmployeeCollege employee = new EmployeeCollege();
            employee.setId(i);
            list.add(employee);
        }
        return list;
    }

    public void printAllEmployee() {
        List<EmployeeCollege> employeeCollegeList = this.getAllEmployee();
        for (EmployeeCollege ce : employeeCollegeList) {
            logger.info("{}", ce.getId());
        }
    }
}
