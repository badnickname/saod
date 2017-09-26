package ru.ress.coursework;

/**
 * Created by ress on 26.09.17.
 */
public class Crutch {

    static char toRussian(byte let) {
        if (let < -32 && let >= -128) {
            return (char)(let+1168);
        } else {
            if (let <= -17) {
                return (char)(1120+let);
            } else {
                return (char)let;
            }
        }
    }

    static int bytesToInt(byte a, byte b) {
        return (b << 8) + (a+256);
    }
}
