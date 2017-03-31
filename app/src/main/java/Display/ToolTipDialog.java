package Display;

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

import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.EmptyBorder;
import com.xinghai.Debug;

    public class ToolTipDialog extends GenericDialog
    {
        protected boolean m_hasData ;
        protected String m_header ;
        protected String m_notes ;
        protected String m_status ;
        protected String m_action ;
        protected String m_titleString ;
        protected ToolTipSchema m_schema ;
        protected boolean m_schemaRefresh =false ;
        protected Array m_components ;
        protected IToolTipTarget m_previousTarget ;
        public static  String HEADER ="header";
        public static  String NOTES ="notes";
        public static  String STATUS ="status";
        public static  String ACTION ="action";

        public  ToolTipDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,boolean param5 =true )
        {
            this.m_components = new Array();
            this.m_schema = ToolTipSchema.TOOLTIP_SCHEMA_DEFAULT;
            super(param1, param2, param3, param4, param2, "", false);
            this.m_header = null;
            this.m_notes = null;
            this.m_status = null;
            this.m_action = null;
            boolean _loc_6 =false ;
            this.mouseEnabled = false;
            this.mouseChildren = _loc_6;
            return;
        }//end

         protected void  init ()
        {
            m_holder = new Sprite();


            this.addChild(m_holder);
            this.m_hasData = m_message != null && m_message != "";
            m_jwindow = new JWindow(m_holder);

            m_content = m_holder;
            m_content.addEventListener("close", closeMe, false, 0, true);
            m_content.addEventListener(MouseEvent.MOUSE_MOVE, this.removePopup, false, 0, true);
            return;
        }//end

         protected boolean  doTrackDialogActions ()
        {
            return false;
        }//end

         protected void  loadAssets ()
        {
            onAssetsLoaded();
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            return new Dictionary();
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new ToolTipDialogView(param1, this.m_schema, m_message, m_title, m_type, m_callback);
        }//end

        protected void  removePopup (MouseEvent event )
        {
            Global.ui.hideAnyToolTip();
            return;
        }//end

        public void  setToolTipSchema (ToolTipSchema param1 )
        {
            if (param1 != this.m_schema)
            {
                this.m_schema = param1;
                this.m_schemaRefresh = true;
                ((ToolTipDialogView)m_jpanel).refreshSchemaFormatting(this.m_schema);
            }
            return;
        }//end

        public void  loadToolTipForTarget (IToolTipTarget param1 )
        {

            Debug.debug6("ToolTipDialog.loadToolTipForTarget");

            Array _loc_2 =null ;
            Array _loc_3 =null ;
            _loc_4 = null;
            boolean _loc_5 =false ;
            String _loc_6 =null ;
            int _loc_7 =0;
            if (param1 instanceof ICustomToolTipTarget)
            {
                _loc_2 = ((ICustomToolTipTarget)param1).getToolTipComponentList();
                _loc_3 = new Array();
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_4 = _loc_2.get(i0);

                    if (_loc_4 instanceof Component)
                    {
                        _loc_3.push(_loc_4);
                    }
                }
                _loc_5 = false;
                if (this.m_components.length != _loc_3.length || this.m_schemaRefresh)
                {
                    _loc_5 = true;
                }
                else
                {
                    _loc_7 = 0;
                    while (_loc_7 < this.m_components.length())
                    {

                        if (this.m_components.get(_loc_7) != _loc_3.get(_loc_7))
                        {
                            _loc_5 = true;
                            break;
                        }
                        _loc_7++;
                    }
                }
                if (_loc_5)
                {
                    this.m_components = _loc_3;
                    ((ToolTipDialogView)m_jpanel).setCustomComponentsList(this.m_components);
                }
                _loc_6 = ((ICustomToolTipTarget)param1).getCustomToolTipTitle();
                if (_loc_6 != this.m_titleString || this.m_previousTarget != param1 || this.m_schemaRefresh)
                {
                    this.m_titleString = _loc_6;
                    ((ToolTipDialogView)m_jpanel).setTitle(this.m_titleString);
                }
                ((ToolTipDialogView)m_jpanel).setImage(((ICustomToolTipTarget)param1).getCustomToolTipImage());

		//added by xinghai
                this.setStatus(param1.getToolTipStatus());
                this.setHeader(param1.getToolTipHeader());
                this.setNotes(param1.getToolTipNotes());
                this.setCallToAction(param1.getToolTipAction());

                //this.m_header = null;
                //this.m_status = null;
                //this.m_notes = null;
                //this.m_action = null;
            }
            else if (param1 != null)
            {
                this.setStatus(param1.getToolTipStatus());
                this.setHeader(param1.getToolTipHeader());
                this.setNotes(param1.getToolTipNotes());
                this.setCallToAction(param1.getToolTipAction());
                ((ToolTipDialogView)m_jpanel).setLegacyComponentsList(param1);
                this.m_titleString = null;
                this.m_components = new Array();
            }
            ((ToolTipDialogView)m_jpanel).finalize();
            this.m_previousTarget = param1;
            this.m_schemaRefresh = false;

            //add by xinghai
            //this.finalizeAndShow();
            return;
        }//end

        public boolean  hasData ()
        {
            return this.m_header && this.m_header != "" || this.m_notes && this.m_notes != "" || this.m_status && this.m_status != "" || this.m_action && this.m_action != "" || this.m_components.length > 0 || this.m_titleString;
        }//end

        public void  setHeader (String param1 )
        {
            Debug.debug6("ToolTipDialog.setHeader."+param1);
            if (param1 != this.m_header || param1 == null)
            {
                this.m_header = param1;
                ((ToolTipDialogView)m_jpanel).setHeader(param1);
                ASwingHelper.prepare(m_jwindow);
            }
            return;
        }//end

        public void  setNotes (String param1 )
        {
            Debug.debug6("ToolTipDialog.setNotes."+param1);
            if (param1 != this.m_notes || param1 == null)
            {
                this.m_notes = param1;
                ((ToolTipDialogView)m_jpanel).setNotes(param1);
                ASwingHelper.prepare(m_jwindow);
            }
            return;
        }//end

        public void  setStatus (String param1 )
        {
            Debug.debug6("ToolTipDialog.setStatus."+param1);
            if (param1 == ZLoc.t("Main", "NoGoods"))
            {
                this.m_status = param1;
                ((ToolTipDialogView)m_jpanel).setStatus(param1, 16729156);
                ASwingHelper.prepare(m_jwindow);
            }
            if (param1 != this.m_status || param1 == null)
            {
                this.m_status = param1;
                ((ToolTipDialogView)m_jpanel).setStatus(param1);
                ASwingHelper.prepare(m_jwindow);
            }
            return;
        }//end

        public void  setCallToAction (String param1 )
        {
            Debug.debug6("ToolTipDialog.setCallToAction."+param1);
            if (param1 != this.m_action || param1 != null)
            {
                this.m_action = param1;
                ((ToolTipDialogView)m_jpanel).setCallToAction(param1);
                ASwingHelper.prepare(m_jwindow);
            }
            return;
        }//end

        public static Component  buildToolTipComponent (String param1 ,Object param2 ,String param3 ,ToolTipSchema param4 =null )
        {
            Debug.debug6("ToolTipDialog.buildToolTipComponent."+param1+";"+param2);

            if (!param1 || !param2)
            {
                return null;
            }
            param2.get(_loc_5 = param1+"_container") ;
            param2.get(_loc_6 = param1+"_label") ;
            if (param3)
            {
                _loc_6 = _loc_6 || prepTooltipLabel(new JLabel(""), param4);
                _loc_5 = _loc_5 || ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_5.removeAll();
                _loc_6.setText(param3);
                _loc_5.appendAll(_loc_6);
            }
            else
            {
                if (_loc_5)
                {
                    _loc_5.removeAll();
                }
                _loc_5 = null;
                _loc_6 = null;
            }
            param2.put(param1 + "_container",  _loc_5);
            param2.put(param1 + "_label",  _loc_6);
            return _loc_5;
        }//end

        public static JLabel  prepTooltipLabel (JLabel param1 ,ToolTipSchema param2 )
        {
            Debug.debug6("ToolTipDialog.prepTooltipLabel.");

            param2 = param2 || ToolTipSchema.TOOLTIP_SCHEMA_DEFAULT;
            ASFont _loc_3 =new ASFont(param2.bodyFontName ,param2.bodyFontSize ,false ,false ,false ,new ASFontAdvProperties(true ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            param1.setForeground(new ASColor(param2.bodyFontColor));
            param1.setFont(_loc_3);
            param1.setTextFilters(param2.bodyTextFilters);
            return param1;
        }//end

    }



