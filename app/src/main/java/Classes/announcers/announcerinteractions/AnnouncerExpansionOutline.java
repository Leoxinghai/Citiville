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

import Classes.*;
import Classes.announcers.*;
import Engine.Helpers.*;
//import flash.events.*;
//import flash.geom.*;

    public class AnnouncerExpansionOutline extends BaseAnnouncerInteraction implements IAnnouncerInteraction
    {
        private Prop m_outline ;
        private static  String EXPANSION_MARKER_BASE_ITEM_NAME ="prop_expansion_marker_base";

        public  AnnouncerExpansionOutline (AnnouncerData param1 ,XML param2 )
        {
            super(param1);
            return;
        }//end  

        private void  createOutline ()
        {
            Announcer _loc_1 =null ;
            Rectangle _loc_2 =null ;
            this.destroyOutline();
            if (m_announcerData && m_announcerData.npc)
            {
                _loc_1 = m_announcerData.npc;
                _loc_2 = Global.world.territory.getExpansionRect(new Vector2(_loc_1.getPosition().x, _loc_1.getPosition().y));
                this.m_outline = new Prop(EXPANSION_MARKER_BASE_ITEM_NAME);
                this.m_outline.setState(this.getAssetState());
                this.m_outline.setPosition(_loc_2.x, _loc_2.y);
                this.m_outline.setDirection(_loc_1.getDirection());
                this.m_outline.setOuter(Global.world);
                this.m_outline.attach();
            }
            Global.stage.addEventListener(MouseEvent.CLICK, this.onStageClick, false, 0, true);
            return;
        }//end  

        private String  getAssetState ()
        {
            String _loc_1 ="static";
            if (Global.world.isThemeEnabled("halloween_theme"))
            {
                _loc_1 = "static_purple";
            }
            return _loc_1;
        }//end  

        private void  destroyOutline ()
        {
            if (this.m_outline)
            {
                this.m_outline.detach();
                this.m_outline.cleanUp();
                this.m_outline = null;
                Global.stage.removeEventListener(MouseEvent.CLICK, this.onStageClick);
            }
            return;
        }//end  

        public void  activate ()
        {
            if (this.m_outline)
            {
                this.destroyOutline();
            }
            else
            {
                this.createOutline();
            }
            return;
        }//end  

        private void  onStageClick (MouseEvent event )
        {
            if (!m_announcerData.npc || !m_announcerData.npc.isHighlighted())
            {
                this.destroyOutline();
            }
            return;
        }//end  

    }



