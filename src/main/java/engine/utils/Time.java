package engine.utils;

public class Time {

    public static long startTimeLong = System.nanoTime();
    public static float deltaTime;

    private static float endTime, startTime;

    public static float getTime(){
        return (float)((System.nanoTime() - startTimeLong) * 1E-9);
    }

    public static void start(){
        startTime = getTime();
    }

    public static void update(){
        endTime = getTime();
        deltaTime = endTime - startTime;
        startTime = endTime;
    }
}
