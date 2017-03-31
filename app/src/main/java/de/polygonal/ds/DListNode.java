package de.polygonal.ds;

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

    public class DListNode implements LinkedListNode
    {
        public Object data ;
        public DListNode next ;
        public DListNode prev ;

        public  DListNode (Object param1)
        {
            this.prev = null;
            this.next = null;
            this.data = param1;
            return;
        }//end

        public void  insertAfter (DListNode param1 )
        {
            param1.next = this.next;
            param1.prev = this;
            if (this.next)
            {
                this.next.prev = param1;
            }
            this.next = param1;
            return;
        }//end

        public void  insertBefore (DListNode param1 )
        {
            param1.next = this;
            param1.prev = this.prev;
            if (this.prev)
            {
                this.prev.next = param1;
            }
            this.prev = param1;
            return;
        }//end

        public void  unlink ()
        {
            if (this.prev)
            {
                this.prev.next = this.next;
            }
            if (this.next)
            {
                this.next.prev = this.prev;
            }

            this.prev = null;
            this.next = null;
            return;
        }//end

        public String  toString ()
        {
            return "[DListNode, data=" + this.data + "]";
        }//end

    }

