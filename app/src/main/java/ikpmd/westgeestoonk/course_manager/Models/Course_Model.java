package ikpmd.westgeestoonk.course_manager.Models;

import ikpmd.westgeestoonk.course_manager.Enums.Toetsing;

/**
 * Created by Joep Oonk on 25-Oct-17.
 */

public class Course_Model {
    private String naam;
    private int EC;
    private String vakcode;
    private Toetsing toetsing;
    private int periode;
    private String toetsmoment;
    private String cijfer;

    public Course_Model(String naam, int EC, String vakcode, Toetsing toetsing, int periode, String toetsmoment, String cijfer) {
        this.naam = naam;
        this.EC = EC;
        this.vakcode = vakcode;
        this.toetsing = toetsing;
        this.periode = periode;
        this.toetsmoment = toetsmoment;
        this.cijfer = cijfer;
    }

    public String getNaam() {
        return naam;
    }

    public int getEC() {
        return EC;
    }

    public String getVakcode() {
        return vakcode;
    }

    public Toetsing getToetsing() {
        return toetsing;
    }

    public int getPeriode() {
        return periode;
    }

    public String getToetsmoment() {
        return toetsmoment;
    }

    public String getCijfer() {
        return cijfer;
    }

    public void setCijfer(String cijfer) {
        this.cijfer = cijfer;
    }
}
