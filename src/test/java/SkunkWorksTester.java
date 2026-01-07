import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.api.features.trees.graph.TreeGraph;
import com.sipke.core.util.HeightMapLoader;
import com.sipke.features.trees.TreeBuilder;
import com.sipke.registeries.Trees;

import java.awt.image.BufferedImage;
import java.util.Random;

public class SkunkWorksTester {

    public static void main(String[] args) {

        BufferedImage image = HeightMapLoader.load("england");

        System.out.println(image);

        /*
        TreeConfig tree = Trees.spruce.getInstance();
        TreeGraph graph = TreeGraph.create(Trees.spruce.getKey());
        TreeBuilder.update(graph, new Random().nextInt(), tree.age-10, 1f);
        graph.log();

         */

    }

}
