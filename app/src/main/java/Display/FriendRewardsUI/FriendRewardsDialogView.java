package Display.FriendRewardsUI;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class FriendRewardsDialogView extends GenericDialogView
    {
        protected Array m_data ;
        protected String m_localeKey ;
        public static  int LIST_WIDTH =400;
        public static  int LIST_HEIGHT =300;

        public  FriendRewardsDialogView (Array param1 ,Dictionary param2 ,String param3 =null ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            this.m_data = param1;
            this.m_localeKey = param3;
            super(param2, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            CustomButton _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            CustomButton _loc_2 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            if (this.m_data.get(0) != null && this.m_data.get(0).uid != "-1")
            {
                _loc_3 = new CustomButton(ZLoc.t("Items", "zoodonation_thankYou"), null, "GreenButtonUI");
                _loc_3.addActionListener(onAccept, 0, true);
                _loc_3.addEventListener(MouseEvent.CLICK, this.onNeighborBarGiftClick, false, 0, true);
                _loc_1.append(_loc_3);
            }
            return _loc_1;
        }//end

        public void  onNeighborBarGiftClick (MouseEvent event )
        {
            Object _loc_3 =null ;
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < this.m_data.size(); i0++) 
            {
            	_loc_3 = this.m_data.get(i0);

                _loc_2.push(_loc_3.uid);
            }
            FrameManager.navigateTo("Gifts.php?action=chooseRecipient&gift=mysterygift_v1&view=custom_friends&friends=" + com.adobe.serialization.json.JSON.encode(_loc_2));
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,0,0);
            VectorListModel _loc_2 =new VectorListModel(this.m_data );
            _loc_3 = m_assetDict;
            _loc_4 = _loc_2;
            FriendRewardsCellFactory _loc_5 =new FriendRewardsCellFactory(FriendRewardsCell ,m_assetDict ,this.m_localeKey );
            int _loc_6 =1;
            int _loc_7 =3;
            _loc_8 = LIST_WIDTH;
            _loc_9 = LIST_HEIGHT;
            boolean _loc_10 =true ;
            VerticalScrollingList _loc_11 =new VerticalScrollingList(_loc_3 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 ,_loc_9 ,_loc_10 );
            ASwingHelper.setEasyBorder(_loc_1, 10, 20, 0, 20);
            _loc_1.append(_loc_11);
            return _loc_1;
        }//end

         protected Object  getTitleTokens ()
        {
            return {count:this.m_data.length};
        }//end

    }



