package GameMode;

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
import Engine.*;
import Engine.Helpers.*;
import Modules.guide.ui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class GMPlaceBizOnLotSite extends GMPlaceMapResource
    {
        protected Sprite m_lotObject ;
        protected GuideArrow m_lotArrow ;

        public  GMPlaceBizOnLotSite (LotSite param1 ,String param2 ,Class param3 ,boolean param4 =false )
        {
            this.m_lotObject = new Sprite();
            m_selectedLotSite = param1;
            super(param2, param3, param4);
            return;
        }//end

         public void  enableMode ()
        {
            if (m_selectedLotSite)
            {
                m_selectedLotSite.alpha = 0;
            }
            GlobalEngine.viewport.overlayBase.addChild(this.m_lotObject);
            _loc_1 =Global.getAssetURL("assets/hud/tutorialArrow.swf");
            _loc_2 = IsoMath.tilePosToPixelPos(Math.floor(m_selectedLotSite.getPosition ().x +2*m_selectedLotSite.getSize ().x ),Math.floor(m_selectedLotSite.getPosition ().y +2*m_selectedLotSite.getSize ().y ));
            this.drawFlatTileOutline(m_selectedLotSite);
            this.m_lotArrow = Global.guide.displayLotArrow(_loc_1, _loc_2.x, _loc_2.y, 0, 0, 3, true);
            super.enableMode();
            return;
        }//end

         public void  disableMode ()
        {
            m_selectedLotSite.alpha = 1;
            if (Global.franchiseManager.placementMode)
            {
                Global.franchiseManager.placementMode = false;
            }
            if (this.m_lotObject.parent == GlobalEngine.viewport.overlayBase)
            {
                GlobalEngine.viewport.overlayBase.removeChild(this.m_lotObject);
            }
            Global.guide.removeArrows();
            this.m_lotArrow = null;
            super.disableMode();
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            Point _loc_2 =null ;
            if (m_dragging)
            {
                _loc_2 = IsoMath.tilePosToPixelPos(Math.floor(m_selectedLotSite.getPosition().x + m_selectedLotSite.getSize().x), Math.floor(m_selectedLotSite.getPosition().y + m_selectedLotSite.getSize().y));
                _loc_2 = IsoMath.viewportToStage(_loc_2);
                this.m_lotArrow.setPosition(_loc_2.x, _loc_2.y);
            }
            return super.onMouseMove(event);
        }//end

        protected void  drawFlatTileOutline (MapResource param1 )
        {
            this.m_lotObject.graphics.clear();
            _loc_2 = param1.getSize ();
            this.m_lotObject.graphics.lineStyle(TILE_OUTLINE_WIDTH / 2, Constants.COLOR_VALID_PLACEMENT);
            this.m_lotObject.graphics.beginFill(0, 0);
            drawTileArea(this.m_lotObject.graphics, new Point(param1.getPosition().x, param1.getPosition().y), _loc_2.x, _loc_2.y);
            this.m_lotObject.graphics.endFill();
            return;
        }//end

    }



