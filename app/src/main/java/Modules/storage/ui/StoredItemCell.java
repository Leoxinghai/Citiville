package Modules.storage.ui;

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
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Modules.storage.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class StoredItemCell extends InventoryCell
    {
        protected int m_cellState ;
        protected BaseStorageUnit m_storageUnit ;
        public static  int CARD_TEXT_MARGIN =2;
        public static  int TEXT_HEIGHT =30;
        public static  int STATE_OCCUPIED =0;
        public static  int STATE_OPEN =1;
        public static  int STATE_LOCKED =2;

        public  StoredItemCell (LayoutManager param1)
        {
            super(param1);
            this.setAssetDict(StorageView.assetDict);
            this.setPreferredHeight(PREFERRED_HEIGHT);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_storageUnit =(BaseStorageUnit) param1.storage;
            this.m_cellState = param1.state;
            if (param1.item instanceof Item)
            {
                super.setCellValue(param1.item as Item);
            }
            else
            {
                this.renderEmptyCell(param1.item);
            }
            return;
        }//end

         protected int  getCount ()
        {
            return this.m_storageUnit.getItemCountByName(this.m_item.name);
        }//end

         protected JPanel  makeRemoveButtonPanel ()
        {
            return null;
        }//end

         protected JPanel  makeNameAndSetPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            ASwingHelper.prepare(this);
            _loc_1.setMinimumWidth(this.width);
            _loc_1.setPreferredWidth(this.width);
            _loc_1.setMaximumWidth(this.width);
            _loc_2 = ASwingHelper.makeMultilineText(m_item.localizedName ,this.width ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,12,2645358);
            _loc_1.appendAll(ASwingHelper.verticalStrut(CARD_TEXT_MARGIN), _loc_2, ASwingHelper.verticalStrut(CARD_TEXT_MARGIN));
            return _loc_1;
        }//end

        protected void  renderEmptyCell (int param1 )
        {
            m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (param1 == STATE_OPEN)
            {
                m_content =(DisplayObject) new m_assetDict.get("inventory_card");
            }
            else
            {
                m_content =(DisplayObject) new m_assetDict.get("inventory_card_locked");
            }
            this.setPreferredWidth(m_content.width + CELL_BORDER_GAP * 2);
            this.setMinimumWidth(m_content.width + CELL_BORDER_GAP * 2);
            this.setMaximumWidth(m_content.width + CELL_BORDER_GAP * 2);
            m_bgDec = new MarginBackground(m_content);
            m_alignmentContainer.setBackgroundDecorator(m_bgDec);
            m_alignmentContainer.setPreferredSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMinimumSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMaximumSize(new IntDimension(m_content.width, m_content.height));
            m_overlapPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM, -OVERLAP_AMOUNT);
            ASwingHelper.prepare(m_alignmentContainer);
            _loc_2.append(m_alignmentContainer);
            m_overlapPane.appendAll(_loc_2, ASwingHelper.verticalStrut(31));
            ASwingHelper.prepare(m_overlapPane);
            this.append(m_overlapPane);
            this.append(ASwingHelper.verticalStrut(20));
            ASwingHelper.prepare(this);
            if (param1 == STATE_LOCKED)
            {
                this.alpha = 0.5;
            }
            m_initialized = true;
            return;
        }//end

        public static int  PREFERRED_HEIGHT ()
        {
            return Catalog.CARD_HEIGHT + TEXT_HEIGHT + 2 * CARD_TEXT_MARGIN;
        }//end

    }



