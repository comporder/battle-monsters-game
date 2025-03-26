/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlemonsters;

/**
 *
 * @author Emir
 */
public class AVLNode {
    Monster monster;     // Bu düğümde saklanan canavar (veri)
    int height;          // Bu düğümün yüksekliği
    AVLNode left, right; // Bu düğümün sol ve sağ alt düğümleri

    /**
     * AVLNode yapıcı metodu.
     * Verilen canavarı düğümde saklar ve yüksekliği 1 olarak başlatır.
     * 
     * @param monster Bu düğümde saklanacak canavar nesnesi
     */
    AVLNode(Monster monster) {
        this.monster = monster; // Canavarı düğüme atama işlemi
        this.height = 1;        // Başlangıçta düğüm yüksekliği 1 olarak ayarlanır (tek düğüm)
        this.left = null;       // Sol alt düğüm başlangıçta yok (null)
        this.right = null;      // Sağ alt düğüm başlangıçta yok (null)
    }
}
