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
import Modules.workers.*;
import org.aswing.*;
import org.aswing.event.*;

    public class ReadyHunterState extends GenericHunterState
    {

        public  ReadyHunterState (HunterData param1 ,int param2 ,HunterCell param3 )
        {
            super(param1, param2, param3);
            return;
        }//end  

         protected JPanel  makeActionPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs",HunterDialog.groupId +"_patrolLabel"),null ,"GreenButtonUI");
            _loc_2.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
            _loc_2.setMargin(m_buttonInsets);
            _loc_1.append(_loc_2);
            _loc_2.addActionListener(this.doAction, 0, true);
            return _loc_1;
        }//end  

         protected void  doAction (AWEvent event )
        {
            return;
        }//end  

    }



