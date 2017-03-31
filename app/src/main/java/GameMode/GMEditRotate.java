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
import Classes.util.*;
import Events.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;

    public class GMEditRotate extends GMEdit
    {

        public  GMEditRotate ()
        {
            m_showMousePointer = true;
            m_cursorImage = EmbeddedArt.hud_act_rotate;
            m_uiMode = UIEvent.ROTATE;
            return;
        }//end  

         public void  enableMode ()
        {
            super.enableMode();
            return;
        }//end  

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return param1.canBeDragged() && param1.canBeRotated();
        }//end  

         public boolean  onMouseUp (MouseEvent event )
        {
            int _loc_4 =0;
            GMObjectMove _loc_5 =null ;
            Object _loc_6 =null ;
            _loc_2 = (MapResource)m_selectedObject
            if (_loc_2 && !m_dragging)
            {
                _loc_4 = _loc_2.getDirection();
                _loc_2.setBeingRotated(true);
                _loc_2.rotate();
                if (_loc_2.sizeX != _loc_2.sizeY)
                {
                    Global.world.addGameMode(new GMEditRotate());
                    _loc_5 = new GMObjectMove(_loc_2, null, getMouseStagePos(), _loc_4);
                    _loc_5.doImmediateDrop();
                    Global.world.addGameMode(_loc_5, false);
                }
                else
                {
                    _loc_6 = new Object();
                    _loc_6.x = _loc_2.positionX;
                    _loc_6.y = _loc_2.positionY;
                    _loc_6.state = _loc_2.getState();
                    _loc_6.direction = _loc_2.getDirection();
                    _loc_6 = _loc_2.addTMoveParams(_loc_6);
                    GameTransactionManager.addTransaction(new TMove(_loc_2, _loc_6));
                }
                _loc_2.setBeingRotated(false);
                _loc_2.trackDetailedAction(TrackedActionType.ROTATE, "successful", "");
            }
            _loc_3 = super.onMouseUp(event);
            return _loc_3;
        }//end  

    }



