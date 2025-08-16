package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>77.使用 {@link java.util.Collections#shuffle(java.util.List)} 打乱列表
 *
 *  1.可以用在程序的“伪装”上
 *      游戏中的打怪、修行、群殴时宝物的分配策略
 *  2.可以用在抽奖程序中
 *      年会的抽奖程序，先使用 {@link java.util.Collections#shuffle(java.util.List)} 把员工排序打乱，每个员工的中奖几率就是相等的了，然后就可以抽取第一名、第二名
 *  3.可以用在安全传输方面
 *      发送端发送一组数据，先随机打乱顺序，然后加密发送，接收端解密，然后自行排序，即可实现即使是相同的数据源，也会产生不同密文的效果，加强了数据的安全性
 *
 * 在网站上我们经常会看到 关键字云（Word Cloud）和 标签云（Tag Cloud），
 * 用于表明这个关键字或标签是经常被查阅的，而且还可以看到这些标签的动态运动，
 * 每次刷新都会有不一样的关键字或标签，让浏览者觉得这个网站的访问量非常大，短短的几分钟就有这么多的搜索量
 */
public class Cy {
}

