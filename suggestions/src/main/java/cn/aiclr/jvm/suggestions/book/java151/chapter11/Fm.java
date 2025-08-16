package cn.aiclr.jvm.suggestions.book.java151.chapter11;

/**
 * <pre>142.推荐使用 Joda 日期时间扩展包
 *
 * Joda-Time 是一个非常流行且功能强大的 Java 日期时间处理库，它在 Java 8 之前被广泛使用，用以替代早期 Java 中 {@link java.util.Date} 和 {@link java.util.Calendar} 的不足。
 * 然而，随着 Java 8 的发布（2014年），引入了全新的日期时间 API —— java.time 包（JSR-310），它正是基于 Joda-Time 的设计思想，并由其作者 Stephen Colebourne 主导开发。
 * 因此：
 *   Joda-Time 已被官方标记为“不再推荐使用”（deprecated in favor of java.time）
 *
 * <a href="https://www.joda.org/joda-time/">Joda-Time 官方声明: </a>
 * {@code "Note that from Java SE 8 onwards, users are asked to migrate to java.time (JSR-310) - a core part of the JDK which replaces this project."}
 *
 * 开发一个项目必然要和日期时间打交道，
 * 特别是一些全球性的项目，必须要考虑语言和时区问题，
 * 但是在 JDK 中，日期时间的操作比较麻烦，例如 1000 小时后是星期几，伦敦时间是几点等
 * 通过 Joda 开源包来操作时间的方法，非常简单方便
 *
 * Joda 可以很好地与现有的日期类保持兼容，
 * 在需要复杂的日期计算时使用 Joda，
 * 在需要与其他系统通信或写到持久层中时则使用 JDK 的 {@link java.util.Date}。
 * Joda 是一种令人惊奇的高效工具，无论是计算日期、打印日期，或是解析日期，Joda 都是首选，
 * 当然日期工具类也可以选择 date4j，它也是一个不错的开源工具
 */
public class Fm {

}

