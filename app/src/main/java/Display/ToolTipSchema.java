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

import Classes.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.colorchooser.*;

    public class ToolTipSchema
    {
        protected String m_titleFontName ;
        protected String m_bodyFontName ;
        protected int m_titleFontColor ;
        protected int m_bodyFontColor ;
        protected double m_titleFontSize ;
        protected double m_bodyFontSize ;
        protected DisplayObject m_bgAsset ;
        protected DisplayObject m_tailBgAsset ;
        protected Array m_titleFilters ;
        protected Array m_bodyTextFilters ;
        protected int m_innerAlign ;
        protected double m_tailVerticalOffset ;
        protected boolean m_useCaps ;
        public static  int LEFT =2;
        public static  int CENTER =0;
        public static  int RIGHT =4;
        public static  int TOP =1;
        public static  int BOTTOM =3;
        public static ToolTipSchema TOOLTIP_SCHEMA_DEFAULT =new ToolTipSchema(EmbeddedArt.titleFont ,EmbeddedArt.defaultFontNameBold ,EmbeddedArt.whiteTextColor ,EmbeddedArt.whiteTextColor ,18,12,EmbeddedArt.commonBlackFilter ,EmbeddedArt.commonBlackFilter );
        public static ToolTipSchema TOOLTIP_SCHEMA_NORMAL =new ToolTipSchema(EmbeddedArt.titleFont ,EmbeddedArt.defaultFontNameBold ,EmbeddedArt.whiteTextColor ,EmbeddedArt.darkBrownTextColor ,18,12,EmbeddedArt.toolTipNormalTitleFilters ,null ,EmbeddedArt.tooltip_bg_blue ,EmbeddedArt.tooltip_tail_blue ,LEFT ,-4);
        public static ToolTipSchema TOOLTIP_SCHEMA_FRANCHISE =new ToolTipSchema(EmbeddedArt.titleFont ,EmbeddedArt.defaultFontNameBold ,EmbeddedArt.whiteTextColor ,EmbeddedArt.darkBrownTextColor ,18,12,EmbeddedArt.toolTipFranchiseTitleFilters ,null ,EmbeddedArt.tooltip_bg_orange ,EmbeddedArt.tooltip_tail_orange ,LEFT ,-4);
        public static ToolTipSchema TOOLTIP_SCHEMA_BLUEBOX =new ToolTipSchema(EmbeddedArt.titleFont ,EmbeddedArt.defaultFontNameBold ,EmbeddedArt.whiteTextColor ,EmbeddedArt.darkBrownTextColor ,18,12,EmbeddedArt.commonBlackFilter ,null ,EmbeddedArt.tooltip_bg_blue ,null ,LEFT ,0);
        public static ToolTipSchema TOOLTIP_SCHEMA_BRANDEDBIZ =new ToolTipSchema(EmbeddedArt.titleFont ,EmbeddedArt.defaultFontNameBold ,EmbeddedArt.whiteTextColor ,EmbeddedArt.darkBrownTextColor ,18,12,EmbeddedArt.toolTipNormalTitleFilters ,null ,EmbeddedArt.tooltip_bg_orange ,EmbeddedArt.tooltip_tail_orange ,LEFT ,-4,false );

        public  ToolTipSchema (String param1 ,String param2 ,int param3 ,int param4 ,double param5 ,double param6 ,Array param7 =null ,Array param8 =null ,Class param9 =null ,Class param10 =null ,int param11 =0,double param12 =0,boolean param13 =true )
        {
            this.m_titleFontName = param1;
            this.m_bodyFontName = param2;
            this.m_titleFontColor = param3;
            this.m_bodyFontColor = param4;
            this.m_titleFontSize = param5;
            this.m_bodyFontSize = param6;
            this.m_titleFilters = param7 ? (param7) : ([]);
            this.m_bodyTextFilters = param8 ? (param8) : ([]);
            this.m_innerAlign = param11;
            this.m_tailVerticalOffset = param12;
            this.m_useCaps = param13;
            this.m_bgAsset = param9 ? (new (DisplayObject)param9) : (null);
            this.m_tailBgAsset = !this.m_bgAsset ? (null) : (param10 ? (new (DisplayObject)param10) : (null));
            return;
        }//end

        public String  titleFontName ()
        {
            return this.m_titleFontName;
        }//end

        public String  bodyFontName ()
        {
            return this.m_bodyFontName;
        }//end

        public int  titleFontColor ()
        {
            return this.m_titleFontColor;
        }//end

        public int  bodyFontColor ()
        {
            return this.m_bodyFontColor;
        }//end

        public double  titleFontSize ()
        {
            return this.m_titleFontSize;
        }//end

        public double  bodyFontSize ()
        {
            return this.m_bodyFontSize;
        }//end

        public Array  titleFilters ()
        {
            return this.m_titleFilters;
        }//end

        public Array  bodyTextFilters ()
        {
            return this.m_bodyTextFilters;
        }//end

        public int  innerAlign ()
        {
            return this.m_innerAlign;
        }//end

        public double  tailVerticalOffset ()
        {
            return this.m_tailVerticalOffset;
        }//end

        public boolean  useCaps ()
        {
            return this.m_useCaps;
        }//end

        public DisplayObject  backgroundAsset ()
        {
            return this.m_bgAsset;
        }//end

        public DisplayObject  tailAsset ()
        {
            return this.m_tailBgAsset;
        }//end

        public String  stringAlignment ()
        {
            switch(this.innerAlign)
            {
                case SoftBoxLayout.CENTER:
                {
                    return TextFormatAlign.CENTER;
                }
                case SoftBoxLayout.LEFT:
                {
                    return TextFormatAlign.LEFT;
                }
                case SoftBoxLayout.RIGHT:
                {
                    return TextFormatAlign.RIGHT;
                }
                default:
                {
                    break;
                }
            }
            return TextFormatAlign.CENTER;
        }//end

        public static ToolTipSchema  getSchemaForObject (IToolTipTarget param1 )
        {
            _loc_2 = TOOLTIP_SCHEMA_DEFAULT;
            if (param1 !=null)
            {
                _loc_2 = param1.getToolTipSchema();
                if (!_loc_2)
                {
                    if (param1 instanceof ICustomToolTipTarget && !(param1 instanceof Wilderness))
                    {
                        if (param1 instanceof Business)
                        {
                            if (((Business)param1).isBranded())
                            {
                                _loc_2 = TOOLTIP_SCHEMA_BRANDEDBIZ;
                            }
                            else if (((Business)param1).isFranchise())
                            {
                                _loc_2 = TOOLTIP_SCHEMA_FRANCHISE;
                            }
                            else
                            {
                                _loc_2 = TOOLTIP_SCHEMA_NORMAL;
                            }
                        }
                        else if (param1 instanceof Decoration)
                        {
                            if (((Decoration)param1).isBranded())
                            {
                                _loc_2 = TOOLTIP_SCHEMA_BRANDEDBIZ;
                            }
                            else
                            {
                                _loc_2 = TOOLTIP_SCHEMA_NORMAL;
                            }
                        }
                        else if (param1 instanceof ConstructionSite)
                        {
                            if (((ConstructionSite)param1).isBranded())
                            {
                                _loc_2 = TOOLTIP_SCHEMA_BRANDEDBIZ;
                            }
                            else
                            {
                                _loc_2 = TOOLTIP_SCHEMA_NORMAL;
                            }
                        }
                        else
                        {
                            _loc_2 = TOOLTIP_SCHEMA_NORMAL;
                        }
                    }
                    else
                    {
                        _loc_2 = TOOLTIP_SCHEMA_DEFAULT;
                    }
                }
            }
            return _loc_2;
        }//end

        public static int  getVerticalLayoutAlignment (int param1 )
        {
            switch(param1)
            {
                case LEFT:
                {
                    return VerticalLayout.LEFT;
                }
                case RIGHT:
                {
                    return VerticalLayout.RIGHT;
                }
                default:
                {
                    return VerticalLayout.CENTER;
                    break;
                }
            }
        }//end

        public static int  getSoftBoxAlignment (int param1 )
        {
            return param1;
        }//end

    }



