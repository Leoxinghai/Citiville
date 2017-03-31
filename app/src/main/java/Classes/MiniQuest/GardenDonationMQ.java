package Classes.MiniQuest;

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
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;

    public class GardenDonationMQ extends MiniQuest
    {
        public static  String QUEST_NAME ="gardenDonationMQ";

        public  GardenDonationMQ ()
        {
            super(QUEST_NAME);
            return;
        }//end

        public boolean  isQuestNeeded ()
        {
            if (this.getFirstGardenWithDonations() != null)
            {
                return true;
            }
            return false;
        }//end

         protected void  onIconClicked (MouseEvent event )
        {
            IGameMechanic _loc_3 =null ;
            super.onIconClicked(event);
            _loc_2 = this.getFirstGardenWithDonations();
            if (_loc_2)
            {
                _loc_3 = MechanicManager.getInstance().getMechanicInstance(_loc_2, "rewardsDialog", MechanicManager.PLAY);
                if (_loc_3 && _loc_3 instanceof FriendRewardsDialogMechanic)
                {
                    UI.displayPopup(((FriendRewardsDialogMechanic)_loc_3).instantiateDialog());
                }
            }
            m_recurrenceTime = 0;
            return;
        }//end

         protected void  initQuest ()
        {
            if (m_questHudIcon)
            {
                super.initQuest();
            }
            return;
        }//end

        private Garden  getFirstGardenWithDonations ()
        {
            Garden _loc_2 =null ;
            Object _loc_3 =null ;
            double _loc_4 =0;
            String _loc_5 =null ;
            _loc_1 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.GARDEN));
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                _loc_3 = _loc_2.getDataForMechanic("giftSenders");
                if (_loc_3)
                {
                    _loc_4 = 0;
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    		_loc_5 = _loc_3.get(i0);

                        _loc_4 = _loc_4 + 1;
                    }
                    if (_loc_4 > 0)
                    {
                        return _loc_2;
                    }
                }
            }
            return null;
        }//end

    }



