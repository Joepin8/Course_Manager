package ikpmd.westgeestoonk.course_manager.Models;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Enums.Toetsing;

/**
 * Created by Joep Oonk on 25-Oct-17.
 */

public class Course_Model {
    private String naam;
    private int ec;
    private String vakcode;
    private Toetsing toetsing;
    private int periode;
    private String toetsmoment;
    private String cijfer;
    private int jaar;

    private DatabaseHelper databaseHelper;

    public Course_Model(@NotNull String naam, int ec, String vakcode, String toetsing, int periode, String toetsmoment, String cijfer, int jaar) {
        this.naam = naam;
        this.ec = ec;
        this.vakcode = vakcode;
        this.toetsing = Toetsing.valueOf(toetsing);
        this.periode = periode;
        this.toetsmoment = toetsmoment;
        this.cijfer = cijfer;
        this.jaar = jaar;
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

    public int getPeriode() {
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


    public int getJaar() {
        return jaar;
    }
}
