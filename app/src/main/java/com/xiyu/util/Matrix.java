package com.xiyu.util;
//import android.graphics.Matrix;
import android.graphics.Point;

public class Matrix {
	public double a;
	public double b;
	public double c;
	public double d;
	public int tx;
	public int ty;
	public android.graphics.Matrix aMatrix;

	public Matrix() {
		a = 0;
		b = 0;
		c = 0;
		d = 0;
		tx = 0;
		ty = 0;
		aMatrix = new android.graphics.Matrix();
		//aMatrix.
	}

	public Matrix(int _a, int _b, int _c, int _d, int _tx, int _ty) {
		a = _a;
		b = _b;
		c = _c;
		d = _d;
		tx = _tx;
		ty = _ty;
		aMatrix = new android.graphics.Matrix();
//		aMatrix.set
	}

	public Matrix(double _a, double _b, double _c, double _d, int _tx, int _ty) {
		a = _a;
		b = _b;
		c = _c;
		d = _d;
		tx = _tx;
		ty = _ty;
		
		aMatrix = new android.graphics.Matrix();
		aMatrix.setScale((float)c, (float)d);
		aMatrix.setRotate(0, (float)a,(float)b);
		aMatrix.setTranslate(tx, ty);
//		aMatrix.set
	}

	public android.graphics.Matrix getMatrix() {
//		float scale0 = (float)0.3;
//		float scale1 = (float)0.3;
//		aMatrix.setScale(scale0, scale1);
		return aMatrix;
		//return new android.graphics.Matrix();
	}

	public Matrix clone() {
		Matrix matrix = new Matrix(a,b,c,d,tx,ty);
		matrix.scale(c, d);
		matrix.rotate(a);
		matrix.translate(tx, ty);
		return matrix;
	}

	public void translate(double x, double y) {
		aMatrix.preTranslate((float)x, (float)y);
//		aMatrix.setTranslate((float)x, (float)y);
	}

	public Point transformPoint(Point point) {
		aMatrix.setTranslate(point.x, point.y);
		return point;
	}

	public void rotate(double degrees) {
		aMatrix.setRotate((float)degrees);
	}

	public void concat(Matrix _m) {

	}

	public void identity() {

	}

	public void scale(double scaleX, double scaleY ) {
		float scale0 = (float)scaleX;
		float scale1 = (float)scaleY;
		c = scaleX;
		d = scaleY;
//		aMatrix.postRotate(30);
		aMatrix.setScale(scale0,scale1);
	}
	
	public void copyAndroidMatrix(android.graphics.Matrix mm) {
		aMatrix = mm;
	}
}


