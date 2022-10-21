package com.texoit.grafilmes.enums;

/**
 * @author Carlos Lima on 21/10/2022
 */
public enum EnIndicated {

    YES("yes"),
    NO("no");

    EnIndicated(final String indicated) {
        this.indicated = indicated;
    }

    private final String indicated;

    public String getIndicated() {
        return indicated;
    }
}
