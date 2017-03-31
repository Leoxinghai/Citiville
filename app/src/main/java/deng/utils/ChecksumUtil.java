package deng.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

//import flash.utils.ByteArray;

	public class ChecksumUtil
	{
		/**
		 * @private
		 */
		private static Array crcTable =makeCRCTable ();

		/**
		 * @private
		 */
		private static Array  makeCRCTable (){
			Array table =new Array();
			int i ;
			int j ;
			int c ;
			for (i = 0; i < 256; i++) {
				c = i;
				for (j = 0; j < 8; j++) {
					if (c & 1) {
						c = 0xEDB88320 ^ (c >>> 1);
					} else {
						c >>>= 1;
					}
				}
				table.push(c);
			}
			return table;
		}

		/**
		 * Calculates a CRC-32 checksum over a ByteArray
		 *
		 * @see http://www.w3.org/TR/PNG/#D-CRCAppendix
		 *
		 * @param data
		 * @param len
		 * @param start
		 * @return CRC-32 checksum
		 */
		public static int  CRC32 (ByteArray data ,int start =0,int len =0){
			if (start >= data.length()) { start = data.length; }
			if (len == 0) { len = data.length - start; }
			if (len + start > data.length()) { len = data.length - start; }
			int i ;
			int c =0xffffffff ;
			for (i = start; i < len; i++) {
				c = uint(crcTable.get((c ^ data.get(i)) & 0xff)) ^ (c >>> 8);
			}
			return (c ^ 0xffffffff);
		}

		/**
		 * Calculates an Adler-32 checksum over a ByteArray
		 *
		 * @see http://en.wikipedia.org/wiki/Adler-32#Example_implementation
		 *
		 * @param data
		 * @param len
		 * @param start
		 * @return Adler-32 checksum
		 */
		public static int  Adler32 (ByteArray data ,int start =0,int len =0){
			if (start >= data.length()) { start = data.length; }
			if (len == 0) { len = data.length - start; }
			if (len + start > data.length()) { len = data.length - start; }
			int i =start ;
			int a =1;
			int b =0;
			while (i < (start + len)) {
				a = (a + data.get(i)) % 65521;
				b = (a + b) % 65521;
				i++;
			}
			return (b << 16) | a;
		}
	}

