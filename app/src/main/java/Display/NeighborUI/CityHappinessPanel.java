package Display.NeighborUI;

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
import Display.PopulationUI.*;
import Display.aswingui.*;
import Display.hud.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import Classes.sim.*;

    public class CityHappinessPanel extends JPanel implements IPopulationStateObserver
    {
        protected AssetPane m_facePane ;
        protected Array m_faceAssets ;
        protected int m_happiness =0;
        protected int m_animCount =0;

        public  CityHappinessPanel (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_faceAssets = .get(HUDThemeManager.getAsset(HUDThemeManager.POP_GOOD), HUDThemeManager.getAsset(HUDThemeManager.POP_NEUTRAL), HUDThemeManager.getAsset(HUDThemeManager.POP_BAD));
            this.init();
            Global.world.citySim.addObserver(this);
            return;
        }//end

        protected void  init ()
        {
            this.m_facePane = new AssetPane(this.getFaceImage());
            this.m_happiness = Global.world.citySim.getHappinessState();
            this.appendAll(ASwingHelper.verticalStrut(1), this.m_facePane);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            this.m_facePane.setAsset(this.getFaceImage());
            this.m_happiness = Global.world.citySim.getHappinessState();
            return;
        }//end

        public void  animateIfHappinessChanged ()
        {
            Object _loc_1 =null ;
            if (this.m_happiness != Global.world.citySim.getHappinessState())
            {
                this.m_animCount = 10;
                this.m_happiness = Global.world.citySim.getHappinessState();
                _loc_1 = {alpha:0.3};
                _loc_1.onComplete = this.animationFinished;
                Z_TweenLite.to(this.m_facePane, 0.5, _loc_1);
            }
            return;
        }//end

        public void  animationFinished ()
        {
            double _loc_1 =0;
            Object _loc_2 =null ;
            this.m_animCount--;
            if (this.m_animCount > 0)
            {
                _loc_1 = this.m_animCount & 1 ? (1) : (0.3);
                _loc_2 = {alpha:_loc_1};
                _loc_2.onComplete = this.animationFinished;
                Z_TweenLite.to(this.m_facePane, 0.5, _loc_2);
                return;
            }
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            this.m_facePane.setAsset(this.getFaceImage());
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            if (!Global.ui.bNeighborActionsMenuOn)
            {
                PopulationPopup.getInstance().show();
                event.stopPropagation();
            }
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            PopulationPopup.getInstance().hide();
            event.stopPropagation();
            return;
        }//end

        protected DisplayObject  getFaceImage ()
        {
            _loc_1 =Global.world.citySim.getHappinessState ();
            return this.m_faceAssets.get(_loc_1);
        }//end

    }



