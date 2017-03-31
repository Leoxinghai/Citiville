package Display.DialogUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.EmptyBorder;

import com.xinghai.Debug;

    public class GenericDialog extends Dialog
    {
        protected Sprite m_holder ;
        protected JPanel m_jpanel ;
        protected String m_message ;
        protected String m_title ;
        protected int m_type ;
        protected Function m_callback ;
        protected Function m_SkipCallback ;
        protected Object m_comObject ;
        protected String m_icon ;
        protected int m_iconPos ;
        protected String m_dialogTitle ;
        protected String m_statsTrackingName ;
        protected GenericDialog m_loadingDialog =null ;
        protected boolean m_imagesComplete =false ;
        protected DisplayObject activeBgAsset ;
        protected DisplayObject m_assetBG ;
        protected String m_feedShareViralType ="";
        protected String m_customOk ="";
        protected boolean m_relativeIcon =true ;
        protected int m_assetDependenciesLoaded ;
        protected Dictionary m_assetDependencies ;
        protected boolean m_html =false ;
        protected String m_fbLikeURL ="http://www.zynga.com";
        protected String m_fbLikeTitle ="Zynga";
        protected String m_fbLikeReference ="cityville";
        public static  int GENERIC =0;
        public static  int LEVELUP =1;
        public static  int NOBUTTONS =2;
        public static  int LOAD_WAIT_TIME =3000;

        public  GenericDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true ,String param13 ="",boolean param14 =false ,String param15 ="",String param16 ="",String param17 ="")
        {
            Debug.debug6("GenericDialog."+param1+";"+param2+";"+param5+";"+param6);
            this.m_assetDependenciesLoaded = 0;
            this.m_assetDependencies = new Dictionary();
            this.m_icon = param6;
            this.m_iconPos = param8;
            this.m_dialogTitle = param5;
            this.m_message = param1;
            this.m_title = param5;
            this.m_type = param3;
            this.m_callback = param4;
            this.m_feedShareViralType = param9;
            this.m_SkipCallback = param10;
            this.m_customOk = param11;
            this.m_relativeIcon = param12;
            this.m_statsTrackingName = param13;
            this.m_html = param14;
            this.m_fbLikeURL = param15;
            this.m_fbLikeReference = param16;
            this.m_fbLikeTitle = param17;
            this.init();
            super(param7);
            this.loadAssets();
            return;
        }//end

        protected void  init ()
        {
            this.m_holder = new Sprite();


	    		m_holder.graphics.beginFill(0x55794B);
	    		m_holder.graphics.drawCircle(20,20,10);
	    		m_holder.graphics.endFill();


            this.addChild(this.m_holder);
            m_jwindow = new JWindow(this.m_holder);
            m_content = this.m_holder;
            m_content.addEventListener(GenericPopupEvent.SELECTED, this.processSelection, false, 0, true);
            m_content.addEventListener(UIEvent.REFRESH_DIALOG, this.onRefreshDialog, false, 0, true);
            m_content.addEventListener("close", this.closeMe, false, 0, true);
            return;
        }//end

        protected boolean  doTrackDialogActions ()
        {
            if (this.toString() == "[object GenericDialog]")
            {
                if (this.m_title.length == 0 && this.m_statsTrackingName.length == 0)
                {
                    return false;
                }
            }
            return true;
        }//end

        protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS);
        }//end

        protected void  loadAssets ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.getAssetDependencies().size(); i0++)
            {
            	_loc_1 = this.getAssetDependencies().get(i0);

                Global.delayedAssets.get(_loc_1, this.makeAssets);
            }
            return;
        }//end

        protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            _loc_3 = this.getAssetDependencies ().length ;
            this.m_assetDependenciesLoaded++;
            this.m_assetDependencies.put(param2,  param1);
            if (_loc_3 == 1 || param2 == DelayedAssetLoader.GENERIC_DIALOG_ASSETS)
            {
                this.m_comObject = param1;
            }
            if (this.m_assetDependenciesLoaded >= _loc_3)
            {
                this.onAssetsLoaded();
            }
            return;
        }//end

        protected void  onRefreshDialog (UIEvent event )
        {
            if (m_jwindow)
            {
                ASwingHelper.prepare(m_jwindow);
            }
            this.centerPopup();
            return;
        }//end

        protected void  onAssetsLoaded (Event event =null )
        {
            _loc_2 = this.createAssetDict();
            this.m_jpanel = this.createDialogView(_loc_2);
            this.finalizeAndShow();
            return;
        }//end

        protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            if (this.m_type == GenericDialogView.TYPE_NOBUTTONS || this.m_type == GenericDialogView.TYPE_GENERIC_OK_WITHOUTCANCEL)
            {
                _loc_1.put("dialog_bg", (DisplayObject) new this.m_comObject.dialog_bg_notitle_noclose());
            }
            else if (this.m_type == GenericDialogView.TYPE_TITLENOCLOSE)
            {
                _loc_1.put("dialog_bg",  new (DisplayObject)this.m_comObject.dialog_bg_noclose());
            }
            else if (this.m_title == "")
            {
                _loc_1.put("dialog_bg",  new (DisplayObject)this.m_comObject.dialog_bg_notitle());
            }
            else
            {
                _loc_1.put("dialog_bg",  new (DisplayObject)this.m_comObject.dialog_bg());
            }
            this.m_assetBG = _loc_1.get("dialog_bg");
            return _loc_1;
        }//end

        protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            GenericDialogView _loc_2 =new GenericDialogView(param1 ,this.m_message ,this.m_dialogTitle ,this.m_type ,this.m_callback ,this.m_icon ,this.m_iconPos ,this.m_feedShareViralType ,this.m_SkipCallback ,this.m_customOk ,this.m_relativeIcon ,this.m_html );
            return _loc_2;
        }//end

        public boolean  hasLoaded ()
        {
            return this.m_jpanel != null;
        }//end

        public JPanel  view ()
        {
            return this.m_jpanel;
        }//end

        protected void  processSelection (GenericPopupEvent event )
        {
            _loc_2 = (GenericDialogView)this.m_jpanel
            String _loc_3 ="";
            if (event.button == GenericDialogView.YES)
            {
                _loc_3 = _loc_2.acceptText;
                if (this.m_callback != null)
                {
                    this.m_callback(event);
                }
            }
            else if (event.button == GenericDialogView.NO)
            {
                _loc_3 = _loc_2.cancelText;
                if (this.m_callback != null)
                {
                    this.m_callback(event);
                }
            }
            else if (event.button == GenericDialogView.SKIP)
            {
                _loc_3 = _loc_2.skipText;
                if (this.m_SkipCallback != null)
                {
                    this.m_SkipCallback(event);
                }
            }
            else if (event.button == GenericDialogView.SHARE)
            {
                if (this.m_callback != null)
                {
                    this.m_callback(event);
                }
            }
            if (_loc_3.length > 0)
            {
                this.countDialogAction(_loc_3);
            }
            event.stopPropagation();
            return;
        }//end

        protected void  closeMe (Event event )
        {
            close();
            return;
        }//end

        public void  setupDialogStatsTracking ()
        {
            this.countDialogAction();
            _loc_1 =(GenericDialogView) this.m_jpanel;
            if (_loc_1 == null)
            {
                return;
            }
            _loc_1.setStatsTrackingFunction(this.doTrackDialogActions as Function, this.getDialogStatsTrackingString);
            return;
        }//end

        protected void  finalizeAndShow ()
        {
            ASwingHelper.prepare(this.m_jpanel);





JButton button ;

button = new JButton("I'm a AsWing button!");

String labelPrefix ="Number of button clicks: ";
int numClicks =0;
JLabel label ;


label = new JLabel(labelPrefix+"0");


label.setText(labelPrefix+numClicks);
label.revalidate();

	JPanel pane =new JPanel(new FlowLayout(FlowLayout.CENTER ));
	pane.append(button);
	pane.append(label);
	pane.setBorder(new EmptyBorder(null, new Insets(10,5,10,5)));


        Debug.debug5("finalizeAndShow.m_jpanel."+m_jpanel.getChildren.length());

*/

            m_jwindow.setContentPane(this.m_jpanel);
            //m_jwindow.setContentPane(pane);

            ASwingHelper.prepare(m_jwindow);

            if (this.m_assetBG && this.m_title == "" && this.m_assetBG.height > m_jwindow.getHeight())
            {
                m_jwindow.setPreferredHeight((this.m_assetBG.height + 1));
                ASwingHelper.prepare(m_jwindow);
            }

            int _loc_1 =m_jwindow.getWidth ();
            int _loc_2 =m_jwindow.getHeight ();
            m_jwindow.show();
            Debug.debug5("GenericDialog.finalizeAndShow "+_loc_1+";"+m_title);
            this.m_holder.width = _loc_1;
            this.m_holder.height = _loc_2;
            this.setupDialogStatsTracking();
            onDialogInitialized();
            return;
        }//end

        protected void  showLoadingDialog ()
        {
            TimerUtil .callLater (void  ()
            {
                if (m_imagesComplete)
                {
                    return;
                }
                m_loadingDialog = new GenericDialog(ZLoc.t("Main", "LoadingDialogText"));
                UI.displayPopup(m_loadingDialog, false, "loadingDialog", true);
                return;
            }//end
            , LOAD_WAIT_TIME);
            return;
        }//end

        protected void  closeLoadingDialog ()
        {
            this.m_imagesComplete = true;
            if (this.m_loadingDialog && this.m_loadingDialog.isShown)
            {
                this.m_loadingDialog.close();
            }
            return;
        }//end

        protected String  getDialogStatsTrackingString ()
        {
            _loc_1 = this.m_title;
            if (_loc_1.length == 0)
            {
                _loc_1 = this.toString();
            }
            if (this.m_statsTrackingName.length > 0)
            {
                _loc_1 = this.m_statsTrackingName;
            }
            _loc_1 = GameUtil.trimBadStatsCharacters(_loc_1);
            return _loc_1;
        }//end

        public void  countDialogAction (String param1 ="view",int param2 =1,String param3 ="",String param4 ="",String param5 ="")
        {
            if (!this.doTrackDialogActions())
            {
                return;
            }
            _loc_6 = this.getDialogStatsTrackingString();
            _loc_7 = GameUtil.trimBadStatsCharacters(param1);
            if (this.trackingIsSampled())
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_6, _loc_7, param3, param4, param5, param2);
            }
            else
            {
                StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, _loc_6, _loc_7, param3, param4, param5, param2);
            }
            return;
        }//end

        protected boolean  trackingIsSampled ()
        {
            return true;
        }//end

    }




