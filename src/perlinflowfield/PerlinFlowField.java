/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perlinflowfield;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Marina
 */
public class PerlinFlowField extends JFrame {

    private double z = 0;
    private int tamanho = 10;
    int linhas = 59, colunas = 79;
    private JPanel jpanel;
    private List<Ball> balls;

    public static void main(String[] args) {
        PerlinFlowField window = new PerlinFlowField();
        window.setVisible(true);
    }

    public PerlinFlowField() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        jpanel = new MyPanel();
        jpanel.setDoubleBuffered(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jpanel);

        balls = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            balls.add(new Ball(new Vector(Math.random() * colunas, Math.random() * linhas)));
        }

        new UpdateThread().start();
    }

    private class MyPanel extends JPanel {

        @Override
        public void paint(Graphics grphcs) {
            double noiseScale = 0.1;

            Graphics2D g2d = (Graphics2D) grphcs.create();
//            g2d.setBackground(Color.WHITE);
            g2d.setColor(new Color(0x10FFFFFF, true));
            g2d.fillRect(0, 0, 800, 600);
            g2d.translate(10, 10);

//            for (int x = 0; x < colunas; x++) {
//                for (int y = 0; y < linhas; y++) {
//                    double noise = (SimplexNoise.noise(x * noiseScale, y * noiseScale, z * noiseScale) + 1) / 2;
//
//                    g2d.setColor(Color.BLACK);
//                    desenhaLinha(g2d, x * tamanho, y * tamanho, noise);
//                }
//            }

            for (Ball ball : balls) {
                double noise = (SimplexNoise.noise(ball.pos.x * noiseScale, ball.pos.y * noiseScale, z * noiseScale) + 1) / 2;
                ball.acc = vetor(noise, 0.04);

                ball.update();
                borda(ball);
                g2d.setColor(new Color(0x10000000, true));
                g2d.fillOval((int) (ball.pos.x * tamanho), (int) (ball.pos.y * tamanho), 3, 3);
            }

            g2d.dispose();

            z += 0.1;
        }
    }

    public void desenhaLinha(Graphics2D g2d, int x, int y, double angulo) {
        Point p = ponto(x, y, angulo, tamanho);
        g2d.drawLine(x, y, p.x, p.y);
    }

    public Point ponto(int x0, int y0, double theta, double r) {
        double x = x0 + r * Math.cos(Math.toRadians(theta * 360));
        double y = y0 + r * Math.sin(Math.toRadians(theta * 360));
        return new Point((int) x, (int) y);
    }

    public Vector vetor(double theta, double forca) {
        double x = forca * Math.cos(Math.toRadians(theta * 360));
        double y = forca * Math.sin(Math.toRadians(theta * 360));
        return new Vector(x, y);
    }

    public void borda(Ball b) {
        if(b.pos.x < 0) {
            b.pos.x = colunas;
        }
        if(b.pos.y < 0) {
            b.pos.y = linhas;
        }
        if(b.pos.x > colunas) {
            b.pos.x = 0;
        }
        if(b.pos.y > linhas) {
            b.pos.y = 0;
        }
    }

    private class UpdateThread extends Thread {

        public UpdateThread() {
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    jpanel.repaint();
                    Thread.sleep(1000 / 60);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PerlinFlowField.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Color cor(double n) {
        return new Color((float) n, (float) n, (float) n);
    }

}
