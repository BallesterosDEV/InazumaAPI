package com.ballesteros.api.utils;

import com.ballesteros.api.persistence.models.PlayerModel;

import java.util.Comparator;

/**
 * Comparador para jugadores basado en su elemento.
 */
public class ElementComparator implements Comparator<PlayerModel> {
    /**
     * Compara dos jugadores por su elemento.
     *
     * @param p1 el primer jugador
     * @param p2 el segundo jugador
     * @return un valor negativo si p1 es menor que p2, un valor positivo si p1 es mayor que p2, o 0 si son iguales
     */
    @Override
    public int compare(PlayerModel p1, PlayerModel p2) {
        String[] elements = {"FIRE", "EARTH", "WOOD", "WIND"};
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equalsIgnoreCase(String.valueOf(p1.getElement()))) {
                index1 = i;
            }
            if (elements[i].equalsIgnoreCase(String.valueOf(p2.getElement()))) {
                index2 = i;
            }
        }

        return Integer.compare(index1, index2);
    }
}
