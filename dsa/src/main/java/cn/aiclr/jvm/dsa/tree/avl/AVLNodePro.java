package cn.aiclr.jvm.dsa.tree.avl;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(exclude = {"left", "right"})
public class AVLNodePro {

    public int val;
    public int height;
    public AVLNodePro left;
    public AVLNodePro right;

    public AVLNodePro(int val) {
        this.val = val;
        this.height = 1;
    }
}
