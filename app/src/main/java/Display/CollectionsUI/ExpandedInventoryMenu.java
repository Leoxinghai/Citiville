package Display.CollectionsUI;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class ExpandedInventoryMenu extends ExpandedMenu
    {
        protected JButton m_collectionsButton ;
        protected JLabel m_collectionsLabel ;
        protected JButton m_inventoryButton ;
        protected JLabel m_inventoryLabel ;
        protected JButton m_franchiseButton ;
        protected JLabel m_franchiseLabel ;

        public  ExpandedInventoryMenu (Object param1 ,boolean param2 =false )
        {
            super(param1, param2);
            return;
        }//end

         protected void  makeContent ()
        {
            m_content = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 0);
            m_bg =(DisplayObject) new EmbeddedArt.hud_act_bg();
            AssetBackground _loc_1 =new AssetBackground(m_bg );
            m_content.setBackgroundDecorator(_loc_1);
            m_content.setPreferredWidth(m_bg.width);
            this.m_collectionsButton = new JButton();
            this.m_collectionsLabel = new JLabel(ZLoc.t("Main", "Collections"));
            this.m_inventoryButton = new JButton();
            this.m_inventoryLabel = new JLabel(ZLoc.t("Dialogs", "InventoryTitle"));
            this.m_franchiseButton = new JButton();
            this.m_franchiseLabel = new JLabel(ZLoc.t("Main", "Franchise"));
            m_content.append(ASwingHelper.verticalStrut(15));
            makeButton(EmbeddedArt.hud_act_collection, this.m_collectionsButton, this.m_collectionsLabel);
            makeButton(EmbeddedArt.hud_act_inventory, this.m_inventoryButton, this.m_inventoryLabel);
            makeButton(EmbeddedArt.hud_actMenu_franchise, this.m_franchiseButton, this.m_franchiseLabel);
            m_content.append(ASwingHelper.verticalStrut(20));
            _loc_2 = this.m_collectionsLabel.getFont();
            this.m_collectionsLabel.setFont(_loc_2.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_2.getSize(), 0.9, null)));
            attachButtonHandlers(this.m_collectionsButton, this.onCollectionsClick, this.onCollectionsHover, this.onCollectionsOut);
            attachButtonHandlers(this.m_inventoryButton, this.onInventoryClick, this.onInventoryHover, this.onInventoryOut);
            attachButtonHandlers(this.m_franchiseButton, this.onFranchiseClick, this.onFranchiseHover, this.onFranchiseOut);
            return;
        }//end

        protected void  onCollectionsClick (Event event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.STORAGE_MENU, "collections");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.SHOW_COLLECTIONS));
            return;
        }//end

        protected void  onCollectionsHover (Event event )
        {
            this.m_collectionsLabel.setTextFilters(.get(glow_lbl));
            return;
        }//end

        protected void  onCollectionsOut (Event event )
        {
            this.m_collectionsLabel.setTextFilters([]);
            return;
        }//end

        protected void  onInventoryClick (Event event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.STORAGE_MENU, "inventory");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.SHOW_INVENTORY));
            return;
        }//end

        protected void  onInventoryHover (Event event )
        {
            this.m_inventoryLabel.setTextFilters(.get(glow_lbl));
            return;
        }//end

        protected void  onInventoryOut (Event event )
        {
            this.m_inventoryLabel.setTextFilters([]);
            return;
        }//end

        protected void  onFranchiseClick (Event event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.STORAGE_MENU, "franchise");
            dispatchEvent(new UIEvent(UIEvent.ACTION_MENU_CLICK, UIEvent.SHOW_FRANCHISE));
            return;
        }//end

        protected void  onFranchiseHover (Event event )
        {
            this.m_franchiseLabel.setTextFilters(.get(glow_lbl));
            return;
        }//end

        protected void  onFranchiseOut (Event event )
        {
            this.m_franchiseLabel.setTextFilters([]);
            return;
        }//end

         public Vector2  getDesiredPosition (Catalog param1)
        {
            return new Vector2(685, 254);
        }//end

    }



