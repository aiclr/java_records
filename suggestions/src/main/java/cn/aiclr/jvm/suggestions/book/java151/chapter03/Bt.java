package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>46.equals 应该考虑 null 值情景
 */
public class Bt {
    public static void main(String[] args) {
        PersonBt p1 = new PersonBt("张三");
        PersonBt p2 = new PersonBt(null);
        List<PersonBt> l = new ArrayList<>();
        l.add(p1);
        l.add(p2);
        System.out.println(l.contains(p1));
        System.out.println(l.contains(p2));
    }
}

class PersonBt {
    private String name;

    public PersonBt(String _name) {
        name = _name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonBt p) {
            //name=null时 空指针异常
//            return name.equalsIgnoreCase(p.getName());

            //优化
            if (p.getName() == null || name == null) {
                return false;
            } else
                return name.equalsIgnoreCase(p.getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}