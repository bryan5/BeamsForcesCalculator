package edu.self.bryan.beams_forcescalculator;

public class BeamListItem {

    private int id;
    private String beamType;
    private int imageID;

    public BeamListItem(int id, String beamType, int imageID) {
        this.id = id;
        this.beamType = beamType;
        this.imageID = imageID;
    }

    public int getId() {
        return id;
    }

    public String getBeamType() {
        return beamType;
    }

    public int getImageID() {
        return imageID;
    }
}
