package cn.aiclr.jvm.designpattern.behavior.command.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编辑器接受命令 执行
 */
public class Editor {
    private static final Logger log = LoggerFactory.getLogger(Editor.class);

    public void save() {
        log.info("save");
    }

    public void open() {
        log.info("open");
    }

    public void close() {
        log.info("close");
    }
}
