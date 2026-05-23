package blocksworld;

import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import bwmodel.*;
import bwui.*;

import modelling.*;
import planning.Action;

public class BlocksWorldDisplayer {
    
    
    private BlocksWorldVariables blocksWorld;
    private int n;
    private BWIntegerGUI gui;
    private BWComponent<Integer> component;
    private JFrame frame;
    private String plannerType;

    public BlocksWorldDisplayer(int n, BlocksWorldVariables blocksWorld,String plannerType){
        this.n= n;
        this.blocksWorld = blocksWorld;
        this.gui = new BWIntegerGUI(n);
        this.frame = new JFrame("BlocksWorld Actions Plan for : " + plannerType);
        this.plannerType = plannerType;
    }

    // Construitre les etats pour le graphique
    public BWState<Integer> bwState(Map<Variable, Object> state){
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = blocksWorld.onVars.get(b); // get instance of Variable for "on_b"
            int under = (int) state.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        return builder.getState();
    }

    // Affiche le graphique
    public void displayState(Map<Variable, Object> state){
        BWState<Integer> bwState = bwState(state);
        if(this.component == null) {
            this.component = gui.getComponent(bwState);
        }else{
            this.component.setState(bwState);
        }
        
        frame.add(component);
        frame.pack();
        frame.setSize(900,550);
        frame.setVisible(true);
    }


    //Permet la visualisation graphique des plan trouvés
    public void showPlan(Map<Variable, Object> initialState, List<Action> plan){

        System.out.println("------- SIMULATION OF "+plannerType+" --------");
        
        this.displayState(initialState);

        Map<Variable,Object> state = initialState;
        for (Action a: plan) {
            try { Thread.sleep(1_100); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state=a.successor(state);
            component.setState(bwState(state));
        }
        System.out.println("Simulation of plan: done.");
       
    }

 
}


