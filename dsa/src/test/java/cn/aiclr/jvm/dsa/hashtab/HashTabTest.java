package cn.aiclr.jvm.dsa.hashtab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("哈希表")
class HashTabTest {

    @DisplayName("数组+链表")
    @Test
    void simpleHashTabTest() {
        SimpleHashTab hashTab = new SimpleHashTab(7);
        Emp caddy1 = new Emp(1, "caddy1");
        Emp caddy2 = new Emp(2, "caddy2");
        Emp caddy3 = new Emp(3, "caddy3");
        Emp caddy4 = new Emp(4, "caddy4");
        Emp caddy5 = new Emp(5, "caddy5");
        Emp caddy6 = new Emp(6, "caddy6");
        Emp caddy7 = new Emp(7, "caddy7");
        Emp caddy8 = new Emp(8, "caddy8");
        Emp caddy15 = new Emp(15, "caddy15");
        hashTab.add(caddy1);
        hashTab.add(caddy2);
        hashTab.add(caddy3);
        hashTab.add(caddy4);
        hashTab.add(caddy5);
        hashTab.add(caddy6);
        hashTab.add(caddy7);
        hashTab.add(caddy8);
        hashTab.add(caddy15);
        hashTab.show();
        Assertions.assertEquals(caddy1, hashTab.find(1));
        Assertions.assertEquals(caddy8, hashTab.find(8));
        Assertions.assertEquals(caddy2, hashTab.find(2));
        hashTab.del(1);
        hashTab.del(8);
        hashTab.del(2);
        hashTab.show();
    }
}
