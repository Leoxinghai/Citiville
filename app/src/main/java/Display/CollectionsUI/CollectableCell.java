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

import Display.InventoryUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class CollectableCell extends InventoryCell
    {
        public static  int BOTTOM_GAP_GIFT =30;
        public static  int BOTTOM_GAP_WISHLIST =22;
        public static  int COLLECTABLE_CELL_GAP =2;

        public  CollectableCell (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end

         protected void  buildCell ()
        {
            m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (this.getCount() > 0)
            {
                m_content =(DisplayObject) new InventoryView.assetDict.get("collections_inventory_item");
            }
            else
            {
                m_content =(DisplayObject) new InventoryView.assetDict.get("collections_item_empty");
            }
            this.setPreferredWidth(m_content.width + COLLECTABLE_CELL_GAP * 2);
            this.setMinimumWidth(m_content.width + COLLECTABLE_CELL_GAP * 2);
            this.setMaximumWidth(m_content.width + COLLECTABLE_CELL_GAP * 2);
            m_bgDec = new MarginBackground(m_content);
            m_alignmentContainer.setBackgroundDecorator(m_bgDec);
            m_alignmentContainer.setPreferredSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMinimumSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMaximumSize(new IntDimension(m_content.width, m_content.height));
            m_overlapPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM, -OVERLAP_AMOUNT);
            m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_alignmentContainer.append(m_itemIconPane);
            ASwingHelper.prepare(m_alignmentContainer);
            _loc_1.append(m_alignmentContainer);
            m_overlapPane.append(_loc_1);
            m_overlapPane.append(this.makeBottomPane());
            ASwingHelper.prepare(m_overlapPane);
            this.append(m_overlapPane);
            this.append(makeNameAndSetPane());
            this.append(ASwingHelper.verticalStrut(13));
            ASwingHelper.prepare(this);
            m_initialized = true;
            return;
        }//end

         protected JPanel  makeBottomPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,BOTTOM_GAP);
            m_useButton = makeUseButton();
            if (m_useButton)
            {
                _loc_1.append(m_useButton);
            }
            else
            {
                _loc_1.append(ASwingHelper.horizontalStrut(BOTTOM_GAP_WISHLIST));
            }
            _loc_1.append(ASwingHelper.horizontalStrut(BOTTOM_GAP_GIFT));
            _loc_1.append(ASwingHelper.horizontalStrut(10));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2.appendAll(ASwingHelper.verticalStrut(10), makeCountPane());
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 5);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected int  getCount ()
        {
            return Global.player.getNumCollectablesOwned(m_item.name);
        }//end

         protected boolean  allowUseButton ()
        {
            return false;
        }//end

    }




