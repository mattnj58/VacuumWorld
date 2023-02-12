package myw219;

import agent.*;
import vacworld.*;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

public class VacAgent extends Agent {

    boolean seeDirt = false;
    boolean seeObst = false;
    boolean shutDown = false;

    int[][] map = new int[19][19];
    int VISITED = 1;
    int OBSTACLE = 99;
    int DIRT = 3;
    int X = 10;
    int Y = 10;
    int direct = Direction.NORTH;
    int cellLimit = 10;

    // VacuumState state = new VacuumState(map);

    // int X = state.getAgentX();
    // int Y = state.getAgentY();
    // int direct = state.getAgentDir();

    // bool state = false;

    public void see(Percept p){

        VacPercept agent = (VacPercept)p;

        // int X = state.getAgentX();
        // int Y = state.getAgentY();
        // int direct = state.getAgentDir();

        System.out.println("X: " + X);
        System.out.println("Y: " + Y);

        if(agent.seeObstacle()){
            seeObst = agent.seeObstacle();
            // direct = state.getAgentDir();
            // X = state.getAgentX();
            // Y = state.getAgentY();
            switch(direct){
                case Direction.NORTH: 
                    map[X-1][Y] = OBSTACLE;
                    break;
                case Direction.EAST:
                    map[X][Y+1] = OBSTACLE;
                    break;
                case Direction.SOUTH:
                    map[X+1][Y] = OBSTACLE;
                    break;
                case Direction.WEST:
                    map[X][Y-1] = OBSTACLE;
            }
        } else if(agent.seeDirt()){
            seeDirt = agent.seeDirt();
            map[X][Y] = DIRT;
        } else {
            map[X][Y] += VISITED;
            if(map[X][Y] == cellLimit){
                shutDown = true;
            }
        }

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public Action selectAction(){
        Random rand = new Random();
        int prob;
        if(seeObst){
            seeObst = false;

            Action something = checkSurroundings(map);

            // prob = rand.nextInt()%2;
            // if(prob==1){
            //     switch(direct){
            //         case Direction.NORTH:
            //             direct = Direction.WEST;
            //             break;
            //         case Direction.EAST:
            //             direct = Direction.NORTH;
            //             break;
            //         case Direction.SOUTH:
            //             direct = Direction.EAST;
            //             break;
            //         default:
            //             direct = Direction.SOUTH;
            //     }
            //     // history.push("left");
            //     return new TurnLeft();
            // } else {
            //     switch(direct){
            //         case Direction.NORTH:
            //             direct = Direction.EAST;
            //             break;
            //         case Direction.EAST:
            //             direct = Direction.SOUTH;
            //             break;
            //         case Direction.SOUTH:
            //             direct = Direction.WEST;
            //             break;
            //         default:
            //             direct = Direction.NORTH;
            //     }
            //     // history.push("right");
            //     return new TurnRight();
            // }
            return something;
        } else if(seeDirt){
            seeDirt = false;
            // history.push("dirt");
            return new SuckDirt();
        } else if(shutDown){
            return new ShutOff();
        } else {
            switch(direct){
                case Direction.NORTH:
                    X-=1;
                    break;
                case Direction.EAST:
                    Y+=1;
                    break;
                case Direction.SOUTH:
                    X+=1;
                   break;
                case Direction.WEST:
                    Y-=1;
                    break;
            }
            // history.push("forward");
            return new GoForward();
        }
    }

    public String getId(){
        return "myw219";
    }

    public Action checkSurroundings(int[][] map){
        Action decision;
        switch(direct){
            case Direction.NORTH: 
                if(map[X][Y-1] == OBSTACLE){
                    decision = new TurnLeft();
                    direct = Direction.WEST;
                } else {
                    decision = new TurnRight();
                    direct = Direction.EAST;
                }
                break;
            case Direction.EAST:
                System.out.println("map: " + map[X+1][Y]);
                if(map[X+1][Y] == OBSTACLE){
                    decision = new TurnLeft();
                    direct = Direction.NORTH; 
                } else {
                    decision = new TurnRight();
                    direct = Direction.SOUTH;
                }
                break;
            case Direction.SOUTH:
                if(map[X][Y+1] == OBSTACLE){
                    decision = new TurnRight();
                    direct = Direction.WEST;
                } else {
                    decision = new TurnLeft();
                    direct = Direction.EAST;
                }
                break;
            case Direction.WEST:
                if(map[X+1][Y] == OBSTACLE){
                    decision = new TurnRight();
                    direct = Direction.NORTH;
                } else {
                    decision = new TurnLeft();
                    direct = Direction.SOUTH;
                }
                break;
            default:
                decision = new TurnLeft();
        }
        
        return decision;
    }
}