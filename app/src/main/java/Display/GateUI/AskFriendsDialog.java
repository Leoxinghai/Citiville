package Display.GateUI;

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
import Events.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;

    public class AskFriendsDialog extends GenericDialog
    {
        private Item m_item ;
        private Item m_building ;
        private DisplayObject m_iconAsset ;
        private String m_viralType ;
        public static  ASFont TITLE_FONT =ASwingHelper.getBoldFont(24);
        public static  ASFont TEXT_FONT =ASwingHelper.getStandardFont(18);
        public static  ASFont BUTTON_FONT =ASwingHelper.getBoldFont(14);
        public static  int TEXT_COLOR =9463372;
        public static  int TITLE_COLOR =16777215;
        public static  GlowFilter GLOW_FILTER =new GlowFilter(489375,1,5,5,4,2);
        public static  String REQUEST_BUILDABLE ="request";
        public static  String FEED_BUILDABLE ="feed";
        public static Dictionary assets ;

        public  AskFriendsDialog (Item param1 ,DisplayObject param2 ,Item param3 ,String param4 ="request")
        {
            this.m_item = param1;
            this.m_building = param3;
            m_message = ZLoc.t("Dialogs", "askDialog_body");
            m_title = ZLoc.t("Dialogs", "askDialog_title", {item:this.m_item.localizedName});
            this.m_viralType = param4;
            super(m_message, "", GenericDialogView.TYPE_SHARECANCEL, this.onAsk, m_title, this.m_item.icon, true, 0, "", this.onSkip);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            assets = new Dictionary();
            assets.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            m_jpanel = new AskFriendsDialogView(assets, m_message, m_title, GenericDialogView.TYPE_ASKFRIEND, m_callback, this.m_item.icon, 0, m_SkipCallback);
            finalizeAndShow();
            return;
        }//end

         protected void  showTween ()
        {
            Point startSize ;
            Object matParams ;
            Matrix mat ;
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            startSize = new Point(m_content.width, m_content.height);
            matParams;
            if (m_centered)
            {
                centerPopup();
            }
            mat = new Matrix();
            mat.translate((-startSize.x) / 2, (-startSize.y) / 2);
            mat.scale(matParams.scale, matParams.scale);
            mat.translate(startSize.x / 2, startSize.y / 2);
            m_content.transform.matrix = mat;
void             TweenLite .to (matParams ,TWEEN_TIME ,{1scale ,Back ease .easeOut , onUpdate ()
            {
                mat = new Matrix();
                mat.translate((-startSize.x) / 2, (-startSize.y) / 2);
                mat.scale(matParams.scale, matParams.scale);
                mat.translate(startSize.x / 2, startSize.y / 2);
                m_content.transform.matrix = mat;
                if (m_centered)
                {
                    centerPopup();
                }
                this.visible = true;
                return;
            }//end
void             , onComplete ()
            {
                onShowComplete();
                return;
            }//end
            });
            return;
        }//end

        public void  onSkip (GenericPopupEvent event )
        {
            closeMe(event);
            return;
        }//end

        public void  onAsk (GenericPopupEvent event )
        {
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            GenericDialog _loc_4 =null ;
            if (event.button == GenericDialogView.YES)
            {
                closeMe(event);
                if (this.m_viralType == REQUEST_BUILDABLE)
                {
                    _loc_2 = Global.world.viralMgr.sendMunicipalMaterialRequest(Global.player, this.m_building.localizedName, this.m_item);
                }
                else if (this.m_viralType == FEED_BUILDABLE)
                {
                    _loc_2 = Global.world.viralMgr.sendMunicipalMaterialFeed(Global.player, this.m_building.localizedName, this.m_item);
                }
                if (!_loc_2)
                {
                    _loc_3 = ZLoc.t("Dialogs", "MunicipalThrottlingMessage");
                    _loc_4 = new GenericDialog(_loc_3);
                    UI.displayPopup(_loc_4);
                }
            }
            else
            {
                this.onSkip(event);
            }
            return;
        }//end

    }



