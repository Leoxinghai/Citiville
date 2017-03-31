package com.xiyu.util;
import android.graphics.*;

public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;
	public Rect rect;

	public Rectangle() {
		x= 0;
		y = 0;
		width = 0;
		height = 0;
		rect = new Rect(x,y,width,height);
	}
	public Rectangle(int _x, int _y, int _width, int _height) {
		x= _x;
		y = _y;
		width = _width;
		height = _height;
		rect = new Rect(x,y,width,height);
	}

	public Rectangle(double _x, double _y, double _width, double _height) {
		x= (int)_x;
		y = (int)_y;
		width = (int)_width;
		height = (int)_height;
		rect = new Rect(x,y,x+width,y+height);
	}

	public Rect androidRect() {
		Rect _rect = new Rect(x,y,x+width,y+height);
		rect = _rect;
		return _rect;
	}

	public void offset(int x, int y) {
		rect.offset(x, y);
		this.x = x;
		this.y = y;
	}
	public boolean contains(int x,int y) {
		return rect.contains(x, y);
	}

	public boolean intersects(Rectangle rec) {
		return rect.intersect(rec.rect);
	}

	public Rectangle intersection(Rectangle rec) {
		return rec;
	}

	public void left(int left) {
		x = left;
		rect.left = left;
	}

	public void right(int right) {
		width = right - x;
		rect.right = right;
	}
	public void top(int top) {
		y = top;
		rect.top = top;
	}
	public void bottom(int bottom) {
		height = bottom - y;
		rect.bottom = bottom;
	}

	public int width () {
		return width;
	}

	public int height () {
		return height;
	}
	
	public int left () {
		return x;
	}

	public int right() {
		return x + width;
	}

	public int top() {
		return x;
	}

	public int bottom() {
		return y + height;
	}

	public Rectangle clone() {
		Rectangle result = new Rectangle(x,y,width,height);
		return result;
	}
}

