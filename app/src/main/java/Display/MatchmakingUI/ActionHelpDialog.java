package Display.MatchmakingUI;

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
import Display.aswingui.inline.util.*;
import ZLocalization.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class ActionHelpDialog extends FlyFishDialog
    {
        private String m_playerId ;
        private String m_context ;
        private Function m_actionHandler ;

        public  ActionHelpDialog (String param1 ,String param2 ,Function param3 =null )
        {
            super("assets/flyfish/ActionHelpDialog.xml");
            this.m_playerId = param2;
            this.m_context = param1;
            this.m_actionHandler = param3;
            return;
        }//end

         protected void  performDialogActions ()
        {
            Object _loc_2 =null ;
            Player _loc_6 =null ;
            LocalizationName _loc_7 =null ;
            _loc_1 = pane.getComponent("title");
            _loc_1.setText(this.localize("title"));
            ASwingHelper.setProperFont(_loc_1, ASwingFont.TITLE_FONT);
            if (this.m_playerId)
            {
                _loc_6 = Global.player.findFriendById(this.m_playerId);
                _loc_7 = ZLoc.tn(_loc_6.firstName, _loc_6.gender);
                _loc_2 = {playerName:_loc_7};
            }
            _loc_3 = pane.getComponent("message");
            _loc_3.setText(this.localize("message", _loc_2));
            _loc_3.setEnabled(false);
            ASwingHelper.setProperFont(_loc_3, ASwingFont.DEFAULT_FONT_BOLD);
            _loc_3.setForeground(ASwingColor.create(30111));
            _loc_4 = pane.getComponent("closeButton");
            pane.getComponent("closeButton").addActionListener(this.onCloseButtonClick, 0, true);
            _loc_5 = pane.getComponent("actionButton");
            pane.getComponent("actionButton").setText(this.localize("actionButton"));
            _loc_5.addActionListener(this.onActionButtonClick, 0, true);
            ASwingHelper.setProperFont(_loc_5, ASwingFont.TITLE_FONT);
            return;
        }//end

        private void  onCloseButtonClick (Event event =null )
        {
            this.close();
            return;
        }//end

        private void  onActionButtonClick (Event event =null )
        {
            if (this.m_actionHandler != null)
            {
                this.m_actionHandler();
            }
            this.close();
            return;
        }//end

        private String  localize (String param1 ,Object param2 )
        {
            _loc_3 = ZLoc.instance ;
            return _loc_3.translate("Dialogs", this.m_context + "_" + param1, param2);
        }//end

    }



