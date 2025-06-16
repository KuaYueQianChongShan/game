/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication7;

/**
 *
 * @author Selen
 */
import processing.core.PApplet;
import java.util.*;
import java.io.*;
import static processing.core.PApplet.min;

public class Sketch2 extends PApplet {
    int n = (int)(Math.random()*2);
    int end[][] = {{9,0},{4,9}};
    final int TILE_SIZE = 40;
    public static int[][] map = new int[10][10];           
    boolean begin;
    int playerX = 0;
    int playerY = 0;

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        loadMaze("C:\\Users\\Selen\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication7\\src\\javaapplication7\\Sketch2\\Map"+(n+1)+".txt");
        frameRate(7);//chatgpt
//        textAlign(CENTER, CENTER);//chatgpt
//        textSize(32);//chatgpt
    }

    public void draw() {
        background(255);
        drawMaze();
        drawPlayer();

        if (!begin && keyPressed) {
            if (keyCode == LEFT) {
                movePlayer(0, -1);
            } else if (keyCode == RIGHT) {
                movePlayer(0, 1);
            } else if (keyCode == UP) {
                movePlayer(-1, 0);
            } else if (keyCode == DOWN) {
                movePlayer(1, 0);
            }
        }

//        if (begin) {
//            fill(0, 200, 0);
//            text("cctv", width / 2, height / 2);
//        }
    }

    void drawMaze() {
        for (int i = 0; i < 10; i++) {
        System.out.println(Arrays.toString(map[i]));
            for (int j = 0; j < 10; j++) {
                if (map[i][j] == 1) {
                    fill(0); 
                }else if (map[i][j] == 2) {
                    fill(157,255,0); 
                }else {
                    fill(255); 
                }
                rect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    void drawPlayer() {
        fill(255, 0, 0);
        ellipse(playerY * TILE_SIZE + TILE_SIZE / 2, playerX * TILE_SIZE + TILE_SIZE / 2, TILE_SIZE * 0.6f, TILE_SIZE * 0.6f);
    }

    void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
        if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && map[newX][newY] != 1) {
            playerX = newX;
            playerY = newY;

            if (playerX == end[n][0] && playerY == end[n][1]) {
                Sketch.main("javaapplication7.Sketch");
            }
        }
        }
    void loadMaze(String filename){
        int i = 0;
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNext()){
                String output = file.nextLine();
                String all[] = output.split(" ");
                for (int j=0;j<10;j++){
                    map[i][j] = Integer.parseInt(all[j]);
                }
                i++;
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("无法读取地图文件！");
            }
        }
    }
