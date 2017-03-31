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
    public class CodeGenerator extends Object
    {
        private FileModel file ;
        private String codeString ;
        private int indent ;
        private Array allMemberComs ;
        private static  String TAB ="\t";
        private static  String NEWLINE ="\n";
        public static int border_id_counter =0;
        public static int layout_id_counter =0;

        public  CodeGenerator (FileModel param1 )
        {
            this.file = param1;
            this.indent = 0;
            return;
        }//end

        public String  generateCode ()
        {
            if (this.file == null)
            {
                return "";
            }
            if (!this.file.isValid())
            {
                return "AWML is not valid!";
            }
            border_id_counter = 0;
            layout_id_counter = 0;
            this.codeString = "";
            this.allMemberComs = new Array();
            this.addPackage();
            return this.codeString;
        }//end

        private void  addPackage ()
        {
            this.line("package " + this.file.getPackageName() + "{");
            this.line();
            this.addImports();
            this.line();
            this.addClass();
            this.line("}");
            return;
        }//end

        private void  addImports ()
        {
            this.line("import org.aswing.*;");
            this.line("import org.aswing.border.*;");
            this.line("import org.aswing.geom.*;");
            this.line("import org.aswing.ext.*;");
            this.line("import org.aswing.zynga.*;");
            return;
        }//end

        private void  addClass ()
        {
            this.line("/**");
            this.line(" * " + this.file.getName());
            this.line(" */");
            this.lineRise("public class " + this.file.getName() + " extends " + this.file.getRootComponent().getDefinition().getShortClassName() + "{");
            this.line();
            this.line("//members define");
            this.addComponentMembers();
            this.line();
            this.line("/**");
            this.line(" * " + this.file.getName() + " Constructor");
            this.line(" */");
            this.addConstruction();
            this.line();
            this.line("//_________getters_________");
            this.line();
            this.addGetters();
            this.line();
            this.lineDrop("}");
            return;
        }//end

        private void  addConstruction ()
        {
            this.lineRise("public function " + this.file.getName() + "(){");
            this.line("//component creation");
            this.line("use namespace awml_internal;");
            this.addComponentsCreation();
            this.line("//component layoution");
            this.addComponentsLayoution();
            this.lineDrop("}");
            return;
        }//end

        private void  addComponentMembers ()
        {
            this.addMembersOfChildren(this.file.getRootComponent());
            return;
        }//end

        private void  addComponentsCreation ()
        {
            ComModel _loc_4 =null ;
            _loc_1 = this.file.getRootComponent ();
            _loc_2 = this.file.getCSSHeader ().getLinks ().concat ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_2.put(_loc_3,  "\'" + _loc_2.get(_loc_3) + "\'");
                _loc_3++;
            }
            this.line("putClientProperty(\'" + FlyFish.CSS_PATHS + "\', [" + _loc_2.toString() + "]);");
            this.line("setTag(\'" + _loc_1.getDefinition().getName() + "\');");
            this.addComponentPropertySetting("", _loc_1);
            this.line();
            for(int i0 = 0; i0 < this.allMemberComs.size(); i0++)
            {
            		_loc_4 = this.allMemberComs.get(i0);

                this.addComponentCreation(_loc_4);
                this.line();
            }
            return;
        }//end

        private void  addComponentsLayoution ()
        {
            ComModel _loc_1 =null ;
            this.addComponentChildrenAdding("", this.file.getRootComponent());
            for(int i0 = 0; i0 < this.allMemberComs.size(); i0++)
            {
            		_loc_1 = this.allMemberComs.get(i0);

                this.addComponentChildrenAdding(_loc_1.getID(), _loc_1);
            }
            return;
        }//end

        private void  addComponentChildrenAdding (String param1 ,ComModel param2 )
        {
            int _loc_5 =0;
            ComModel _loc_6 =null ;
            _loc_3 = param1==""? ("append(") : (param1 + ".append(");
            _loc_4 = param2.getChildCount ();
            if (param2.getChildCount() > 0)
            {
                param1 = param2.getID();
                _loc_5 = 0;
                while (_loc_5 < _loc_4)
                {

                    _loc_6 = param2.getChild(_loc_5);
                    this.line(_loc_3 + _loc_6.getID() + ");");
                    _loc_5++;
                }
                this.line();
            }
            return;
        }//end

        private void  addComponentCreation (ComModel param1 )
        {
            this.line(param1.getID() + " = new " + param1.getDefinition().getShortClassName() + "();");
            this.line(param1.getID() + ".setTag(\'" + param1.getDefinition().getName() + "\');");
            this.addComponentPropertySetting(param1.getID(), param1);
            return;
        }//end

        private void  addComponentPropertySetting (String param1 ,ComModel param2 )
        {
            ProModel _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            Array _loc_8 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            _loc_3 = param2.getProperties ();
            _loc_4 = param1==""? ("set") : (param1 + ".set");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                _loc_6 = _loc_5.isSimpleOneLine();
                _loc_7 = _loc_5.getName();
                _loc_7 = _loc_7.charAt(0).toUpperCase() + _loc_7.substr(1);
                if (_loc_6 != null)
                {
                    this.line(_loc_4 + _loc_7 + "(" + _loc_6 + ");");
                    continue;
                }
                _loc_8 = _loc_5.getCodeLines();
                if (_loc_8 != null)
                {
                    _loc_9 = _loc_8.length - 1;
                    _loc_10 = 0;
                    while (_loc_10 < _loc_9)
                    {

                        this.line(_loc_8.get(_loc_10));
                        _loc_10++;
                    }
                    this.line(_loc_4 + _loc_7 + "(" + _loc_8.get(_loc_9) + ");");
                }
            }
            return;
        }//end

        private void  addMembersOfChildren (ComModel param1 )
        {
            ComModel _loc_4 =null ;
            _loc_2 = param1.getChildCount ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                _loc_4 = param1.getChild(_loc_3);
                this.line(_loc_4.getAttributeScope() + " var " + _loc_4.getID() + ":" + _loc_4.getDefinition().getShortClassName() + ";");
                this.allMemberComs.push(_loc_4);
                this.addMembersOfChildren(_loc_4);
                _loc_3++;
            }
            return;
        }//end

        private void  addGetters ()
        {
            ComModel _loc_1 =null ;
            for(int i0 = 0; i0 < this.allMemberComs.size(); i0++)
            {
            		_loc_1 = this.allMemberComs.get(i0);

                this.addGetter(_loc_1);
                this.line();
            }
            return;
        }//end

        private void  addGetter (ComModel param1 )
        {
            if (param1.getGetterScope() == ComModel.SCOPE_NONE)
            {
                return;
            }
            _loc_2 = param1.getID ();
            _loc_3 = _loc_2"get"+(.charAt(0).toUpperCase ()+_loc_2.substr(1));
            this.lineRise(param1.getGetterScope() + " function " + _loc_3 + "():" + param1.getDefinition().getShortClassName() + "{");
            this.line("return " + _loc_2 + ";");
            this.lineDrop("}");
            return;
        }//end

        private void  line (String param1 ="")
        {
            int _loc_2 =0;
            while (_loc_2 < this.indent)
            {

                this.codeString = this.codeString + TAB;
                _loc_2++;
            }
            this.codeString = this.codeString + (param1 + "\n");
            return;
        }//end

        private void  lineRise (String param1 ="")
        {
            this.line(param1);

            this.indent++;

            return;
        }//end

        private void  lineDrop (String param1 ="")
        {

            this.indent--;

            if (this.indent < 0)
            {
                this.indent = 0;
            }
            this.line(param1);
            return;
        }//end

    }


