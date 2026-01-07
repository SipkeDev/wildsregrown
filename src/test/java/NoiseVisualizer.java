import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.rivers.RiverConstants;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.math.MathUtil;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.noise2d.generator.SinWave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NoiseVisualizer extends JPanel {

    private static final Seed seed = Seed.of(new Random().nextInt());
    private static final int size = (int)RiverConstants.maxLakeWithRadius;

    private static Noise current() {

        int offset = size/2;
        seed.reset();

        Noise noise = NoiseGenerator.voronoi(seed.next(), 64, Distance.hybrid, CellType.distance2Div,0.5f, true)
                .fbm(3)
                .map(MapType.quintic, 0.25f, 0.75f)
                .invert()
                .warp(seed.next(), 128, 128)
                .warp(seed.next(), 32, 16);

        Noise mask = NoiseGenerator.simplex(seed.next(), size)
                .ridged(2)
                .map(MapType.hermite, 0.25f, 0.5f);

        return noise.multiply(mask);
    }

    public static void main(String[] args) {

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();

        float[] noiseMap = new float[size*size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                noiseMap[i*size+j] = current().getNoise(i, j);
            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Execution took {}ms " + estimatedTime);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float v = noiseMap[i*size+j];
                image.setRGB(i, j, Placement.Elevation.get(v).rgb);
            }
        }


        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(image.getScaledInstance(600, 600, 0))));
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
