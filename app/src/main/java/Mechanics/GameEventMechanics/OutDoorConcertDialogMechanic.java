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

import Classes.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.stats.types.*;
import Modules.sunset.*;
import com.zynga.skelly.util.*;
//import flash.display.*;

    public class OutDoorConcertDialogMechanic extends DialogGenerationMechanic
    {

        public  OutDoorConcertDialogMechanic ()
        {
            return;
        }//end

        public Object  getDefault_mData ()
        {
            Array _loc_2 =null ;
            Object _loc_1 =null ;
            if (this.getOwner() instanceof Attraction && (this.getOwner() as Attraction).isOpen())
            {
                _loc_1 = {statsTrackingName:"enrique_tourinfo", message:ZLoc.t("Dialogs", "Enrique_tourInfo"), background:{url:"assets/dialogs/enrique/enrique_dialog_bg.png", fixedWidth:true, fixedHeight:true}, preloadAssets:.get({name:"customTitleImage", url:"assets/dialogs/enrique/enrique_logo.png"}, {name:"speechBubble", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_bubble"}, {name:"speechTail", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_tail"}, {name:"timerExclamation", url:"assets/dialogs/timer_exclamation_burst.png"}), buttons:.get({label:ZLoc.t("Dialogs", "Enrique_watchVideo"), callback:Curry.curry(this.showVideo)}, {label:ZLoc.t("Dialogs", "Enrique_visitFanPage"), isClose:false, callback:Curry.curry(this.doFanPageRedirect)}), viewClass:CharacterTalkDialogView, sunsetTheme:m_config.params.get("sunsetTheme"), timerLabel:"BeforeEnriqueLeaves_timer", timerDaysLabel:"BeforeEnriqueLeavesDays_timer"};
                if (this.isIpBlocked())
                {
                    _loc_1.put("buttons",  .get({label:ZLoc.t("Dialogs", "Enrique_watchVideo"), isClose:false, callback:Curry.curry(this.doBlockedVideoPopup)}));
                }
            }
            else
            {
                _loc_2 = new Array();
                _loc_2.push("skipDialog");
                _loc_1 = {statsTrackingName:"enrique_tourinfo", message:ZLoc.t("Dialogs", "Enrique_tourInfo", {concertLink:"http://bit.ly/jhscDY", itunesLink:"http://itunes.apple.com/WebObjects/MZStore.woa/wa/viewArtistSeeAllTracks?ids=90895"}), background:{url:"assets/dialogs/enrique/enrique_dialog_bg.png", fixedWidth:true, fixedHeight:true}, preloadAssets:[{name:"customTitleImage", url:"assets/dialogs/enrique/enrique_logo.png"}, {name:"speechBubble", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_bubble"}, {name:"speechTail", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_tail"}, {name:"timerExclamation", url:"assets/dialogs/timer_exclamation_burst.png"}], buttons:[this.getSecondaryButton(), {label:ZLoc.t("Dialogs", "Enrique_startConcert"), callback:Curry.curry(this.startConcert, _loc_2)}], viewClass:CharacterTalkDialogView, sunsetTheme:m_config.params["sunsetTheme"], timerLabel:"BeforeEnriqueLeaves_timer", timerDaysLabel:"BeforeEnriqueLeavesDays_timer"};
            }
            return _loc_1;
        }//end

        public Object  getSecondaryButton ()
        {
            if (Global.player.getSeenFlag(m_config.params.get("sunsetTheme") + "_flag"))
            {
                return {label:ZLoc.t("Dialogs", "Enrique_watchVideo"), callback:Curry.curry(this.showVideo)};
            }
            return {label:ZLoc.t("Dialogs", "Enrique_visitFanPage"), isClose:false, callback:Curry.curry(this.doFanPageRedirect)};
        }//end

        public void  doBlockedVideoPopup ()
        {
            GlobalEngine.socialNetwork.redirect("http://www.myvideo.de/watch/8286257/Enrique_Iglesias_featuring_Pitbull_I_Like_How_It_Feels", null, "_blank", false);
            return;
        }//end

        public void  doFanPageRedirect ()
        {
            GlobalEngine.socialNetwork.redirect("http://www.facebook.com/Enrique", null, "_blank", false);
            StatsManager.sample(1, StatsCounterType.DIALOG, "enrique_fan_click");
            return;
        }//end

        public void  startConcert (Array param1 )
        {
            Object _loc_2 =null ;
            if (!Global.player.getSeenFlag(m_config.params.get("sunsetTheme") + "_flag"))
            {
                _loc_2 = null;
                this.showVideo();
                Global.player.setSeenFlag(m_config.params.get("sunsetTheme") + "_flag");
            }
            MechanicManager.getInstance().handleAction(this.getOwner() as IMechanicUser, "GMPlay", param1);
            return;
        }//end

        public void  showVideo ()
        {
            if (this.isIpBlocked())
            {
                this.doBlockedVideoPopup();
                return;
            }
            Object _loc_1 =null ;
            _loc_1 = {statsTrackingName:"enrique_video", videoId:"GfN18VaLEm4", message:ZLoc.t("Dialogs", "Enrique_seeFullVideo"), background:{url:"assets/dialogs/enrique/enrique_dialog_bg.png", fixedWidth:true, fixedHeight:true}, preloadAssets:.get({name:"customTitleImage", url:"assets/dialogs/enrique/enrique_logo.png"}, {name:"speechBubble", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_bubble"}, {name:"speechTail", url:DelayedAssetLoader.NEW_QUEST_ASSETS, className:"quest_speech_tail"}, {name:"timerExclamation", url:"assets/dialogs/timer_exclamation_burst.png"}), buttons:.get({label:ZLoc.t("Dialogs", "Enrique_visitFanPage"), isClose:false, callback:Curry.curry(this.doFanPageRedirect)}), viewClass:CharacterYoutubeDialogView, sunsetTheme:"enrique", timerLabel:"BeforeEnriqueLeaves_timer", timerDaysLabel:"BeforeEnriqueLeavesDays_timer"};
            BaseDialog _loc_2 =new BaseDialog(_loc_1 );
            UI.displayPopup(_loc_2);
            return;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            if (param2 && param2.get(0) == "skipDialog")
            {
                return new MechanicActionResult(false, true, false);
            }
            this.trackDialog();
            return super.executeOverrideForGameEvent(param1, param2);
        }//end

        public boolean  isIpBlocked ()
        {
            Array _loc_2 =null ;
            _loc_1 =Global.player.getGeoData("country_code");
            if (m_config.params.hasOwnProperty("lockedGlobalIps") && _loc_1 != "")
            {
                _loc_2 = m_config.params.get("lockedGlobalIps").toString().split(",");
                if (_loc_2.indexOf(_loc_1) != -1)
                {
                    return true;
                }
            }
            return false;
        }//end

         public boolean  canPopDialog ()
        {
            int _loc_2 =0;
            Array _loc_3 =null ;
            Sunset _loc_4 =null ;
            Object _loc_5 =null ;
            if (m_config.params.hasOwnProperty("experiment") && m_config.params.hasOwnProperty("variants"))
            {
                _loc_2 = Global.experimentManager.getVariant(m_config.params.get("experiment").toString());
                _loc_3 = m_config.params.get("variants").toString().split(",");
                if (_loc_3.indexOf(_loc_2.toString()) == -1)
                {
                    return false;
                }
            }
            boolean _loc_1 =true ;
            if (m_config.params.get("sunsetTheme"))
            {
                _loc_4 = Global.sunsetManager.getSunsetByThemeName(m_config.params.get("sunsetTheme"));
                _loc_1 = _loc_4.isInSunsetInterval();
                if (!_loc_1)
                {
                    return false;
                }
            }
            if (this.getOwner() instanceof Attraction)
            {
                _loc_5 = m_owner.getDataForMechanic("harvestState");
                if ((m_owner.getDataForMechanic("sleepTS") <= 0 || m_owner.getDataForMechanic("wakeTS") <= 0) && _loc_5 == null)
                {
                    return true;
                }
                if (this.getOwner() instanceof Attraction && (this.getOwner() as Attraction).isOpen())
                {
                    return true;
                }
                return false;
            }
            return true;
        }//end

         public DisplayObject  instantiateDialog ()
        {
            BaseDialog _loc_1 =new BaseDialog(this.getDefault_mData ());
            return _loc_1;
        }//end

    }



