package com.wildsregrown.util;

import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.render.TintUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TintVisualizer extends JPanel {

    private static int dry = Color.decode("#c8b478").getRGB();
    private static int temperate = Color.decode("#748937").getRGB();
    private static int wet = Color.decode("#93ad46").getRGB();

    public static void main(String[] args) {
        System.out.println("Hello world!");
        int size = 1024;

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();

        int[] noiseMap = new int[size*size];
        for (int i = 0; i < size; i++) {
            int overlay = dry;
            if (i < size/2) {
                overlay = wet;
            }
            float alpha = 0.5f*(1f-MathUtil.parabola(MathUtil.range(i, 0, size, 0f, 1f), 5f));
            for (int j = 0; j < size; j++) {
                noiseMap[i*size+j] = TintUtil.overlay(temperate, overlay, alpha);
            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Execution took {}ms " + estimatedTime);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                image.setRGB(i, j, noiseMap[i*size+j]);
            }
        }


        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(image.getScaledInstance(700, 700, 0))));
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
