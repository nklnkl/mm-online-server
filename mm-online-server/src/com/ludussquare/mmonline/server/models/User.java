/**
 * @author Niko Lagman
 * @version 0.1
 */

package com.ludussquare.mmonline.server.models;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity("users")
public class User extends Base {
	
	private String username;
	private String passsword;
	private int color;
	private int room;
	private int points;
	private float x;
	private float y;
	public ObjectId getId() {
		return id;
	}
	
	public User () {
		
	}
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
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
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
