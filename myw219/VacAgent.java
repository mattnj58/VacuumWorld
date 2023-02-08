package myw219;

import agent.*;
import vacworld.*;

public class VacAgent extends Agent{
    public void see(Percept p){
        if(!(p instanceof VacPercept)){
            System.out.println("Invalid Percept");
            return;
        }

        VacPercept state = (VacPercept)p;
        if(state.seeObstacle()){
            System.out.println("I see something");
        }

    }

    public Action selectAction(){

        return new TurnLeft();
    }

    public String getId(){
        return "Leeroy";
    }
}