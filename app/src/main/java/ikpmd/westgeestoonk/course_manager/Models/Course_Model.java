package ikpmd.westgeestoonk.course_manager.Models;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Enums.Toetsing;


public class Course_Model implements Serializable {
    private String naam;
    private int ec;
    private String vakcode;
    private Toetsing toetsing;
    private String periode;
    private String toetsmoment;
    private String cijfer;
    private String jaar;
    private int keuzevak;
    private String notitie = "";

    private DatabaseHelper databaseHelper;

    public Course_Model(String naam, int ec, String vakcode, String toetsing, String periode, String toetsmoment, String cijfer, String jaar, int keuzevak) {
        this.naam = naam;
        this.ec = ec;
        this.vakcode = vakcode;
        this.toetsing = Toetsing.valueOf(toetsing);
        this.periode = periode;
        this.toetsmoment = toetsmoment;
        this.cijfer = cijfer;
        this.jaar = jaar;
        this.keuzevak = keuzevak;
    }

    public Course_Model(@NotNull String naam, int ec, String vakcode, String toetsing, String periode, String toetsmoment, String cijfer, String jaar, int keuzevak, String notitie) {
        this.naam = naam;
        this.ec = ec;
        this.vakcode = vakcode;
        this.toetsing = Toetsing.valueOf(toetsing);
        this.periode = periode;
        this.toetsmoment = toetsmoment;
        this.cijfer = cijfer;
        this.jaar = jaar;
        this.keuzevak = keuzevak;
        this.notitie = notitie;
    }

    public String getNaam() {
        return naam;
    }

    public int getEC() {
        return ec;
    }

    public String getVakcode() {
        return vakcode;
    }

    public Toetsing getToetsing() {
        return toetsing;
    }

    public String getPeriode() {
        return periode;
    }

    public String getToetsmoment() {
        return toetsmoment;
    }

    public String getCijfer() {
        return cijfer;
    }

    public void setCijfer(String cijfer, Context ctx, String uid) {
        this.cijfer = cijfer;
        databaseHelper = databaseHelper.getHelper(ctx, uid);
        databaseHelper.updateCijfer(this);
    }

    public String getJaar() {
        return this.jaar;
    }

    public Boolean isKeuzeVak() {
        return keuzevak == 0;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie, Context ctx, String uid) {
        this.notitie = notitie;
        databaseHelper = databaseHelper.getHelper(ctx, uid);
        databaseHelper.updateNotitie(this);
    }

    public boolean isGehaald() {

        if(this.cijfer.toLowerCase().equals("geen cijfer")){
            return false;
        } else if(this.cijfer.toLowerCase().equals("onvoldoende")) {
            return false;
        } else if(this.cijfer.toLowerCase().equals("voldoende")) {
            return true;
        } else {
            double cijfer = Double.parseDouble(this.cijfer);
            if(cijfer >= 5.5) {
                return true;
            } else {
                return false;
            }
        }
    }

}
