/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlemonsters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Emir
 */
public class MonsterBattleGame {

    AVLTree monsterTree; // Canavarların saklandığı AVL ağacı

    // Oyunu başlatırken dosyadan canavarları yükler
    MonsterBattleGame(String filename) {
        this.monsterTree = new AVLTree();
        loadMonstersFromFile(filename);
    }

    // Dosyadan canavarları yükler ve AVL ağacına ekler
    private void loadMonstersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Altı satırda bir canavar bilgisi alınır
                try {
                    int id = Integer.parseInt(line.trim());
                    String name = br.readLine().trim();
                    int health = Integer.parseInt(br.readLine().trim());
                    int attack = Integer.parseInt(br.readLine().trim());
                    int defense = Integer.parseInt(br.readLine().trim());
                    int level = Integer.parseInt(br.readLine().trim());

                    Monster monster = new Monster(id, name, health, attack, defense, level);
                    monsterTree.insert(monster);
                } catch (NumberFormatException | IOException e) {
                    System.err.println("Hatalı veri formatı: " + e.getMessage());
                    // Hatalı veri formatı ile karşılaşıldığında o canavarı atla
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Oyunu başlatır ve canavarları savaştırır
    void startGame() {
        Scanner scanner = new Scanner(System.in);

        // Oyuncu isimlerini alma
        System.out.print("Oyuncu 1 ismi: ");
        String player1Name = scanner.nextLine();

        System.out.print("Oyuncu 2 ismi: ");
        String player2Name = scanner.nextLine();

        // Oyuncuları oluştur
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        // Seçilecek canavar sayısını belirle
        System.out.println("Her oyuncu 10 canavar secmelidir.");

        // Oyuncu 1 canavar seçimi
        for (int i = 0; i < 10; i++) {
            System.out.print(player1Name + " lutfen " + (i + 1) + ". olarak, secmek istedigi canavar ID'sini gir: ");
            int id = scanner.nextInt();
            Monster monster = monsterTree.search(id);
            if (monster != null) {
                player1.addMonster(monster);
            } else {
                System.out.println("Canavar bulunamadi.");
                i--; // Geçerli seçim başarısız, tekrar deneyin
            }
        }

        // Oyuncu 2 canavar seçimi
        for (int i = 0; i < 10; i++) {
            System.out.print(player2Name + " lutfen " + (i + 1) + ". olarak, secmek istedigi canavar ID'sini gir: ");
            int id = scanner.nextInt();
            Monster monster = monsterTree.search(id);
            if (monster != null) {
                player2.addMonster(monster);
            } else {
                System.out.println("Canavar bulunamadi.");
                i--; // Geçerli seçim başarısız, tekrar deneyin
            }
        }

        // Oyunun ana döngüsü
        while (!player1.getMonsters().isEmpty() && !player2.getMonsters().isEmpty()) {
            // Oyuncuların canavar listelerini göster
            System.out.println("***************************************************************************");
            System.out.println(player1Name + " adli oyuncunun canavar listesi guncel olarak su sekilde: ");
            player1.displayMonsters();
            System.out.println("***************************************************************************");
            System.out.print(player1Name + ", savasacak canavar ID'si secin: ");
            int id1 = scanner.nextInt();
            System.out.println("***************************************************************************");
            System.out.println(player2Name + " adli oyuncunun canavar listesi guncel olarak su sekilde: ");
            player2.displayMonsters();
            System.out.println("***************************************************************************");
            System.out.print(player2Name + ", savasacak canavar ID'si secin: ");
            int id2 = scanner.nextInt();
            System.out.println("***************************************************************************\n\n");

            // Seçilen canavarları bul
            Monster monster1 = null, monster2 = null;
            for (Monster m : player1.getMonsters()) {
                if (m.id == id1) {
                    monster1 = m;
                    break;
                }
            }
            for (Monster m : player2.getMonsters()) {
                if (m.id == id2) {
                    monster2 = m;
                    break;
                }
            }

            //Eğer bulamazsa uyarı verir, mesela canavarlar listelerinde yoksa burası çalışır
            if (monster1 == null || monster2 == null) {
                System.out.println("Gecersiz secimler yapildi. Lutfen tekrar deneyin.");
                continue;
            }

            // Savaş mekaniği
            battle(monster1, monster2, player1, player2);

            // Savaş sonucunda dlü canavarları oyuncu listesinden kaldır
            if (monster1.isDead()) {
                player1.getMonsters().remove(monster1);
                player1.displayMonsters();
            }
            if (monster2.isDead()) {
                player2.getMonsters().remove(monster2);
                player2.displayMonsters();
            }

            // Oyunu bitir
            if (player1.getMonsters().isEmpty() || player2.getMonsters().isEmpty()) {
                break;
            }
        }

        // Oyunun sonu
        System.out.println("*****************************OYUN SONUCU***********************************");
        System.out.println("***************************************************************************");
        if (player1.getMonsters().isEmpty()) {
            System.out.println("Tebrikler " + player2Name + " kazandi!");
        } else {
            System.out.println("Tebrikler " + player1Name + " kazandi!");
        }
        System.out.println("***************************************************************************");

        scanner.close();
    }

    // Canavarlar arasındaki savaşı yönetir
    private void battle(Monster m1, Monster m2, Player player1, Player player2) {
        int damageToM2 = m1.attack - m2.defense; // M1'in M2'ye verdiği hasar
        int damageToM1 = m2.attack - m1.defense; // M2'nin M1'e verdiği hasar

        if (damageToM2 > 0) {
            m2.health -= damageToM2; // M2'nin sağlığı düşürülür
        }

        if (damageToM1 > 0) {
            m1.health -= damageToM1; // M1'in sağlığı düşürülür
        }

        // Savaş sonucu değerlendirme
        System.out.println("***************************CARPISMA SONUCU*********************************");
        System.out.println("***************************************************************************");
        if (m1.isDead() && m2.isDead()) {
            System.out.println(player1.getName() + " ve " + player2.getName() + " arasındaki savasta "
                    + m1.name + " ve " + m2.name + " birbirini yok etti! İkisi de yok oldu.");
        } else if (m1.isDead()) {
            System.out.println(player2.getName() + " kazandi! " + m2.name + " hayatta kaldi ve "
                    + player1.getName() + " adina savasan " + m1.name + " yenildi.");
            m2.levelUp();
        } else if (m2.isDead()) {
            System.out.println(player1.getName() + " kazandi! " + m1.name + " hayatta kaldi ve "
                    + player2.getName() + " adina savasan " + m2.name + " yenildi.");
            m1.levelUp();
        } else {
            System.out.println(player1.getName() + " adina savasan " + m1.name + " ile "
                    + player2.getName() + " adina savasan " + m2.name + " savasti, ancak her ikisi de hala hayatta.");
            System.out.println(player1.getName() + " adina savasan " + m1.name + " guncel saglik durumu: " + m1.health);
            System.out.println(player2.getName() + " adina savasan " + m2.name + " guncel saglik durumu: " + m2.health);
        }
        System.out.println("***************************************************************************\n\n");
    }
}
