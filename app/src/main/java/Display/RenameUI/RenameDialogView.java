package Display.RenameUI;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class RenameDialogView extends GenericDialogView
    {
        protected VectorListModel m_listData_city ;
        protected VectorListModel m_listData_bus ;
        public static  int DIALOG_WIDTH =744;
        public static  int BODY_PAD_TOP =10;
        public static  int BODY_PAD_BOTTOM =0;
        public static  int BODY_PAD_SIDE =20;

        public  RenameDialogView (Dictionary param1 ,Array param2 ,Array param3 ,String param4 ="",String param5 ="",int param6 =8,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            this.m_listData_city = new VectorListModel();
            this.m_listData_bus = new VectorListModel();
            this.refreshCityModel(param2);
            this.refreshBusinessModel(param3);
            super(param1, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected void  makeBackground ()
        {
            super.makeBackground();
            this.setPreferredWidth(DIALOG_WIDTH);
            this.setMinimumWidth(DIALOG_WIDTH);
            this.setMaximumWidth(DIALOG_WIDTH);
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeTextField(m_message ,EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.darkBrownTextColor );
            _loc_2.setPreferredWidth(DIALOG_WIDTH - 2 * BODY_PAD_SIDE);
            ASwingHelper.setEasyBorder(_loc_1, BODY_PAD_TOP, BODY_PAD_SIDE, BODY_PAD_BOTTOM, BODY_PAD_SIDE);
            _loc_4 = this.createTopPanel ();
            _loc_5 = this.createDivider ();
            _loc_6 = this.createBottomPanel ();
            _loc_2.appendAll(_loc_4, ASwingHelper.verticalStrut(5), _loc_5, _loc_3, ASwingHelper.verticalStrut(5), _loc_6);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  createTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            GridList _loc_2 =new GridList(this.m_listData_city ,new RenameCellFactory(RenameCell ,m_assetDict ),1,1);
            _loc_2.setTileWidth(DIALOG_WIDTH - 2 * BODY_PAD_SIDE);
            _loc_2.setTileHeight(95);
            _loc_2.setPreferredHeight(95);
            _loc_2.setSelectable(false);
            _loc_2.addEventListener(RenameEvent.FINISH_CLICK, this.onRenameEvent);
            _loc_2.addEventListener(RenameEvent.PROPOSE_CLICK, this.onRenameEvent);
            _loc_2.addEventListener(RenameEvent.PURCHASE_CLICK, this.onRenameEvent);
            _loc_2.addEventListener(RenameEvent.REQUEST_CLICK, this.onRenameEvent);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected AssetPane  createDivider ()
        {
            DisplayObject _loc_1 =(DisplayObject)new m_assetDict.get( "dialog_div");
            _loc_1.width = DIALOG_WIDTH - 2 * BODY_PAD_SIDE;
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            return _loc_2;
        }//end

        protected JPanel  createBottomPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            VerticalScrollingList _loc_2 =new VerticalScrollingList(m_assetDict ,this.m_listData_bus ,new RenameCellFactory(RenameCell ,m_assetDict ),1,3,DIALOG_WIDTH -2*BODY_PAD_SIDE ,300);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        public void  refreshCityModel (Array param1 )
        {
            this.refreshModel(this.m_listData_city, param1);
            return;
        }//end

        public void  refreshBusinessModel (Array param1 )
        {
            this.refreshModel(this.m_listData_bus, param1);
            return;
        }//end

        protected void  refreshModel (VectorListModel param1 ,Array param2 )
        {
            boolean _loc_4 =false ;
            int _loc_3 =0;
            while (_loc_3 < param2.length())
            {

                if (_loc_3 >= param1.getSize())
                {
                    param1.append(param2.get(_loc_3));
                }
                else
                {
                    _loc_4 = param1.get(_loc_3).state != param2.get(_loc_3).state || param1.get(_loc_3).requiredCount != param2.get(_loc_3).requiredCount;
                    if (_loc_4)
                    {
                        param1.removeAt(_loc_3);
                        param1.append(param2.get(_loc_3), _loc_3);
                    }
                }
                _loc_3++;
            }
            return;
        }//end

        protected void  onRenameEvent (RenameEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            this.dispatchEvent(new RenameEvent(event.type, event.itemName));
            return;
        }//end

    }



