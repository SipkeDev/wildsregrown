package com.wildsregrown.blocks;

import net.minecraft.util.math.random.Random;

public class Dice {

    public static int d4(Random random){
        return random.nextInt(3)+1;
    }

    public static int d4(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d4(random);
        }
        return count;
    }

    public static int d6(Random random){
        return random.nextInt(5)+1;
    }

    public static int d6(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d6(random);
        }
        return count;
    }

    public static int d8(Random random){
        return random.nextInt(7)+1;
    }

    public static int d8(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d8(random);
        }
        return count;
    }

    public static int d10(Random random){
        return random.nextInt(9)+1;
    }

    public static int d10(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d10(random);
        }
        return count;
    }

    public static int d12(Random random){
        return random.nextInt(11)+1;
    }

    public static int d12(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d12(random);
        }
        return count;
    }

    public static int d16(Random random){
        return random.nextInt(15)+1;
    }

    public static int d16(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d16(random);
        }
        return count;
    }

    public static int d20(Random random){
        return random.nextInt(19)+1;
    }

    public static int d20(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d20(random);
        }
        return count;
    }

    public static int d100(Random random){
        return random.nextInt(99)+1;
    }

    public static int d100(Random random, int dices){
        int count = 0;
        for (int i = 0; i < dices; i++) {
            count += d100(random);
        }
        return count;
    }
}
