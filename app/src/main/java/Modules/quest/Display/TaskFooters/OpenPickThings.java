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

import Classes.*;
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.events.*;
import org.aswing.*;

    public class OpenPickThings implements ITaskFooter
    {
        private String m_type ;
        private GenericDialogView m_dialogView ;
        private CustomButton btnStartGame ;

        public  OpenPickThings (GenericDialogView param1 ,String param2 )
        {
            this.m_type = param2;
            this.m_dialogView = param1;
            this.m_dialogView.addEventListener(Event.CLOSE, this.onDialogViewClose);
            return;
        }//end

        public Component  getComponent ()
        {
            this.btnStartGame = new CustomButton(ZLoc.t("Market", "Promo_mystery_button"), null, "GreenButtonUI");
            this.btnStartGame.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            this.btnStartGame.addActionListener(this.onButtonClick);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.append(this.btnStartGame);
            return _loc_1;
        }//end

        private void  destroy ()
        {
            this.m_dialogView.removeEventListener(Event.CLOSE, this.onDialogViewClose);
            this.btnStartGame.removeActionListener(this.onButtonClick);
            return;
        }//end

        private void  onButtonClick (Event event )
        {
            this.m_dialogView.countDialogViewAction("OPEN_PICK_THINGS");
            this.m_dialogView.close();
            Global.ui.startPickThings(this.m_type);
            this.btnStartGame.removeActionListener(this.onButtonClick);
            return;
        }//end

        private void  onDialogViewClose (Event event )
        {
            this.destroy();
            return;
        }//end

    }



