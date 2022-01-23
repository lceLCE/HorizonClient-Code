package me.James_.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.network.Packet;

public class EventPacket implements Event {
	public Packet packet;
    private boolean cancelled;

    public EventPacket(Packet p) {
        this.packet = p;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setPacket(Packet p) {
        this.packet = p;
    }
}
