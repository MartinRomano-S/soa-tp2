package com.example.testlogin.models;

import com.example.testlogin.interfaces.JSONable;

import org.json.JSONException;
import org.json.JSONObject;

public class TestAnswers implements JSONable {

    double temperaturaCorporal;
    private boolean olfato, gusto, tos, dolorGarganta, dificultadRespiratoria, dolorCabeza, diarrea, vomitos, dolorMuscular;

    public TestAnswers(){}

    public TestAnswers(float temperaturaCorporal, boolean olfato, boolean gusto, boolean tos, boolean dolorGarganta, boolean dificultadRespiratoria, boolean dolorCabeza, boolean diarrea, boolean vomitos, boolean dolorMuscular) {
        this.temperaturaCorporal=temperaturaCorporal;
        this.olfato = olfato;
        this.gusto = gusto;
        this.tos= tos;
        this.dolorGarganta = dolorGarganta;
        this.dificultadRespiratoria = dificultadRespiratoria;
        this.dolorCabeza=dolorCabeza;
        this.diarrea=diarrea;
        this.vomitos= vomitos;
        this.dolorMuscular=dolorMuscular;
    }

    public double getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public void setTemperaturaCorporal(double temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public boolean isOlfato() {
        return olfato;
    }

    public void setOlfato(CharSequence answer) {
        this.olfato=convertAnswerToBool(answer);
    }

    public boolean isGusto() {
        return gusto;
    }

    public void setGusto(CharSequence answer) {
        this.gusto=convertAnswerToBool(answer);
    }

    public boolean isTos() {
        return tos;
    }

    public void setTos(CharSequence answer) {
        this.tos=convertAnswerToBool(answer);
    }

    public boolean isDolorGarganta() {
        return dolorGarganta;
    }

    public void setDolorGarganta(CharSequence answer) {
        this.dolorGarganta=convertAnswerToBool(answer);
    }

    public boolean isDificultadRespiratoria() {
        return dificultadRespiratoria;
    }

    public void setDificultadRespiratoria(CharSequence answer) {
        this.dificultadRespiratoria=convertAnswerToBool(answer);
    }

    public boolean isDolorCabeza() {
        return dolorCabeza;
    }

    public void setDolorCabeza(CharSequence answer) {
        this.dolorCabeza=convertAnswerToBool(answer);
    }

    public boolean isDiarrea() {
        return diarrea;
    }

    public void setDiarrea(CharSequence answer) {
        this.diarrea=convertAnswerToBool(answer);
    }

    public boolean isVomitos() {
        return vomitos;
    }

    public void setVomitos(CharSequence answer) {
        this.vomitos=convertAnswerToBool(answer);
    }

    public boolean isDolorMuscular() {
        return dolorMuscular;
    }

    public void setDolorMuscular(CharSequence answer) {
        this.dolorMuscular=convertAnswerToBool(answer);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        try {
            json.put("olfato", isOlfato());
            json.put("gusto", isGusto());
            json.put("tos", isTos());
            json.put("dolorGarganta", isDolorGarganta());
            json.put("dificultadRespiratoria", isDificultadRespiratoria());
            json.put("dolorCabeza", isDolorCabeza());
            json.put("diarrea", isDiarrea());
            json.put("vomitos", isVomitos());
            json.put("dolorMuscular", isDolorMuscular());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public void getFromJSON(JSONObject jsonObject) {

    }

    public boolean convertAnswerToBool(CharSequence answer){
        if ("SI".equals(answer)) {
            return true;
        } else if ("NO".equals(answer)) {
            return false;
        }
        return false;
    }
}
