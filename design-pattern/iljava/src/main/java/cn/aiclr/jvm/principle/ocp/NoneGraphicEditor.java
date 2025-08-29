package cn.aiclr.jvm.principle.ocp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>开闭原则：对扩展开放，对修改关闭
 *
 * 需求: 提供者开放扩展，使用者关闭修改
 * 新增具体形状，提供者支持扩展，但是使用者也要修改，不符合开闭原则。
 *
 * 提供者 {@link NoneShape}
 * 使用者 {@link NoneGraphicEditor}
 */
public class NoneGraphicEditor {

    private static final Logger log = LoggerFactory.getLogger(NoneGraphicEditor.class);

    public void drawShape(NoneShape shape) {
        if (shape.m_type == 1) {
            drawCircle();
        } else if (shape.m_type == 2) {
            drawRectangle();
        }
    }

    private void drawCircle() {
        log.info("圆形");
    }

    private void drawRectangle() {
        log.info("矩形");
    }
}
