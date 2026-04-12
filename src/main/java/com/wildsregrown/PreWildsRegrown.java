package com.wildsregrown;

import com.wildsregrown.registries.world.*;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PreWildsRegrown implements PreLaunchEntrypoint {

    private static final URL splashURL = PreWildsRegrown.class.getResource("/assets/wildsregrown/icon.png");
    public static JFrame frame;

    public void onPreLaunch() {
        try {
            this.initSplashscreen();
            this.initWorldRegistries();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initWorldRegistries(){
        //World
        MaterialRegistery.init();
        Biomes.init();
        Ecosystems.init();
        Landforms.init();
        Trees.init();
        Floras.init();
        Structures.init();
    }

    private void initSplashscreen() throws IOException {
        frame = new JFrame("Minecraft");

        assert splashURL != null;
        BufferedImage image = ImageIO.read(splashURL);

        int w = (int) (0.785f*image.getWidth()), h = (int)(0.785f*image.getHeight());

        ImageIcon icon = new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH));

        Color t = new Color(0,0,0,0);
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBackground(t);
        label.setSize(w, h);
        label.setIcon(icon);
        label.setOpaque(true);

        frame.getContentPane().setBackground(t);
        frame.setType(Window.Type.UTILITY);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(w, h);
        frame.setUndecorated(true);
        frame.setBackground(t);
        frame.setContentPane(label);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(false);
        frame.setVisible(true);

    }

}