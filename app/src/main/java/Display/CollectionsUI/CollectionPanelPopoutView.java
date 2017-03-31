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
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class CollectionPanelPopoutView extends GenericDialogView
    {
        protected JPanel m_mainPanel ;
        protected JTextField m_collectionHeader ;
        protected JPanel m_collectionHeaderPanel ;
        protected GridList m_gridList ;
        protected GridListCellFactory m_cellFactory ;
        protected VectorListModel m_model ;
        protected boolean m_initialized =false ;

        public  CollectionPanelPopoutView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("collectionFlyout");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.m_mainPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -15);
            this.m_collectionHeaderPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            this.m_collectionHeader = ASwingHelper.makeTextField("", EmbeddedArt.titleFont, 12, 16777215);
            this.m_collectionHeaderPanel.append(this.m_collectionHeader);
            this.m_collectionHeaderPanel.setPreferredHeight(CollectionPanelPopout.NAME_HEIGHT);
            ASwingHelper.prepare(this.m_collectionHeaderPanel);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            this.m_model = new VectorListModel();
            this.m_cellFactory = new CollectionItemCellFactory(new IntDimension(CollectionPanelPopout.SLOT_SIDE_LENGTH, CollectionPanelPopout.SLOT_SIDE_LENGTH));
            this.m_gridList = new GridList(this.m_model, this.m_cellFactory, CollectionPanelPopout.MAX_SLOTS, 1);
            this.m_gridList.setPreferredHeight(m_bgAsset.height);
            this.m_gridList.setPreferredWidth(m_bgAsset.width);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(CollectionPanelPopout.GRID_MARGIN), this.m_gridList, ASwingHelper.horizontalStrut(CollectionPanelPopout.GRID_MARGIN));
            MarginBackground _loc_2 =new MarginBackground(m_bgAsset );
            _loc_1.setBackgroundDecorator(_loc_2);
            ASwingHelper.prepare(_loc_1);
            this.m_mainPanel.append(_loc_1);
            this.m_mainPanel.append(this.m_collectionHeaderPanel);
            ASwingHelper.prepare(this.m_mainPanel);
            this.append(this.m_mainPanel);
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

        public void  prepopulatePanelSlots (Collection param1 )
        {
            CollectionItemCell _loc_5 =null ;
            Item _loc_6 =null ;
            this.m_collectionHeader.setText(param1.localizedName);
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.align = TextFormatAlign.RIGHT;
            _loc_2.size = 18;
            TextFieldUtil.formatSmallCaps(this.m_collectionHeader.getTextField(), _loc_2);
            this.m_collectionHeader.filters = .get(new GlowFilter(0));
            this.m_collectionHeaderPanel.setPreferredHeight(this.m_collectionHeader.getTextField().textHeight + 5);
            ASwingHelper.prepare(this.m_collectionHeaderPanel);
            ASwingHelper.prepare(this.m_mainPanel);
            ASwingHelper.prepare(this);
            int _loc_3 =0;
            while (_loc_3 < this.m_model.getSize())
            {

                _loc_5 =(CollectionItemCell) this.m_gridList.getCellByIndex(_loc_3);
                _loc_5.forceFadeComplete();
                _loc_3++;
            }
            this.m_model.clear();
            _loc_4 = param1.getCollectableNames ();
            _loc_3 = 0;
            while (_loc_3 < CollectionPanelPopout.MAX_SLOTS)
            {

                if (_loc_3 < _loc_4.length())
                {
                    _loc_6 = Global.gameSettings().getItemByName(_loc_4.get(_loc_3));
                    if (_loc_6)
                    {
                        this.m_model.append(_loc_6);
                    }
                }
                _loc_3++;
            }
            return;
        }//end

        public void  invalidateData ()
        {
            int _loc_1 =0;
            CollectionItemCell _loc_2 =null ;
            if (this.m_initialized)
            {
                _loc_1 = 0;
                while (_loc_1 < this.m_model.getSize())
                {

                    _loc_2 =(CollectionItemCell) this.m_gridList.getCellByIndex(_loc_1);
                    _loc_2.invalidateData();
                    _loc_1++;
                }
            }
            return;
        }//end

        public void  invalidateCell (String param1 ,Collection param2 )
        {
            _loc_3 = this.getCollectableNumber(param1 ,param2 );
            _loc_4 = this.m_gridList.getCellByIndex(_loc_3 )as CollectionItemCell ;
            (this.m_gridList.getCellByIndex(_loc_3) as CollectionItemCell).invalidateData();
            return;
        }//end

        protected int  getCollectableNumber (String param1 ,Collection param2 )
        {
            return param2.getCollectableNames().indexOf(param1);
        }//end

        public void  playFadeIn (String param1 ,Collection param2 ,Function param3 ,Function param4 )
        {
            _loc_5 = this.getCollectableNumber(param1 ,param2 );
            _loc_6 = this.m_gridList.getCellByIndex(_loc_5 )as CollectionItemCell ;
            (this.m_gridList.getCellByIndex(_loc_5) as CollectionItemCell).playFadeIn(param3, param4);
            return;
        }//end

    }



