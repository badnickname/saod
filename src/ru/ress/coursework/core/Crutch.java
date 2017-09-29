package ru.ress.coursework.core;

/**
 * Created by ress on 26.09.17.
 */
class Crutch {

    static String getString(byte[] arr) {
        String out = new String("");
        for (byte elm : arr) {
            out += Crutch.toRussian(elm);
        }
        return out;
    }

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
