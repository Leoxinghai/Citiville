package Display.FactoryUI;

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
import Engine.*;
import Engine.Helpers.*;
//import flash.events.*;
//import flash.geom.*;

    public class NPCSlidePick extends SlidePick implements IToolTipTarget
    {
        protected  Point PICK_OFFSET =new Point (-35,-110);
        protected Vector3 m_customTilePositionOffset ;
        protected Vector2 m_toolTipOffset ;
        protected NPC m_npc ;
        protected String m_hashedPictureURL ;
        protected Vector3 m_position ;
        protected boolean m_showPick =true ;
        protected Vector3 m_tooltipPosition ;
        protected Function m_onClickCallback ;
        protected Class m_pickBackGround ;
        protected Class m_innerBackground ;
        protected boolean m_listeningToRender =false ;
        protected boolean m_shouldListenToRender =false ;

        public  NPCSlidePick (NPC param1 ,String param2 ,Class param3 =null ,Class param4 =null )
        {
            this.m_customTilePositionOffset = new Vector3(0, 0, 0);
            this.m_toolTipOffset = new Vector2(0, -40);
            this.m_tooltipPosition = new Vector3();
            this.m_npc = param1;
            this.m_hashedPictureURL = param2;
            this.m_pickBackGround = param3 ? (param3) : (EmbeddedArt.deliveryPick);
            this.m_innerBackground = param4 ? (param4) : (EmbeddedArt.deliveryInfoField);
            this.owner = param1;
            if (this.m_hashedPictureURL == null || this.m_hashedPictureURL.length == 0)
            {
                this.m_hashedPictureURL = Global.getAssetURL("assets/dialogs/citysam_neighbor_card.jpg");
            }
            this.setupVisibilityCallbacks();
            super(this.m_hashedPictureURL, false, false);
            return;
        }//end

        protected void  setupVisibilityCallbacks ()
        {
            if (this.m_npc)
            {
                this .m_npc .enterMapResourceCallback =void  (MapResource param1 )
            {
                hideNPCPick();
                return;
            }//end
            ;
                this .m_npc .exitMapResourceCallback =void  (MapResource param1 )
            {
                showNPCPick();
                return;
            }//end
            ;
            }
            return;
        }//end

        public void  owner (NPC param1 )
        {
            this.m_npc = param1;
            if (this.m_npc)
            {
                this.m_npc.slidePick = this;
            }
            return;
        }//end

        public NPC  owner ()
        {
            return this.m_npc;
        }//end

        public Function  clickCallback ()
        {
            return this.m_onClickCallback;
        }//end

        public void  clickCallback (Function param1 )
        {
            this.m_onClickCallback = param1;
            return;
        }//end

        public Vector3  customTilePositionOffset ()
        {
            return this.m_customTilePositionOffset;
        }//end

        public void  customTilePositionOffset (Vector3 param1 )
        {
            this.m_customTilePositionOffset = param1;
            return;
        }//end

         protected Class  getPickBackground ()
        {
            return this.m_pickBackGround;
        }//end

         protected Class  getInnerBackground ()
        {
            return this.m_innerBackground;
        }//end

        public void  showNPCPick ()
        {
            if (this.owner && parent == null)
            {
                this.owner.showSlidePick();
                this.m_showPick = true;
            }
            return;
        }//end

        public void  hideNPCPick (int param1 =1)
        {
            if (this.owner && parent)
            {
                this.owner.hideSlidePick(param1);
                this.m_showPick = false;
            }
            return;
        }//end

         public void  setPosition (double param1 ,double param2 )
        {
            this.m_position = this.m_position || new Vector3();
            this.m_position.x = param1 + this.customTilePositionOffset.x;
            this.m_position.y = param2 + this.customTilePositionOffset.y;
            if (this.m_shouldListenToRender && !this.m_listeningToRender)
            {
                this.startRenderListen();
            }
            else if (!this.m_shouldListenToRender && this.m_listeningToRender)
            {
                this.stopRenderListen();
            }
            else if (!this.m_listeningToRender)
            {
                this.renderPosition();
            }
            return;
        }//end

         protected void  onRollOver (MouseEvent event )
        {
            super.onRollOver(event);
            Global.ui.showToolTip(this);
            return;
        }//end

         protected void  onRollOut (MouseEvent event )
        {
            super.onRollOut(event);
            Global.ui.hideToolTip(this);
            return;
        }//end

         protected void  onClick (MouseEvent event )
        {
            super.onClick(event);
            if (this.clickCallback != null)
            {
                this.clickCallback();
            }
            return;
        }//end

         protected void  onIconLoaded (Event event )
        {
            super.onIconLoaded(event);
            m_icon.y = m_icon.y - 5;
            m_iconBackground.y = m_iconBackground.y - 5;
            return;
        }//end

         protected void  removeSelectedItem (Event event )
        {
            return;
        }//end

         public void  kill (Function param1)
        {
            super.kill(param1);
            this.stopRenderListen();
            return;
        }//end

        public String  getToolTipStatus ()
        {
            return null;
        }//end

        public String  getToolTipHeader ()
        {
            return null;
        }//end

        public String  getToolTipNotes ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            return null;
        }//end

        public boolean  toolTipFollowsMouse ()
        {
            return false;
        }//end

        public Vector3  getToolTipPosition ()
        {
            return this.m_tooltipPosition;
        }//end

        public int  getToolTipFloatOffset ()
        {
            return 0;
        }//end

        public Vector3  getDimensions ()
        {
            return new Vector3(this.width, this.height);
        }//end

        public boolean  getToolTipVisibility ()
        {
            return true;
        }//end

        public Vector2  getToolTipScreenOffset ()
        {
            return this.m_toolTipOffset;
        }//end

        public ToolTipSchema  getToolTipSchema ()
        {
            return null;
        }//end

        public void  setPositionRenderListener (boolean param1 )
        {
            if (param1 !=null)
            {
                this.startRenderListen();
            }
            else
            {
                this.stopRenderListen();
            }
            this.m_shouldListenToRender = param1;
            return;
        }//end

        protected void  startRenderListen ()
        {
            if (!this.m_listeningToRender && stage != null)
            {
                stage.addEventListener(Event.RENDER, this.renderListener, false, 0, true);
                this.m_listeningToRender = true;
            }
            return;
        }//end

        protected void  stopRenderListen ()
        {
            if (this.m_listeningToRender && stage != null)
            {
                stage.removeEventListener(Event.RENDER, this.renderListener);
                this.m_listeningToRender = false;
            }
            return;
        }//end

        protected void  renderListener (Event event )
        {
            this.renderPosition();
            return;
        }//end

        protected void  renderPosition ()
        {
            Point _loc_1 =null ;
            if (this.m_position)
            {
                this.m_tooltipPosition = this.m_position;
                _loc_1 = IsoMath.tilePosToPixelPos(this.m_position.x, this.m_position.y);
                _loc_1 = IsoMath.viewportToStage(_loc_1);
                this.x = _loc_1.x + this.PICK_OFFSET.x;
                this.y = _loc_1.y + this.PICK_OFFSET.y;
            }
            return;
        }//end

    }


