package Classes.announcers.announcerinteractions;

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

import Classes.announcers.*;
import Classes.announcers.ui.*;

//import flash.events.*;
//import flash.geom.*;

    public class AnnouncerInteractionSpeechBubble extends BaseAnnouncerInteraction implements IAnnouncerInteraction
    {
        private AnnouncerSpeechBubble m_currBubble ;
        private String m_defaultText ;
        private Vector<String> m_altTexts;
        private Point m_offset ;

        public  AnnouncerInteractionSpeechBubble (AnnouncerData param1 ,XML param2 )
        {
            XMLList _loc_3 =null ;
            XML _loc_4 =null ;
            super(param1);
            this.m_defaultText = param2.attribute("text").toString();
            if (param2.child("alt").length() > 0)
            {
                this.m_altTexts = new Vector<String>();
                _loc_3 = param2.child("alt");
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    this.m_altTexts.push(_loc_4.attribute("text").toString());
                }
            }
            this.m_offset = new Point();
            if (param2.attribute("offsetX").length() > 0)
            {
                this.m_offset.x = parseInt(param2.attribute("offsetX").toString());
            }
            if (param2.attribute("offsetY").length() > 0)
            {
                this.m_offset.y = parseInt(param2.attribute("offsetY").toString());
            }
            return;
        }//end

        private void  createBubble ()
        {
            this.destroyBubble();
            this.m_currBubble = new AnnouncerSpeechBubble();
            Object _loc_1 ={cityName Global.player.cityName };
            _loc_2 = ZLoc.t("Announcers",this.m_defaultText ,_loc_1 );
            if (this.m_altTexts)
            {
                if (Global.player.getSeenFlag(this.m_defaultText))
                {
                    _loc_2 = ZLoc.t("Announcers", this.m_altTexts.get(Math.floor(Math.random() * this.m_altTexts.length())), _loc_1);
                }
                else
                {
                    Global.player.setSeenFlag(this.m_defaultText);
                }
            }
            this.m_currBubble.setMessage(_loc_2);
            Global.ui.addChildAt(this.m_currBubble, 0);
            Global.stage.addEventListener(MouseEvent.CLICK, this.onStageClick, false, 0, true);
            Global.stage.addEventListener(Event.ENTER_FRAME, this.onStageEnterFrame, false, 0, true);
            return;
        }//end

        private void  destroyBubble ()
        {
            if (this.m_currBubble)
            {
                Global.stage.removeEventListener(Event.ENTER_FRAME, this.onStageEnterFrame);
                Global.stage.removeEventListener(MouseEvent.CLICK, this.onStageClick);
                if (this.m_currBubble.parent)
                {
                    this.m_currBubble.parent.removeChild(this.m_currBubble);
                }
                this.m_currBubble = null;
            }
            return;
        }//end

        public void  activate ()
        {
            if (this.m_currBubble)
            {
                this.destroyBubble();
            }
            else
            {
                this.createBubble();
            }
            return;
        }//end

        private void  onStageEnterFrame (Event event )
        {
            Point _loc_2 =null ;
            if (this.m_currBubble && m_announcerData.npc.displayObject)
            {
                _loc_2 = m_announcerData.npc.displayObject.localToGlobal(new Point(this.m_offset.x, this.m_offset.y));
                _loc_2 = Global.ui.globalToLocal(_loc_2);
                this.m_currBubble.x = Math.round(_loc_2.x);
                this.m_currBubble.y = Math.round(_loc_2.y);
            }
            return;
        }//end

        private void  onStageClick (MouseEvent event )
        {
            if (!m_announcerData.npc || !m_announcerData.npc.isHighlighted())
            {
                this.destroyBubble();
            }
            return;
        }//end

    }



