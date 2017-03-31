package Display.GridlistUI.model;

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

import Display.GridlistUI.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class GenericCellModel extends EventDispatcher implements GridListCell
    {
        protected GridList m_gridList ;
        protected boolean m_selected ;
        protected int m_index ;
        protected GenericGridListData m_value ;

        public  GenericCellModel ()
        {
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            this.m_gridList = param1;
            this.m_selected = param2;
            this.m_index = param3;
            if (this.m_gridList && this.getCellComponent())
            {
                if (this.getCellComponent().getPreferredWidth() > this.m_gridList.getTileWidth())
                {
                    this.m_gridList.setTileWidth(this.getCellComponent().getPreferredWidth());
                }
                if (this.getCellComponent().getPreferredHeight() > this.m_gridList.getTileHeight())
                {
                    this.m_gridList.setTileHeight(this.getCellComponent().getPreferredHeight());
                }
            }
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_value =(GenericGridListData) param1;
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_value.data;
        }//end

        public Component  getCellComponent ()
        {
            return this.m_value.view;
        }//end

        public void  addListeners ()
        {
            this.m_value.view.addEventListener(ViewActionEvent.EVENT_TYPE, this.onViewEventTrigger);
            return;
        }//end

        protected void  onViewEventTrigger (ViewActionEvent event )
        {
        /*
            Function _loc_2 =event.action ;
            if (_loc_2)
            {
                _loc_2();
            }
            else
            {
                throw new Error("Model action " + _loc_2 + " not supported.");
            }
            */
            return;
        }//end

        public void  removeListeners ()
        {
            return;
        }//end

    }


