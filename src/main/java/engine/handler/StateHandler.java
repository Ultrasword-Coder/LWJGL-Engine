package engine.handler;

import engine.utils.State;

import java.util.Queue;

public class StateHandler {

    public static Queue<State> states;
    public static State currentState;

    public static <T extends State> void pushState(T state){
        currentState = state;
        states.add(state);
    }

}
