package Display.DialogUI;

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

import Classes.util.*;
//import flash.display.*;
//import flash.utils.*;

    public class PlayerChooserDialog extends GenericDialog
    {

        public  PlayerChooserDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true ,String param13 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PlayerChooserDialogView _loc_2 =new PlayerChooserDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback ,m_customOk ,m_relativeIcon );
            return _loc_2;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.PAYER_CAMPAIGN);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary(true );
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.PAYER_CAMPAIGN) ;
            _loc_1.put("dialog_bg",  new (DisplayObject)_loc_2.campaignRally_dialogBg());
            _loc_1.put("campaignRally_dialogDivider", _loc_2.campaignRally_dialogDivider);
            _loc_1.put("campaignRally_profileBox", _loc_2.campaignRally_profileBox);
            _loc_1.put("campaignRally_sam", _loc_2.campaignRally_sam);
            _loc_1.put("campaignRally_w2w_90x90", _loc_2.campaignRally_w2w_90x90);
            _loc_1.put("campaignRally_whiteRectangleBG", _loc_2.campaignRally_whiteRectangleBG);
            return _loc_1;
        }//end

    }




