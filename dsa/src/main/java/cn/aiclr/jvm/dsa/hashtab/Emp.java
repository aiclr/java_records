package cn.aiclr.jvm.dsa.hashtab;

import lombok.ToString;

@ToString(exclude = {"next"})
public class Emp {
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

