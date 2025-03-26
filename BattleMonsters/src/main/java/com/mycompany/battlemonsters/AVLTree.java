/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlemonsters;

/**
 *
 * @author Emir
 */
public class AVLTree {

    /**
     * AVLTree sınıfı, AVL ağacında düğüm ekleme, kaldırma ve arama işlemlerini
     * gerçekleştirir.
     */
    private AVLNode root; // AVL ağacının kök düğümü

    // AVL ağacına canavar ekler
    void insert(Monster monster) {
        this.root = insertNode(this.root, monster);
    }

    // AVL ağacından canavar kaldırır
    void remove(int id) {
        this.root = removeNode(this.root, id);
    }

    // AVL ağacında canavar arar
    Monster search(int id) {
        return searchNode(this.root, id);
    }

    // AVL ağacına düğüm ekler ve ağacı dengede tutar
    private AVLNode insertNode(AVLNode node, Monster monster) {
        if (node == null) {
            return new AVLNode(monster); // Yeni düğüm ekle
        }

        // İlgili alt ağaçta ekleme işlemi yap
        if (monster.id < node.monster.id) {
            node.left = insertNode(node.left, monster);
        } else if (monster.id > node.monster.id) {
            node.right = insertNode(node.right, monster);
        } else {
            // Aynı ID'ye sahip canavar eklenmez
            return node;
        }

        // Düğümün yüksekliğini güncelle
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // AVL ağacını dengede tut
        return balance(node, monster.id);
    }

    // AVL ağacından düğüm kaldırır ve ağacı dengede tutar
    private AVLNode removeNode(AVLNode node, int id) {
        if (node == null) {
            return node; // Kaldırılacak düğüm yok
        }

        // İlgili alt ağaçta kaldırma işlemi yap
        if (id < node.monster.id) {
            node.left = removeNode(node.left, id);
        } else if (id > node.monster.id) {
            node.right = removeNode(node.right, id);
        } else {
            // Kaldırılacak düğüm bulundu
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = node.left != null ? node.left : node.right;
                node = (temp == null) ? null : temp; // Tek çocuk ya da çocuk yoksa düğümü kaldır
            } else {
                // Sağ alt ağaçtaki en küçük düğümü bul ve değeri güncelle
                AVLNode temp = minValueNode(node.right);
                node.monster = temp.monster;
                node.right = removeNode(node.right, temp.monster.id); // Minimum düğümü kaldır
            }
        }

        if (node == null) {
            return node; // Kök düğüm yoksa geri dön
        }

        // Düğümün yüksekliğini güncelle
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // AVL ağacını dengede tut
        return balance(node, id);
    }

    // AVL ağacını dengede tutar
    private AVLNode balance(AVLNode node, int id) {
        int balance = getBalance(node); // Düğümün dengesi

        // Sol-sol durumu
        if (balance > 1 && id < node.left.monster.id) {
            return rightRotate(node);
        }

        // Sağ-sağ durumu
        if (balance < -1 && id > node.right.monster.id) {
            return leftRotate(node);
        }

        // Sol-sağ durumu
        if (balance > 1 && id > node.left.monster.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Sağ-sol durumu
        if (balance < -1 && id < node.right.monster.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Dengeli düğüm
    }

    // Düğümün yüksekliğini döner
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Düğümün dengesi: sol yüksekliği - sağ yüksekliği
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Sağ rotasyon işlemi
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Sağ rotasyon
        x.right = y;
        y.left = T2;

        // Yükseklikleri güncelle
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // Yeni kök döndür
    }

    // Sol rotasyon işlemi
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Sol rotasyon
        y.left = x;
        x.right = T2;

        // Yükseklikleri güncelle
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // Yeni kök döndür
    }

    // En küçük değerli düğümü bulur
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left; // En sola giderek minimum değeri bul
        }
        return current;
    }

    // AVL ağacında canavar arar
    private Monster searchNode(AVLNode node, int id) {
        if (node == null) {
            return null; // Canavar bulunamadı
        }

        if (id < node.monster.id) {
            return searchNode(node.left, id); // Sol alt ağaçta ara
        } else if (id > node.monster.id) {
            return searchNode(node.right, id); // Sağ alt ağaçta ara
        } else {
            return node.monster; // Canavar bulundu
        }
    }
}
