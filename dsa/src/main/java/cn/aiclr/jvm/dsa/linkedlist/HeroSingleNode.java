package cn.aiclr.jvm.dsa.linkedlist;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = "next")
@Data
public class HeroSingleNode {
    public int no;
    public String name;
    public HeroSingleNode next;

    public HeroSingleNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public HeroSingleNode(int no) {
        this.no = no;
    }
}
