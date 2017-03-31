package Modules.franchise.display;

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

import Display.*;
import Display.aswingui.*;
import Events.*;
import Modules.franchise.data.*;

//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class FranchiseMenu extends Sprite
    {
        protected JPanel m_content =null ;
        protected Sprite m_holder ;
        public static FranchiseMenuUI menuUI ;
        public static OwnedFranchiseData selectedFranchise ;
        public static  String STATUS_NEED_REFILL ="REFILL";
        public static  String STATUS_WAITING ="WAIT";
        public static  String STATUS_NEED_REMIND ="REMIND";
        public static  String STATUS_DONE_REMIND ="DONE";
        public static  String STATUS_ADD_NEIGHBOR ="ADD";
        public static  String STATUS_COLLECT_WITHER ="WITHER";
        public static  String STATUS_COLLECT_NOWITHER ="NOWITHER";
        public static  String STATUS_BLANK ="BLANK";
        public static  String STATUS_COLLECT_WAITING ="COLLECT_WAIT";
        public static  int COLOR_MENU_STANDARD =10056537;
        public static  int COLOR_MENU_GRAY =7631988;
        public static  int COLOR_MENU_GREEN =43520;
        public static  int COLOR_MENU_RED =16738816;

        public  FranchiseMenu ()
        {
            OwnedFranchiseData _loc_2 =null ;
            _loc_1 =Global.franchiseManager.getAllFranchises ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.getLocationCount() > 0)
                {
                    selectedFranchise = _loc_2;
                    break;
                }
            }
            this.loadAssets();
            return;
        }//end

        protected void  loadAssets ()
        {
            menuUI = new FranchiseMenuUI();
            this.m_content = menuUI;
            this.m_holder = new Sprite();
            this.addChild(this.m_holder);
            JWindow _loc_1 =new JWindow(this.m_holder );
            _loc_1.setContentPane(menuUI);
            ASwingHelper.prepare(_loc_1);
            _loc_1.show();
            (this.m_content as FranchiseMenuUI).init(this, selectedFranchise);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver);
            return;
        }//end

        public void  close ()
        {
            Global.ui.removeChild(this);
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            return;
        }//end

        public void  switchBusiness (String param1 )
        {
            _loc_2 =(FranchiseMenuUI) this.m_content;
            if (_loc_2)
            {
                _loc_2.switchBusiness(param1);
            }
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            UI.setCursor(null);
            Global.ui.cleanUpToolTip();
            return;
        }//end

        public static int  dailyCycleDelta ()
        {
            return Math.floor(Global.gameSettings().getNumber("dailyCollectCycleTimeSeconds") * Global.gameSettings().inGameDaySeconds / 86400);
        }//end

    }



