package Display.GridlistUI.view;

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
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class ItemCellView extends JPanel implements IAssetComponent, IContentComponent
    {
        protected Dictionary m_assetDict ;
        protected DisplayObject m_content ;
        protected JPanel m_panel ;
        protected JPanel m_bodyPanel ;
        protected JPanel m_footPanel ;
        protected JPanel m_contentPanel ;
        protected Insets m_bodyPanelInsets ;
        protected Insets m_footPanelInsets ;

        public  ItemCellView ()
        {
            this.m_assetDict = new Dictionary();
            this.m_bodyPanelInsets = new Insets();
            this.m_footPanelInsets = new Insets();
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_panel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_bodyPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.m_footPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            return;
        }//end

        public DisplayObject  bgAsset ()
        {
            return this.m_assetDict.get("bg");
        }//end

        public void  makeAssets (Dictionary param1 )
        {
            _loc_2 = param1.get(DelayedAssetLoader.MARKET_ASSETS) ;
            this.m_assetDict.put("bg",  new (DisplayObject)_loc_2.get("marketItem"));
            return;
        }//end

        public void  setContent (DisplayObject param1 )
        {
            this.m_content = param1;
            this.updateContentPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  updateView (Object param1)
        {
            return;
        }//end

        final public void  build ()
        {
            this.cleanup();
            this.initLayout();
            this.addEventListeners();
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  cleanup ()
        {
            this.removeAll();
            this.removeEventListeners();
            return;
        }//end

        protected void  initLayout ()
        {
            if (this.m_panel)
            {
                if (this.m_bodyPanel)
                {
                    this.updateBodyPanel();
                    if (this.m_contentPanel)
                    {
                        this.updateContentPanel();
                        this.m_bodyPanel.append(this.m_contentPanel);
                    }
                    this.m_panel.append(this.m_bodyPanel);
                }
                if (this.m_footPanel)
                {
                    this.updateFooterPanel();
                    this.m_panel.append(this.m_footPanel);
                }
                this.append(this.m_panel);
            }
            return;
        }//end

        protected void  updateBodyPanel ()
        {
            if (this.bgAsset)
            {
                ASwingHelper.setSizedBackground(this.m_bodyPanel, this.bgAsset, this.m_bodyPanelInsets);
                ASwingHelper.prepare(this.m_bodyPanel);
            }
            return;
        }//end

        protected void  updateFooterPanel ()
        {
            return;
        }//end

        protected void  updateContentPanel ()
        {
            if (!this.m_content)
            {
                this.m_content = this.createContentPlaceholder();
            }
            this.prepareContent();
            AssetPane _loc_1 =new AssetPane(this.m_content );
            _loc_2 = this(.m_bodyPanel.getWidth ()-this.m_content.width )/2;
            ASwingHelper.setEasyBorder(_loc_1, 0, _loc_2);
            this.m_contentPanel.removeAll();
            this.m_contentPanel.append(_loc_1);
            return;
        }//end

        protected DisplayObject  createContentPlaceholder ()
        {
            return ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Loading"), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.darkBlueTextColor, JLabel.CENTER);
        }//end

        private void  prepareContent ()
        {
            ASwingHelper.prepare(this);
            _loc_1 = this.scaleToFit(this.m_content);
            this.m_content.scaleY = this.scaleToFit(this.m_content);
            this.m_content.scaleX = _loc_1;
            if (this.m_content instanceof Bitmap)
            {
                ((Bitmap)this.m_content).smoothing = true;
            }
            return;
        }//end

        private double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            _loc_4 = this.m_bodyPanel.getHeight();
            _loc_5 = this.m_bodyPanel.getWidth();
            if (param1.width > _loc_5)
            {
                _loc_2 = ((Number)_loc_5) / param1.width;
            }
            if (param1.height > _loc_4)
            {
                _loc_3 = ((Number)_loc_4) / param1.height;
            }
            return Math.min(_loc_2, _loc_3);
        }//end

        public void  addEventListeners ()
        {
            addEventListener(MouseEvent.ROLL_OVER, this.onRollOver);
            addEventListener(MouseEvent.ROLL_OUT, this.onRollOut);
            addEventListener(MouseEvent.CLICK, this.onClick);
            return;
        }//end

        public void  removeEventListeners ()
        {
            if (hasEventListener(MouseEvent.ROLL_OVER))
            {
                removeEventListener(MouseEvent.ROLL_OVER, this.onRollOver);
            }
            if (hasEventListener(MouseEvent.ROLL_OUT))
            {
                removeEventListener(MouseEvent.ROLL_OUT, this.onRollOut);
            }
            if (hasEventListener(MouseEvent.CLICK))
            {
                removeEventListener(MouseEvent.CLICK, this.onClick);
            }
            return;
        }//end

        protected void  onRollOver (MouseEvent event =null )
        {
            return;
        }//end

        protected void  onRollOut (MouseEvent event =null )
        {
            return;
        }//end

        protected void  onClick (MouseEvent event =null )
        {
            return;
        }//end

    }




