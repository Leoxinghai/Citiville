package Display.FlashMFSList;

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

import Classes.Managers.*;
import Classes.util.*;
import Display.DialogUI.*;

    public class FlashMFSListDialog extends BaseDialog
    {

        public  FlashMFSListDialog (Array param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="SEND")
        {
            Object _loc_7 ={name title "FlashMFSListDialog",,subtitle ,message ,friendData ,viewClass ,sendLabel ,sendCallback.sendToRecipients ,background {.IN_GAME_MFS_ASSETS ,className "mfsBG",,fixedHeight },preloadAssets {name "mfsAssets",.IN_GAME_MFS_ASSETS }] };
            if (param5 != "")
            {
                _loc_7.get("preloadAssets").push({name:"itemIcon", url:param5});
            }
            super(_loc_7);
            return;
        }//end  

        private void  sendToRecipients (Array param1 ,String param2 )
        {
            _loc_3 = (FlashMFSListManager)Global.flashMFSManager.getManager(FlashMFSManager.TYPE_FLASH_MFS_LIST)
            if (_loc_3)
            {
                _loc_3.sendRequest(param1);
            }
            return;
        }//end  

         protected boolean  doTrackDialogActions ()
        {
            return false;
        }//end  

    }



