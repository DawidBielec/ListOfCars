package com.company;

import java.time.LocalDate;

public class Car {
    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public int getPojemnośćSilnika() {
        return pojemnośćSilnika;
    }

    public double getCena() {
        return cena;
    }

    private String marka;
    private String model;
    private int rokProdukcji;
    private int pojemnośćSilnika;
    private double cena;

    public int getPrzebieg() {
        return przebieg;
    }

    private int przebieg;
    public Car(String marka, String model, int rok, int pojemność, double cena ,int przebieg) {
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rok;
        this.pojemnośćSilnika = pojemność;
        this.cena = cena;
        this.przebieg = przebieg;
    }
    public Pair<String, Double> getPara() {
        Pair<String, Double> para = new Pair(this.toString(), przebieg/(LocalDate.now().getYear()- rokProdukcji+1));
        return para;
    }
    @Override
    public String toString() {
        return marka + " " + model + " " + rokProdukcji + " cena: " + cena;
    }
}