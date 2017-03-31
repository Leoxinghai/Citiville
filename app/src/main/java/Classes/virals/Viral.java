package Classes.virals;

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

import validation.*;
    public class Viral
    {
        private String m_viralType ;
        private double m_originTimestamp ;
        private int m_timesPosted ;
        private Object m_recipients ;

        public  Viral (String param1 ,int param2 ,double param3 ,Object param4 =null )
        {
            this.m_viralType = param1;
            this.m_originTimestamp = param3;
            this.m_timesPosted = param2;
            if (!param4)
            {
                this.m_recipients = {};
            }
            else
            {
                this.m_recipients = param4;
            }
            return;
        }//end

        public void  addPost (String param1 )
        {
            this.m_timesPosted++;
            _loc_2 =Global.gameSettings().getViralXMLByName(this.m_viralType );
            if (_loc_2.limitOnePerRecipient && String(_loc_2.limitOnePerRecipient) == "true")
            {
                this.m_recipients.put("i" + param1,  new Date().getTime());
            }
            return;
        }//end

        public int  timesPosted ()
        {
            return this.m_timesPosted;
        }//end

        public String  getType ()
        {
            return this.m_viralType;
        }//end

        public boolean  checkValidator ()
        {
            GenericValidationScript _loc_3 =null ;
            _loc_1 =Global.gameSettings().getViralXMLByName(this.m_viralType );
            _loc_2 = _loc_1? (_loc_1.@validate) : (null);
            if (_loc_2 && _loc_2.length())
            {
                _loc_3 = Global.validationManager.getValidator(_loc_2);
                if (_loc_3)
                {
                    return _loc_3.validate();
                }
            }
            return true;
        }//end

        public int  timeToUnlock ()
        {
            _loc_1 = GlobalEngine.serverTime /1000;
            _loc_2 = _loc_1-this.m_originTimestamp ;
            _loc_3 =Global.gameSettings().getViralXMLByName(this.m_viralType );
            _loc_4 =(double)(_loc_3.timeTillReset *3600)-_loc_2 ;
            return Math.max(0, _loc_4);
        }//end

        public boolean  hasReset ()
        {
            _loc_1 = GlobalEngine.serverTime /1000;
            _loc_2 = _loc_1-this.m_originTimestamp ;
            _loc_3 =Global.gameSettings().getViralXMLByName(this.m_viralType );
            return _loc_2 >= Number(_loc_3.timeTillReset * 3600);
        }//end

        public boolean  hasHitPostLimit (String param1 )
        {
            _loc_2 =Global.gameSettings().getViralXMLByName(this.m_viralType );
            if (_loc_2.limitOnePerRecipient && String(_loc_2.limitOnePerRecipient) == "true")
            {
                if (this.m_recipients.get("i" + param1))
                {
                    return true;
                }
            }
            if (_loc_2.maxPosts.length() > 0)
            {
                return this.m_timesPosted >= uint(_loc_2.maxPosts);
            }
            return false;
        }//end

        private static boolean  checkIfRewardIsPresent (XMLList param1 ,String param2 )
        {
            XML _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < param1.reward.length())
            {

                _loc_4 = param1.reward.get(_loc_3);
                if (_loc_4.@type == param2)
                {
                    return true;
                }
                _loc_3++;
            }
            return false;
        }//end

        public static String  getViralHelperRewardText (String param1 )
        {
            _loc_2 =Global.gameSettings().getViralXMLByName(param1 );
            if (_loc_2.reward == null)
            {
                return ZLoc.t("Dialogs", "Share");
            }
            if (_loc_2.reward.helperRewards == null)
            {
                return ZLoc.t("Dialogs", "Share");
            }
            if (checkIfRewardIsPresent(_loc_2.reward.helperRewards, "reward_cash"))
            {
                return ZLoc.t("Dialogs", "ShareCash");
            }
            if (checkIfRewardIsPresent(_loc_2.reward.helperRewards, "reward_energy"))
            {
                return ZLoc.t("Dialogs", "ShareEnergy");
            }
            if (checkIfRewardIsPresent(_loc_2.reward.helperRewards, "reward_xp"))
            {
                return ZLoc.t("Dialogs", "ShareExperience");
            }
            if (checkIfRewardIsPresent(_loc_2.reward.helperRewards, "reward_coins"))
            {
                return ZLoc.t("Dialogs", "ShareCoins");
            }
            if (checkIfRewardIsPresent(_loc_2.reward.helperRewards, "reward_goods"))
            {
                return ZLoc.t("Dialogs", "ShareGoods");
            }
            return ZLoc.t("Dialogs", "Share");
        }//end

    }



