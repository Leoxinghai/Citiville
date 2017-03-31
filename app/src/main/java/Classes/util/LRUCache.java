package Classes.util;

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

//import flash.utils.*;
    public class LRUCache
    {
        private int maxSize ;
        private int addCount =0;
        private Node sentinel ;
        private Dictionary dictionary ;

        public  LRUCache (int param1 =16)
        {
            this.sentinel = new Node();
            this.dictionary = new Dictionary();
            this.maxSize = param1;
            _loc_2 = this.sentinel ;
            this.sentinel.prev = this.sentinel;
            this.sentinel.next = _loc_2;
            return;
        }//end

        public void  putItem (String param1 ,Object param2 )
        {
            if (param2 == null)
            {
                return;
            }
            _loc_3 =(Object) this.dictionary.get(param1);
            if (_loc_3 != null)
            {
                _loc_3.unlink();
            }
            else if (this.addCount < this.maxSize)
            {
                _loc_3 = new Node();
                this.addCount++;
            }
            else
            {
                _loc_3 = this.sentinel.prev;
                _loc_3.unlink();
            }
            _loc_3.data = param2;
            this.sentinel.linkRight(_loc_3);
            this.dictionary.put(param1,  _loc_3);
            return;
        }//end

        public Object  getItem (String param1 )
        {
            _loc_2 =(Object) this.dictionary.get(param1);
            if (_loc_2)
            {
                _loc_2.unlink();
                this.sentinel.linkRight(_loc_2);
                return _loc_2.data;
            }
            return null;
        }//end

        public void  traceCache ()
        {
            String _loc_1 ="LRU:";
            _loc_2 = this.sentinel.next ;
            while (_loc_2 != this.sentinel)
            {

                _loc_1 = _loc_1 + (_loc_2.data + " ");
                _loc_2 = _loc_2.next;
            }
            return;
        }//end

    }
class Node
    public Node next ;
    public Node prev ;
    public Object data ;

     Node ()
    {
        return;
    }//end

    public void  unlink ()
    {
        this.prev.next = this.next;
        this.next.prev = this.prev;

        this.prev = null;
        this.next = null;
        return;
    }//end

    public void  linkRight (Node param1 )
    {
        param1.prev = this;
        param1.next = this.next;
        this.next.prev = param1;
        this.next = param1;
        return;
    }//end




