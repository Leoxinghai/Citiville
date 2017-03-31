package Modules.guide.actions;

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

import Display.*;
import Display.DialogUI.*;
import Modules.guide.ui.*;
//import flash.events.*;

    public class GADisplayDialog extends GuideAction
    {
        protected String m_buttonName ="";
        protected GenericDialog m_dialog =null ;
        protected boolean m_modal =false ;
        protected int m_position =0;
        protected double m_offsetX =0;
        protected double m_offsetY =0;
        protected double m_avatarOffsetX =0;
        protected double m_avatarOffsetY =0;
        protected Function m_callback =null ;
        protected String m_message ="test message";
        protected String m_title ="test title";
        protected String m_textPackage ="Tutorials";
        protected String m_titleKey ="";
        protected String m_hint ="test hint";
        protected String m_type ;
        protected String m_avatar ;
        protected String m_avatarLocation ;
        protected double m_titleFontSize =20;
        protected double m_titleSmallCapsFontSize =30;
        public static  String TYPE_WELCOME ="welcome";
        public static  String TYPE_GUIDE ="guide";
        public static  String TYPE_CHARACTER ="character";
        public static  String TYPE_INVITE_FRIENDS ="inviteFriends";

        public  GADisplayDialog ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"dialog");
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = _loc_2.@text;
            _loc_4 = _loc_2.@type;
            _loc_5 = _loc_2.@button;
            _loc_6 = _loc_2.@callback;
            _loc_7 = _loc_2.@modal;
            _loc_8 = _loc_2.@position;
            _loc_9 = _loc_2.@offsetX;
            _loc_10 = _loc_2.@offsetY;
            _loc_11 = _loc_2.@avatarOffsetX;
            _loc_12 = _loc_2.@avatarOffsetY;
            this.m_titleFontSize = String(_loc_2.@titleFontSize).length > 0 ? (_loc_2.@titleFontSize) : (20);
            this.m_titleSmallCapsFontSize = String(_loc_2.@titleSmallCapsFontSize).length > 0 ? (_loc_2.@titleSmallCapsFontSize) : (30);
            this.m_textPackage = _loc_2.attribute("textPackage").length() > 0 ? (_loc_2.@textPackage) : ("Tutorials");
            this.m_avatar = _loc_2.@avatar;
            this.m_avatarLocation = _loc_2.attribute("avatarLocation").length() > 0 ? (_loc_2.@avatarLocation) : ("right");
            this.m_type = _loc_4;
            this.m_message = ZLoc.t(this.m_textPackage, _loc_3, {name:Global.player.firstName});
            this.m_title = ZLoc.t(this.m_textPackage, _loc_3 + "_title");
            this.m_titleKey = _loc_3;
            if (_loc_5.length > 0)
            {
                this.m_buttonName = _loc_5;
            }
            if (_loc_6.length > 0)
            {
            }
            if (_loc_7.length > 0 && _loc_7.toLowerCase() == "true")
            {
                this.m_modal = true;
            }
            else
            {
                this.m_modal = false;
            }
            switch(_loc_8)
            {
                case "center":
                {
                    break;
                }
                case "ne":
                {
                    break;
                }
                case "sw":
                {
                    break;
                }
                case "se":
                {
                    break;
                }
                case "abs":
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_9.length > 0)
            {
                this.m_offsetX = Number(_loc_9);
            }
            if (_loc_10.length > 0)
            {
                this.m_offsetY = Number(_loc_10);
            }
            if (_loc_11.length > 0)
            {
                this.m_avatarOffsetX = Number(_loc_11);
            }
            if (_loc_12.length > 0)
            {
                this.m_avatarOffsetY = Number(_loc_12);
            }
            return true;
        }//end

         public void  enter ()
        {
            Object _loc_1 =null ;
            switch(this.m_type)
            {
                case TYPE_WELCOME:
                {
                    this.m_dialog = new WelcomeDialog(this.m_message, this.m_title, 0, this.m_position, null, true, this.m_avatar, this.m_hint);
                    this.m_dialog.addEventListener(Event.CLOSE, this.onDialogClose, false, 0, true);
                    break;
                }
                case TYPE_INVITE_FRIENDS:
                {
                    this.m_dialog = new InviteFriendsGuideDialog(this.m_message, this.m_title, 0, null, true, this.m_avatar, this.m_hint);
                    this.m_dialog.addEventListener(Event.CLOSE, this.onDialogClose, false, 0, true);
                    break;
                }
                case TYPE_CHARACTER:
                {
                    _loc_1 = {titleFontSize:this.m_titleFontSize, titleSmallCapsFontSize:this.m_titleSmallCapsFontSize};
                    this.m_dialog = new CharacterDialog(this.m_message, this.m_titleKey, 0, null, null, true, this.m_avatar, this.m_buttonName, _loc_1);
                    this.m_dialog.addEventListener(Event.CLOSE, this.onDialogClose, false, 0, true);
                    break;
                }
                case TYPE_GUIDE:
                {
                }
                default:
                {
                    this.m_dialog = new GuideDialog(this.m_message, this.m_title, this.m_buttonName, this.m_modal, this.m_position, this.m_offsetX, this.m_offsetY, this.m_avatar, this.m_avatarOffsetX, this.m_avatarOffsetY, this.m_avatarLocation);
                    removeState(this);
                    break;
                    break;
                }
            }
            UI.displayPopup(this.m_dialog);
            m_guide.trackDialog(this.m_dialog);
            super.enter();
            return;
        }//end

        private void  onDialogClose (Event event )
        {
            this.m_dialog.removeEventListener(Event.CLOSE, this.onDialogClose);
            super.removeState(this);
            return;
        }//end

         protected boolean  allAssetsLoaded ()
        {
            return this.m_dialog.hasLoaded();
        }//end

    }



