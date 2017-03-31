package Modules.bandits.transactions;

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
import Engine.Transactions.*;
import Modules.bandits.*;

    public class TUpgradeCatchUp extends Transaction
    {
        private Municipal m_station ;
        protected Item m_oldItem ;
        protected Item m_newItem ;
        protected String m_groupId ;

        public  TUpgradeCatchUp (Municipal param1 )
        {
            String _loc_3 =null ;
            this.m_station = param1;
            this.m_oldItem = param1.getItem();
            this.m_groupId = Global.gameSettings().getHubGroupIdForItemName(param1.getItemName());
            _loc_2 = Math.max(PreyUtil.getHubLevel(this.m_groupId ),Global.world.citySim.preyManager.getHubUnlockLevel(this.m_groupId ));
            if (_loc_2 > 0)
            {
                _loc_3 = Global.gameSettings().getHubName(this.m_groupId, _loc_2);
                this.m_newItem = Global.gameSettings().getItemByName(_loc_3);
            }
            if (this.m_newItem != null)
            {
                this.m_station.setItem(this.m_newItem);
                this.m_station.setState(this.m_station.getState());
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("PreyService.upgradeCatchUp", this.m_groupId, this.m_station.getId());
            return;
        }//end

         protected void  onFault (int param1 ,String param2 )
        {
            super.onFault(param1, param2);
            this.m_station.setItem(this.m_oldItem);
            this.m_station.setState(this.m_station.getState());
            return;
        }//end

    }



