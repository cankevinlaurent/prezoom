/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

/**
 * This is used as the pure data structure rather than the class, so no getters
 * or setters are needed.
 * 
 * @author Delta Group
 */

public class Delta {
	public int type;
	public int X;
	public int Y;
	public float transparent;
	public int red;
	public int green;
	public int blue;
	public int X2;
	public int Y2;
	public int width;
	public int height;
	public boolean filled;
	public String title;
	
	public Delta() {
		type = 0;
		X = 0;
		Y = 0;
		red = 0;
		green = 0;
		blue = 0;
		transparent = 0f;
		X2 = 0;
		Y2 = 0;
		width = 0;
		height = 0;
		filled = true;
		title = "hello, world";
	}
}
