package org.aswing.flyfish.awml;

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

import org.aswing.flyfish.*;
import org.aswing.flyfish.css.*;
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class Definition extends Object
    {
        private EventGenerator eventer ;
        private XML xml ;
        private HashMap protypes ;
        private HashMap components ;
        private HashMap layouts ;
        private HashMap layoutsClassMap ;
        private HashMap borders ;
        private HashMap bordersClassMap ;
        private Array orderComponents ;
        private Array orderLayouts ;
        private Array orderBorders ;
        private Array extComponents ;
        public static  String TAG_COM ="Com";
        private static Definition ins ;
        private static  XML varXML =new XML("<Definition>"+
"\n<Types>"+
"\n\t<Type name='Boolean' editor='org.aswing.guibuilder.property.BooleanEditor' serializer='org.aswing.flyfish.awml.property.BooleanSerializer'/>"+
"\n\t<Type name='int' editor='org.aswing.guibuilder.property.IntEditor' serializer='org.aswing.flyfish.awml.property.IntSerializer'/>"+
"\n\t<Type name='Number' editor='org.aswing.guibuilder.property.NumberEditor' serializer='org.aswing.flyfish.awml.property.NumberSerializer'/>"+
"\n\t<Type name='String' editor='org.aswing.guibuilder.property.StringEditor' serializer='org.aswing.flyfish.awml.property.StringSerializer'/>"+
"\n\t<Type name='IntDimension' editor='org.aswing.guibuilder.property.IntDimensionEditor' serializer='org.aswing.flyfish.awml.property.IntDimensionSerializer'/>"+
"\n"+
"\n\t<Type name='Size' editor='org.aswing.guibuilder.property.SizeEditor' serializer='org.aswing.flyfish.awml.property.IntDimensionSerializer'/>"+
"\n"+
"\n\t<Type name='IntPoint' editor='org.aswing.guibuilder.property.IntPointEditor' serializer='org.aswing.flyfish.awml.property.IntPointSerializer'/>"+
"\n"+
"\n\t<Type name='Location' editor='org.aswing.guibuilder.property.LocationEditor' serializer='org.aswing.flyfish.awml.property.IntPointSerializer'/>"+
"\n\t"+
"\n\t<Type name='CheckLocation' editor='org.aswing.guibuilder.property.CheckLocationEditor' serializer='org.aswing.flyfish.awml.property.IntPointSerializer'/>"+
"\n"+
"\n\t<Type name='PreferSize' editor='org.aswing.guibuilder.property.PreferSizeEditor' serializer='org.aswing.flyfish.awml.property.IntDimensionSerializer'/>"+
"\n\t"+
"\n\t<Type name='CheckSize' editor='org.aswing.guibuilder.property.CheckSizeEditor' serializer='org.aswing.flyfish.awml.property.IntDimensionSerializer'/>\t"+
"\n"+
"\n\t<Type name='Align' editor='org.aswing.guibuilder.property.AlignEditor' serializer='org.aswing.flyfish.awml.property.AlignSerializer'/>"+
"\n"+
"\n\t<Type name='Axis' editor='org.aswing.guibuilder.property.AxisEditor' serializer='org.aswing.flyfish.awml.property.AxisSerializer'/>"+
"\n"+
"\n\t<Type name='Orientation' editor='org.aswing.guibuilder.property.OrientationEditor' serializer='org.aswing.flyfish.awml.property.OrientationSerializer'/>"+
"\n"+
"\n\t<Type name='Layout' editor='org.aswing.guibuilder.property.LayoutEditor' serializer='org.aswing.flyfish.awml.property.LayoutSerializer'/>"+
"\n"+
"\n\t<Type name='Border' editor='org.aswing.guibuilder.property.BorderEditor' serializer='org.aswing.flyfish.awml.property.BorderSerializer'/>"+
"\n"+
"\n\t<Type name='Constraints' editor='org.aswing.guibuilder.property.ConstraintsEditor' serializer='org.aswing.flyfish.awml.property.StringSerializer'/>"+
"\n"+
"\n\t<Type name='Color' editor='org.aswing.guibuilder.property.ColorEditor' serializer='org.aswing.flyfish.awml.property.ColorSerializer'/>"+
"\n"+
"\n\t<Type name='Font' editor='org.aswing.guibuilder.property.FontEditor' serializer='org.aswing.flyfish.awml.property.FontSerializer'/>"+
"\n"+
"\n\t<Type name='IntEmu' editor='org.aswing.guibuilder.property.IntEmuEditor' serializer='org.aswing.flyfish.awml.property.IntSerializer'/>"+
"\n"+
"\n\t<Type name='Scope' editor='org.aswing.guibuilder.property.ScopeEditor' serializer='org.aswing.flyfish.awml.property.ScopeSerializer'/>"+
"\n"+
"\n\t<Type name='Array' editor='org.aswing.guibuilder.property.ArrayEditor' serializer='org.aswing.flyfish.awml.property.ArraySerializer'/>"+
"\n"+
"\n\t<Type name='Pack' editor='org.aswing.guibuilder.property.PackEditor' serializer='org.aswing.flyfish.awml.property.EmptySerializer'/>"+
"\n"+
"\n\t<Type name='Asset' editor='org.aswing.guibuilder.property.AssetEditor' serializer='org.aswing.flyfish.awml.property.AssetSerializer'/>"+
"\n\t\t"+
"\n\t<Type name='ScaleMode' editor='org.aswing.guibuilder.property.ScaleModeEditor' serializer='org.aswing.flyfish.awml.property.ScaleModeSerializer'/>"+
"\n</Types>"+
"\n"+
"\n<Borders>"+
"\n\t<Border name='EmptyBorder' clazz='org.aswing.border.EmptyBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Top' name='Top' type='int'/>"+
"\n\t\t<Property label='Left' name='Left' type='int'/>"+
"\n\t\t<Property label='Bottom' name='Bottom' type='int'/>"+
"\n\t\t<Property label='Right' name='Right' type='int'/>"+
"\n\t</Border>"+
"\n\t<Border name='LineBorder' clazz='org.aswing.border.LineBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Color' name='Color' type='Color' editorParam='nonull'>"+
"\n\t\t\t<Value value='0,1'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Thickness' name='Thickness' type='int'/>"+
"\n\t\t<Property label='Round' name='Round' type='int'/>"+
"\n\t</Border>"+
"\n\t<Border name='BevelBorder' clazz='org.aswing.border.BevelBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Type' name='BevelType' type='IntEmu' editorParam='Raised,Lowered'>"+
"\n\t\t\t<Value value='1'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Thickness' name='Thickness' type='int'/>"+
"\n\t</Border>"+
"\n\t<Border name='SideLineBorder' clazz='org.aswing.border.SideLineBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Color' name='Color' type='Color' editorParam='nonull'>"+
"\n\t\t\t<Value value='0,1'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Thickness' name='Thickness' type='int'/>"+
"\n\t\t<Property label='Side' name='Side' type='IntEmu' editorParam='North,South,East,West'>"+
"\n\t\t\t<Value value='0'></Value>"+
"\n\t\t</Property>"+
"\n\t</Border>"+
"\n\t<Border name='TitledBorder' clazz='org.aswing.border.TitledBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Color' name='Color' type='Color' editorParam='nonull'>"+
"\n\t\t\t<Value value='0,1'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Title' name='Title' type='String'>"+
"\n\t\t\t<Value value='title'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Position' name='Position' type='IntEmu' editorParam=',Top,,Bottom'>"+
"\n\t\t\t<Value value='1'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Align' name='Align' type='Align' editorParam='hor-only'/>"+
"\n\t\t<Property label='Beveled' name='Beveled' type='Boolean'>"+
"\n\t\t\t<Value value='true'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Edge' name='Edge' type='int'/>"+
"\n\t\t<Property label='Round' name='Round' type='int'/>"+
"\n\t\t<Property label='LineColor' name='LineColor' type='Color'/>"+
"\n\t\t<Property label='LineLightColor' name='LineLightColor' type='Color'/>"+
"\n\t</Border>"+
"\n\t<Border name='SimpleTitledBorder' clazz='org.aswing.border.SimpleTitledBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Title' name='Title' type='String'>"+
"\n\t\t\t<Value value='title'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Color' name='Color' type='Color' editorParam='nonull'>"+
"\n\t\t\t<Value value='0,1'/>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Position' name='Position' type='IntEmu' editorParam=',Top,,Bottom'>"+
"\n\t\t\t<Value value='1'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Offset' name='Offset' type='int'>"+
"\n\t\t\t<Value value='0'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Align' name='Align' type='Align' editorParam='hor-only'/>"+
"\n\t</Border>"+
"\n\t<Border name='CaveBorder' clazz='org.aswing.border.CaveBorder'>"+
"\n\t\t<Property label='Interior' name='Interior' type='Border'/>"+
"\n\t\t<Property label='Beveled' name='Beveled' type='Boolean'>"+
"\n\t\t\t<Value value='true'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='LineColor' name='LineColor' type='Color'/>"+
"\n\t\t<Property label='LineLightColor' name='LineLightColor' type='Color'/>"+
"\n\t\t<Property label='Round' name='Round' type='int'/>"+
"\n\t</Border>"+
"\n</Borders>"+
"\n"+
"\n<Layouts>"+
"\n\t<Layout name='EmptyLayout' clazz='org.aswing.EmptyLayout'>"+
"\n\t</Layout>"+
"\n\t<Layout name='CenterLayout' clazz='org.aswing.CenterLayout'>"+
"\n\t</Layout>"+
"\n\t<Layout name='LayeredLayout' clazz='org.aswing.zynga.LayeredLayout'>"+
"\n\t</Layout>"+
"\n\t<Layout name='FlowLayout' clazz='org.aswing.FlowLayout'>"+
"\n\t\t<Property label='align' name='Alignment' type='Align' editorParam='hor-only'/>"+
"\n\t\t<Property label='hgap' name='Hgap' type='int'/>"+
"\n\t\t<Property label='vgap' name='Vgap' type='int'/>"+
"\n\t\t<Property label='margin' name='Margin' type='Boolean'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='FlowWrapLayout' clazz='org.aswing.FlowWrapLayout'>"+
"\n\t\t<Property label='prefer width' name='PreferWidth' type='int'>"+
"\n\t\t\t<Value value='200'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='align' name='Alignment' type='Align' editorParam='hor-only'/>"+
"\n\t\t<Property label='hgap' name='Hgap' type='int'/>"+
"\n\t\t<Property label='vgap' name='Vgap' type='int'/>"+
"\n\t\t<Property label='margin' name='Margin' type='Boolean'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='BorderLayout' clazz='org.aswing.BorderLayout'>"+
"\n\t\t<Property label='hgap' name='Hgap' type='int'/>"+
"\n\t\t<Property label='vgap' name='Vgap' type='int'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='SoftBoxLayout' clazz='org.aswing.SoftBoxLayout'>"+
"\n\t\t<Property label='axis' name='Axis' type='Axis'/>"+
"\n\t\t<Property label='align' name='Align' type='Align'/>"+
"\n\t\t<Property label='gap' name='Gap' type='int'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='BoxLayout' clazz='org.aswing.BoxLayout'>"+
"\n\t\t<Property label='axis' name='Axis' type='Axis'/>"+
"\n\t\t<Property label='gap' name='Gap' type='int'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='GridLayout' clazz='org.aswing.GridLayout'>"+
"\n\t\t<Property label='rows' name='Rows' type='int'>"+
"\n\t\t\t<Value value='1'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='columns' name='Columns' type='int'>"+
"\n\t\t\t<Value value='0'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='hgap' name='Hgap' type='int'/>"+
"\n\t\t<Property label='vgap' name='Vgap' type='int'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='WeightBoxLayout' clazz='org.aswing.WeightBoxLayout'>"+
"\n\t\t<Property label='axis' name='Axis' type='Axis'/>"+
"\n\t\t<Property label='gap' name='Gap' type='int'/>"+
"\n\t</Layout>"+
"\n\t<Layout name='VerticalLayout' clazz='org.aswing.VerticalLayout'>"+
"\n\t\t<Property label='align' name='Align' type='Align' editorParam='hor-only'/>"+
"\n\t\t<Property label='gap' name='Gap' type='int'/>"+
"\n\t</Layout>"+
"\n</Layouts>"+
"\n"+
"\n"+
"\n<Components>"+
"\n\t<BaseComponent name='Component' clazz='org.aswing.Component'>"+
"\n\t\t<Property label='ID' name='id' type='String' category='Common' order='0'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Attr scope' name='attr-scope' type='Scope' category='Common' order='10'>"+
"\n\t\t\t<Value value='private'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='public'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Opaque' name='Opaque' type='Boolean' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Font' name='Font' type='Font' action='repaintAndRevalidate' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Foreground' name='Foreground' type='Color' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Background' name='Background' type='Color' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Enabled' name='Enabled' type='Boolean' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Clip Masked' name='ClipMasked' type='Boolean' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Trans Trigger' name='DrawTransparentTrigger' type='Boolean' action='repaint' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Location' name='Location' type='CheckLocation' action='revalidate' category='Layout' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Size' name='Size' type='CheckSize' action='revalidate' category='Layout' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='PreferSize' name='PreferredSize' type='PreferSize' action='revalidate' category='Layout' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Constraints' name='Constraints' type='Constraints' action='parentReAppendChildren' category='Layout' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tooltip' name='ToolTipText' type='String' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Border' name='Border' type='Border' action='repaintAndRevalidate' category='Common' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='css class' name='class' type='String' action='repaint' category='Common' order='11'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='css style' name='style' type='String' action='repaint' category='Common' order='12'>"+
"\n\t\t</Property>"+
"\n\t</BaseComponent>"+
"\n\t"+
"\n\t<BaseContainer name='Container' clazz='org.aswing.Container' sup='Component'>"+
"\n\t\t<Property label='Layout' name='Layout' type='Layout' action='reAppendChildren' category='Layout' order='-10'>"+
"\n\t\t</Property>"+
"\n\t</BaseContainer>"+
"\n"+
"\n\t<Com name='JPanel' clazz='org.aswing.JPanel' sup='Container'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Size' name='Size' type='CheckSize' action='revalidate' category='Layout' order='10'>"+
"\n\t\t\t<Value value='400,400'></Value>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t"+
"\n\t<Com name='JButton' clazz='org.aswing.JButton' sup='Component'>"+
"\n\t\t<Property label='Label' name='Text' type='String' action='revalidate' category='Feature' order='0'>"+
"\n\t\t\t<Value value='label'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor alig' name='HorizontalAlignment' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver alig' name='VerticalAlignment' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor text pos' name='HorizontalTextPosition' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver text pos' name='VerticalTextPosition' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Icon text gap' name='IconTextGap' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JLabel' clazz='org.aswing.JLabel' sup='Component'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Label' name='Text' type='String' category='Feature' order='0'>"+
"\n\t\t\t<Value value='label'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Selectable' name='Selectable' type='Boolean' action='repaint' category='Feature' order='1'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor alig' name='HorizontalAlignment' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver alig' name='VerticalAlignment' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor text pos' name='HorizontalTextPosition' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver text pos' name='VerticalTextPosition' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Icon text gap' name='IconTextGap' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JLabelButton' clazz='org.aswing.JLabelButton' sup='JButton'>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JToggleButton' clazz='org.aswing.JToggleButton' sup='JButton'>"+
"\n\t\t<Property label='Selected' name='Selected' type='Boolean' action='repaint' category='Feature' order='1'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n\t<Com name='JCheckBox' clazz='org.aswing.JCheckBox' sup='JToggleButton'>"+
"\n\t</Com>"+
"\n\t<Com name='JRadioButton' clazz='org.aswing.JRadioButton' sup='JToggleButton'>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JTextField' clazz='org.aswing.JTextField' sup='Component'>"+
"\n\t\t<Property label='Columns' name='Columns' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Text' name='Text' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Html text' name='HtmlText' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Editable' name='Editable' type='Boolean' action='repaint' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Password' name='DisplayAsPassword' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='MaxChars' name='MaxChars' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Restrict' name='Restrict' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Wrap' name='WordWrap' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JTextArea' clazz='org.aswing.JTextArea' sup='Component'>"+
"\n\t\t<Property label='Rows' name='Rows' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Columns' name='Columns' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Text' name='Text' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Html text' name='HtmlText' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Editable' name='Editable' type='Boolean' action='repaint' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Password' name='DisplayAsPassword' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='MaxChars' name='MaxChars' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Restrict' name='Restrict' type='String' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Wrap' name='WordWrap' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n\t"+
"\n\t<Com name='AssetPane' clazz='org.aswing.AssetPane' sup='Component'>"+
"\n\t\t<Property label='Asset' name='Asset' type='Asset' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='OffsetX' name='OffsetX' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='OffsetY' name='OffsetY' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor alig' name='HorizontalAlignment' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver alig' name='VerticalAlignment' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Scale Mode' name='ScaleMode' type='ScaleMode' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Custom Scale' name='CustomScale' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='MultilineLabel' clazz='org.aswing.ext.MultilineLabel' sup='JTextArea'>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JStepper' clazz='org.aswing.JStepper' sup='Component'>"+
"\n\t\t<Property label='Orientation' name='Orientation' type='Orientation' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='columns' name='Columns' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t\t<Value value='3'></Value>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n\t"+
"\n\t<Com name='JScrollBar' clazz='org.aswing.JScrollBar' sup='Component'>"+
"\n\t\t<Property label='Orientation' name='Orientation' type='Orientation' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Value' name='Value' type='int' category='Feature' order='10'>"+
"\n\t\t\t<Value value='0'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Extent' name='VisibleAmount' type='int' category='Feature' order='10'>"+
"\n\t\t\t<Value value='10'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Minimum' name='Minimum' type='int' category='Feature' order='10'>"+
"\n\t\t\t<Value value='0'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Maximum' name='Maximum' type='int' category='Feature' order='10'>"+
"\n\t\t\t<Value value='100'></Value>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n"+
"\n\t<Com name='JScrollPane' clazz='org.aswing.JScrollPane' sup='Container'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='-10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Layout' name='Layout' type='Layout' action='reAppendChildren' editorParam='disabled' category='Layout' order='-10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n\t<Com name='JViewport' clazz='org.aswing.JViewport' sup='Container'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor alig' name='HorizontalAlignment' type='Align' action='revalidate' editorParam='hor-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver alig' name='VerticalAlignment' type='Align' action='revalidate' editorParam='ver-only' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tracks width' name='TracksWidth' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tracks height' name='TracksHeight' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor unit increment' name='HorizontalUnitIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor block increment' name='HorizontalBlockIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver unit iuncrement' name='VerticalUnitIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver block increment' name='VerticalBlockIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Layout' name='Layout' type='Layout' action='reAppendChildren' editorParam='disabled' category='Layout' order='-10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JSeparator' clazz='org.aswing.JSeparator' sup='Component'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Orientation' name='Orientation' type='Orientation' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JList' clazz='org.aswing.JList' sup='Component'>"+
"\n\t\t<Property label='Data' name='ListData' type='Array' category='Feature' order='0'>"+
"\n\t\t\t<Value value='item1,item2,item3,item4,item5,itemABC,itemDEF,itemHIJKLMN,itemOPQ,itemRST,AnyOtherItem,addHere'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Selection mode' name='SelectionMode' type='IntEmu' editorParam='Single,Multiple' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Prefer cell width' name='PreferredCellWidthWhenNoCount' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Visible cell width' name='VisibleCellWidth' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Visible row count' name='VisibleRowCount' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Selection fore' name='SelectionForeground' type='Color' action='repaint' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Selection back' name='SelectionBackground' type='Color' action='repaint' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tracks width' name='TracksWidth' type='Boolean' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor unit increment' name='HorizontalUnitIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Hor block increment' name='HorizontalBlockIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver unit iuncrement' name='VerticalUnitIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Ver block increment' name='VerticalBlockIncrement' type='int' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='JSpacer' clazz='org.aswing.JSpacer' sup='Component'>"+
"\n\t\t<Property label='Getter scope' name='getter-scope' type='Scope' editorParam='none-enabled' category='Common' order='10'>"+
"\n\t\t\t<Value value='none'></Value>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n"+
"\n\t<Com name='GridList' clazz='org.aswing.ext.GridList' sup='JViewport'>"+
"\n\t\t<Property label='Data' name='ListData' type='Array' category='Feature' order='0'>"+
"\n\t\t\t<Value value='item1,item2,item3,item4,item5,itemABC,itemDEF,itemHIJKLMN,itemOPQ,itemRST,AnyOtherItem,addHere'></Value>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Selection mode' name='SelectionMode' type='IntEmu' editorParam='Single,Multiple' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Columns' name='Columns' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Rows' name='Rows' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tile Width' name='TileWidth' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='Tile Height' name='TileHeight' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='HGap' name='HGap' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='VGap' name='VGap' type='int' action='revalidate' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t\t<Property label='AutoScroll' name='AutoScroll' type='Boolean' category='Feature' order='10'>"+
"\n\t\t</Property>"+
"\n\t</Com>"+
"\n</Components>"+
"\n"+
"\n</Definition>" );


        public  Definition (XML param1 )
        {
            if (ins)
            {
                throw new Error("Sington can\'t be instant more than once!");
            }
            ins = this;
            this.eventer = new EventGenerator();
            this.protypes = new HashMap();
            this.components = new HashMap();
            this.orderComponents = new Array();
            this.orderLayouts = new Array();
            this.layouts = new HashMap();
            this.layoutsClassMap = new HashMap();
            this.borders = new HashMap();
            this.bordersClassMap = new HashMap();
            this.orderBorders = new Array();
            this.extComponents = new Array();
            this.init(param1);
            return;
        }//end

        public XML  getCSSDefXML ()
        {
            return StyleSheetParser.ins.getDefXML();
        }//end

        public void  setExtraComponents (XMLList param1 )
        {
            XML _loc_4 =null ;
            ComDefinition _loc_5 =null ;
            String _loc_6 =null ;
            ComDefinition _loc_7 =null ;
            Array _loc_2 =new Array();
            _loc_3 = param1;
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_6 = _loc_4.@sup;
                if (ResourceManager.ins.getClass(_loc_4.@clazz))
                {
                    _loc_7 = new ComDefinition(_loc_4, this.getComDefinition(_loc_6));
                    _loc_2.push(_loc_7);
                    continue;
                }
                AGLog.warn("Can not find definition in libs, it will be ignored : " + _loc_4.@clazz);
            }
            for(int i0 = 0; i0 < this.extComponents.size(); i0++) 
            {
            		_loc_5 = this.extComponents.get(i0);

                this.components.remove(_loc_5.getName());
            }
            this.extComponents = _loc_2;
            for(int i0 = 0; i0 < this.extComponents.size(); i0++) 
            {
            		_loc_5 = this.extComponents.get(i0);

                this.components.put(_loc_5.getName(), _loc_5);
            }
            this.extComponents.sort(this.__compareCD);
            this.eventer.dispatchEvent("extraComponentChangeHandler", [this.extComponents]);
            return;
        }//end

        public Array  getExtComponents ()
        {
            return this.extComponents.concat();
        }//end

        public void  addExtraComponentChangeHandler (Function param1 )
        {
            this.eventer.addListener("extraComponentChangeHandler", param1);
            return;
        }//end

        private void  init (XML param1 )
        {
            XML _loc_4 =null ;
            ComDefinition _loc_5 =null ;
            ComDefinition _loc_6 =null ;
            _loc_7 = null;
            XML _loc_8 =null ;
            _loc_9 = undefined;
            XML _loc_10 =null ;
            _loc_11 = undefined;
            XML _loc_12 =null ;
            ProTypeDefinition _loc_13 =null ;
            String _loc_14 =null ;
            ComDefinition _loc_15 =null ;
            LayoutDefinition _loc_16 =null ;
            BorderDefinition _loc_17 =null ;
            this.xml = param1;
            _loc_2 = StyleSheetParser.ins;
            _loc_3 = param1.Types.Type ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++) 
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_13 = new ProTypeDefinition(_loc_4);
                this.protypes.put(_loc_13.getName(), _loc_13);
            }
            _loc_5 = new ComDefinition(param1.Components.BaseComponent, null);
            _loc_6 = new ComDefinition(param1.Components.BaseContainer, _loc_5);
            this.components.put(_loc_5.getName(), _loc_5);
            this.components.put(_loc_6.getName(), _loc_6);
            _loc_7 = param1.Components.Com;
            for(int i0 = 0; i0 < _loc_7.size(); i0++) 
            {
            		_loc_8 = _loc_7.get(i0);

                _loc_14 = _loc_8.@sup;
                _loc_15 = new ComDefinition(_loc_8, this.getComDefinition(_loc_14));
                this.components.put(_loc_15.getName(), _loc_15);
                if (_loc_8.name() == TAG_COM)
                {
                    this.orderComponents.push(_loc_15);
                }
            }
            this.orderComponents.sort(this.__compareCD);
            _loc_9 = param1.Layouts.Layout;
            for(int i0 = 0; i0 < _loc_9.size(); i0++) 
            {
            		_loc_10 = _loc_9.get(i0);

                _loc_14 = _loc_10.@sup;
                _loc_16 = new LayoutDefinition(_loc_10, this.getLayoutDefinition(_loc_14));
                this.layouts.put(_loc_16.getName(), _loc_16);
                this.layoutsClassMap.put(_loc_16.getClassName(), _loc_16);
                this.orderLayouts.push(_loc_16);
            }
            _loc_11 = param1.Borders.Border;
            for(int i0 = 0; i0 < _loc_11.size(); i0++) 
            {
            		_loc_12 = _loc_11.get(i0);

                _loc_14 = _loc_12.@sup;
                _loc_17 = new BorderDefinition(_loc_12, this.getBorderDefinition(_loc_14));
                this.borders.put(_loc_17.getName(), _loc_17);
                this.bordersClassMap.put(_loc_17.getClassName(), _loc_17);
                this.orderBorders.push(_loc_17);
            }
            return;
        }//end

        private int  __compareCD (ComDefinition param1 ,ComDefinition param2 )
        {
            if (param1.getName() < param2.getName())
            {
                return -1;
            }
            return 1;
        }//end

        public Array  getComNames ()
        {
            return this.components.keys();
        }//end

        public Array  getProNames ()
        {
            return this.protypes.keys();
        }//end

        public ComDefinition  getComDefinition (String param1 )
        {
            return this.components.getValue(param1);
        }//end

        public LayoutDefinition  getLayoutDefinition (String param1 )
        {
            return this.layouts.getValue(param1);
        }//end

        public LayoutDefinition  getLayoutDefinitionWithClassName (String param1 )
        {
            return this.layoutsClassMap.getValue(param1);
        }//end

        public ProTypeDefinition  getProTypeDefinition (String param1 )
        {
            return this.protypes.getValue(param1);
        }//end

        public BorderDefinition  getBorderDefinitionWithClassName (String param1 )
        {
            return this.bordersClassMap.getValue(param1);
        }//end

        public BorderDefinition  getBorderDefinition (String param1 )
        {
            return this.borders.getValue(param1);
        }//end

        public Array  getComponents ()
        {
            return this.orderComponents.concat();
        }//end

        public Array  getTypes ()
        {
            return this.protypes.keys();
        }//end

        public Array  getLayouts ()
        {
            return this.orderLayouts.concat();
        }//end

        public Array  getBorders ()
        {
            return this.orderBorders.concat();
        }//end

        public static Definition  getIns ()
        {
            if (ins == null)
            {
                new Definition(varXML);
            }
            return ins;
        }//end

    }


