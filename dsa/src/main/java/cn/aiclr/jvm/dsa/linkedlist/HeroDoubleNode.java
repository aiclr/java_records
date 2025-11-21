package cn.aiclr.jvm.dsa.linkedlist;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = {"next", "pre"})
@Data
public class HeroDoubleNode {
    public int no;
    public String name;
    public HeroDoubleNode next;
    public HeroDoubleNode pre;

    public HeroDoubleNode(int no, String name) {
        this.no = no;
        this.name = name;
    }
}
