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

//import flash.utils.*;
    public class SocialLevelUpDialog extends GenericDialog
    {
        protected int m_SocialLevel ;
        protected int m_RewardGoods ;
        protected int m_RewardSocialHelp ;
        protected String m_Message ;
        protected String m_Title ;
        protected String m_DialogIcon ;
        protected String m_LevelUpTitleIcon ;

        public  SocialLevelUpDialog (int param1 =0,int param2 =0,int param3 =0,Function param4 =null ,String param5 ="socialLevelUp_prompt",String param6 ="socialLevelUp",String param7 ="assets/dialogs/feeds/feed_viral_reputation.png",String param8 ="assets/dialogs/feeds/socialLevelUp.png")
        {
            this.m_SocialLevel = param1;
            this.m_RewardGoods = param2;
            this.m_RewardSocialHelp = param3;
            this.m_Message = param5;
            this.m_Title = param6;
            this.m_DialogIcon = param7;
            this.m_LevelUpTitleIcon = param8;
            super(ZLoc.t("Dialogs", this.m_Message), this.m_Title, GenericDialogView.TYPE_SHARECANCEL, param4, this.m_Title, this.m_DialogIcon, true);
            return;
        }//end  

         public boolean  isLockable ()
        {
            return true;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            SocialLevelUpDialogView _loc_2 =new SocialLevelUpDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,0,this.m_SocialLevel ,this.m_RewardGoods ,this.m_RewardSocialHelp ,this.m_DialogIcon ,this.m_LevelUpTitleIcon );
            return _loc_2;
        }//end  

    }


