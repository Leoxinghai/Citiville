package Mechanics.GameEventMechanics;

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
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.display.*;
//import flash.utils.*;

    public class FriendRewardsDialogMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {

        public  FriendRewardsDialogMechanic ()
        {
            return;
        }//end

         public boolean  canPopDialog ()
        {
            _loc_1 = super.getDataForDialog();
            if (_loc_1 && GameUtil.countObjectLength(_loc_1) > 0)
            {
                return super.canPopDialog();
            }
            return false;
        }//end

         public DisplayObject  instantiateDialog ()
        {
            DisplayObject _loc_3 =null ;
            _loc_1 = getDefinitionByName(m_config.params.get( "dialogToPop") )as Class ;
            _loc_2 = m_config.params.get( "localeKey") ;
            if (_loc_2 == null)
            {
                throw new Error("localeKey attribute not specified for " + m_config.params.get("dialogToPop") + "!");
            }
            _loc_4 = getDataForDialog();
            if (getDataForDialog())
            {
                _loc_3 = new _loc_1(m_owner, _loc_2, _loc_4, this.dialogCloseCallback);
            }
            return _loc_3;
        }//end

        protected void  dialogCloseCallback (GenericPopupEvent event )
        {
            _loc_2 = m_config.params.get( "dataSourceType") ;
            _loc_3 = (IDictionaryDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,_loc_2,MechanicManager.ALL)
            if (_loc_3)
            {
                _loc_3.clear();
            }
            _loc_4 = m_config.params.get( "miniQuest") ;
            if (m_config.params.get("miniQuest"))
            {
                Global.world.citySim.miniQuestManager.endMiniQuest(_loc_4);
            }
            return;
        }//end

    }



