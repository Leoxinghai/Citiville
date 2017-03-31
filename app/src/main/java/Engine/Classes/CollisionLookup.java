package Engine.Classes;

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

import Engine.*;
import Engine.Helpers.*;

    public class CollisionLookup
    {
        public int startX ;
        public int startY ;
        public int endX ;
        public int endY ;
        public Array ignoreObjects =null ;
        public Array colliders ;
        public WorldObject collidingObject =null ;
        public int colliderTypes ;
        public int flags ;

        public  CollisionLookup ()
        {
            this.colliders = new Array();
            this.colliderTypes = Constants.WORLDOBJECT_ALL;
            this.flags = Constants.COLLISION_ALL;
            return;
        }//end

        public void  init (int param1 ,int param2 ,int param3 ,int param4 )
        {
            this.ignoreObjects = null;
            this.colliders = new Array();
            this.collidingObject = null;
            this.colliderTypes = Constants.WORLDOBJECT_ALL;
            this.flags = Constants.COLLISION_ALL;
            this.startX = param1;
            this.startY = param2;
            this.endX = param3;
            this.endY = param4;
            this.fixBounds();
            return;
        }//end

        public void  fixBounds ()
        {
            int _loc_1 =0;
            if (this.startX > this.endX)
            {
                _loc_1 = this.startX;
                this.startX = this.endX;
                this.endX = _loc_1;
            }
            if (this.startY > this.endY)
            {
                _loc_1 = this.startY;
                this.startY = this.endY;
                this.endY = _loc_1;
            }
            return;
        }//end

        public int  getFlags (int param1 ,int param2 )
        {
            Vector3 _loc_4 =null ;
            Vector3 _loc_5 =null ;
            int _loc_3 =0;
            if (this.collidingObject != null)
            {
                _loc_4 = this.collidingObject.getPositionNoClone();
                _loc_5 = this.collidingObject.getSizeNoClone();
                param1 = param1 - this.startX;
                param2 = param2 - this.startY;
                if (param1 >= 0 && param2 >= 0 && param1 < _loc_5.x && param2 < _loc_5.y)
                {
                    _loc_3 = this.collidingObject.getCollisionFlags(param1, param2);
                }
            }
            else
            {
                _loc_3 = this.flags;
            }
            return _loc_3;
        }//end

        public boolean  isLine ()
        {
            return this.startX == this.endX || this.startY == this.endY;
        }//end

    }



