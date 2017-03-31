package Display;

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
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;

    public class SlotDialog extends SWFDialog
    {
        protected MovieClip m_window ;
        protected Array m_data ;
        protected int m_tileSet =0;
        protected Array m_tiles ;

        public  SlotDialog ()
        {
            this.m_data = new Array();
            this.m_tiles = new Array();
            super(true);
            return;
        }//end

        protected void  fixArrows ()
        {
            this.m_window.arrowLt_bt.visible = this.m_tileSet > 0 ? (true) : (false);
            this.m_window.arrowRt_bt.visible = (this.m_tileSet + 1) * this.getNumSlots() < this.m_data.length ? (true) : (false);
            return;
        }//end

        protected void  onLtArrowClick (MouseEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            SoundManager.chooseAndPlaySound(.get("click1", "click2", "click3"));
            if (this.m_tileSet > 0)
            {
                this.removeTiles();
                this.m_tileSet--;
                _loc_2 = this.m_tileSet * this.getNumSlots();
                _loc_3 = 0;
                while (_loc_3 < this.getNumSlots())
                {

                    this.addComponent(_loc_3, _loc_2);
                    _loc_3++;
                }
            }
            this.fixArrows();
            return;
        }//end

        protected void  onRtArrowClick (MouseEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            SoundManager.chooseAndPlaySound(.get("click1", "click2", "click3"));
            if (this.m_data.length > (this.m_tileSet + 1) * this.getNumSlots())
            {
                this.removeTiles();
                this.m_tileSet++;
                _loc_2 = this.m_tileSet * this.getNumSlots();
                _loc_3 = 0;
                while (_loc_3 < this.getNumSlots())
                {

                    this.addComponent(_loc_3, _loc_2);
                    _loc_3++;
                }
            }
            this.fixArrows();
            return;
        }//end

        public void  refreshLayout ()
        {
            this.removeTiles();
            _loc_1 = this.m_tileSet *this.getNumSlots ();
            int _loc_2 =0;
            while (_loc_2 < this.getNumSlots() && _loc_1 + _loc_2 < this.m_data.length())
            {

                this.addComponent(_loc_2, _loc_1);
                _loc_2++;
            }
            this.fixArrows();
            return;
        }//end

        protected void  removeTiles (boolean param1 =true )
        {
            int _loc_2 =0;
            while (_loc_2 < this.getNumSlots())
            {

                if (param1 !=null)
                {
                    Utilities.removeAllChildren(this.m_tiles.get(_loc_2).icon);
                    this.m_tiles.get(_loc_2).visible = false;
                    this.postRemoveTiles(_loc_2);
                }
                _loc_2++;
            }
            return;
        }//end

        protected void  addComponent (int param1 ,int param2)
        {
            throw new Error("This function needs to be overriden");
        }//end

        protected void  postRemoveTiles (int param1 )
        {
            return;
        }//end

        protected int  getNumSlots ()
        {
            throw new Error("This function needs to be overriden");
        }//end

    }



