/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlemonsters;

/**
 *
 * @author Emir
 */
public class Monster {

    //Txt dosyası düzenine göre canavarların özellik tanımlamları
    int id;
    String name;
    int health;
    int attack;
    int defense;
    int level;

    //Canavar oluşturucu, tüm özellikleri başlatır
    Monster(int id, String name, int health, int attack, int defense, int level) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
    }

    //Projede istenildiği gibi canavara seviye atlatır, sağlık, saldırı ve savunmayı artırır
    void levelUp() {
        this.level += 1;
        this.health += 3;
        this.attack += 1;
        this.defense += 2;
    }

    //Canavarın ölü olup olmadığını kontrol eder
    boolean isDead() {
        return this.health <= 0;
    }
}
