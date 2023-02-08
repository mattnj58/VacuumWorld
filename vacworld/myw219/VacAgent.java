package myw219;

import agent.*;
import vacworld.*;

public class VacAgent extends Agent{

    boolean seeDirt = false;
    boolean seeObst = false;
    // bool something = false;

    public void see(Percept p){
        if(!(p instanceof VacPercept)){
            System.out.println("Invalid Percept");
            return;
        }

        VacPercept state = (VacPercept)p;
        if(state.seeObstacle()){
            System.out.println("I see something");
            seeObst = true;
        } else if(state.seeDirt()){
            seeDirt = state.seeDirt();
        }

    }

    public Action selectAction(){
        if(seeObst == true){
            seeObst = false;
            return new TurnLeft();
        } else if(seeDirt == true){
            // Action suck = new SuckDirt();
            System.out.println("I see the dirt");
            seeDirt = false;
            return new SuckDirt();
        } else {
            return new GoForward();
        }
    }

    public String getId(){
        return "Leeroy";
    }
}