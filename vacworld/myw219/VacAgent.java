package myw219;

import agent.*;
import vacworld.*;

import java.net.NoRouteToHostException;
import java.util.Random;

public class VacAgent extends Agent {

    boolean seeDirt = false;
    boolean seeObst = false;
    boolean shutDown = false;

    // Stack<String> history = new Stack<>();

    int[][] map = new int[19][19];
    int VISITED = 1;
    int OBSTACLE = 99;
    int DIRT = 11;
    int X = 9;
    int Y = 9;
    int direct = Direction.NORTH;
    int cellLimit = 4;
    
    int up = map[X-1][Y];
    int right = map[X][Y+1];
    int down = map[X+1][Y];
    int left = map[X][Y-1];
    int min = 0;

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

        // System.out.println("X: " + X);
        // System.out.println("Y: " + Y);

        int up = map[X-1][Y];
        int right = map[X][Y+1];
        int down = map[X+1][Y];
        int left = map[X][Y-1];
        min = Math.min(up, Math.min(right, Math.min(down, left)));

        System.out.println("Up: " + up + " right: " + right + " down: " + down + " left " + left);
        System.out.println("min: " + min);

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
            map[X][Y] = 0;
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
        // Random rand = new Random();
        // int prob;

        if(seeObst){
            seeObst = false;

            Action turn = seeObject(map);

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

            return turn;
        } else if(seeDirt){
            seeDirt = false;
            // history.push("SUCK DIRT");
            return new SuckDirt();
        } else if(shutDown){
            return new ShutOff();
        } else {

            Action explore = checkSurround(map);

            // System.out.println("Explore " + explore.toString());

            // switch(direct){
            //     case Direction.NORTH:
            //         X-=1;
            //         break;
            //     case Direction.EAST:
            //         Y+=1;
            //         break;
            //     case Direction.SOUTH:
            //         X+=1;
            //        break;
            //     case Direction.WEST:
            //         Y-=1;
            //         break;
            // }

            if(explore.toString()=="GO FORWARD"){
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
            }

            return explore;
        }
    }

    public String getId(){
        return "myw219";
    }

    public Action checkSurround(int[][] map){
        Action decision = new GoForward();
        // int up = map[X-1][Y];
        // int right = map[X][Y+1];
        // int down = map[X+1][Y];
        // int left = map[X][Y-1];

        Random rand = new Random();
        int prob = rand.nextInt(2);

        // if(up == right && right == down && down == left && left == up){
        //     decision = new GoForward();
        // } else {
        //     switch(direct){
        //         case Direction.NORTH:
        //             if(min == Direction.NORTH){
        //                 decision = new GoForward();
        //             } else if(min==Direction.EAST){
        //                 decision = new TurnRight();
        //                 direct = Direction.EAST;
        //             } else if(min == Direction.SOUTH){
        //                 decision = new TurnLeft();
        //                 direct = Direction.WEST;
        //             } else{
        //                 decision = new TurnLeft();
        //                 direct = Direction.WEST;
        //             }
        //         case Direction.EAST:
        //             if(min == Direction.NORTH){
        //                 decision = new TurnLeft();
        //                 direct = Direction.NORTH;
        //             } else if(min == Direction.EAST){
        //                 decision = new GoForward();
        //             } else if(min == Direction.SOUTH){
        //                 decision = new TurnRight();
        //                 direct = Direction.SOUTH;
        //             } else {
        //                 decision = new TurnRight();
        //                 direct = Direction.SOUTH;
        //             }
        //             break;
        //         case Direction.SOUTH:
        //             if(min == Direction.NORTH){
        //                 decision = new TurnLeft();
        //                 direct = Direction.NORTH;
        //             } else if(min == Direction.EAST){
        //                 decision = new TurnLeft();
        //                 direct = Direction.EAST;
        //             } else if(min == Direction.SOUTH){
        //                 decision = new GoForward();
        //             } else {
        //                 decision = new TurnRight();
        //                 direct = Direction.WEST;
        //             }
        //             break;
        //         default:
        //             if(min == Direction.NORTH){
        //                 decision = new TurnRight();
        //                 direct = Direction.NORTH;
        //             } else if(min == Direction.EAST){
        //                 decision = new TurnRight();
        //                 direct = Direction.NORTH;
        //             } else if(min == Direction.SOUTH){
        //                 decision = new TurnLeft();
        //                 direct = Direction.SOUTH;
        //             } else {
        //                 decision = new GoForward();
        //             }
        //     }
        // }

        // System.out.println("Check Surroundings: " + decision.toString());

        // return decision;

        switch(direct){
            case Direction.NORTH:
                if(left==right && left<up){
                    if(prob==1){
                        decision = new TurnRight();
                        direct = Direction.EAST;
                    } else {
                        System.out.println("left turn");
                        decision = new TurnLeft();
                        direct = Direction.WEST;
                    }
                } else if(left>right){
                    if(right<up){
                        decision = new TurnRight();
                        direct = Direction.EAST;
                    }
                } else if(left<right){
                    if(left<up){
                        decision = new TurnLeft();
                        direct = Direction.WEST;
                    }
                } else {
                    decision = new GoForward();
                }
                break;
            case Direction.EAST:
                if(up==down && up<right){
                    if(prob==1){
                        decision = new TurnRight();
                        direct = Direction.SOUTH;
                    } else {
                        decision = new TurnLeft();
                        direct = Direction.NORTH; 
                    }
                } else if(up>down){
                    if(down<right){
                        decision = new TurnRight();
                        direct = Direction.SOUTH;
                    }
                } else if(down>up){
                    if(up<right){
                        decision = new TurnLeft();
                        direct = Direction.NORTH;
                    }
                } else {
                    decision = new GoForward();
                }
                break;
            case Direction.SOUTH:
                if(left==right && left<down){
                    if(prob==1){
                        decision = new TurnRight();
                        direct = Direction.WEST;
                    } else {
                        decision = new TurnLeft();
                        direct = Direction.EAST;
                    }
                } else if(left>right){
                    if(right<down){
                        decision = new TurnLeft();
                        direct = Direction.EAST;
                    }
                } else if(left<right){
                    if(left<down){
                        decision = new TurnRight();
                        direct = Direction.WEST;
                    }
                } else {
                    decision = new GoForward();
                }
                break;
            default:
                if(up==down && up<left){
                    if(prob==1){
                        decision = new TurnRight();
                        direct = Direction.SOUTH;
                    } else {
                        decision = new TurnLeft();
                        direct = Direction.NORTH; 
                    }
                } else if(down>up){
                    if(up<left){
                        decision = new TurnRight();
                        direct = Direction.NORTH;
                    }
                } else if(up>down){
                    if(down<left){
                        decision = new TurnLeft();
                        direct = Direction.SOUTH;
                    }
                } else {
                    decision = new GoForward();
                }
        }
        return decision;
    }

    // seed 166 fails
    public Action seeObject(int[][] map){
        Action decision;
        int up = map[X-1][Y];
        int right = map[X][Y+1];
        int down = map[X+1][Y];
        int left = map[X][Y-1];

        // int[] change;

        Random rand = new Random();
        int picker = rand.nextInt(2); 
        // System.out.println("Picker:" + picker);

        switch(direct){
            case Direction.NORTH:
                // change = new int[]{right, left};
                // if(change[picker] == OBSTACLE){
                //     System.out.println("Equality: " + history.pop().toString().equals("Turn Left"));
                //     System.out.println("Empty: " + history.peek());
                //     if(history.peek().toString().equals("Turn Left")){
                //         decision = new TurnLeft();
                //         direct = Direction.WEST;
                //     }

                //     decision = new TurnLeft();
                //     direct = Direction.WEST;
                // } else {
                //     if(history.peek().toString().equals("Turn Right")){
                //         decision = new TurnRight();
                //         direct = Direction.EAST;
                //     }
                //     decision = new TurnRight();
                //     direct = Direction.EAST;
                // }

                if(right>left){
                    decision = new TurnLeft();
                    direct = Direction.WEST;
                } else if(left>right) {
                    decision = new TurnRight();
                    direct = Direction.EAST;
                } else {
                    if(picker==1){
                        decision = new TurnLeft();
                        direct = Direction.WEST;
                    } else {
                        decision = new TurnRight();
                        direct = Direction.EAST;
                    }
                }
                break;
                
            case Direction.EAST:
                // change = new int[]{up, down};
                // if(change[picker] == OBSTACLE){
                //     if(history.peek().toString().equals("Turn Left")){
                //         decision = new TurnLeft();
                //         direct = Direction.NORTH; 
                //     }
                //     decision = new TurnLeft();
                //     direct = Direction.NORTH; 
                // } else {
                //     if(history.peek().toString().equals("Turn Right")){                
                //         decision = new TurnRight();
                //         direct = Direction.SOUTH;
                //     }
                //     decision = new TurnRight();
                //     direct = Direction.SOUTH;
                // }
                if(up>down){
                    decision = new TurnRight();
                    direct = Direction.SOUTH;
                } else if(down>up){
                    decision = new TurnLeft();
                    direct = Direction.NORTH;
                } else {
                    if(picker == 1){
                        decision = new TurnRight();
                        direct = Direction.SOUTH;
                    } else {
                        decision = new TurnLeft();
                        direct = Direction.NORTH;
                    }
                }
                break;
            case Direction.SOUTH:
                // change = new int[]{right, left};
                // if(change[picker] == OBSTACLE){
                //     if(history.peek().toString().equals("Turn Right")){
                //         decision = new TurnRight();
                //         direct = Direction.WEST;
                //     }
                //     decision = new TurnRight();
                //     direct = Direction.WEST;
                // } else {
                //     if(history.peek().toString().equals("Turn Left")){
                //         decision = new TurnLeft();
                //         direct = Direction.EAST;
                //     }
                //     decision = new TurnLeft();
                //     direct = Direction.EAST;
                // }

                if(right>left){
                    decision = new TurnLeft();
                    direct = Direction.EAST;
                } else if(left>right){
                    decision = new TurnRight();
                    direct = Direction.WEST;
                } else {
                    if(picker == 1){
                        decision = new TurnLeft();
                        direct = Direction.EAST;
                    } else {
                        decision = new TurnRight();
                        direct = Direction.WEST;
                    }
                }
                break;
            case Direction.WEST:
                // change = new int[]{up, down};
                // if(change[picker] == OBSTACLE){
                //     if(history.peek().toString().equals("Turn Right")){
                //         decision = new TurnRight();
                //         direct = Direction.NORTH;
                //     }
                //     decision = new TurnRight();
                //     direct = Direction.NORTH;
                // } else {
                //     if(history.peek().toString().equals("Turn Left")){
                //         decision = new TurnLeft();
                //         direct = Direction.SOUTH;
                //     }
                //     decision = new TurnLeft();
                //     direct = Direction.SOUTH;
                // }

                if(up>down){
                    decision = new TurnLeft();
                    direct = Direction.SOUTH;
                } else if(down>up){
                    decision = new TurnRight();
                    direct = Direction.NORTH;
                } else {
                    if(picker==1){
                        decision = new TurnLeft();
                        direct = Direction.SOUTH;
                    } else {
                        decision = new TurnRight();
                        direct = Direction.NORTH;
                    }
                }
                break;
            default:
                decision = new TurnLeft();
        }
        
        // System.out.println("something: " + decision.toString());
        // history.push(decision.toString());
        // System.out.println("history: "  + history.peek());
        return decision;
    }
}