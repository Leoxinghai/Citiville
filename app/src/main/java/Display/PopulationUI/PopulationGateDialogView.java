package Display.PopulationUI;

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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class PopulationGateDialogView extends GenericDialogView
    {

        public  PopulationGateDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            CustomButton _loc_2 =new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","ResourceInfoPop_Business_bold1")),null ,"GreenButtonUI");
            CustomButton _loc_3 =new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main","Cancel")),null ,"RedButtonUI");
            _loc_2.addEventListener(MouseEvent.MOUSE_UP, this.onOpenClick, false, 0, true);
            _loc_3.addEventListener(MouseEvent.MOUSE_UP, this.onCancelClick, false, 0, true);
            _loc_1.appendAll(_loc_2, _loc_3);
            return _loc_1;
        }//end

        protected void  onOpenClick (MouseEvent event )
        {
            UI.displayCatalog(new CatalogParams("municipal"));
            closeMe();
            return;
        }//end

        protected void  onCancelClick (MouseEvent event )
        {
            closeMe();
            return;
        }//end

    }



