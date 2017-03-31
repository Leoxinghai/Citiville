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

    public class CollisionCell
    {
        public int x ;
        public int y ;
        public Array windows ;
        public Array walls ;
        public Array items ;
        public boolean isPathable ;

        public  CollisionCell (int param1 ,int param2 )
        {
            this.windows = new Array();
            this.walls = [[], [], [], []];
            this.items = new Array();
            this.x = param1;
            this.y = param2;
            this.isPathable = false;
            return;
        }//end

        public void  dispose ()
        {
            this.windows = null;
            this.walls = null;
            this.items = null;
            return;
        }//end

        public void  setWall (int param1 ,WorldObject param2 )
        {
            _loc_3 =(Array) this.walls.get(param1);
            if (_loc_3.indexOf(param2) == -1)
            {
                _loc_3.push(param2);
            }
            return;
        }//end

        public Array  getWalls (int param1 )
        {
            return this.walls.get(param1);
        }//end

        public void  addObject (WorldObject param1 )
        {
            this.items.push(param1);
            return;
        }//end

        public void  removeObject (WorldObject param1 )
        {
            Array _loc_4 =null ;
            _loc_2 = this.items.indexOf(param1 );
            if (_loc_2 != -1)
            {
                this.items.splice(_loc_2, 1);
            }
            int _loc_3 =0;
            while (_loc_3 < 4)
            {

                _loc_4 = this.walls.get(_loc_3);
                _loc_2 = _loc_4.indexOf(param1);
                if (_loc_2 != -1)
                {
                    _loc_4.splice(_loc_2, 1);
                }
                _loc_3++;
            }
            return;
        }//end

    }



