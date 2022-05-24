package engine.utils;

public class Color {
    private int r, g, b, a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getRedf() {
        return (float) this.r / 255;
    }

    public float getGreenf() {
        return (float) this.g / 255;
    }

    public float getBluef() {
        return (float) this.b / 255;
    }

    public float getAlphaf() {
        return (float) this.a / 255;
    }

}
