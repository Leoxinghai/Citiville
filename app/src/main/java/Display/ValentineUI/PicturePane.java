package Display.ValentineUI;

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
import Classes.orders.Valentines2011.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.event.*;

    public class PicturePane extends Sprite
    {
        protected Dictionary m_data ;
        protected Dictionary m_senders ;
        protected Dictionary m_recipients ;
        protected Dictionary m_objects ;
        protected Dictionary m_backgrounds ;
        protected Sprite m_imageSprite ;
        protected Sprite m_bgHolder ;
        protected Sprite m_senderHolder ;
        protected Sprite m_recipientHolder ;
        protected Sprite m_objectHolder ;
        protected int m_curSender =1;
        protected int m_curRecipient =1;
        protected int m_curObject =1;
        protected int m_curBackground =1;

        public  PicturePane (Dictionary param1 ,ValentineCard param2 )
        {
            this.m_senders = new Dictionary(true);
            this.m_recipients = new Dictionary(true);
            this.m_objects = new Dictionary(true);
            this.m_backgrounds = new Dictionary(true);
            this.m_imageSprite = new Sprite();
            this.m_bgHolder = new Sprite();
            this.m_senderHolder = new Sprite();
            this.m_recipientHolder = new Sprite();
            this.m_objectHolder = new Sprite();
            this.m_data = param1;
            if (param2)
            {
                this.m_curSender = param2.leftPart;
                this.m_curObject = param2.middlePart;
                this.m_curRecipient = param2.rightPart;
                StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDVIEWER, "view");
            }
            else
            {
                this.m_curSender = Math.ceil(Math.random() * this.m_data.get("senders").length());
                this.m_curObject = Math.ceil(Math.random() * this.m_data.get("objects").length());
                this.m_curRecipient = Math.ceil(Math.random() * this.m_data.get("recipients").length());
            }
            this.init();
            return;
        }//end

        public int  leftID ()
        {
            return this.m_curSender;
        }//end

        public int  centerID ()
        {
            return this.m_curObject;
        }//end

        public int  rightID ()
        {
            return this.m_curRecipient;
        }//end

        public void  changeCard (ValentineCard param1 )
        {
            this.m_curSender = param1.leftPart;
            this.m_curObject = param1.middlePart;
            this.m_curRecipient = param1.rightPart;
            if (this.m_recipientHolder.numChildren > 0)
            {
                this.m_recipientHolder.removeChildAt(0);
            }
            if (this.m_recipients.get(this.m_curRecipient))
            {
                this.m_recipientHolder.addChild(this.m_recipients.get(this.m_curRecipient));
            }
            if (this.m_senderHolder.numChildren > 0)
            {
                this.m_senderHolder.removeChildAt(0);
            }
            if (this.m_senders.get(this.m_curSender))
            {
                this.m_senderHolder.addChild(this.m_senders.get(this.m_curSender));
            }
            if (this.m_objectHolder.numChildren > 0)
            {
                this.m_objectHolder.removeChildAt(0);
            }
            if (this.m_objects.get(this.m_curObject))
            {
                this.m_objectHolder.addChild(this.m_objects.get(this.m_curObject));
            }
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        public void  swapRecipient (AWEvent event )
        {
            this.m_curRecipient++;
            if (this.m_curRecipient > this.m_recipients.get("length"))
            {
                this.m_curRecipient = 1;
            }
            if (this.m_recipients.get(this.m_curRecipient))
            {
                this.m_recipientHolder.removeChildAt(0);
                this.m_recipientHolder.addChild(this.m_recipients.get(this.m_curRecipient));
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "iconchange", "right");
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        public void  swapSender (AWEvent event )
        {
            this.m_curSender++;
            if (this.m_curSender > this.m_senders.get("length"))
            {
                this.m_curSender = 1;
            }
            if (this.m_senders.get(this.m_curSender))
            {
                this.m_senderHolder.removeChildAt(0);
                this.m_senderHolder.addChild(this.m_senders.get(this.m_curSender));
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "iconchange", "left");
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        public void  swapObject (AWEvent event )
        {
            this.m_curObject++;
            if (this.m_curObject > this.m_objects.get("length"))
            {
                this.m_curObject = 1;
            }
            if (this.m_objects.get(this.m_curObject))
            {
                this.m_objectHolder.removeChildAt(0);
                this.m_objectHolder.addChild(this.m_objects.get(this.m_curObject));
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "iconchange", "center");
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        public void  swapBackground (AWEvent event )
        {
            this.m_curBackground++;
            if (this.m_curBackground > this.m_backgrounds.get("length"))
            {
                this.m_curBackground = 1;
            }
            this.m_bgHolder.removeChildAt(0);
            this.m_bgHolder.addChild(this.m_backgrounds.get(this.m_curBackground));
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        protected TextField  makeLoadingText (String param1 )
        {
            TextField _loc_2 =new TextField ();
            _loc_2.text = param1;
            _loc_2.height = 0;
            _loc_2.selectable = false;
            _loc_2.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_2.antiAliasType = AntiAliasType.ADVANCED;
            _loc_2.gridFitType = GridFitType.PIXEL;
            _loc_2.autoSize = TextFieldAutoSize.CENTER;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.font = EmbeddedArt.defaultFontNameBold;
            _loc_3.color = EmbeddedArt.darkBlueTextColor;
            _loc_3.align = TextFormatAlign.CENTER;
            _loc_3.size = 16;
            _loc_2.setTextFormat(_loc_3);
            return _loc_2;
        }//end

        protected void  init ()
        {
            XML sendernode ;
            XML repnode ;
            XML objnode ;
            String sendurl ;
            int sendid ;
            Loader image_senders ;
            String repurl ;
            int repid ;
            Loader image_recipients ;
            String objurl ;
            int objid ;
            Loader image_obj ;
            this.addChild(this.m_imageSprite);
            this.m_imageSprite.addChild(this.m_bgHolder);
            this.m_bgHolder.addChild(new ValentineDialog.assetDict.get("valentineBG"));
            loadingText1 = this.makeLoadingText(ZLoc.t("Dialogs","FrameLoading"));
            loadingText2 = this.makeLoadingText(ZLoc.t("Dialogs","FrameLoading"));
            loadingText3 = this.makeLoadingText(ZLoc.t("Dialogs","FrameLoading"));
            this.m_senderHolder.addChild(loadingText1);
            loadingText1.x = 20;
            loadingText1.y = 140;
            this.m_objectHolder.addChild(loadingText2);
            loadingText2.x = 40;
            loadingText2.y = 140;
            this.m_recipientHolder.addChild(loadingText3);
            loadingText3.x = 100;
            loadingText3.y = 140;
            this.m_imageSprite.addChild(this.m_senderHolder);
            this.m_senderHolder.x = 10;
            this.m_senderHolder.y = -5;
            this.m_imageSprite.addChild(this.m_recipientHolder);
            this.m_recipientHolder.x = 213;
            this.m_recipientHolder.y = -5;
            this.m_imageSprite.addChild(this.m_objectHolder);
            this.m_objectHolder.x = 131;
            this.m_senders.put("length",  0);
            this.m_recipients.put("length",  0);
            this.m_objects.put("length",  0);
            this.m_backgrounds.put("length",  0);
            callback = function(param1Sprite,param2,param3,param4,param5,param6)
            {
                if (param6.target && param6.target.content)
                {
                    param4.put(param2,  param6.target.content);
                    _loc_7 = param4;
                    String _loc_8 ="length";
                    _loc_9 = param4.get("length") +1;
                    _loc_7.put(_loc_8,  _loc_9);
                    if (param2 == param5)
                    {
                        param1.removeChildAt(0);
                        param1.addChild(param6.target.content);
                        param3.dispatchEvent(new Event(MakerPanel.PREPARE, true));
                    }
                }
                return;
            }//end
            ;


            for(int i0 = 0; i0 < this.m_data.get("senders").size(); i0++)
            {
            	sendernode = this.m_data.get("senders").get(i0);


                sendurl = sendernode.@url;
                sendid = int(sendernode.@id);
                image_senders = LoadingManager.load(Global.getAssetURL(sendurl), Curry.curry(callback, this.m_senderHolder, sendid, this, this.m_senders, this.m_curSender));
            }


            for(int i0 = 0; i0 < this.m_data.get("recipients").size(); i0++)
            {
            	repnode = this.m_data.get("recipients").get(i0);


                repurl = repnode.@url;
                repid = int(repnode.@id);
                image_recipients = LoadingManager.load(Global.getAssetURL(repurl), Curry.curry(callback, this.m_recipientHolder, repid, this, this.m_recipients, this.m_curRecipient));
            }


            for(int i0 = 0; i0 < this.m_data.get("objects").size(); i0++)
            {
            	objnode = this.m_data.get("objects").get(i0);


                objurl = objnode.@url;
                objid = int(objnode.@id);
                image_obj = LoadingManager.load(Global.getAssetURL(objurl), Curry.curry(callback, this.m_objectHolder, objid, this, this.m_objects, this.m_curObject));
            }
            return;
        }//end

    }



