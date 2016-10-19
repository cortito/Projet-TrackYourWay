package fr.trackyourway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bab on 19/10/16.
 */

public class TeamObject {

    private final String teamName;
    private final List<RunnerObject> runners = new ArrayList<>();

    public TeamObject(String teamName) {
        this.teamName = teamName;
        for (RunnerObject r : runners){
            r.setTeamName(this.teamName);
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public List<RunnerObject> getRunners() {
        return runners;
    }

    public void addRunner(RunnerObject r){
        runners.add(r);
    }
}
