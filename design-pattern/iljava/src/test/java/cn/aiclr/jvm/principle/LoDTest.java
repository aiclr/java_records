package cn.aiclr.jvm.principle;

import cn.aiclr.jvm.principle.lod.Manager;
import cn.aiclr.jvm.principle.lod.ManagerCollege;
import cn.aiclr.jvm.principle.lod.NoneManager;
import cn.aiclr.jvm.principle.lod.NoneManagerCollege;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("迪米特法则")
class LoDTest {

    @DisplayName("符合")
    @Test
    void lodTest() {
        Manager manager = new Manager();
        ManagerCollege managerCollege = new ManagerCollege();
        manager.printAllEmployee(managerCollege);
    }

    @DisplayName("不符合")
    @Test
    void noneLodTest() {
        NoneManager manager = new NoneManager();
        NoneManagerCollege managerCollege = new NoneManagerCollege();
        manager.printAllEmployee(managerCollege);
    }
}
