/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication7;
import processing.core.PApplet;
import java.util.ArrayList;
/**
 *
 * @author 345467385
 */
public class Sketch extends PApplet {
    Gamer gamer;
    ArrayList<Gamer.TriangleEnemy> enemies;
    ArrayList<Gamer.HealItem> heals;
    boolean hasHealSpawned = false;
    boolean victory = false;
    boolean gameOver = false;
    int scoreTime = 0;
    int highScore = 0;

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        gamer = new Gamer(this);
        enemies = new ArrayList<>();
        heals = new ArrayList<>();
        frameRate(60);
    }

    public void draw() {
        background(255);
        if (!victory && !gameOver) scoreTime++;
        gamer.update();

        if (!victory && !gameOver && enemies.size() < 12 && frameCount % 20 == 0) {
            enemies.add(new Gamer.TriangleEnemy(this, gamer, scoreTime));
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Gamer.TriangleEnemy t = enemies.get(i);
            t.update(gamer.getOffsetX(), gamer.getOffsetY());
            t.display();
            if (t.hitsCenter()) {
                gamer.takeDamage();
                enemies.remove(i);
            } else if (t.isOffScreen()) {
                enemies.remove(i);
            }
        }

        if (!hasHealSpawned && scoreTime >= 120 * 60) {
            heals.add(new Gamer.HealItem(this, gamer));
            hasHealSpawned = true;
        }

        for (int i = heals.size() - 1; i >= 0; i--) {
            Gamer.HealItem h = heals.get(i);
            h.update(gamer.getOffsetX(), gamer.getOffsetY());
            h.display();
            if (h.collectedByCenter()) {
                if (gamer.getVisibleHealth() == 1 && gamer.getRealHealth() == 0) {
                    victory = true;
                    highScore = max(highScore, scoreTime);
                } else {
                    gamer.visualHeal();
                }
                heals.remove(i);
            }
        }

        if (!victory && !gameOver && gamer.getRealHealth() == 0 && gamer.getVisibleHealth() == 0) {
            gameOver = true;
            highScore = max(highScore, scoreTime);
        }else if(gamer.getRealHealth() == 0 && gamer.getVisibleHealth() == 1) {
            victory = true;
            highScore = max(highScore, scoreTime);
        }

        gamer.display();

        fill(0);
        textSize(16);
        text("HP: " + gamer.getVisibleHealth(), 10, 20);
        text("Time: " + scoreTime / 60 + "s", 10, 40);
        text("High Score: " + highScore / 60 + "s", 10, 60);

        if (victory) {
            fill(0, 200, 0);
            textSize(32);
            text("Victory!", width / 2 - 80, height / 2);
            text("Press R to Restart", width / 2 - 110, height / 2 + 40);
        } else if (gameOver) {
            fill(255, 0, 0);
            textSize(32);
            text("Game Over!", width / 2 - 100, height / 2);
            text("Press R to Restart", width / 2 - 110, height / 2 + 40);
        }
    }

    public void keyPressed() {
        if (key == 'r' || key == 'R') {
            gamer = new Gamer(this);
            enemies.clear();
            heals.clear();
            hasHealSpawned = false;
            victory = false;
            gameOver = false;
            scoreTime = 0;
        }
    }
}