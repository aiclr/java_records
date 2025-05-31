package cn.aiclr.jvm.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * 李秋莉的题
 */
public class LiQiuli {

    private static final Logger logger = LoggerFactory.getLogger(LiQiuli.class);

    public static void main(String[] args) {
        Scanner sc = null;
        try {
            logger.info("输入：tom:187,jim:288,mike:688,jimi:888");
            sc = new Scanner(System.in);
            String str = sc.next();
            String[] customers = str.split(",");
            String[] name = new String[customers.length];
            int[] score = new int[customers.length];
            for (int i = 0; i < customers.length; i++) {
                String[] user = customers[i].split(":");
                name[i] = user[0];
                score[i] = Integer.parseInt(user[1]);
            }
            StringBuilder sb = new StringBuilder();
            logger.info("输入：数字");
            int luckyNum = sc.nextInt();
            for (int i = 0; i < score.length; i++) {
                if (luckyNum == score[i]) {
                    sb.append(name[i]);
                    sb.append(",");
                }
            }
            if (!sb.isEmpty()) {
                sb.deleteCharAt(sb.length() - 1);
                logger.info("{}", sb);
            } else logger.warn("NONE");
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        } finally {
            if (null != sc)
                sc.close();
        }
    }
}
