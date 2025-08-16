package cn.aiclr.jvm.suggestions.book.java151.chapter03;

/**
 * <pre>47.在 equals 中使用 getClass 进行类型判断
 * 使用 instanceof 关键字检查子类 e1 是否是父类 PersonBu 的实例，由于两者存在继承关系，那结果当然是 true 了
 * 但是反过来就不成立了，e1 或 e2 可不等于 p1
 * 这也是违反对称性原则的一个典型案例
 *
 * p1 与 e1、e2 相等，但 e1 竟然与 e2 不相等，似乎一个简单的等号传递都不能实现。
 * e1.equals(e2) 调用的是子类 Employee 的 equals 方法，
 * 不仅仅要判断姓名相同，还要判断工号是否相同，两者工号是不同的，不相等也是自然的了。
 *
 * 等式不传递是因为违反了 equals 的传递性原则
 *
 * 传递性原则是指对于实例对象 x、y、z 来说，如果 x.equals(y) 返回 true，y.equals(z) 返回 true，那么 x.equals(z) 也应该返回true
 *
 * 这种情况发生的关键是父类使用了 instanceof 关键字，
 * 它是用来判断是否是一个类的实例对象的，这很容易让子类“钻空子”。
 * 想要解决也很简单，使用 getClass 来代替 instanceof 进行类型判断
 * 考虑到 Employee 也有可能被继承，也需要把它的 instanceof 修改为 getClass。
 * 总之，在覆写 equals 时建议使用 getClass 进行类型判断，而不要使用 instanceof
 */
public class Bu {
    public static void main(String[] args) {
        EmployeeBu e1 = new EmployeeBu("张三", 100);
        EmployeeBu e2 = new EmployeeBu("张三", 1001);
        PersonBu p3 = new PersonBu("张三");
        //调用父类equals
        System.out.println(p3.equals(e1));
        //调用子类equals
        System.out.println(e1.equals(p3));

        System.out.println(p3.equals(e2));
        System.out.println(e2.equals(p3));

        System.out.println(e1.equals(e2));
        System.out.println(e2.equals(e1));
    }
}

class EmployeeBu extends PersonBu {
    private int id;

    public EmployeeBu(String _name, int _id) {
        super(_name);
        id = _id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            EmployeeBu e = (EmployeeBu) obj;
            return super.equals(obj) && e.getId() == id;

        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class PersonBu {
    private String name;

    public PersonBu(String _name) {
        name = _name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            PersonBu p = (PersonBu) obj;
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