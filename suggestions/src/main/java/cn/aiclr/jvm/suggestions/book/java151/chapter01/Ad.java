package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

/**
 * <pre>4.避免带有变长参数的方法重载
 * JAVA 在编译时，首先会根据实参的数量和类型来进行处理（都是两个实参，都是int类型，没有转成int数组）
 * int 是原生数据类型，数组是对象，编译器会从最简单数据类型（int）处理，只要符合编译条件即可通过
 */
public class Ad {

    private static final Logger logger = LoggerFactory.getLogger(Ad.class);

    //简单折扣计算
    public void calPrice(int price, int discount) {
        float knockdownPrice = price * discount / 100.0F;
        logger.info("简单折扣后的价格：{}", formateCurrency(knockdownPrice));
    }

    //复杂多折扣计算
    public void calPrice(int price, int... discounts) {
        float knockdownPrice = price;
        for (int discount : discounts) {
            knockdownPrice = knockdownPrice * discount / 100;
        }
        logger.info("复杂折扣后的价格：{}", formateCurrency(knockdownPrice));
    }

    //display
    private String formateCurrency(float price) {
        return NumberFormat.getCurrencyInstance().format(price / 100);
    }

    public static void main(String[] args) {
        Ad ad = new Ad();
        ad.calPrice(49900, 75);
    }
}