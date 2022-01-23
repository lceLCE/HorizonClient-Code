package me.James_.util.timeUtils;

import net.minecraft.client.Minecraft;

public final class TimerUtil {
    public double time;

    public TimerUtil() {
        this.time = (System.nanoTime() / 1000000l);
    }

    public boolean hasTimeElapsed(double time, boolean reset) {
        if (getTime() >= time) {
            if (reset) {
                reset();
            }

            return true;
        }

        return false;
    }

    public double getTime() {
        return System.nanoTime() / 1000000l - this.time;
    }

    public void reset() {
        this.time = (System.nanoTime() / 1000000l);
    }
}