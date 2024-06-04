package com.ballesteros.api.utils;

import com.ballesteros.api.persistence.models.PlayerModel;

import java.util.Comparator;
/**
 * Comparador para jugadores basado en su posición.
 */
public class PositionComparator implements Comparator<PlayerModel> {
    /**
     * Compara dos jugadores por su posición.
     *
     * @param p1 el primer jugador
     * @param p2 el segundo jugador
     * @return un valor negativo si p1 es menor que p2, un valor positivo si p1 es mayor que p2, o 0 si son iguales
     */
    @Override
    public int compare(PlayerModel p1, PlayerModel p2) {
        String[] positions = {"FW", "MF", "DF", "GK"};
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < positions.length; i++) {
            if (positions[i].equalsIgnoreCase(String.valueOf(p1.getPosition()))) {
                index1 = i;
            }
            if (positions[i].equalsIgnoreCase(String.valueOf(p2.getPosition()))) {
                index2 = i;
            }
        }

        return Integer.compare(index1, index2);
    }
}
