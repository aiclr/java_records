package cn.aiclr.jvm.suggestions.book.java151.chapter03;

/**
 * <pre>40.匿名类的构造函数很特殊
 */
public class Bn {
    private int i, j, result;

    public Bn() {
        System.out.println("Bn Contract");
    }

    public Bn(int _i, int _j) {
        System.out.println("Bn(int,int) Contract");
        i = _i;
        j = _j;
    }

    protected void setOperator(Ops _ops) {
        result = switch (_ops) {
            case ADD -> {
                System.out.println("i+j");
                yield i + j;
            }
            case SUB -> {
                System.out.println("i-j");
                yield i - j;
            }
        };
    }

    public int getResult() {
        return result;
    }

}

enum Ops {ADD, SUB}

class ClientBn {
    public static void main(String[] args) {
        Bn bn = new Bn(2, 1) {
            {
                setOperator(Ops.SUB);
            }
        };
        System.out.println(bn.getResult());

        AddBn addBn = new AddBn(1, 2);
        System.out.println(addBn.getResult());
    }
}


/**
 * <pre>模拟匿名类
 * 匿名类的构造函数特殊处理机制，
 * 一般类（也就是具有显式名字的类）的所有构造函数默认都是调用父类的无参构造的，
 * 而匿名类因为没有名字，只能由构造代码块代替，也就无所谓的有参和无参构造函数了，
 * 它在初始化时直接调用了父类的同参数构造，然后再调用了自己的构造代码块
 */
class AddBn extends Bn {
    {
        setOperator(Ops.ADD);
    }

    public AddBn(int _i, int _j) {
        super(_i, _j);
    }
}