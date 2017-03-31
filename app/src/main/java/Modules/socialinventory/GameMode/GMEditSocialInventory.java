package Modules.socialinventory.GameMode;

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
import Events.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.socialinventory.*;
//import flash.events.*;

    public class GMEditSocialInventory extends GMEdit
    {

        public  GMEditSocialInventory ()
        {
            Global.ui.hideExpandedMainMenu();
            m_cursorImage = EmbeddedArt.hud_act_hearts;
            this.m_uiMode = UIEvent.HEARTS;
            return;
        }//end

         protected Object  getCursor ()
        {
            return m_cursorImage;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            IMechanicUser _loc_2 =null ;
            if (m_highlightedObject && m_highlightedObject.isAttached())
            {
                if (m_highlightedObject instanceof IMechanicUser)
                {
                    _loc_2 =(IMechanicUser) m_highlightedObject;
                    if (_loc_2.getMechanicConfig(SocialInventoryManager.MECHANIC_DATA, Global.getClassName(this)) == null)
                    {
                        SocialInventoryManager.showIneligibleToaster();
                    }
                    MechanicManager.getInstance().handleAction(_loc_2, Global.getClassName(this), [m_highlightedObject.PLAY_ACTION]);
                }
                Global.hud.conditionallyRefreshHUD();
            }
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            if (Global.hud)
            {
                Global.hud.applyConfig(Global.getClassName(this));
            }
            SocialInventoryManager.showFirstToaster();
            SocialInventoryManager.showAllHearts();
            return;
        }//end

         public void  disableMode ()
        {
            SocialInventoryManager.hideAllHearts();
            if (Global.ui && Global.ui.toaster)
            {
                Global.ui.toaster.hide(true);
            }
            if (Global.hud)
            {
                Global.hud.applyConfig("default");
            }
            if (Global.questManager)
            {
                Global.questManager.refreshActiveIconQuests();
            }
            super.disableMode();
            return;
        }//end

    }



