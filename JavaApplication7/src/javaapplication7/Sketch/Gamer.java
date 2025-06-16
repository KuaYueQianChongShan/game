/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication7;

import processing.core.PApplet;

/**
 *
 * @author 345467385
 */
import processing.core.PApplet;
public class Gamer {
    private PApplet app;
    private float offsetX, offsetY;
    private float vx, vy;
    private final float accel = 0.6f;
    private final float friction = 0.1f;
    private final float maxSpeed = 2;

    private int realHealth = 9;
    private int visibleHealth = 10;

    public Gamer(PApplet app) {
        this.app = app;
    }

    public void update() {
        if (app.keyPressed) {
            if (app.keyCode == PApplet.LEFT) vx += accel;
            if (app.keyCode == PApplet.RIGHT) vx -= accel;
            if (app.keyCode == PApplet.UP) vy += accel;
            if (app.keyCode == PApplet.DOWN) vy -= accel;
        }

        vx = PApplet.constrain(vx, -maxSpeed, maxSpeed);
        vy = PApplet.constrain(vy, -maxSpeed, maxSpeed);
        vx *= friction;
        vy *= friction;

        offsetX += vx;
        offsetY += vy;
    }

    public void display() {
        app.fill(255, 140, 0);
        app.ellipse(app.width / 2, app.height / 2, 30, 30); // 中心玩家
    }

    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }

    public void takeDamage() {
        if (realHealth > 0) {
            realHealth--;
            visibleHealth--;
        }
    }

    public void visualHeal() {
        visibleHealth++;
    }

    public int getRealHealth() { return realHealth; }
    public int getVisibleHealth() { return visibleHealth; }

    // 内部类 TriangleEnemy
    public static class TriangleEnemy {
        private PApplet app;
        private float x, y, vx, vy;
        private int color;

        public TriangleEnemy(PApplet app, Gamer gamer, int time) {
            this.app = app;
            float side = app.random(4);
            if (side < 1) { x = app.random(app.width); y = -20; }
            else if (side < 2) { x = app.random(app.width); y = app.height + 20; }
            else if (side < 3) { x = -20; y = app.random(app.height); }
            else { x = app.width + 20; y = app.random(app.height); }

            float tx = app.width / 2;
            float ty = app.height / 2;
            float angle = PApplet.atan2(ty - y, tx - x);

            float chance = app.random(1);
            float speed;
            if (chance < Math.min(0.2f + time / 300.0f, 0.9f)) {
                speed = app.random(2.5f, 3.5f);
                color = app.color(255, 0, 0);
            } else if (chance < 0.6f) {
                speed = app.random(1.5f, 2.5f);
                color = app.color(0, 255, 0);
            } else {
                speed = app.random(0.8f, 1.5f);
                color = app.color(0, 0, 255);
            }

            vx = PApplet.cos(angle) * speed;
            vy = PApplet.sin(angle) * speed;
        }

        public void update(float dx, float dy) {
            x += vx + dx;
            y += vy + dy;
        }

        public void display() {
            app.fill(color);
            app.triangle(x, y, x - 5, y + 10, x + 5, y + 10);
        }

        public boolean hitsCenter() {
            float cx = app.width / 2;
            float cy = app.height / 2;
            return app.dist(x, y, cx, cy) < 15;
        }

        public boolean isOffScreen() {
            return x < -40 || x > app.width + 40 || y < -40 || y > app.height + 40;
        }
    }

    // 内部类 HealItem
    public static class HealItem {
        private PApplet app;
        private float x, y;

        public HealItem(PApplet app, Gamer gamer) {
            this.app = app;
            x = app.random(50, app.width - 50) + gamer.getOffsetX();
            y = app.random(50, app.height - 50) + gamer.getOffsetY();
        }

        public void update(float dx, float dy) {
            x += dx;
            y += dy;
        }

        public void display() {
            app.fill(0, 255, 255);
            app.rect(x, y, 20, 20);
        }

        public boolean collectedByCenter() {
            float cx = app.width / 2;
            float cy = app.height / 2;
            return app.dist(x, y, cx, cy) < 25;
        }
    }
}