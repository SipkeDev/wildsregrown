package com.wildsregrown.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sipke.registeries.Biomes;
import com.sipke.registeries.Ecosystems;
import com.sipke.registeries.Landforms;
import com.sipke.registeries.Structures;
import com.wildsregrown.WildsRegrown;
import com.sipke.World;
import com.sipke.api.PosTranslator;
import com.sipke.api.categorization.Moisture;
import com.sipke.api.categorization.Temperature;
import com.sipke.api.cell.BiomeCell;
import com.sipke.api.features.caves.CaveLayer;
import com.sipke.api.features.caves.CaveNode;
import com.sipke.api.features.structures.Structure;
import com.sipke.api.features.structures.StructureSpawn;
import com.sipke.api.grid.WorldGrid;
import com.sipke.api.heightmap.HeightMapPos;
import com.sipke.api.rivers.RiverConstants;
import com.sipke.api.terrain.Biome;
import com.sipke.api.terrain.Ecosystem;
import com.sipke.api.terrain.Landform;
import com.sipke.api.cell.EcoSystemCell;
import com.sipke.api.cell.LandFormCell;
import com.sipke.api.rivers.River;
import com.sipke.api.rivers.RiverBasin;
import com.sipke.api.rivers.RiverNode;
import com.sipke.math.Distance;
import com.sipke.math.MathUtil;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.permission.PermissionPredicate;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

import static com.sipke.api.rivers.RiverConstants.noiseRivers;

public class Locate {

