package com.xiyu.flash.framework.parsers.desc;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

    public class DescriptionParser {
        public Array  parse (String descriptor ){
        	/*
            RegExp crFilter =new RegExp("\r","g");
            RegExp commentFilter =new RegExp("^ ?#.*$","gm");
            RegExp nlFilter =new RegExp("\n","g");
            RegExp whitespaceFilter =new RegExp("\t|.get( )+","g");
            descriptor = descriptor.replace(crFilter, "");
            descriptor = descriptor.replace(whitespaceFilter, " ");
            descriptor = descriptor.replace(commentFilter, "");
            descriptor = descriptor.replace(nlFilter, "");
            */
            Array results =this.parseCommands(descriptor );
            if (results == null)
            {
                return (null);
            };
            return (results);
        }
        private boolean  parseToList (ParseState state ,ListDataElement list ,boolean expectListEnd ){
            boolean addSingleChar =false;
            String aChar ;
            boolean isSeparator =false;
            ListDataElement childList ;
            boolean inSingleQuotes =false;
            boolean inDoubleQuotes =false;
            boolean isEscaped =false;
            boolean wantTerminate =false;
            SingleDataElement keyElement =null;
            SingleDataElement currentElement =null;
            int len =state.line.length() ;
            for (;state.linePosition < len;)
            {
                addSingleChar = false;
                aChar = state.readChar();
                isSeparator = (((aChar == " ")) || ((aChar == ",")));
                if (isEscaped)
                {
                    addSingleChar = true;
                }
                else
                {
                    if ((((aChar == "'")) && (!(inDoubleQuotes))))
                    {
                        inSingleQuotes = !(inSingleQuotes);
                        continue;
                    };
                    if ((((aChar == "\"")) && (!(inSingleQuotes))))
                    {
                        inDoubleQuotes = !(inDoubleQuotes);
                        continue;
                    };
                    if (aChar == "\\")
                    {
                        isEscaped = true;
                    }
                    else
                    {
                        if (((!(inSingleQuotes)) && (!(inDoubleQuotes))))
                        {
                            if (aChar == ";")
                            {
                                return (true);
                            };
                            if (aChar == ")")
                            {
                                if (expectListEnd)
                                {
                                    return (true);
                                };
                                //throw (new Error("Unexpected List End."));
                            };
                            if (aChar == "(")
                            {
                                if (wantTerminate)
                                {
                                    currentElement = null;
                                    wantTerminate = false;
                                };
                                if (currentElement != null)
                                {
                                    //throw (new Error("Unexpected List Start."));
                                };
                                childList = new ListDataElement();
                                if (!this.parseToList(state, childList, true))
                                {
                                    return (false);
                                };
                                if (keyElement != null)
                                {
                                    keyElement.mValue = childList;
                                    keyElement = null;
                                }
                                else
                                {
                                    list.mElements.push(childList);
                                };
                            }
                            else
                            {
                                if (aChar == "=")
                                {
                                    keyElement = currentElement;
                                    wantTerminate = true;
                                }
                                else
                                {
                                    if (isSeparator)
                                    {
                                        if (((!((currentElement == null))) && ((currentElement.mString.length() > 0))))
                                        {
                                            wantTerminate = true;
                                        };
                                    }
                                    else
                                    {
                                        if (wantTerminate)
                                        {
                                            currentElement = null;
                                            wantTerminate = false;
                                        };
                                        addSingleChar = true;
                                    };
                                };
                            };
                        }
                        else
                        {
                            if (wantTerminate)
                            {
                                currentElement = null;
                                wantTerminate = false;
                            };
                            addSingleChar = true;
                        };
                    };
                };
                if (addSingleChar)
                {
                    if (currentElement == null)
                    {
                        currentElement = new SingleDataElement();
                        if (keyElement != null)
                        {
                            keyElement.mValue = currentElement;
                            keyElement = null;
                        }
                        else
                        {
                            list.mElements.push(currentElement);
                        };
                    };
                    if (isEscaped)
                    {
                        currentElement.mString = (currentElement.mString + "\\");
                        isEscaped = false;
                    };
                    currentElement.mString = (currentElement.mString + aChar);
                };
            };
            if (inSingleQuotes)
            {
                //throw (new Error("Unterminated Single Quotes."));
            };
            if (inDoubleQuotes)
            {
                //throw (new Error("Unterminated Double Quotes."));
            };
            if (expectListEnd)
            {
                //throw (new Error("Unterminated List."));
            };
            return (true);
        }
        private Array  parseCommands (String line ){
            ListDataElement command ;
            DataElement elem ;
            ParseState state =new ParseState ();
            state.line = line;
            state.linePosition = 0;
            Array commands =new Array ();
            while (true)
            {
                if (state.isEOF())
                {
                    break;
                };
                command = new ListDataElement();
                if (!this.parseToList(state, command, false))
                {
                    return (null);
                };
                commands.push(command);
            };
            int numElements =commands.length() ;
            int i =(numElements -1);
            while (i > -1)
            {
            	elem=(DataElement)commands.elementAt(i);
                i = (i + -1);
            };
            return (commands);
        }

    }


