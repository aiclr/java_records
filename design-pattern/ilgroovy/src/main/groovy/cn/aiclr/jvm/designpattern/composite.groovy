package cn.aiclr.jvm.designpattern

/**
 * 组合模式
 */

abstract class Component {
    def name

    def toString(indent) { ("-" * indent) + name }
}
//输出 9 次 -
println(("-" * 9))

class Composite extends Component {
    private children = []

    def toString(indent) {
        def s = super.toString(indent)
        children.each { child ->
            s += "\n" + child.toString(indent + 1)
        }
        s
    }

    /**
     * <pre>
     * 在 Groovy 中，<< 操作符对应的方法名是 leftShift
     * 当你在一个对象上使用 << 时，Groovy 会自动调用该对象的 leftShift 方法
     */
    def leftShift(component) {
        children << component
    }
}

class Leaf extends Component {}

def root = new Composite(name: "root")

//<< 操作符相当于 root.leftShift(new Leaf(name: "leaf A"))
root << new Leaf(name: "leaf A")

def comp = new Composite(name: "comp B")
root << comp
root << new Leaf(name: "leaf C")
comp << new Leaf(name: "leaf B1")
comp << new Leaf(name: "leaf B2")
println root.toString(0)