    private static final DynamicCommandExceptionType KEY_NOT_FOUND = new DynamicCommandExceptionType((id) ->
            Text.of("No config of " + id + "found, try generating another world"));
    private static final DynamicCommandExceptionType STRUCTURE_INVALID_EXCEPTION = new DynamicCommandExceptionType((id) ->
            Text.of("No structure of " + id + "found, try generating another world"));
    public static final SuggestionProvider<ServerCommandSource> ALL_LANDFORMS = SuggestionProviders.register(Identifier.of("landforms"), (context, builder) ->
            CommandSource.suggestMatching(Landforms.getEntries().stream().map(Landform::toString), builder));
    public static final SuggestionProvider<ServerCommandSource> ALL_ECOSYSTEMS = SuggestionProviders.register(Identifier.of("ecosystems"), (context, builder) ->
            CommandSource.suggestMatching(Ecosystems.getEntries().stream().map(Ecosystem::toString), builder));
    public static final SuggestionProvider<ServerCommandSource> ALL_BIOMES = SuggestionProviders.register(Identifier.of("biomes"), (context, builder) ->
            CommandSource.suggestMatching(Biomes.getEntries().stream().map(Biome::toString), builder));
    public static final SuggestionProvider<ServerCommandSource> ALL_STRUCTURES = SuggestionProviders.register(Identifier.of("structures"), (context, builder) ->
            CommandSource.suggestMatching(Structures.getEntries().stream().map(Structure::toString), builder));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(((CommandManager.literal("wrg").requires(ServerCommandSource::isExecutedByPlayer))

                .then(CommandManager.literal("landform")
                .then(CommandManager.argument("landform", StringArgumentType.word()).suggests(ALL_LANDFORMS).executes((context) ->
                        nearest(context.getSource(), StringArgumentType.getString(context, "landform"))))))

                .then(CommandManager.literal("ecosystem")
                        .then(CommandManager.argument("ecosystem", StringArgumentType.word()).suggests(ALL_ECOSYSTEMS).executes((context) ->
                                ecosystem(context.getSource(), StringArgumentType.getString(context, "ecosystem")))))

                .then(CommandManager.literal("biome")
                        .then(CommandManager.argument("biome", StringArgumentType.word()).suggests(ALL_BIOMES).executes((context) ->
                                biome(context.getSource(), StringArgumentType.getString(context, "biome")))))

                .then(CommandManager.literal("info").executes((context) ->
                        here(context.getSource())))

                .then(CommandManager.literal("Cavern").executes((context) ->
                        cavern(context.getSource())))
                .then(CommandManager.literal("CavernNode").executes((context) ->
                        cavernNode(context.getSource())))


                .then(CommandManager.literal("river")
                        .then(CommandManager.literal("noise_river").executes((context) -> riverNode(context.getSource(), 0f, noiseRivers, false)))
                        .then(CommandManager.literal("river").executes((context) -> riverNode(context.getSource(), noiseRivers, 1f, false)))
                        .then(CommandManager.literal("lake").executes((context) -> riverNode(context.getSource(), 0f, 1f, true)))
                )
                .then(CommandManager.literal("structure")
                        .then(CommandManager.argument("structure", StringArgumentType.word()).suggests(ALL_STRUCTURES).executes((context) ->
                                structure(context.getSource(), StringArgumentType.getString(context, "structure")))))
        );
    }

    private static int riverNode(ServerCommandSource source, float lowerBound, float upperBound, boolean lake) throws CommandSyntaxException {

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        World world = ((WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator()).getWorld();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        int size = world.getGrid().getSize();

        RiverNode closestNode = closestRiverNode(lowerBound, upperBound, world, playerX, playerZ, size, lake);

        if (closestNode == null){
            source.sendFeedback(() -> Text.of("Not found"), false);
        }
        BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(closestNode.x, size),0,PosTranslator.gridToGlobal(closestNode.z, size));

        if (source.getWorld().getChunkManager().getChunkGenerator() == null || (playerX + playerZ) == 0) {
            throw KEY_NOT_FOUND.create("Error");
        } else {
            return sendCoordinates(source, blockPos, targetPos,
                    "Node:" + "\n" +
                            "x: " + closestNode.x + "\n" +
                            "y: " + closestNode.y + "\n" +
                            "z: " + closestNode.z + "\n" +
                            "Vol: " + closestNode.volume + "\n" +
                            "Vel: " + closestNode.velocity + "\n"
            );
        }
    }

    private static RiverNode closestRiverNode(float lowerBound, float upperBound, World world, int x, int z, int size, boolean lake){

        float dist = Float.MAX_VALUE;
        RiverNode closestNode = null;
        for (RiverBasin basin : world.getGrid().getRiverBasins()){
            for (River river : basin.getRivers()) {
                for (RiverNode node : river.path) {

                    //matches generator conversion
                    float velocity = RiverConstants.convertVelocity(node);
                    if (velocity > upperBound || velocity < lowerBound){continue;}

                    float dx = MathUtil.abs(node.x - PosTranslator.globalToGrid(x, size));
                    float dz = MathUtil.abs(node.z - PosTranslator.globalToGrid(z, size));
                    float newDist = Distance.euclidean.apply(dx, dz);

                    if (dist > newDist) {
                        dist = newDist;
                        if (lake){
                            if (node.lake){
                                closestNode = node;
                            }
                        }else {
                            closestNode = node;
                        }
                    }
                }
            }
        }

        return closestNode;
    }

    private static int here(ServerCommandSource source) throws CommandSyntaxException {

        World world = ((WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator()).getWorld();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        HeightMapPos pos = world.generator.getHeightMapPos(playerX, playerZ);

        Ecosystem ecosystem = Ecosystems.get(pos.getEcosystem());
        Text text = Text.of("\n" +
                "Landform: " + Landforms.get(pos.getLandform()) + "\n" +
                "Ecosystem: " + ecosystem + "\n" +
                "Biome: " + Biomes.get(pos.getBiome()).toString() + "\n" +
                pos.getHeight()   + " [Height]" + "\n" +
                pos.getTemperature() + " |Temperature [" + Temperature.get(pos.getTemperature()) + "]" + "\n" +
                pos.getMoisture()    + " |Moisture [" + Moisture.get(pos.getMoisture()) + "]" + "\n" +
                pos.getFlows()    + " [Flows]" + "\n");// +
                //pos.getBiomePlacement() + " [Placement] "
        //);

        if (source.getWorld().getChunkManager().getChunkGenerator() == null || (playerX + playerZ) == 0) {
            throw KEY_NOT_FOUND.create("Error");
        } else {
            source.sendFeedback(() -> text, false);
            return 0;
        }
    }

    private static int cavern(ServerCommandSource source) throws CommandSyntaxException {

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int size = grid.getSize();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        float dist = Float.MAX_VALUE;
        LandFormCell idx = grid.getLandforms().get(0);

        for (LandFormCell cell : grid.getLandforms()){
            if (cell.hasCavern()){
                int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
                int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
                float newDist = Distance.euclidean.apply(dx, dz);
                if (dist > newDist){
                    dist = newDist;
                    idx = cell;
                }
            }
        }
        BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(idx.getX(), size),0,PosTranslator.gridToGlobal(idx.getZ(), size));


        if (chunkGenerator == null || (targetPos.getX() + targetPos.getZ()) == 0) {
            throw KEY_NOT_FOUND.create("empty");
        } else {
            return sendCoordinates(source, blockPos, targetPos, "Cavern");
        }
    }

    private static int cavernNode(ServerCommandSource source) throws CommandSyntaxException {

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int size = grid.getSize();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        float dist = Float.MAX_VALUE;
        LandFormCell idx = grid.getLandforms().get(0);

        for (LandFormCell cell : grid.getLandforms()){
            if (cell.hasCavern()){
                int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
                int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
                float newDist = Distance.euclidean.apply(dx, dz);
                if (dist > newDist){
                    dist = newDist;
                    idx = cell;
                }
            }
        }
        BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(idx.getX(), size),0,PosTranslator.gridToGlobal(idx.getZ(), size));

        CaveNode closestNode = null;
        dist = Float.MAX_VALUE;
        float y = 0;
        for (CaveLayer layer : idx.getCavern().getLayers()){
            for (CaveNode newNode : layer.nodes()){

                float vecX = newNode.getX() - playerX;
                float vecZ = newNode.getZ() - playerZ;
                float newDist = Distance.euclidean.apply(vecX, vecZ);
                if (dist > newDist){
                    dist = newDist;
                    closestNode = newNode;
                }
            }
        }

        if (closestNode!=null){
            blockPos = new BlockPos(closestNode.getX(), 64, closestNode.getZ());
            return sendCoordinates(source, blockPos, targetPos, "Cavern");
        }else
        if (chunkGenerator == null || (targetPos.getX() + targetPos.getZ()) == 0) {
            throw KEY_NOT_FOUND.create("empty");
        } else {
            throw KEY_NOT_FOUND.create("invalid");
        }
    }

    private static int nearest(ServerCommandSource source, String l) throws CommandSyntaxException {
        Landform landform = Landforms.get(l);
        if (landform == null) {
            throw KEY_NOT_FOUND.create(l);
        }

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int size = grid.getSize();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        float dist = Float.MAX_VALUE;
        LandFormCell idx = grid.getLandforms().get(0);

        for (LandFormCell cell : grid.getLandforms()){


            if (landform == Landforms.get(cell.getConfig())){
                int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
                int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
                float newDist = Distance.euclidean.apply(dx, dz);
                if (dist > newDist){
                    dist = newDist;
                    idx = cell;
                }
            }
        }
        BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(idx.getX(), size),0,PosTranslator.gridToGlobal(idx.getZ(), size));


        if (chunkGenerator == null || (targetPos.getX() + targetPos.getZ()) == 0) {
            throw KEY_NOT_FOUND.create(landform.toString());
        } else {
            return sendCoordinates(source, blockPos, targetPos, landform.toString());
        }
    }

    private static int ecosystem(ServerCommandSource source, String l) throws CommandSyntaxException {
        Ecosystem ecosystem = Ecosystems.get(l);
        if (ecosystem == null) {
            throw KEY_NOT_FOUND.create(l);
        }

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int size = grid.getSize();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        float dist = Float.MAX_VALUE;
        EcoSystemCell idx = grid.getEcosystems().get(0);

        for (EcoSystemCell cell : grid.getEcosystems()){
            if (ecosystem == Ecosystems.get(cell.getConfig())){
                int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
                int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
                float newDist = Distance.euclidean.apply(dx, dz);
                if (dist > newDist){
                    dist = newDist;
                    idx = cell;
                }
            }
        }
        BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(idx.getX(), size),0,PosTranslator.gridToGlobal(idx.getZ(), size));


        if (chunkGenerator == null || (targetPos.getX() + targetPos.getZ()) == 0) {
            throw KEY_NOT_FOUND.create(ecosystem.toString());
        } else {
            return sendCoordinates(source, blockPos, targetPos, ecosystem.toString());
        }
    }

    private static int biome(ServerCommandSource source, String searchEntry) throws CommandSyntaxException {
        if (Biomes.get(searchEntry) == null) {
            throw new DynamicCommandExceptionType((id) -> Text.translatable("commands.locate.biomes.invalid", id)).create(null);
        }

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int size = grid.getSize();
        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();

        float dist = Float.MAX_VALUE;
        EcoSystemCell closestEcosystem = grid.getEcosystems().getFirst();

        for (EcoSystemCell cell : grid.getEcosystems()) {
            int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
            int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
            float newDist = Distance.euclidean.apply(dx, dz);
            if (dist > newDist) {
                dist = newDist;
                closestEcosystem = cell;
            }
        }

        dist = Float.MAX_VALUE;
        BiomeCell closestBiome = null;
        for (BiomeCell cell : closestEcosystem.getBiomes()){
            if (Objects.equals(cell.getBiome().toString(), searchEntry)) {
                int dx = PosTranslator.gridToGlobal(cell.getX(), size) - playerX;
                int dz = PosTranslator.gridToGlobal(cell.getZ(), size) - playerZ;
                float newDist = Distance.euclidean.apply(dx, dz);
                if (dist > newDist) {
                    dist = newDist;
                    closestBiome = cell;
                }
            }
        }

        if (closestBiome == null) {
            throw KEY_NOT_FOUND.create(searchEntry);
        } else {
            BlockPos targetPos = new BlockPos(PosTranslator.gridToGlobal(closestBiome.getX(), size),0,PosTranslator.gridToGlobal(closestBiome.getZ(), size));
            return sendCoordinates(source, blockPos, targetPos, searchEntry);
        }
    }

    private static int structure(ServerCommandSource source, String l) throws CommandSyntaxException {

        Structure structure = Structures.get(l);
        if (structure == null) {throw STRUCTURE_INVALID_EXCEPTION.create(l);}

        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        WRGChunkGenerator chunkGenerator = (WRGChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();
        WorldGrid grid = chunkGenerator.getWorld().getGrid();

        int playerX = source.getPlayer().getBlockX(), playerZ = source.getPlayer().getBlockZ();
        float dist = Float.MAX_VALUE;
        StructureSpawn pos = null;

        for (EcoSystemCell ecoSystemCell : grid.getEcosystems()){
            for (BiomeCell cell : ecoSystemCell.getBiomes()){
                for (StructureSpawn newPos : cell.getStructures()) {

                    if (structure.getKey() == newPos.getStructure().getKey()) {
                        int dx = newPos.getX() - playerX;
                        int dz = newPos.getZ() - playerZ;
                        float newDist = Distance.euclidean.apply(dx, dz);
                        if (dist > newDist) {
                            dist = newDist;
                            pos = newPos;
                        }
                    }

                }
            }
        }

        if (pos == null) {
            throw STRUCTURE_INVALID_EXCEPTION.create(structure.name);
        } else {
            BlockPos targetPos = new BlockPos(pos.getX(),0, pos.getZ());
            return sendCoordinates(source, blockPos, targetPos, structure.name);
        }
    }

    private static int sendCoordinates(ServerCommandSource source, BlockPos currentPos, BlockPos targetPos, String landform) {
        int i = MathHelper.floor(MathHelper.sqrt((float)currentPos.getSquaredDistance(targetPos)));
        int y = (int) source.getPosition().y;
        Text text = Texts.bracketed(Text.translatable("chat.coordinates", targetPos.getX(), y, targetPos.getZ())).styled((style) ->
                style.withColor(Formatting.GREEN)
                .withClickEvent(new ClickEvent() {
                    @Override
                    public Action getAction() {
                        return ClickEvent.Action.SUGGEST_COMMAND;
                        //"/tp @s " + targetPos.getX() + " ~ " + targetPos.getZ()
                    }
                })
                .withHoverEvent(new HoverEvent() {
                    @Override
                    public Action getAction() {
                        return HoverEvent.Action.SHOW_TEXT;
                    }
                    //, Text.translatable("chat.coordinates.tooltip"))
                })
        ).append(" " + landform + " with distance of " + i);
        source.sendFeedback(() -> text, false);
        WildsRegrown.LOGGER.info("Locating element " + landform + " for distance " + i);
        return i;
    }
}
