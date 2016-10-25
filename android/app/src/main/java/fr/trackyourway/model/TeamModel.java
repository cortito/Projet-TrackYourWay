package fr.trackyourway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bab on 19/10/16.
 */

public class TeamModel {

    private final String teamName;
    private final List<RunnerModel> runners = new ArrayList<>();

    public TeamModel(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<RunnerModel> getRunners() {
        return runners;
    }

    public void addRunner(RunnerModel r){
        runners.add(r);
    }
}
