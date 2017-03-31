package Display.HunterAndPreyUI.HunterCellStates;

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
import Display.HunterAndPreyUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;

    public class LockedHunterState extends GenericHunterState
    {

        public  LockedHunterState (int param1 ,HunterCell param2 )
        {
            super(null, param1, param2);
            return;
        }//end  

         protected JPanel  makeImagePanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_slot_locked");
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end  

         protected JPanel  makeNamePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId+"_statusOnDuty"),EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.blueTextColor);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end  

         protected JPanel  makeActionPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId+"_statusUpgrade"),EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.blueTextColor);
            _loc_1.appendAll(_loc_2);
            return _loc_1;
        }//end  

         protected JPanel  makeDonutPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_doughnut_disabled");
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end  

    }



