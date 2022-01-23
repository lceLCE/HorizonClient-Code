package me.James_.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.network.play.client.C02PacketUseEntity;

public class EventMove implements Event {
	public double x;
	public double y;
	public double z;
	
	public EventMove(double a, double b, double c) {
		this.x = a;
		this.y = b;
		this.z = c;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public C02PacketUseEntity getPacket() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOnGround(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setPosY(double d) {
		// TODO Auto-generated method stub
		
	}
}
