/**
 * @author Niko Lagman
 * @version 0.1
 */

package com.ludussquare.mmonline.server.schemas;
import org.mongodb.morphia.annotations.Entity;

@Entity("users")
public class User extends Base {
	
	private String username;
	private String passsword;
	private int color;
	private int room;
	private int level;
	private float x;
	private float y;
	public User () {}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasssword() {
		return passsword;
	}
	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getLeve() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
