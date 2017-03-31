package Display.hud.components;

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
import Classes.orders.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.franchise.data.*;
import Modules.quest.Display.*;

//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDFranchiseRequestComponent extends HUDComponent
    {
        private String m_pendingFranchiseName ;
        private static  int MIN_LEVEL =6;

        public  HUDFranchiseRequestComponent ()
        {
            this.m_pendingFranchiseName = null;
            return;
        }//end

         public boolean  isVisible ()
        {
            _loc_1 =Global.getVisiting ()!= GameWorld.CITY_SAM_OWNER_ID;
            if (_loc_1)
            {
                _loc_1 = Global.franchiseManager.eligibleFranchises.length > 0 && Global.player.level >= MIN_LEVEL && BuyLogic.canAcceptFranchiseRequests(Global.getVisiting());
            }
            return _loc_1;
        }//end

        private String  getPendingFranchiseType ()
        {
            OwnedFranchiseData _loc_4 =null ;
            String _loc_1 =null ;
            boolean _loc_2 =false ;
            _loc_3 =Global.franchiseManager.getPendingSentFranchiseOrders ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_2 = _loc_4.hasLocation(Global.getVisiting());
                if (_loc_2)
                {
                    _loc_1 = _loc_4.franchiseType;
                    break;
                }
            }
            return _loc_1;
        }//end

         protected void  buildComponent ()
        {
            Component _loc_1 =null ;
            GameSprite _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            this.m_pendingFranchiseName = this.getPendingFranchiseType();
            if (this.m_pendingFranchiseName)
            {
                _loc_3 = Global.player.getFriendFirstName(Global.getVisiting());
                _loc_4 = this.m_pendingFranchiseName ? (Global.franchiseManager.getFranchiseName(this.m_pendingFranchiseName, Global.player.uid)) : (null);
                _loc_2 = new GameSprite();
                _loc_2.addChild(new EmbeddedArt.franchiseRequestPendingIcon());
                _loc_5 = ZLoc.t("Main", "pendingFranchiseTooltip", {friendName:_loc_3});
                _loc_5 = _loc_5 + "\n";
                _loc_5 = _loc_5 + ZLoc.t("Main", "pendingFranchiseTooltipLine3");
                _loc_2.toolTip = _loc_5;
                _loc_1 = new AssetPane(_loc_2);
                _loc_1.addEventListener(MouseEvent.CLICK, this.onPendingClick, false, 0, true);
            }
            else
            {
                _loc_2 = new GameSprite();
                _loc_2.addChild(new EmbeddedArt.franchiseRequestIcon());
                ButtonManager.buttonize(_loc_2, null, EffectsUtil.getGlowEffectByAssignment("sidebar"));
                _loc_2.toolTip = ZLoc.t("Main", "requestFranchiseTooltip");
                _loc_1 = new AssetPane(_loc_2);
                _loc_1.addEventListener(MouseEvent.CLICK, this.onClick, false, 0, true);
            }
            _loc_1.addEventListener(MouseEvent.MOUSE_OVER, this.onButtonMouseOver);
            _loc_1.addEventListener(MouseEvent.MOUSE_OUT, this.onButtonMouseOut);
            m_jPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 0);
            m_jPanel.append(_loc_1);
            return;
        }//end

        private void  onButtonMouseOut (MouseEvent event )
        {
            this.filters = null;
            return;
        }//end

        private void  onButtonMouseOver (MouseEvent event )
        {
            GlowFilter _loc_2 =new GlowFilter(EmbeddedArt.HOVER_GLOW_COLOR ,1,8,8,5);
            this.filters = .get(_loc_2);
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            super.refresh(param1);
            if (this.getPendingFranchiseType() != this.m_pendingFranchiseName)
            {
                this.buildComponent();
                showComponent();
            }
            return;
        }//end

        private void  onPendingClick (Event event )
        {
            this.m_pendingFranchiseName = this.getPendingFranchiseType();
            _loc_2 =Global.player.getFriendFirstName(Global.getVisiting ());
            _loc_3 = this.m_pendingFranchiseName ? (Global.franchiseManager.getFranchiseName(this.m_pendingFranchiseName, Global.player.uid)) : (null);
            this.filters = null;
            UI.displayMessage(ZLoc.t("Dialogs", "lotOrder_cancel_message", {friendName:_loc_2, franchiseName:_loc_3}), GenericDialogView.TYPE_YESNO, this.cancelLotOrder);
            return;
        }//end

        private void  cancelLotOrder (GenericPopupEvent event )
        {
            if (event.button != GenericDialogView.YES)
            {
                return;
            }
            _loc_2 =Global.franchiseManager.getPendingSentFranchiseOrderForNeighbor(Global.getVisiting ());
            Global.world.citySim.lotManager.cancel(_loc_2);
            return;
        }//end

        private void  onClick (Event event )
        {
            UI.displayCatalog(new CatalogParams("business"));
            Global.franchiseManager.placementMode = true;
            return;
        }//end

    }



