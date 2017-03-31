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

import Classes.*;
//import flash.display.*;
//import flash.utils.*;

    public class ItemUnlockDialog extends GenericDialog
    {
        protected Item m_item ;
        protected String m_unlockedCostString ;

        public  ItemUnlockDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,Item param11 =null )
        {
            this.m_item = param11;
            this.m_unlockedCostString = this.m_item ? (this.m_item.unlockedCostString) : (null);
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            ItemUnlockDialogView _loc_2 =new ItemUnlockDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback );
            return _loc_2;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            _loc_1.put("marketItem",  new (DisplayObject)m_comObject.marketItem());
            _loc_1.put("hr",  new (DisplayObject)m_comObject.dialog_train_horizontalRule());
            _loc_1.put("item", this.m_item);
            _loc_1.put("unlockedCostString", this.m_unlockedCostString);
            m_assetBG = _loc_1.get("dialog_bg");
            return _loc_1;
        }//end

    }




