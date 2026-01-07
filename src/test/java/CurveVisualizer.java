import com.sipke.api.rivers.RiverNode;
import com.sipke.core.vector.Vec2;
import com.sipke.math.Distance;
import com.sipke.math.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class CurveVisualizer {

    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Hello world!");
        int size = 256;

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        RiverNode[] points = new RiverNode[5];
        ArrayList<RiverNode> nodes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            points[i] = node(size, i);
        }
        System.out.println("Points: " + points.length);

        long startTime = System.currentTimeMillis();

        int samples = 24;
        float step = 1f / samples;

        calc(0, image, points, size, samples, step, nodes);
        calc(1, image, points, size, samples, step, nodes);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Execution took {}ms " + estimatedTime);

        for (RiverNode pos : nodes){
            setRGB(image, pos.x, pos.z, size, Color.BLUE);
        }

        for (RiverNode pos : points){
            setRGB(image, pos.x-1, pos.z, size, Color.RED);
            setRGB(image, pos.x+1, pos.z, size, Color.RED);
            setRGB(image, pos.x, pos.z, size, Color.RED);
            setRGB(image, pos.x, pos.z-1, size, Color.RED);
            setRGB(image, pos.x, pos.z+1, size, Color.RED);
        }

        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(image.getScaledInstance(700, 700, 0))));
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void calc(int i, BufferedImage image, RiverNode[] points, int size, int samples, float step, ArrayList<RiverNode> nodes) {

        Vec2 controlIn = calcControlPoint(points[i], points[i+1], false);
        Vec2 controlOut = calcControlPoint(points[i+1], points[i+2], true);

        //SkunkworksDraw
        setRGB(image, controlIn.x, controlIn.y, size, Color.YELLOW);
        setRGB(image, controlOut.x, controlOut.y, size, Color.MAGENTA);

        float vel0 = MathUtil.float01(MathUtil.range(points[i+1].velocity, 0f, 12f, 0f, 1f));
        float vel1 = MathUtil.float01(MathUtil.range(points[i+2].velocity, 0f, 12f, 0f, 1f));
        for (int j = 1; j <= samples; j++) {
            float t = j * step;
            RiverNode newNode = interpolateSpline(points[i], points[i+1], points[i+2], points[i+3], t);
            interpolateCurve(newNode, points[i+1], points[i+2], controlIn, controlOut, t, MathUtil.lerp(vel0, vel1, t));
            nodes.add(newNode);
        }
    }

    private static void setRGB(BufferedImage image, float x, float z, int size, Color color) {
        if (x >= size || x <= 0){
            return;
        }
        if (z >= size || z <= 0){
            return;
        }
        image.setRGB((int) x, (int) z, color.getRGB());
    }

    private static RiverNode node(int size, int i){
        float tangent = size/6f;
        float offset = size/4f;
        float z = offset + (random.nextFloat()*(offset*2f));
        return new RiverNode(tangent+(tangent*i), 12f, z, 12f, random.nextFloat()*12);
    }

    private static RiverNode interpolateSpline(RiverNode p1, RiverNode p2, RiverNode p3, RiverNode p4, float tangent) {

        float t2 = tangent * tangent;
        float t3 = t2 * tangent;

        float b1 = 0.5f * (-t3 + 2f*t2 - tangent);
        float b2 = 0.5f * (3f*t3 - 5f*t2 + 2f);
        float b3 = 0.5f * (-3f*t3 + 4f*t2 + tangent);
        float b4 = 0.5f * (t3 - t2);

        float x = b1*p1.getX() + b2*p2.getX() + b3*p3.getX() + b4*p4.getX();
        float z = b1*p1.getZ() + b2*p2.getZ() + b3*p3.getZ() + b4*p4.getZ();

        float volume = MathUtil.lerp(p2.getVolume(), p3.getVolume(), tangent);
        float y = MathUtil.lerp(p2.getY(), p3.getY(), tangent);

        return RiverNode.of(x, y, z, volume, 0);

    }

    private static void interpolateCurve(RiverNode current, RiverNode p1, RiverNode p2, Vec2 controlIn, Vec2 controlOut, float tangent, float velocity) {

        //ControlIn
        float xi0 = MathUtil.lerp(p1.x, controlIn.x, tangent);
        float zi0 = MathUtil.lerp(p1.z, controlIn.y, tangent);
        float xi1 = MathUtil.lerp(controlIn.x, p2.x, tangent);
        float zi1 = MathUtil.lerp(controlIn.y, p2.z, tangent);
        float xi2 = MathUtil.lerp(xi0, xi1, tangent);
        float zi2 = MathUtil.lerp(zi0, zi1, tangent);

        //ControlOut
        float xo0 = MathUtil.lerp(p1.x, controlOut.x, tangent);
        float zo0 = MathUtil.lerp(p1.z, controlOut.y, tangent);
        float xo1 = MathUtil.lerp(controlOut.x, p2.x, tangent);
        float zo1 = MathUtil.lerp(controlOut.y, p2.z, tangent);
        float xo2 = MathUtil.lerp(xo0, xo1, tangent);
        float zo2 = MathUtil.lerp(zo0, zo1, tangent);

        float newX = MathUtil.lerp(xi2, xo2, tangent);
        float newZ = MathUtil.lerp(zi2, zo2, tangent);

        current.x = MathUtil.lerp(current.x, newX, velocity);
        current.z = MathUtil.lerp(current.z, newZ, velocity);

    }

    private static Vec2 calcControlPoint(RiverNode controlPoint, RiverNode currentNode, boolean inverse) {

        float angle = MathUtil.angle(currentNode.x, currentNode.z, controlPoint.x, controlPoint.z);
        float distance = 2.2f*Distance.euclidean.apply(currentNode.x - controlPoint.x, currentNode.z - controlPoint.z);
        float x, z;
        if (inverse){
            x = currentNode.x - MathUtil.cos(angle) * distance;
            z = currentNode.z - MathUtil.sin(angle) * distance;
        }else {
            x = currentNode.x + MathUtil.cos(angle) * distance;
            z = currentNode.z + MathUtil.sin(angle) * distance;
        }

        return new Vec2(x, z);
    }

}
