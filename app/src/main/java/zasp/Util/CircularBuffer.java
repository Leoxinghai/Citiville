package zasp.Util;

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

    public class CircularBuffer
    {
        private Array arr =null ;
        private int ins =0;
        private int num =0;

        public  CircularBuffer (int param1 )
        {
            this.arr = new Array(param1);
            this.ins = 0;
            this.num = 0;
            return;
        }//end

        public int  capacity ()
        {
            return this.arr.length;
        }//end

        public void  capacity (int param1 )
        {
            CircularBuffer _loc_2 =new CircularBuffer(param1 );
            while (this.length > 0)
            {

                _loc_2.push(this.pop());
            }
            this.arr = _loc_2.arr;
            this.ins = _loc_2.ins;
            this.num = _loc_2.num;
            return;
        }//end

        public int  length ()
        {
            return this.num;
        }//end

        public void  push (Object param1 )
        {
            if (this.num >= this.arr.length())
            {
                throw new Error("overflow");
            }
            this.arr.put(this.ins,  param1);
            (this.num + 1);
            this.ins = (this.ins + 1) % this.arr.length();
            return;
        }//end

        public Object  pop ()
        {
            if (this.num <= 0)
            {
                throw new Error("underflow");
            }
            _loc_1 = this(.arr.length +this.ins -this.num )% this.arr.length;
            _loc_2 = this.arr.get(_loc_1) ;
            this.arr.put(_loc_1,  0);
            (this.num - 1);
            return _loc_2;
        }//end

        public Object  elementAt (int param1 )
        {
            if (param1 < 0 || param1 >= this.num)
            {
                throw new Error("out of range");
            }
            _loc_2 = this(.arr.length +this.ins -this.num +param1 )% this.arr.length;
            return this.arr.get(_loc_2);
        }//end

        public static void  test ()
        {
            boolean happy ;
            CircularBuffer buf ;
            String str1 ;
            String str2 ;
            Object pop1 ;
            Object pop2 ;
            buf = new CircularBuffer(0);
            if (buf.capacity != 0)
            {
                throw new Error("a1");
            }
            if (buf.length != 0)
            {
                throw new Error("b1");
            }
            try
            {
                buf.push(10);
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("c1");
            }
            try
            {
                buf.pop();
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("d1");
            }
            buf = new CircularBuffer(1);
            if (buf.capacity != 1)
            {
                throw new Error("a2");
            }
            if (buf.length != 0)
            {
                throw new Error("b2");
            }
            buf.push(str1);
            try
            {
                buf.push(10);
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("c2");
            }
            pop1 = buf.pop();
            if (str1 != pop1)
            {
                throw new Error("d2");
            }
            try
            {
                buf.pop();
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("e2");
            }
            buf = new CircularBuffer(2);
            if (buf.capacity != 2)
            {
                throw new Error("a3");
            }
            if (buf.length != 0)
            {
                throw new Error("b3");
            }
            buf.push(str1);
            pop1 = buf.pop();
            if (str1 != pop1)
            {
                throw new Error("c3");
            }
            buf.push(str2);
            pop2 = buf.pop();
            if (str2 != pop2)
            {
                throw new Error("d3");
            }
            buf.push(str1);
            buf.push(str2);
            try
            {
                buf.push(3);
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("e3");
            }
            pop1 = buf.pop();
            pop2 = buf.pop();
            if (str1 != pop1)
            {
                throw new Error("f3");
            }
            if (str2 != pop2)
            {
                throw new Error("g3");
            }
            try
            {
                buf.pop();
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("h3");
            }
            buf = new CircularBuffer(2);
            if (buf.capacity != 2)
            {
                throw new Error("a4");
            }
            if (buf.length != 0)
            {
                throw new Error("b4");
            }
            buf.push(str1);
            pop1 = buf.pop();
            if (str1 != pop1)
            {
                throw new Error("c4");
            }
            buf.push(str2);
            pop2 = buf.pop();
            if (str2 != pop2)
            {
                throw new Error("d4");
            }
            buf.push(str1);
            buf.push(str2);
            try
            {
                buf.push(3);
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("e4");
            }
            len = buf.length;
            buf.capacity = 1 + buf.capacity;
            if (buf.length != len)
            {
                throw new Error("len4.1");
            }
            buf.push(3);
            if (str1 != buf.pop())
            {
                throw new Error("f4.a");
            }
            if (str2 != buf.pop())
            {
                throw new Error("f4.b");
            }
            if (buf.pop() != 3)
            {
                throw new Error("f4.c");
            }
            buf.push(1);
            buf.push(2);
            try
            {
                buf.capacity = 1;
                happy;
            }
            catch (expected:Error)
            {
            }
            if (!happy)
            {
                throw new Error("g4");
            }
            return;
        }//end

    }



