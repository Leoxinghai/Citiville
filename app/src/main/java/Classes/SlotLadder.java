package Classes;

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

//import flash.display.*;
//import flash.events.*;

    public class SlotLadder extends MovieClip
    {
        public  double SLOT_LADDER_SPACING =51;
        public double m_itemsCount ;

        public  SlotLadder (double param1 =1)
        {
            this.m_itemsCount = param1;
            if (param1 > 0)
            {
                this.update();
            }
            addEventListener(MouseEvent.MOUSE_DOWN, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMaskSlotLadder);
            addEventListener(MouseEvent.MOUSE_UP, this.onMaskSlotLadder);
            addEventListener(MouseEvent.CLICK, this.onMaskSlotLadder);
            return;
        }//end  

        public void  onMaskSlotLadder (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end  

        public void  update ()
        {
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_6 =null ;
            Sprite _loc_7 =null ;
            Bitmap _loc_8 =null ;
            double _loc_1 =0;
            Sprite _loc_2 =new Sprite ();
            _loc_2.addChild(_loc_3);
            _loc_2.y = _loc_1;
            addChild(_loc_2);
            _loc_1 = _loc_1 + _loc_3.height;
            double _loc_4 =1;
            while (_loc_4 < this.m_itemsCount)
            {
                
                _loc_7 = new Sprite();
                _loc_7.addChild(_loc_8);
                _loc_7.y = _loc_1;
                _loc_1 = _loc_1 + this.SLOT_LADDER_SPACING;
                addChild(_loc_7);
                _loc_4 = _loc_4 + 1;
            }
            Sprite _loc_5 =new Sprite ();
            _loc_5.addChild(_loc_6);
            _loc_5.y = _loc_1;
            addChild(_loc_5);
            return;
        }//end  

    }


