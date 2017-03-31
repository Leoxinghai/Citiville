package Transactions;

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
import Display.*;
import Engine.Classes.*;

    public class TGetAvatarData extends TFarmTransaction
    {
        protected String m_userId ;
        private Function m_callBackFunc ;

        public  TGetAvatarData (String param1 ,Function param2 )
        {
            this.m_userId = param1;
            this.m_callBackFunc = param2;
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.getAvatarData", this.m_userId);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Player _loc_2 =null ;
            SocialNetworkUser _loc_3 =null ;
            if (param1 !=null)
            {
                if (this.m_userId == Global.player.snUser.uid)
                {
                    _loc_2 = Global.getPlayer();
                }
                else if (this.m_userId)
                {
                    _loc_2 = Global.player.findFriendById(this.m_userId);
                }
                if (_loc_2 == null)
                {
                    _loc_3 = new SocialNetworkUser(parseFloat(this.m_userId));
                    _loc_3.firstName = "";
                    _loc_3.name = "";
                    _loc_3.picture = "";
                    if (param1.firstName)
                    {
                        _loc_3.firstName = param1.firstName;
                    }
                    if (param1.name)
                    {
                        _loc_3.name = param1.name;
                    }
                    if (param1.picture)
                    {
                        _loc_3.picture = param1.picture;
                    }
                    _loc_2 = new Player(_loc_3);
                }
                this.m_callBackFunc(_loc_2);
            }
            return;
        }//end

         protected void  onFault (int param1 ,String param2 )
        {
            UI.displayMessage(ZLoc.t("Main", "UnableToReadSign"));
            return;
        }//end

    }



