package cn.aiclr.jvm.designpattern

import java.math.RoundingMode

/**
 * 方形桩
 */
class SquarePeg {
    def width
}

/**
 * 圆形桩
 */
class RoundPeg {
    def radius
}

/**
 * 圆孔
 */
class RoundHole {
    //半径
    def radius

    def pegFits(peg) {
        peg.radius <= radius
    }

    String toString() { "RoundHole with radius $radius" }
}

class SquarePegAdapter {
    def peg

    /**
     * 正方形外接圆半径
     *
     */
    def getRadius() {
        // sqrt ：开平方
        // **2 ：** 标识幂运算、2 平方
        // *2 ：两个直角边
        def result = Math.sqrt(((peg.width / 2)**2) * 2)
        new BigDecimal(result).setScale(2, RoundingMode.UP)
    }

    String toString() {
        "SquarePegAdapter with peg width $peg.width (and notional radius $radius)"
    }
}

def hole = new RoundHole(radius: 4.0)

(5..6).each { w ->
    //具体适配器
    def peg = new SquarePegAdapter(peg: new SquarePeg(width: w))
    if (hole.pegFits(peg)) {
        println "peg $peg fits in hole $hole"
    } else {
        println "peg $peg does not fit in hole $hole"
    }
}

println 'inheritance example'

/**
 * <pre>inheritance example
 * 继承示例
 */
class SquarePegAdapter2 extends SquarePeg {
    def getRadius() {
        def result = Math.sqrt(((width / 2)**2) * 2)
        new BigDecimal(result).setScale(2, RoundingMode.UP)
    }

    String toString() {
        "SquarePegAdapter2 with peg width $width (and notional radius $radius)"
    }
}

(5..6).each { w ->
    //具体适配器
    def peg = new SquarePegAdapter2(width: w)
    if (hole.pegFits(peg)) {
        println "peg $peg fits in hole $hole"
    } else {
        println "peg $peg does not fit in hole $hole"
    }
}

println 'adapting using closures'

interface RoundThing {
    def getRadius()
}

def adapter = { p ->
    [getRadius: {
        def result = Math.sqrt(((p.width / 2)**2) * 2)
        new BigDecimal(result).setScale(2, RoundingMode.UP)
    }] as RoundThing
}

(5..6).each { w ->
    def peg = new SquarePeg(width: w)
    if (hole.pegFits(adapter(peg))) {
        println "peg $peg fits in hole $hole"
    } else {
        println "peg $peg does not fit in hole $hole"
    }
}

println 'adapting using the ExpandoMetaClass'


(5..6).each { w ->
    def peg = new SquarePeg(width: w)
    // 添加 属性 radius
    peg.metaClass.radius = new BigDecimal(Math.sqrt(((peg.width / 2)**2) * 2)).setScale(2, RoundingMode.UP)
    peg.metaClass.string = "SquarePeg MetaClass with peg width $peg.width (and notional radius $peg.radius)"

    if (hole.pegFits(peg)) {
        println "peg $peg.string fits in hole $hole"
    } else {
        println "peg $peg.string does not fit in hole $hole"
    }
}