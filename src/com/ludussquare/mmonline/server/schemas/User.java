/**
 * @author Niko Lagman
 * @version 0.1
 */

package com.ludussquare.mmonline.server.schemas;
import org.mongodb.morphia.annotations.Entity;

@Entity("users")
public class User extends Base {
	
	private String username = "";
	private String password = "";
	private int color = -1;
	private int room = -1;
	private int level = -1;
	private float x = -1;
	private float y = -1;
	public User () {}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getLevel() {
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
