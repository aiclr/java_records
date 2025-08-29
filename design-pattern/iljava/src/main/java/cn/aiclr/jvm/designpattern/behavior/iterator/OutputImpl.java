package cn.aiclr.jvm.designpattern.behavior.iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class OutputImpl {

    private static final Logger log = LoggerFactory.getLogger(OutputImpl.class);

    List<College> collegeList;

    public OutputImpl(List<College> collegeList) {
        this.collegeList = collegeList;
    }

    public void printCollege() {
        for (College college : collegeList) {
            log.info(college.getName());
            printDepartment(college.createIterator());
        }
    }


    public void printDepartment(Iterator<Department> iterator) {
        while (iterator.hasNext()) {
            Department next = iterator.next();
            log.info(next.getDesc());
        }
    }
}
