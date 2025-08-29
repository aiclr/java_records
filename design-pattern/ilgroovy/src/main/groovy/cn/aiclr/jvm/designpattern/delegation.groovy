package cn.aiclr.jvm.designpattern

/**
 * <pre> Delegation Pattern
 * 委托模式
 */
println "Implement Delegation Pattern using ExpandoMetaClass"

class Delegator {

    private targetClass

    private delegate

    Delegator(targetClass, delegate) {
        this.targetClass = targetClass
        this.delegate = delegate
    }

    def delegate(String methodName) {
        delegate(methodName, methodName)
    }

    def delegate(String methodName, String asMethodName) {
        // “.&”运算符是用来引用一个方法
        targetClass.metaClass."$asMethodName" = delegate.&"$methodName"
    }

    def delegateAll(String[] names) {
        names.each { delegate(it) }
    }

    def delegateAll(Map names) {
        names.each { k, v -> delegate(k, v) }
    }

    def delegateAll() {
        delegate.class.methods*.name.each { delegate(it) }
    }
}

class Person {
    String name
}

class MortgageLender {
    def borrowAmount(amount) {
        "borrow \$$amount"
    }

    def borrowFor(thing) {
        "buy $thing"
    }
}

//创建 实例
def lender = new MortgageLender()

// 给 Person 添加 MortgageLender 的能力
def delegator = new Delegator(Person, lender)

// Person#borrowFor -> MortgageLender#borrowFor
// Person#borrowFor 的调用委托给 lender#borrowFor
delegator.delegate 'borrowFor'

// Person#getMoney -> MortgageLender#borrowAmount
// Person#getMoney 的调用委托给 MortgageLender#borrowAmount
delegator.delegate 'borrowAmount', 'getMoney'

def p = new Person()

println p.borrowFor('persent')
println p.getMoney(50)

/**
 * @Delegate annotation
 */
println "Implement Delegation Pattern using @Delegate annotation"

class Person2 {
    def name

    @Delegate
    MortgageLender mortgageLender = new MortgageLender()
}

def p2 = new Person2()
assert "buy present" == p2.borrowFor('present')
assert "borrow \$50" == p2.borrowAmount(50)