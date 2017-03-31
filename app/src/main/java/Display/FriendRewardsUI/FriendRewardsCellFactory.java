package Display.FriendRewardsUI;

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

//import flash.utils.*;
import org.aswing.ext.*;

    public class FriendRewardsCellFactory implements GridListCellFactory
    {
        protected Class m_cellClass ;
        protected Dictionary m_assetDict ;
        protected String m_localeKey ;

        public  FriendRewardsCellFactory (Class param1 ,Dictionary param2 ,String param3 )
        {
            this.m_cellClass = param1;
            this.m_assetDict = param2;
            this.m_localeKey = param3;
            return;
        }//end

        public Class  cellClass ()
        {
            return this.m_cellClass;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            if (this.m_cellClass)
            {
                return new this.m_cellClass(this.m_assetDict, null, this.m_localeKey);
            }
            return null;
        }//end

    }

