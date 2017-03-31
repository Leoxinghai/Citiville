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

import Classes.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.utils.*;

    public class ResourceExplanationDialog extends GenericDialog
    {
        protected String m_explanationType ;
        protected Object m_params ;
        public static  String TYPE_NOT_ENOUGH_GOODS ="ResourceInfoGoods";
        public static  String TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE ="ResourceInfoPop_Residence";
        public static  String TYPE_NOT_ENOUGH_HAPPINESS_BUSINESS ="ResourceInfoPop_Business";
public static Dictionary m_shownAlready =new Dictionary ();
        private static boolean m_forceShow =false ;
        private static boolean m_showMultiPopulation =false ;

        public  ResourceExplanationDialog (String param1 ,InstantiationSentinel param2 ,Function param3 ,Object param4 )
        {
            this.m_explanationType = param1;
            this.m_params = param4;
            if (ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE == param1)
            {
                if (Global.gameSettings().hasMultiplePopulations())
                {
                    m_showMultiPopulation = true;
                }
            }
            super("", "", GenericDialogView.TYPE_OK, param3, param1);
            return;
        }//end

         protected void  loadAssets ()
        {
            if (m_showMultiPopulation)
            {
                Global.delayedAssets.get(DelayedAssetLoader.MULTI_POPULATION_ASSETS, makeAssets);
            }
            else
            {
                Global.delayedAssets.get(DelayedAssetLoader.POPULATION_ASSETS, makeAssets);
            }
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            String _loc_1 ="info_dialog_with_title";
            if (m_showMultiPopulation && this.m_params.hasOwnProperty("capType"))
            {
                _loc_1 = _loc_1 + ("_" + this.m_params.capType);
            }
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("bg",  this.getBackground());
            _loc_2.put("info",  this.getInfoAsset(this.m_explanationType));
            return _loc_2;
        }//end

        protected Class  getBackground ()
        {
            String _loc_1 ="info_dialog_with_title";
            if (m_showMultiPopulation && this.m_params.hasOwnProperty("capType"))
            {
                _loc_1 = _loc_1 + ("_" + this.m_params.capType);
            }
            return m_comObject.get(_loc_1);
        }//end

        protected Class  getInfoAsset (String param1 )
        {
            String _loc_2 =null ;
            switch(param1)
            {
                case TYPE_NOT_ENOUGH_GOODS:
                {
                    return m_comObject.info_food_flow;
                }
                case TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE:
                case TYPE_NOT_ENOUGH_HAPPINESS_BUSINESS:
                {
                }
                default:
                {
                    _loc_2 = "info_municipal_flow";
                    if (m_showMultiPopulation && this.m_params.hasOwnProperty("capType"))
                    {
                        _loc_2 = _loc_2 + ("_" + this.m_params.capType);
                    }
                    return m_comObject.get(_loc_2);
                    break;
                }
            }
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            if (m_forceShow)
            {
                m_forceShow = false;
            }
            _loc_2 = this.getDialogText(this.m_explanationType );
            return new ResourceExplanationDialogView(param1, m_title, _loc_2.line1, _loc_2.line2, _loc_2.legend, m_callback);
        }//end

        protected Object  getDialogText (String param1 )
        {
            this.addFormattingParams(param1, this.m_params);
            Object _loc_2 ={line1 line2 "",""legend ,""};
            _loc_2.line1 = ZLoc.t("Dialogs", param1 + "_line1", this.m_params);
            if (this.showLine1a(param1))
            {
                _loc_2.line1 = ZLoc.t("Dialogs", param1 + "_line1a", this.m_params);
            }
            _loc_2.line2 = ZLoc.t("Dialogs", param1 + "_line2", this.m_params);
            _loc_2.legend = ZLoc.t("Dialogs", param1 + "_legend", this.m_params);
            return _loc_2;
        }//end

        protected boolean  showLine1a (String param1 )
        {
            switch(param1)
            {
                case TYPE_NOT_ENOUGH_GOODS:
                {
                    return Global.player.commodities.getCount(Commodities.GOODS_COMMODITY) > 0;
                }
                default:
                {
                    return false;
                    break;
                }
            }
        }//end

        protected void  addFormattingParams (String param1 ,Object param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            if (param2)
            {
                _loc_3 = param1;
                _loc_4 = EmbeddedArt.defaultFontNameBold;
                _loc_5 = {};
                if (m_showMultiPopulation && param2.hasOwnProperty("capType"))
                {
                    _loc_10 = PopulationHelper.getPopulationZlocType(param2.capType);
                    _loc_5.popType = _loc_10;
                    _loc_3 = "ResourceInfoPopType_Residence";
                }
                _loc_6 = ZLoc.t("Dialogs", _loc_3 + "_bold0", _loc_5);
                _loc_7 = ZLoc.t("Dialogs", _loc_3 + "_bold1", _loc_5);
                _loc_8 = ZLoc.t("Dialogs", _loc_3 + "_bold2", _loc_5);
                _loc_9 = ZLoc.t("Dialogs", _loc_3 + "_bold3", _loc_5);
                param2.bold0 = ASwingHelper.applyFont(_loc_6 ? (_loc_6) : (""), _loc_4);
                param2.bold1 = ASwingHelper.applyFont(_loc_7 ? (_loc_7) : (""), _loc_4);
                param2.bold2 = ASwingHelper.applyFont(_loc_8 ? (_loc_8) : (""), _loc_4);
                param2.bold3 = ASwingHelper.applyFont(_loc_9 ? (_loc_9) : (""), _loc_4);
            }
            return;
        }//end

        public static void  show (String param1 ,boolean param2 =false ,Function param3 =null ,Object param4 =null )
        {
            ResourceExplanationDialog _loc_5 =null ;
            if (Global.disableLoadPopups && !param2)
            {
                return;
            }
            if (m_shownAlready.get(param1) == true && param2 == false)
            {
                return;
            }
            if (IdleDialogManager.ShowingIdleDialog && param2 == false)
            {
                return;
            }
            m_forceShow = param2;
            if (param1 == TYPE_NOT_ENOUGH_GOODS)
            {
                UI.displayOutOfGoodsDialog();
                m_shownAlready.put(param1,  true);
            }
            else
            {
                _loc_5 = new ResourceExplanationDialog(param1, new InstantiationSentinel(), param3, param4);
                UI.displayPopup(_loc_5);
            }
            return;
        }//end

        //added by xinghai
         public void  show ()
        {
           return;
	}//end

    }
class InstantiationSentinel

     InstantiationSentinel ()
    {
        return;
    }//end





