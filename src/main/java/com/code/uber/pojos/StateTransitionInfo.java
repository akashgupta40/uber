package com.code.uber.pojos;


import com.code.uber.enums.StateType;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString;


@ToString
public class StateTransitionInfo {

    private final int ordinal;
    private final boolean isTerminalState;
    private final StateType stateType;
    private final String version;
    private String stateName;
    private List<String> jumpStates;

    public StateTransitionInfo(int ordinal){
        this(ordinal, false);
    }

    public StateTransitionInfo(int ordinal, boolean isTerminalState) {
        this.ordinal = ordinal;
        this.isTerminalState = isTerminalState;
        if(ordinal == 0){
            this.stateType = StateType.START;
        } else if (isTerminalState) {
            this.stateType = StateType.TERMINAL;
        } else {
            this.stateType = StateType.TRANSIT;
        }
        this.version = "1.0";
        this.jumpStates = new ArrayList<>();
    }

    public StateTransitionInfo(int ordinal, boolean isTerminalState, List<String> jumpStates) {
        this(ordinal, isTerminalState);
        this.jumpStates = jumpStates;
    }

    public StateTransitionInfo(int ordinal, List<String> jumpStates) {
        this(ordinal);
        this.jumpStates = jumpStates;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public boolean isTerminalState() {
        return isTerminalState;
    }

    public StateType getStateType() {
        return stateType;
    }

    public String getVersion() {
        return version;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<String> getJumpStates() {
        return jumpStates;
    }

    public void setJumpStates(List<String> jumpStates) {
        this.jumpStates = jumpStates;
    }
}
