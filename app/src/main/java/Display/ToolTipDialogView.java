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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.colorchooser.*;
import org.aswing.geom.*;

import org.aswing.border.EmptyBorder;

import com.xinghai.Debug;

    public class ToolTipDialogView extends GenericDialogView
    {
        protected  int LABEL_HEIGHT =14;
        protected JPanel m_tooltipWindow ;
        protected JPanel m_legacyComponentContainer ;
        protected Container m_headerContainer ;
        protected Container m_notesContainer ;
        protected Container m_statusContainer ;
        protected Container m_actionContainer ;
        protected JTextField m_headerLabel ;
        protected JLabel m_notesLabel ;
        protected JLabel m_statusLabel ;
        protected JLabel m_actionLabel ;
        protected JPanel m_titleContainer ;
        protected JTextField m_titleLabel ;
        protected TextFormat m_titleTextFormat ;
        protected JPanel m_componentListContainer ;
        protected Array m_customComponents ;
        protected JPanel m_textPanel ;
        protected JPanel m_borderBufferContainer ;
        protected JPanel m_componentsOuterContainer ;
        protected JPanel m_componentsInnerContainer ;
        protected JPanel m_imageContainer ;
        protected ToolTipSchema m_schema ;
        protected JPanel m_tailContainer ;
        protected AssetPane m_tail ;
        protected Insets m_margins ;

        public  ToolTipDialogView (Dictionary param1 ,ToolTipSchema param2 ,String param3 ="",String param4 ="",int param5 =0,Function param6 =null )
        {
            this.m_customComponents = new Array();
            this.m_margins = new Insets(3, 3, 3, 3);
            this.m_schema = param2;
            this.m_titleTextFormat = new TextFormat(this.m_schema.titleFontName, this.m_schema.titleFontSize);
            this.m_titleTextFormat.align = TextFormatAlign.CENTER;
            this.m_tooltipWindow = new JPanel(new VerticalLayout(VerticalLayout.CENTER));
            super(param1, param3, param4, param5, param6);
            return;
        }//end

         protected void  init ()
        {
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            m_bgAsset = this.m_schema.backgroundAsset;
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.m_tooltipWindow.removeAll();
            if (this.m_textPanel)
            {
                this.m_textPanel.removeAll();
            }
            if (this.m_tailContainer)
            {
                this.m_tailContainer.removeAll();
            }
            if (this.m_borderBufferContainer)
            {
                this.m_borderBufferContainer.removeAll();
            }
            this.cleanUpLegacyComponents();
            this.cleanUpCustomComponents();
            this.m_titleContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_textPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_tailContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_borderBufferContainer = ASwingHelper.makeSoftBoxJPanel();
            ASwingHelper.setEasyBorder(this.m_borderBufferContainer, this.m_margins.top, this.m_margins.left, this.m_margins.bottom, this.m_margins.right);
            this.m_textPanel.append(this.m_borderBufferContainer);
            this.m_componentsOuterContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP);
            this.m_componentsInnerContainer = new JPanel(new VerticalLayout(ToolTipSchema.getVerticalLayoutAlignment(this.m_schema.innerAlign)));
            this.m_imageContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_componentsOuterContainer.append(this.m_imageContainer);
            this.m_componentsOuterContainer.append(this.m_componentsInnerContainer);
            this.m_borderBufferContainer.append(this.m_componentsOuterContainer);
            this.makeLegacyComponents();
            this.makeCustomComponents();
            this.refreshBackground();
            this.m_tooltipWindow.append(this.m_titleContainer);
            this.m_tooltipWindow.append(this.m_textPanel);
            if (this.m_schema.tailVerticalOffset != 0)
            {
                this.m_tooltipWindow.append(ASwingHelper.verticalStrut(this.m_schema.tailVerticalOffset));
            }
            this.m_tooltipWindow.append(this.m_tailContainer);

            Debug.debug6("ToolTipDialgoView.makeCenterPanel");

            this.append(this.m_tooltipWindow);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  makeLegacyComponents ()
        {
            this.m_legacyComponentContainer = new JPanel(new VerticalLayout(ToolTipSchema.getVerticalLayoutAlignment(this.m_schema.innerAlign)));
            this.m_headerContainer = ASwingHelper.makeSoftBoxJPanel();
            this.m_notesContainer = ASwingHelper.makeSoftBoxJPanel();
            this.m_statusContainer = ASwingHelper.makeSoftBoxJPanel();
            this.m_actionContainer = ASwingHelper.makeSoftBoxJPanel();
            this.m_statusLabel = new JLabel("");
            this.m_actionLabel = new JLabel("action");
            ASFont _loc_1 =new ASFont(this.m_schema.bodyFontName ,this.m_schema.bodyFontSize ,false ,false ,false ,new ASFontAdvProperties(true ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));

            Debug.debug6("ToolTipDialogView.makeLegacyComponents " + this.m_schema.bodyFontColor);

            ASColor _loc_2 =new ASColor(this.m_schema.bodyFontColor );
            this.m_headerLabel = ASwingHelper.makeTextField("", this.m_schema.titleFontName, this.m_schema.titleFontSize, this.m_schema.titleFontColor);
            //this.m_statusLabel.setFont(_loc_1);
            //this.m_actionLabel.setFont(_loc_1);
            this.m_headerLabel.filters = this.m_schema.titleFilters;
            this.m_statusLabel.setForeground(_loc_2);
            this.m_statusLabel.setTextFilters(this.m_schema.bodyTextFilters);
            this.m_actionLabel.setForeground(_loc_2);
            this.m_actionLabel.setTextFilters(this.m_schema.bodyTextFilters);
            this.m_legacyComponentContainer.append(this.m_headerContainer);
            this.m_legacyComponentContainer.append(this.m_statusContainer);
            this.m_legacyComponentContainer.append(this.m_actionContainer);
            this.m_componentsInnerContainer.append(this.m_legacyComponentContainer);








            Debug.debug6("ToolTipDialgoView.makeLegacyComponents");

            return;
        }//end

        protected void  makeCustomComponents ()
        {
            this.m_componentListContainer = new JPanel(new VerticalLayout(ToolTipSchema.getVerticalLayoutAlignment(this.m_schema.innerAlign)));
            this.m_titleLabel = ASwingHelper.makeTextField("", this.m_schema.titleFontName, this.m_schema.titleFontSize, this.m_schema.titleFontColor);
            this.m_titleLabel.filters = this.m_schema.titleFilters;
            //this.m_titleContainer.append(this.m_titleLabel);
            //this.m_componentsInnerContainer.append(this.m_componentListContainer);
            return;
        }//end

        protected void  refreshBackground (boolean param1 =false )
        {
            this.m_tailContainer.removeAll();
            boolean _loc_2 =true ;
            if (param1 !=null)
            {
                if (this.m_customComponents.length == 0 && this.m_legacyComponentContainer.parent == null)
                {
                    _loc_2 = false;
                }
            }
            if (m_bgAsset && _loc_2)
            {
                this.m_textPanel.setBackgroundDecorator(new AssetBackground(m_bgAsset));
            }
            else
            {
                this.m_textPanel.setBackgroundDecorator(null);
            }
            if (!this.m_tail)
            {
                this.m_tail = new AssetPane();
                this.m_tail.setMaskAsset(false);
            }
            this.m_tail.setAsset(_loc_2 ? (this.m_schema.tailAsset) : (null));
            if (this.m_tail.getAsset() == null)
            {
                this.m_tail.setPreferredSize(new IntDimension(0, 0));
                this.m_tail.clearMask();
            }
            else
            {
                this.m_tail.setPreferredSize(null);
            }
            this.m_tailContainer.append(this.m_tail);
            return;
        }//end

        protected void  cleanUpLegacyComponents ()
        {
        Debug.debug6("ToolTipDialogView.cleanUpLegacyComponents");
            if (this.m_legacyComponentContainer && this.m_legacyComponentContainer.parent)
            {

            }
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  cleanUpCustomComponents ()
        {
            if (this.m_titleContainer)
            {
                this.m_titleContainer.removeAll();
            }
            if (this.m_componentListContainer)
            {
                this.m_componentListContainer.removeAll();
            }
            if (this.m_imageContainer)
            {
                this.m_imageContainer.removeAll();
            }
            this.m_customComponents = new Array();
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setTitle (String param1 )
        {
            Debug.debug6("ToolTipDilaogView.setTitle" + param1);
            if (param1 !=null)
            {
                this.m_titleLabel.setText(param1);
            }
            else
            {
                this.m_titleLabel.setText("");
            }
            if (this.m_schema.useCaps)
            {
                Debug.debug6("ToolTipDilaogView.setTitle.useCaps");
                //TextFieldUtil.formatSmallCaps(this.m_titleLabel.getTextField(), this.m_titleTextFormat);
            }
            //ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setImage (Component param1 )
        {
            Debug.debug6("ToolTipDilaogView.setImage" + param1);

            this.m_imageContainer.removeAll();
            if (param1 !=null)
            {
                this.m_imageContainer.append(param1);
            }


            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setCustomComponentsList (Array param1 )
        {
            Component _loc_2 =null ;
            this.cleanUpLegacyComponents();
            this.m_customComponents = param1;
            this.m_componentListContainer.removeAll();
            for(int i0 = 0; i0 < this.m_customComponents.size(); i0++)
            {
            	_loc_2 = this.m_customComponents.get(i0);

                this.m_componentListContainer.append(_loc_2);
            }
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setLegacyComponentsList (IToolTipTarget param1 )
        {
            this.cleanUpCustomComponents();

            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  finalize ()
        {
            this.refreshBackground(true);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  refreshSchemaFormatting (ToolTipSchema param1 )
        {
            this.m_schema = param1;
            this.m_titleTextFormat = new TextFormat(this.m_schema.titleFontName, this.m_schema.titleFontSize);
            this.m_titleTextFormat.align = TextFormatAlign.CENTER;
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setHeader (String param1 )
        {
            Debug.debug6("ToolTipDilaogView.setHeader" + param1);

            TextFormat _loc_2 =null ;
            if (param1 != null)
            {
                this.m_headerLabel.setText(param1);
                _loc_2 = new TextFormat(this.m_schema.titleFontName, this.m_schema.titleFontSize);
                _loc_2.align = this.m_schema.stringAlignment;
                TextFieldUtil.formatSmallCaps(this.m_headerLabel.getTextField(), _loc_2);
            }
            else
            {
                this.m_headerLabel.setText("");
            }
            this.replaceComponent(this.m_headerContainer, this.m_headerLabel);
            return;
        }//end

        public void  setNotes (String param1 )
        {
            Debug.debug6("ToolTipDilaogView.setNotes" + param1);
            return;
        }//end

        public void  setStatus (String param1 ,int param2 =16777215)
        {
            Debug.debug6("ToolTipDilaogView.setStatus" + param1);

            this.m_statusLabel.setText(param1);
            this.m_statusLabel.setForeground(new ASColor(param2));
            this.replaceComponent(this.m_statusContainer, this.m_statusLabel);
            return;
        }//end

        public void  setCallToAction (String param1 )
        {
            this.m_actionLabel.setText(param1);
            this.replaceComponent(this.m_actionContainer, this.m_actionLabel);
            return;
        }//end

        private void  replaceComponent (Container param1 ,Component param2 )
        {
            param1.removeAll();
            param1.append(param2);
            ASwingHelper.prepare(param1);
            return;
        }//end

    }



