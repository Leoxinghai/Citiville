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

import Engine.Classes.*;
//import flash.display.*;

    public class RoadLayer extends GameObjectLayer
    {
        private Sprite m_baseObject ;
        private Sprite m_topObject ;
        private Sprite m_bottomObject ;

        public  RoadLayer (String param1 ,double param2)
        {
            super(param1, param2);
            this.m_baseObject = new Sprite();
            this.m_topObject = new Sprite();
            this.m_bottomObject = new Sprite();
            _loc_3 = (Sprite)m_displayObject
            if (_loc_3)
            {
                _loc_3.addChild(this.m_bottomObject);
                _loc_3.addChild(this.m_baseObject);
                _loc_3.addChild(this.m_topObject);
            }
            return;
        }//end

        public Sprite  topObject ()
        {
            return this.m_topObject;
        }//end

        public Sprite  bottomObject ()
        {
            return this.m_bottomObject;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            if (this.m_baseObject.parent)
            {
                this.m_baseObject.parent.removeChild(this.m_baseObject);
            }
            if (this.m_topObject.parent)
            {
                this.m_topObject.parent.removeChild(this.m_topObject);
            }
            _loc_1 = (Sprite)m_displayObject
            if (_loc_1)
            {
                _loc_1.addChild(this.m_baseObject);
                _loc_1.addChild(this.m_topObject);
            }
            return;
        }//end

         public void  insertObjectIntoDepthArray (WorldObject param1 ,String param2 )
        {
            _loc_3 = binarySearch(param1);
            m_children.splice(_loc_3, 0, param1);
            _loc_4 = param1.getDisplayObject();
            _loc_5 = this.m_baseObject;
            if (_loc_4 == null)
            {
                _loc_5.addChildAt(param1.createDisplayObject(), _loc_3);
            }
            else if (_loc_4.parent == null)
            {
                _loc_5.addChildAt(_loc_4, _loc_3);
            }
            else
            {
                _loc_4.parent.setChildIndex(_loc_4, _loc_3);
            }
            return;
        }//end

    }




