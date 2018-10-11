package com.fitness.centrale.mobile.fragments.challenges;

import com.fitness.centrale.mobile.R;

public enum Machines {

    BIKE("bike", R.drawable.bikelogo),
    HELIPTIC("heliptic", R.drawable.elliptiquelogo),
    TRAKE("trake", R.drawable.tapis);

    public final String value;
    public final int drawable;

    Machines(String machine, int drawable) {
        this.value = machine;
        this.drawable = drawable;
    }

    public static Machines getMachineEnum(String name){
        name = name.toUpperCase();
        return Machines.valueOf(name);
    }
}
