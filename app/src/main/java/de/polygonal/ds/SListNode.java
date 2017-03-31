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

    public class SListNode implements LinkedListNode
    {
        public Object data ;
        public SListNode next ;

        public  SListNode (Object param1)
        {
            this.data = param1;
            this.next = null;
            return;
        }//end

        public void  insertAfter (SListNode param1 )
        {
            param1.next = this.next;
            this.next = param1;
            return;
        }//end

        public String  toString ()
        {
            return "[SListNode, data=" + this.data + "]";
        }//end

    }

