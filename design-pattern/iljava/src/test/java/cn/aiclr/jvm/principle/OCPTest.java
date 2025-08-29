package cn.aiclr.jvm.principle;

import cn.aiclr.jvm.principle.ocp.NoneGraphicEditor;
import cn.aiclr.jvm.principle.ocp.NoneShapeCircle;
import cn.aiclr.jvm.principle.ocp.NoneShapeRectangle;
import cn.aiclr.jvm.principle.ocp.OCPGraphicEditor;
import cn.aiclr.jvm.principle.ocp.OCPShapeCircle;
import cn.aiclr.jvm.principle.ocp.OCPShapeRectangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("开闭原则")
class OCPTest {

    @DisplayName("符合")
    @Test
    void ocpTest() {
        OCPGraphicEditor editor = new OCPGraphicEditor();
        editor.drawShape(new OCPShapeCircle());
        editor.drawShape(new OCPShapeRectangle());
    }

    @DisplayName("不符合")
    @Test
    void noneOCPTest() {
        NoneGraphicEditor editor = new NoneGraphicEditor();
        editor.drawShape(new NoneShapeCircle());
        editor.drawShape(new NoneShapeRectangle());
    }
}
