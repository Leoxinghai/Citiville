package Modules.request;

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
import Classes.virals.*;
import Modules.matchmaking.*;

    public class GiftViralRequest extends ViralRequest
    {
        protected String m_giftName ;
        protected String m_messageKey ;

        public  GiftViralRequest (Array param1 ,Object param2 ,Function param3 =null ,Object param4 =null )
        {
            super(RequestType.GIFT_REQUEST, param1, param2, param3, param4);
            return;
        }//end

         protected void  onSendSuccessful (Array param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < m_recipientIds.size(); i0++)
            {
            		_loc_2 = m_recipientIds.get(i0);

                Global.player.addSentGift(_loc_2);
                if (MatchmakingManager.instance.isBuildingBuddy(_loc_2))
                {
                    MatchmakingManager.instance.addSentGift(_loc_2);
                }
            }
            return;
        }//end

         public boolean  canSend ()
        {
            _loc_1 = super.canSend();
            _loc_2 =Global.gameSettings().getItemByName(this.m_giftName );
            if (!_loc_2)
            {
                _loc_1 = false;
            }
            if (!Global.player.canSendGift(_loc_2))
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

         protected Array  getIneligibleIds ()
        {
            String _loc_2 =null ;
            _loc_1 = super.getIneligibleIds();
            for(int i0 = 0; i0 < m_recipientIds.size(); i0++)
            {
            		_loc_2 = m_recipientIds.get(i0);

                if (!Global.player.isEligibleGiftRecipient(_loc_2))
                {
                    _loc_1.push(_loc_2);
                }
                if (MatchmakingManager.instance.isBuildingBuddy(_loc_2) && !MatchmakingManager.instance.canSendGift(_loc_2))
                {
                    _loc_1.push(_loc_2);
                }
            }
            return _loc_1;
        }//end

         public Object  getData ()
        {
            _loc_1 = super.getData();
            _loc_1.item = this.m_giftName;
            _loc_1.gift = this.m_giftName;
            _loc_1.message = this.m_messageKey;
            _loc_1.reverse = false;
            return _loc_1;
        }//end

         public void  setData (Object param1 )
        {
            super.setData(param1);
            this.m_giftName = param1.item ? (param1.item) : ("");
            this.m_messageKey = param1.message ? (param1.message) : ("");
            return;
        }//end

    }



