/*
 * Decompiled with CFR 0.139.
 */
package com.mayakplay.cscase;

import java.awt.Color;

public class ColorHelper {
    public static Color getColorByRare(int rarity) {
        Color color = Color.BLUE;
        switch (rarity) {
            case 1: {
                color = new Color(37, 59, 179);
                break;
            }
            case 2: {
                color = new Color(75, 0, 108);
                break;
            }
            case 3: {
                color = new Color(186, 0, 130);
                break;
            }
            case 4: {
                color = new Color(150, 0, 14);
                break;
            }
            case 5: {
                color = new Color(234, 207, 0);
            }
        }
        return color;
    }
}

