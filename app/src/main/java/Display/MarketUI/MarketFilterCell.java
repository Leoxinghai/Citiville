package Display.MarketUI;

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
import Display.aswingui.*;
import Events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class MarketFilterCell extends JPanel implements GridListCell
    {
        protected Dictionary m_assetDict ;
        protected Item m_data ;
        protected String m_all ;

        public  MarketFilterCell (Dictionary param1 ,LayoutManager param2 )
        {
            this.m_assetDict = param1;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            if (param1 instanceof String)
            {
                this.m_all = param1;
                this.buildAll();
            }
            else
            {
                this.m_data = param1;
                this.buildCell();
            }
            return;
        }//end

        public Object getCellValue ()
        {
            if (this.m_data)
            {
                return this.m_data;
            }
            return this.m_all;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            return;
        }//end

        protected void  buildCell ()
        {
            CustomButton _loc_1 =new CustomButton(this.m_data.localizedName ,null ,"MarketSortButtonUI");
            _loc_1.addActionListener(this.filter, 0, true);
            this.append(_loc_1);
            return;
        }//end

        protected void  buildAll ()
        {
            CustomButton _loc_1 =new CustomButton(this.m_all ,null ,"MarketSortButtonUI");
            _loc_1.addActionListener(this.filterAll, 0, true);
            this.append(_loc_1);
            return;
        }//end

        protected void  filter (AWEvent event )
        {
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MARKET_FILTER, this.m_data, true));
            return;
        }//end

        protected void  filterAll (AWEvent event )
        {
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MARKET_FILTER, this.m_all, true));
            return;
        }//end

    }




