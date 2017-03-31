package GameMode;

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

import Classes.*;
import Engine.Classes.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;

    public class GMPlay extends GMDefault
    {

        public  GMPlay ()
        {
            m_uiMode = UIEvent.CURSOR;
            return;
        }//end

         public boolean  supportsEditing ()
        {
            return false;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return false;
        }//end

         protected boolean  isObjectDraggable (GameObject param1 )
        {
            return false;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            if (!Global.franchiseManager.placementMode)
            {
                return super.onMouseDown(event);
            }
            return false;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            boolean _loc_3 =false ;
            Point _loc_4 =null ;
            boolean _loc_5 =false ;
            _loc_2 = m_highlightedObject;
            if (m_highlightedObject && m_highlightedObject.isAttached())
            {
                _loc_3 = true;
                if (m_highlightedObject && m_highlightedObject instanceof IMechanicUser)
                {
                    _loc_3 = !MechanicManager.getInstance().handleAction(m_highlightedObject as IMechanicUser, Global.getClassName(this));
                }
                if (_loc_2 instanceof IMechanicPrePlayAction && !_loc_3)
                {
                    ((IMechanicPrePlayAction)_loc_2).onPrePlayTrack();
                }
                if (m_highlightedObject && _loc_3)
                {
                    m_highlightedObject.onPlayAction();
                }
            }
            else
            {
                _loc_4 = getMouseTilePos(event);
                _loc_5 = Global.world.rectInTerritory(new Rectangle((_loc_4.x - 1), (_loc_4.y - 1), 1, 1));
                if (!Global.isVisiting() && !_loc_5 && event.currentTarget instanceof IsoViewport && !(event.target instanceof AWSprite))
                {
                    Global.world.wildernessSim.trySellExpansion(_loc_4.x, _loc_4.y, false);
                }
                else
                {
                    super.handleClick(event);
                }
            }
            return;
        }//end

    }



