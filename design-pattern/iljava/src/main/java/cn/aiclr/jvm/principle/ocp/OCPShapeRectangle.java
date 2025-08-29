package cn.aiclr.jvm.principle.ocp;

public class OCPShapeRectangle extends OCPShape {

    public OCPShapeRectangle() {
        super(2);
    }

    @Override
    public void draw() {
        log.info("矩形");
    }
}
