package Display;

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
import Display.DialogUI.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class MissionPopup extends SWFDialog
    {
        private MovieClip m_window ;
        protected Array m_missionObjs ;
        protected Mission m_mission ;
        private String m_greeting ;
        private String m_greetingText ;
        private Player m_missionHost ;
        private GenericButton m_accept ;
        private String m_missionType ;
        private Function m_callback ;
        private static  double AVATAR_SCALE =1;
        public static  String COUNTER ="missionPopup";

        public  MissionPopup (Mission param1 ,String param2)
        {
            this.m_missionObjs = new Array();
            SoundManager.addSound(param1.missionType, param1.soundURL, false);
            m_dialogAsset = "assets/dialogs/FV_FriendMission.swf";
            if (!param1.hostPlayer)
            {
                this.m_missionHost = Global.player.findFriendById(param1.missionHostId);
            }
            else
            {
                this.m_missionHost = param1.hostPlayer;
            }
            if (!this.m_missionHost)
            {
                throw new Error("Host of mission must be a friend of the player");
            }
            this.m_mission = param1;
            this.m_missionType = param2;
            return;
        }//end

        private void  addFacebookPic ()
        {
            DisplayObject _loc_1 =null ;
            String _loc_2 =null ;
            if (this.m_missionHost && this.m_missionHost.snUser)
            {
                _loc_2 = this.m_missionHost.snUser.picture;
                if (_loc_2)
                {
                    _loc_1 = LoadingManager.load(_loc_2);
                }
            }
            if (_loc_1 == null)
            {
                _loc_1 = new EmbeddedArt.hud_no_profile_pic();
                _loc_1.width = 48;
                _loc_1.height = 48;
            }
            this.m_window.helpfriend_mc.fbImage_mc.addChild(_loc_1);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            Array _loc_1 =new Array();
            this.m_window =(MovieClip) m_loader.content;
            this.m_accept = new GenericButton(this.m_window.helpfriend_mc.accept_bt, this.onAccept);
            this.m_accept.text = ZLoc.t("Dialogs", "Accept");
            this.m_window.helpfriend_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCancel);
            this.setGreeting(this.m_greeting);
            this.addFacebookPic();
            this.count("view_initial");
            addChild(this.m_window);
            if (Global.isVisiting())
            {
                StatsManager.milestone("social_miss_md");
            }
            return;
        }//end

        private void  count (String param1 ,String param2 )
        {
            _loc_3 = MissionPopup.COUNTER;
            if (this.m_missionType != "")
            {
                _loc_3 = _loc_3 + ("_" + this.m_missionType);
            }
            if (param2 != null)
            {
                StatsManager.count(_loc_3, this.m_mission.missionType, param1, param2);
            }
            else
            {
                StatsManager.count(_loc_3, this.m_mission.missionType, param1);
            }
            return;
        }//end

        protected void  onAccept (MouseEvent event )
        {
            this.m_callback(true);
            this.count("click_accept");
            return;
        }//end

        protected void  onCancel (MouseEvent event )
        {
            this.m_callback(false);
            this.count("click_cancel");
            return;
        }//end

        private void  setGreeting (String param1 )
        {
            this.m_greeting = param1;
            if (this.m_window != null && param1)
            {
                this.m_window.helpfriend_mc.greeting_tf.text = param1;
                this.m_window.helpfriend_mc.playerName_tf.text = this.m_missionHost.firstName;
                Global.missionHostFirstName = this.m_missionHost.firstName;
            }
            return;
        }//end

        public void  offerIdleMission ()
        {
            _loc_1 = ZLoc.t("Main","Mission_Idle_Prompt",{name Global.player.firstName ,fname.m_missionHost.firstName });
            this.setGreeting(_loc_1);
            this.m_callback = this.onIdleMission;
            return;
        }//end

        protected void  onIdleMission (boolean param1 )
        {
            if (param1 !=null)
            {
                this.close();
                UI.visitNeighbor(this.m_missionHost.snUser.uid);
            }
            else
            {
                this.close();
            }
            return;
        }//end

        public void  startMission ()
        {
            _loc_1 = ZLoc.t("Missions",this.m_mission.missionType +"_description",{name ZLoc.tn(Global.player.firstName ),fname.m_missionHost.firstName });
            this.setGreeting(_loc_1);
            this.m_callback = this.onConfirmMission;
            this.createMissionObjects();
            return;
        }//end

        private void  onConfirmMission (boolean param1 )
        {
            if (param1 !=null)
            {
                this.completeMission();
            }
            else
            {
                this.close();
            }
            return;
        }//end

        private void  completeMission ()
        {
            String _loc_3 =null ;
            Player _loc_4 =null ;
            GenericDialog _loc_5 =null ;
            SoundManager.playSound(this.m_mission.missionType);
            GameTransactionManager.addTransaction(new TCompleteMission(this.m_mission.missionType), true);
            _loc_1 =Global.gameSettings().getInt("missionXp");
            _loc_2 =Global.gameSettings().getInt("missionGold");
            if (this.m_mission.hitLimit)
            {
                _loc_1 = Global.gameSettings().getInt("missionXpLimit");
                _loc_2 = Global.gameSettings().getInt("missionGoldLimit");
            }
            Global.player.xp = Global.player.xp + _loc_1;
            Global.player.gold = Global.player.gold + _loc_2;
            this.removeMissionObjects();
            if (GlobalEngine.getFlashVar("giftMission") == null)
            {
                _loc_3 = ZLoc.t("Main", "Mission_Thank_You");
                this.setGreeting(_loc_3);
                this.m_callback = this.onOKMessage;
                this.hideCancelButton();
            }
            else
            {
                this.close();
                _loc_4 = this.m_mission.hostPlayer;
                _loc_3 = ZLoc.t("Main", "Mission_Thank_You_Gift", {name:ZLoc.tn(_loc_4.firstName)});
                _loc_5 = new GenericDialog(_loc_3, "", GenericDialogView.TYPE_SENDGIFTS, this.playerInputHandler);
                UI.displayPopup(_loc_5);
            }
            if (Global.isVisiting())
            {
                StatsManager.milestone("social_miss_ty_md");
            }
            return;
        }//end

        protected void  playerInputHandler (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.ACCEPT)
            {
                GlobalEngine.socialNetwork.redirect("gifts.php?ref=missionDialogDone");
            }
            return;
        }//end

        private void  hideCancelButton ()
        {
            this.m_window.helpfriend_mc.close_bt.visible = false;
            return;
        }//end

        private void  onOKMessage (boolean param1 )
        {
            this.close();
            return;
        }//end

         public void  close ()
        {
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            super.close();
            return;
        }//end

        private void  createMissionObjects ()
        {
            MissionObject _loc_3 =null ;
            double _loc_4 =0;
            double _loc_5 =0;
            _loc_1 = this.m_mission.numObjects ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                _loc_3 = new MissionObject(this.m_mission);
                _loc_4 = int(Utilities.randBetween(4, Global.world.getGridWidth() - 4));
                _loc_5 = int(Utilities.randBetween(4, Global.world.getGridHeight() - 4));
                _loc_3.setPosition(_loc_4, _loc_5);
                _loc_3.setOuter(Global.world);
                _loc_3.attach();
                this.m_missionObjs.push(_loc_3);
                _loc_2++;
            }
            return;
        }//end

        private void  removeMissionObjects ()
        {
            _loc_1 = this.m_missionObjs ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_1.get(_loc_2).detach();
                _loc_2++;
            }
            this.m_missionObjs = new Array();
            return;
        }//end

        public Array  getMissionObjects ()
        {
            return this.m_missionObjs;
        }//end

         public Point  getDialogOffset ()
        {
            return new Point((-Global.ui.screenWidth) / 2 + this.width / 2 + 40, (-Global.ui.screenHeight) / 2 + this.height / 2 + 40);
        }//end

    }



