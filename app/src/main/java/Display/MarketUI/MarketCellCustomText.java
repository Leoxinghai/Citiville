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

import Display.*;
import Display.aswingui.*;
import org.aswing.*;

    public class MarketCellCustomText extends MarketCell
    {
        private String m_buttonText ;

        public  MarketCellCustomText (String param1 ,LayoutManager param2 ,boolean param3 =false )
        {
            super(param2);
            this.m_buttonText = param1;
            m_isQuest = param3;
            return;
        }//end

         protected void  setupPurchaseButton ()
        {
            m_purchaseButton = new CustomButton(this.m_buttonText, null, "CoinsButtonUI");
            _loc_1 = m_purchaseButton.getFont();
            m_purchaseButton.setFont(_loc_1.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_1.getSize(), 1, [{locale:"de", ratio:0.8}, {locale:"id", ratio:0.75}, {locale:"es", ratio:0.9}, {locale:"fr", ratio:1}, {locale:"it", ratio:0.8}, {locale:"pt", ratio:0.9}, {locale:"tr", ratio:0.8}])));
            return;
        }//end

    }




