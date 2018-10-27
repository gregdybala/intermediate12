package pl.sda.intermediate;
// głębokość/poziom  nazwa_kategorii
//  0 Książki
//  1      Powieści
//  2           Fantastyka
//  1     Lektury
//  2            Klasa pierwsza
//  2            Klasa druga
//  0 Ebooki
//  1      Powieści

import org.junit.jupiter.api.Test;

public class CategoryParseTest {

    @Test
    public void checkParseOK() {
        Category novel1 = Category.builder()
                .depth(1)
                .id(2)
                .parentId(1)
                .name("Powieści")
                .build();
        Category novel2 = Category.builder()
                .depth(1)
                .id(8)
                .parentId(7)
                .name("Powieści")
                .build();
        Category novel3 = Category.builder()
                .depth(2)
                .id(6)
                .parentId(4)
                .name("Powieści")
                .build();
    }
}
