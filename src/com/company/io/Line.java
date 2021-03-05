package com.company.io;

import com.company.io.Points;
import javafx.scene.effect.Light;

import java.io.Serializable;

public class Line implements Serializable {
    private static final long serialVersionUID = -1892561327013038124L;

    private Points p1;
    private Points p2;

    transient private String secretCode = "secret";

    public Line(Points p1, Points p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Points getP1() {
        return p1;
    }

    public void setP1(Points p1) {
        this.p1 = p1;
    }

    public Points getP2() {
        return p2;
    }

    public void setP2(Points p2) {
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return "Line{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
