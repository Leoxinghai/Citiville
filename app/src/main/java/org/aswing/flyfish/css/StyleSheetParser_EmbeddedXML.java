package org.aswing.flyfish.css;

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

    public class StyleSheetParser_EmbeddedXML extends Object
    {
        public static XML data =new XML("<CSS>"+
"\n\t<Types>"+
"\n\t\t<Type name=\"Int\" decoder=\"org.aswing.flyfish.css.property.IntDecoder\"/>"+
"\n\t\t<Type name=\"Number\" decoder=\"org.aswing.flyfish.css.property.NumberDecoder\"/>"+
"\n\t\t<Type name=\"Boolean\" decoder=\"org.aswing.flyfish.css.property.BooleanDecoder\"/>"+
"\n\t\t<Type name=\"String\" decoder=\"org.aswing.flyfish.css.property.StringDecoder\"/>"+
"\n\t\t<Type name=\"Color\" decoder=\"org.aswing.flyfish.css.property.ColorDecoder\"/>"+
"\n\t\t<Type name=\"Font\" decoder=\"org.aswing.flyfish.css.property.FontDecoder\"/>"+
"\n\t\t<Type name=\"Border\" decoder=\"org.aswing.flyfish.css.property.BorderDecoder\"/>"+
"\n\t\t<Type name=\"Layout\" decoder=\"org.aswing.flyfish.css.property.LayoutDecoder\"/>"+
"\n\t\t<Type name=\"GroundDecorator\" decoder=\"org.aswing.flyfish.css.property.GroundDecoratorDecoder\"/>"+
"\n\t\t<Type name=\"Filters\" decoder=\"org.aswing.flyfish.css.property.FiltersDecoder\"/>"+
"\n\t\t<Type name=\"Icon\" decoder=\"org.aswing.flyfish.css.property.IconDecoder\"/>"+
"\n\t\t<Type name=\"ButtonIcon\" decoder=\"org.aswing.flyfish.css.property.ButtonIconDecoder\"/>"+
"\n\t\t<Type name=\"ButtonSkin\" decoder=\"org.aswing.flyfish.css.property.ButtonSkinDecoder\"/>"+
"\n\t</Types>"+
"\n\t"+
"\n\t<Properties>"+
"\n\t\t<Property name=\"font\" type=\"Font:name 12 #000 false false false ...\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:supplyFont\"/>"+
"\n\t\t<Property name=\"font-name\" type=\"String:Arail|Tahoma|...\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-size\" type=\"Int:12|14|16|...\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-color\" type=\"Color\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-bold\" type=\"Boolean\" holder=\"font\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"font-italic\" type=\"Boolean\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-underline\" type=\"Boolean\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-url\" type=\"String\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-target\" type=\"String:_self|_blank|_top|_parent\" holder=\"font\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"font-align\" type=\"String:left|center|right|justify\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-leftMargin\" type=\"Int:0|2|4\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-rightMargin\" type=\"Int:0|2|4\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-indent\" type=\"Int:0|2|4\" holder=\"font\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"font-leading\" type=\"Int:0|2|4\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-embedFonts\" type=\"Boolean\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-antiAliasType\" type=\"String:normal|advanced\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-gridFitType\" type=\"String:none|pixel|subpixel\" holder=\"font\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"font-sharpness\" type=\"Int:-400|0|400\" holder=\"font\"/>"+
"\n\t\t<Property name=\"font-thickness\" type=\"Int:-200|0|200\" holder=\"font\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"opaque\" type=\"Boolean\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setOpaque\"/>"+
"\n\t\t<Property name=\"foreground-color\" type=\"Color\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setForeground\"/>"+
"\n\t\t<Property name=\"background-color\" type=\"Color\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setBackground\"/>"+
"\n\t\t<Property name=\"background-decorator\" type=\"GroundDecorator\" injector=\"org.aswing.flyfish.css.property.DecoratorInjector:back\"/>"+
"\n\t\t<Property name=\"foreground-decorator\" type=\"GroundDecorator\" injector=\"org.aswing.flyfish.css.property.DecoratorInjector:fore\"/>"+
"\n\t\t<Property name=\"uiClassID\" type=\"String\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setUIClassID\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"constraints\" type=\"String:Center|South|North|West|East|...\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setConstraints\"/>"+
"\n\t\t<Property name=\"tooltip\" type=\"String:the tooltip text\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setToolTipText\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"x\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setX\"/>"+
"\n\t\t<Property name=\"y\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setY\"/>"+
"\n\t\t<Property name=\"width\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setWidth\"/>"+
"\n\t\t<Property name=\"height\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setHeight\"/>"+
"\n\t\t<Property name=\"preferredWidth\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setPreferredWidth\"/>"+
"\n\t\t<Property name=\"preferredHeight\" type=\"Int\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setPreferredHeight\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"border\" type=\"Border:emtpy 2 0 2 0|line #00ff00 2 0|none\" injector=\"org.aswing.flyfish.css.property.SimpleInjector:setBorder\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"filters\" type=\"Filters\" injector=\"org.aswing.flyfish.css.property.FiltersInjector\"/>"+
"\n\t\t"+
"\n\t\t<!-- dedicated styles  -->"+
"\n\t\t<Property name=\"layout\" type=\"Layout\" injector=\"org.aswing.flyfish.css.property.LayoutInjector\" comment=\"Container only\"/>"+
"\n\t\t<Property name=\"button-margin\" type=\"String:0 0 0 0\" injector=\"org.aswing.flyfish.css.property.ButtonMarginInjector\" comment=\"Buttons only\"/>"+
"\n\t\t<Property name=\"button-skin\" type=\"ButtonSkin:@linkagePrefix|@linkage explore...\" injector=\"org.aswing.flyfish.css.property.ButtonSkinInjector\" comment=\"Button/CheckBox/RadioButton/ToggleButton/Menu/MenuItem only\"/>"+
"\n\t\t<Property name=\"button-icon\" type=\"ButtonIcon:@linkagePrefix|@linkage explore...\" injector=\"org.aswing.flyfish.css.property.ButtonIconInjector\" comment=\"Button/CheckBox/RadioButton/ToggleButton/Menu/MenuItem only\"/>"+
"\n\t\t<Property name=\"textFilters\" type=\"Filters\" injector=\"org.aswing.flyfish.css.property.TextFiltersInjector\" comment=\"Buttons/JLabel/JLabelButton only\"/>"+
"\n\t\t"+
"\n\t\t<Property name=\"icon\" type=\"Icon:@linkage 32 32|url(''123.jpg'') 32 32|none|@linkage explore...\" injector=\"org.aswing.flyfish.css.property.IconInjector\" comment=\"Buttons/JLabel/JLabelButton/JFrame only\"/>"+
"\n\t</Properties>"+
"\n</CSS>" );

        public  StyleSheetParser_EmbeddedXML ()
        {
            return;
        }//end  

    }


