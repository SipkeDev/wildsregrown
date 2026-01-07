import com.wildsregrown.WildsRegrown;
import com.sipke.api.PosTranslator;

public class ValueTester {

    public static void main(String[] args) {
        System.out.println(WildsRegrown.class.getClassLoader().getResource("grid.data"));
        int val = 8192;
        int gridSize = 1024;
        float updatedVal = PosTranslator.globalToGrid(val, gridSize);

        System.out.println("Value: " + val + " to " + updatedVal);
    }

}
