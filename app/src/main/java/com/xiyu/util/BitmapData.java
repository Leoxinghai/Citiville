package com.xiyu.util;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;

public class BitmapData {
	public int width;
	public int height;
	public Bitmap bitmap;
	public Canvas canvas;

	public BitmapData(Bitmap _bitmap) {
		width = (int)_bitmap.getWidth();
		height = (int)_bitmap.getHeight();
		bitmap = _bitmap;
		try {
			if(bitmap.isMutable()) {
				
				canvas = new Canvas(bitmap);
				
			}else {
				canvas = null;
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public BitmapData(int w, int h, boolean isFull,int flag) {
		width = (int)w;
		height = (int)h;
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		bitmap = Bitmap.createBitmap((int)w,(int)h, config);
		canvas = new Canvas(bitmap);
	}

	public void fillRect(Rectangle rect, int clor) {

	}

	public void copyPixels(BitmapData src ,Rectangle rect, Point srcp, Point desp) {

	}

	public void copyPixels(BitmapData src ,Rectangle rect, Point srcp, Point desp, Object obj, boolean flag) {

	}

	public void copyPixels(BitmapData src ,Rectangle rect, Point srcp) {

	}

	public BitmapData createScaleBitmap(int sw, int sh) {
		this.bitmap = Bitmap.createScaledBitmap(this.bitmap, sw, sh, true);
		return this;
	}
	
	public void colorTransform(Rectangle rec, ColorTransform ct) {
		Paint paint = new Paint();
		canvas.drawRect(rec.rect, paint);
	}

	public void dispose() {

	}

	public int getPixel32(int x, int y) {
		return bitmap.getPixel(x, y);
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void draw(BitmapData dest, Matrix matrix , ColorTransform ct, String blendMode, Rectangle clipRect, boolean smothing) {
		Paint paint = new Paint();
		canvas.drawBitmap(dest.getBitmap(), matrix.getMatrix(), paint);
	}
}

