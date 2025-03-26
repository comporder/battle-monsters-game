/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.battlemonsters;

/**
 *
 * @author Emir
 */
public class BattleMonsters {

    public static void main(String[] args) {
        /**
         * Buraya kendi mosnters.txt dosya yolunuzu ekleyiniz, ben size yardımcı
         * olması adına proje dosyasının içine de yerleştirmiş bulunmaktayım
         */
        MonsterBattleGame game = new MonsterBattleGame("C:\\Users\\Ceren\\Documents\\NetBeansProjects\\BattleMonsters\\src\\main\\java\\com\\mycompany\\battlemonsters\\monsters.txt");
        game.startGame();
    }

}
