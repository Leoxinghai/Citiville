package Modules.franchise.display;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.franchise.data.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;

    public class FranchiseTab extends DataItemCell
    {
        protected Loader m_setIconLoader =null ;
        protected DisplayObject m_content ;
        protected JPanel m_itemIconPane ;
        private JPanel m_cellPanel ;
        private JPanel m_iconPanel ;
        private JPanel m_itemIconHolder ;
        private JPanel m_itemBgHolder ;
        private boolean m_selected =false ;
        private JPanel m_bubblePanel ;
        private DisplayObject m_bgNorm ;
        private DisplayObject m_bgOver ;
        private MarginBackground m_iconBgDec ;
        private OwnedFranchiseData m_selectedFranchise ;
        private  int TAB_CELL_WIDTH =101;

        public  FranchiseTab (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.LEFT));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.setFranchiseCellValue(param1);
            return;
        }//end

        private void  setFranchiseCellValue (OwnedFranchiseData param1 )
        {
            this.m_selectedFranchise = param1;
            _loc_2 =Global.gameSettings().getImageByName(this.m_selectedFranchise.franchiseType ,"icon");
            if (_loc_2)
            {
                m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            }
            this.buildCell();
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_cellPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            if (this.franchiseType == "bus_dummy")
            {
                this.buildNewFranchiseCell();
            }
            else
            {
                this.buildExistingFranchiseCell();
            }
            this.append(this.m_cellPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  initializeCell ()
        {
            if (this.franchiseType == "bus_dummy")
            {
                return;
            }
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            AssetPane _loc_2 =new AssetPane(m_itemIcon );
            this.m_itemIconHolder.setBorder(new EmptyBorder(null, new Insets(15, 0, 0, 0)));
            _loc_1.append(_loc_2);
            this.m_itemIconPane.append(_loc_1);
            return;
        }//end

        protected void  onTabOver (MouseEvent event )
        {
            MarginBackground _loc_2 =new MarginBackground(this.m_bgOver ,new Insets(0,0,0,0));
            this.m_cellPanel.setBackgroundDecorator(_loc_2);
            ASwingHelper.prepare(this.m_cellPanel);
            return;
        }//end

        protected void  onTabOut (MouseEvent event )
        {
            MarginBackground _loc_2 =new MarginBackground(this.m_bgNorm ,new Insets(0,0,0,0));
            this.m_cellPanel.setBackgroundDecorator(_loc_2);
            ASwingHelper.prepare(this.m_cellPanel);
            return;
        }//end

        protected void  onTabClick (MouseEvent event )
        {
            this.m_selected = true;
            FranchiseMenu.menuUI.switchBusiness(this.m_selectedFranchise.franchiseType);
            this.onTabOver(event);
            return;
        }//end

        private void  buildExistingFranchiseCell ()
        {
            this.buildBackground("fr_tab_normal_new", "fr_tab_selected_new");
            JLabel _loc_1 =new JLabel(Global.franchiseManager.model.getFranchiseName(this.m_selectedFranchise.franchiseType ));
            _loc_1.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_STANDARD));
            _loc_1.setFont(ASwingHelper.getBoldFont(12));
            this.m_itemIconHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_itemIconHolder.append(this.m_itemIconPane);
            this.m_itemBgHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            this.m_itemBgHolder.setMinimumSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 25));
            this.m_itemBgHolder.setPreferredSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 25));
            this.m_itemBgHolder.setMaximumSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 25));
            this.m_itemBgHolder.append(this.m_itemIconHolder);
            this.m_cellPanel.append(this.m_itemBgHolder);
            this.m_cellPanel.append(_loc_1);
            this.m_cellPanel.addEventListener(MouseEvent.CLICK, this.onTabClick, false, 0, true);
            return;
        }//end

        private void  buildNewFranchiseCell ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            this.buildBackground("fr_tab_normalNewFranchise_new", "fr_tab_normalNewFranchise_new");
            if (Global.franchiseManager.isFranchiseLocked())
            {
                _loc_1 = ZLoc.t("Main", "franchiseUI_tab_nextFranchiseLocked_header");
                _loc_2 = ZLoc.t("Main", "franchiseUI_tab_nextFranchiseLocked_footer", {level:Global.franchiseManager.nextFranchiseUnlock.toString()});
            }
            else
            {
                _loc_1 = ZLoc.t("Main", "franchiseUI_tab_nextFranchiseUnlocked_header");
                _loc_2 = ZLoc.t("Main", "franchiseUI_tab_nextFranchiseUnlocked_footer");
            }
            _loc_3 = ASwingHelper.makeMultilineCapsText(_loc_1 ,this.m_cellPanel.width ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,13,EmbeddedArt.blueTextColor );
            _loc_4 = ASwingHelper.makeMultilineCapsText(_loc_2 ,this.m_cellPanel.width ,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,14,EmbeddedArt.orangeTextColor );
            ASwingHelper.prepare(_loc_3, _loc_4);
            _loc_5 = this.m_content.height -25-(_loc_3.height +_loc_4.height +10);
            this.m_cellPanel.append(ASwingHelper.verticalStrut(_loc_5 / 2));
            this.m_cellPanel.append(_loc_3);
            this.m_cellPanel.append(ASwingHelper.verticalStrut(10));
            this.m_cellPanel.append(_loc_4);
            this.m_cellPanel.append(ASwingHelper.verticalStrut(_loc_5 / 2));
            ASwingHelper.prepare(this.m_cellPanel);
            return;
        }//end

        private void  buildBackground (String param1 ,String param2 )
        {
            DisplayObject _loc_3 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get(param1);
            DisplayObject _loc_4 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get(param2);
            this.m_content = _loc_3;
            this.m_bgNorm = _loc_3;
            this.m_bgOver = _loc_4;
            this.setPreferredWidth(this.TAB_CELL_WIDTH);
            this.setMinimumWidth(this.TAB_CELL_WIDTH);
            this.setMaximumWidth(this.TAB_CELL_WIDTH);
            MarginBackground _loc_5 =new MarginBackground(this.m_content ,new Insets(0,0,0,0));
            this.m_cellPanel.setBackgroundDecorator(_loc_5);
            this.m_cellPanel.setPreferredSize(new IntDimension(this.TAB_CELL_WIDTH, this.m_content.height));
            this.m_cellPanel.setMinimumSize(new IntDimension(this.TAB_CELL_WIDTH, this.m_content.height));
            this.m_cellPanel.setMaximumSize(new IntDimension(this.TAB_CELL_WIDTH, this.m_content.height));
            ASwingHelper.prepare(this.m_cellPanel);
            return;
        }//end

        public String  franchiseType ()
        {
            return this.m_selectedFranchise.franchiseType;
        }//end

        public void  selectionStatus (boolean param1 )
        {
            if (param1 !=null)
            {
                this.onTabOver(null);
            }
            else
            {
                this.onTabOut(null);
            }
            return;
        }//end

    }



