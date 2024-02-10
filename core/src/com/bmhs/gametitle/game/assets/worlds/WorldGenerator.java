package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, lightGreen, Green;

    public WorldGenerator(int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        seedColor = 2;
        lightGreen = 17;
        //lightGreen =


        //call methods to build 2D array
        sea();
        seedMap();
        searchAndExpand(10, seedColor, lightGreen, 0.99);
        searchAndExpand(8, seedColor, 18, 0.99);
        searchAndExpand(6, seedColor, 19, 0.85);
        searchAndExpand(5, seedColor, 20, 0.55);
        searchAndExpand(4, seedColor, 21, 0.25);




        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.print(mapSeed.y + " " + mapSeed.x);

        worldIntMap[(int) mapSeed.y][(int) mapSeed.x] = 4;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                Vector2 tempVector = new Vector2(c, r);
                {
                    if (tempVector.dst(mapSeed) < 10) {
                        worldIntMap[r][c] = 7;
                    }
                }

            }

            randomize();
            generateWorld();

            //Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
        }
    }

    private void seedIslands(int num) {
        for (int i = 0; i < num; i++) {
            int rSeed = MathUtils.random(worldIntMap.length - 1);
            int cSeed = MathUtils.random(worldIntMap[0].length - 1);
            worldIntMap[rSeed][cSeed] = 24;
        }
    }

//    private void greenDots(int numToFind) {
//        if ()
//    }

    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == seedColor) {

                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {

                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != numToFind) {
                                if (Math.random() < probability) {
                                    worldIntMap[subRow][subCol] = numToWrite;
                                }
                            }
                        }
                    }
                }
            }


        }

    }

    private void generateIsland(int[][] islandMap, int islandSize, int numClusters) {
        int islandWidth = islandMap.length;
        int islandHeight = islandMap[0].length;
        double irregularityFactor = 0.2; // Adjust this factor to control irregularity

        // Generate multiple clusters of land tiles
        for (int cluster = 0; cluster < numClusters; cluster++) {
            int centerX = (int) (Math.random() * islandWidth);
            int centerY = (int) (Math.random() * islandHeight);
            int maxDist = islandSize / 2; // Maximum distance from the center of the cluster

            // Generate land tiles for the cluster
            for (int x = 0; x < islandWidth; x++) {
                for (int y = 0; y < islandHeight; y++) {
                    // Calculate the distance from the cluster center
                    double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

                    // Adjust the irregularity factor based on distance
                    double adjustedFactor = irregularityFactor * (1 - distance / maxDist);

                    // Add or remove land tiles based on irregularity factor and randomness
                    if (distance < maxDist) {
                        double randomness = Math.random();

                        if (randomness < adjustedFactor) {
                            islandMap[x][y] = 16; // Green land tile
                        }
                    }
                }
            }
        }
    }

    private void searchAndExpand(int radius) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == seedColor) {

                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {

                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != seedColor)
                                worldIntMap[subRow][subCol] = 3;
                        }
                    }
                }
            }


        }

    }
        public String getWorld3DArrayToString() {
            String returnString = "";

            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    returnString += worldIntMap[r][c] + " ";
                }
                returnString += "\n";
            }

            return returnString;
        }

    public void sea(){
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = 27;
            }
        }
    }

    public void seedMap() {
        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                Vector2 tempVector = new Vector2(c,r);
                if(tempVector.dst(mapSeed) < 10) {
                    worldIntMap[r][c] = seedColor;

                }
            }
        }
    }


        public void randomize () {
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size - 1);
                }
            }
        }

        public WorldTile[][] generateWorld () {
            WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
                }
            }
            return worldTileMap;
        }
        private void generateWorldTextFile () {
            FileHandle file = Gdx.files.local("assets/worlds/world.txt");
            file.writeString(getWorld3DArrayToString(), false);
        }
}


//    private void generateWorldTextFile() {
//    FileHandle
//
