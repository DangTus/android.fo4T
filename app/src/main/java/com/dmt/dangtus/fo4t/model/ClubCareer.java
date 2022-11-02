package com.dmt.dangtus.fo4t.model;

public class ClubCareer {
    private int id;
    private int idPlayerFootball;
    private Club club;
    private String yearStart;
    private String yearFinal;
    private boolean lease;

    public ClubCareer() {
    }

    public ClubCareer(int id, int idPlayerFootball, Club club, String yearStart, String yearFinal, boolean lease) {
        this.id = id;
        this.idPlayerFootball = idPlayerFootball;
        this.club = club;
        this.yearStart = yearStart;
        this.yearFinal = yearFinal;
        this.lease = lease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlayerFootball() {
        return idPlayerFootball;
    }

    public void setIdPlayerFootball(int idPlayerFootball) {
        this.idPlayerFootball = idPlayerFootball;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getYearStart() {
        return yearStart;
    }

    public void setYearStart(String yearStart) {
        this.yearStart = yearStart;
    }

    public String getYearFinal() {
        return yearFinal;
    }

    public void setYearFinal(String yearFinal) {
        this.yearFinal = yearFinal;
    }

    public boolean isLease() {
        return lease;
    }

    public void setLease(boolean lease) {
        this.lease = lease;
    }
}
