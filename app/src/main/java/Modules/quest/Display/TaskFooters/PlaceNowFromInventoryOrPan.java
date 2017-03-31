package Modules.quest.Display.TaskFooters;

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

import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Classes.*;
import com.zynga.skelly.util.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;

    public class PlaceNowFromInventoryOrPan extends PlaceNowFromInventory implements ITaskFooter
    {

        public  PlaceNowFromInventoryOrPan (GenericDialogView param1 ,String param2 )
        {
            super(param1, param2);
            return;
        }//end  

         public Component  getComponent ()
        {
            CustomButton _loc_2 =null ;
            Component _loc_1 =null ;
            if (Global.player.inventory.getItemCountByName(m_type) > 0)
            {
                _loc_1 = getFooterComponent();
                m_dialogView.addEventListener(Event.CLOSE, onClose, false, CLEANUP_EVENT_PRIORITY);
            }
            else
            {
                _loc_2 = new CustomButton(ZLoc.t("Dialogs", "ScrollTo"), null, "GreenSmallButtonUI");
                _loc_2.addActionListener(Curry.curry(this.onViewClick));
                return _loc_2;
            }
            return _loc_1;
        }//end  

        private void  onViewClick (AWEvent event )
        {
            Array _loc_2 =null ;
            WorldObject _loc_3 =null ;
            if (_loc_3 == null)
            {
                _loc_2 = Global.world.getObjectsByNames(.get(m_type));
                if (_loc_2.length > 0)
                {
                    _loc_3 =(WorldObject) _loc_2.get(0);
                }
            }
            if (_loc_3 == null)
            {
                _loc_2 = Global.world.getObjectsByTargetName(m_type);
                if (_loc_2.length > 0)
                {
                    _loc_3 =(WorldObject) _loc_2.get(0);
                }
            }
            if (_loc_3 == null)
            {
                return;
            }
            Global.world.centerOnObject(_loc_3);
            m_dialogView.close();
            removeListeners();
            return;
        }//end  

    }



