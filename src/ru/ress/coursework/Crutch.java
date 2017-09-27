package ru.ress.coursework;

/**
 * Created by ress on 26.09.17.
 */
public class Crutch {

    static char toRussian(byte let) {
        if (let < -32 && let >= -128) {
            return (char)(let+1168);
        }
        if (let <= -17) {
            return (char)(1120+let);
        }
        return (char)let;
    }

    static int bytesToInt(byte a, byte b) {
        int _a = a, _b = b;
        if (_a<0) _a+=256;
        if (_b<0) _b+=256;
        return (_b << 8) + (_a);
    }
}
