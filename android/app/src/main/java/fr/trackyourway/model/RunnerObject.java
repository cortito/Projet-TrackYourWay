package fr.trackyourway.model;

/**
 * Created by bab on 19/10/16.
 */

public class RunnerObject {

    private final int idBib;
    private double latitude;
    private double longitude;
    private String teamName;

    public RunnerObject(int idBib, double latitude, double longitude, String teamName) {
        this.idBib = idBib;
        this.latitude = latitude;
        this.longitude = longitude;
        this.teamName = teamName;
    }

    public int getIdBib() {
        return idBib;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getInfo(){
        return "Id : " + idBib + "\n" +
                " Team : " +teamName;
    }
}
