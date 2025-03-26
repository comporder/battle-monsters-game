/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlemonsters;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emir
 */
public class Player {

    private String name; // Oyuncunun adı
    private List<Monster> monsters; // Oyuncunun sahip olduğu canavarlar

    // Yapıcı metot: Oyuncu adı ile birlikte boş bir canavar listesi başlatır
    public Player(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
    }

    // Oyuncunun adını döndürür
    public String getName() {
        return name;
    }

    // Oyuncunun canavar listesine yeni bir canavar ekler
    public void addMonster(Monster monster) {
        // Aynı ID'ye sahip canavar zaten varsa eklemez
        if (monsters.stream().noneMatch(m -> m.id == monster.id)) {
            monsters.add(monster);
        } else {
            System.out.println("Bu canavar zaten listede, başka bir canavar ekleyiniz.");
        }
    }

    // Oyuncunun sahip olduğu canavarları döndürür
    public List<Monster> getMonsters() {
        return monsters;
    }

    // Ölü canavarları listeden kaldırır
    public void removeDeadMonsters() {
        monsters.removeIf(Monster::isDead);
    }

    // Canavarların bilgilerini ekrana yazdırır
    public void displayMonsters() {
        for (Monster monster : monsters) {
            System.out.println("Monster {id: " + monster.id + ", name: " + monster.name + ", "
                    + "health: " + monster.health + ", attack: " + monster.attack
                    + ", defense: " + monster.defense + ", level: " + monster.level + "}");
        }
    }
}
