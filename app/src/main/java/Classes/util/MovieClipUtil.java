package Classes.util;

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

//import flash.display.*;
//import flash.events.*;

    public class MovieClipUtil
    {

        public  MovieClipUtil ()
        {
            return;
        }//end

        public static void  recursiveStop (DisplayObjectContainer param1 )
        {
            _loc_2 = null;
            if (param1 instanceof MovieClip)
            {
                ((MovieClip)param1).stop();
            }
            _loc_3 = param1.numChildren ;
            while (_loc_3--)
            {

                _loc_2 = param1.getChildAt(_loc_3);
                if (_loc_2 && _loc_2 instanceof DisplayObjectContainer)
                {
                    recursiveStop(_loc_2);
                }
            }
            return;
        }//end

        public static void  recursivePlay (DisplayObjectContainer param1 )
        {
            _loc_2 = null;
            if (param1 instanceof MovieClip)
            {
                ((MovieClip)param1).play();
            }
            _loc_3 = param1.numChildren ;
            while (_loc_3--)
            {

                _loc_2 = param1.getChildAt(_loc_3);
                if (_loc_2 && _loc_2 instanceof DisplayObjectContainer)
                {
                    recursivePlay(_loc_2);
                }
            }
            return;
        }//end

        public static void  recursiveRewind (DisplayObjectContainer param1 )
        {
            _loc_2 = null;
            if (param1 instanceof MovieClip)
            {
                ((MovieClip)param1).gotoAndStop(1);
            }
            _loc_3 = param1.numChildren ;
            while (_loc_3--)
            {

                _loc_2 = param1.getChildAt(_loc_3);
                if (_loc_2 && _loc_2 instanceof DisplayObjectContainer)
                {
                    recursiveRewind(_loc_2);
                }
            }
            return;
        }//end

        public static void  playAndStop (MovieClip param1 ,int param2 =1,int param3 =-1,boolean param4 =false ,Function param5 =null )
        {
            clip = param1;
            startFrame = param2;
            endFrame = param3;
            fullStop = param4;
            callback = param5;
            if (endFrame < startFrame)
            {
                endFrame = clip.totalFrames;
            }
            runWhenOnStage (clip ,void  ()
            {
                clip .stage .addEventListener (Event .ENTER_FRAME ,void  (Event event )
                {
                    if (clip.currentFrame >= endFrame)
                    {
                        if (clip.stage)
                        {
                            clip.stage.removeEventListener(Event.ENTER_FRAME, arguments.callee);
                            if (fullStop)
                            {
                                recursiveStop(clip);
                            }
                            else
                            {
                                clip.stop();
                            }
                            if (callback != null)
                            {
                                callback();
                            }
                        }
                    }
                    return;
                }//end
                );
                return;
            }//end
            );
            clip.gotoAndPlay(startFrame);
            return;
        }//end

        public static void  playBackwardsAndStop (MovieClip param1 ,int param2 =-1,int param3 =1,boolean param4 =false ,Function param5 =null )
        {
            clip = param1;
            startFrame = param2;
            endFrame = param3;
            fullStop = param4;
            callback = param5;
            if (startFrame < endFrame)
            {
                startFrame = clip.totalFrames;
            }
            runWhenOnStage (clip ,void  ()
            {
                clip .stage .addEventListener (Event .ENTER_FRAME ,void  (Event event )
                {
                    clip.gotoAndStop(startFrame);
                    if (clip.currentFrame <= endFrame)
                    {
                        if (clip.stage)
                        {
                            clip.stage.removeEventListener(Event.ENTER_FRAME, arguments.callee);
                            if (fullStop)
                            {
                                recursiveStop(clip);
                            }
                            else
                            {
                                clip.stop();
                            }
                            if (callback != null)
                            {
                                callback();
                            }
                        }
                    }
                    _loc_4 = startFrame-1;
                    startFrame = _loc_4;
                    return;
                }//end
                );
                return;
            }//end
            );
            clip.gotoAndPlay(startFrame);
            return;
        }//end

        public static void  playAndRemove (MovieClip param1 ,int param2 =1,int param3 =-1)
        {
            clip = param1;
            startFrame = param2;
            endFrame = param3;
            playAndStop (clip ,startFrame ,endFrame ,false ,void  ()
            {
                if (clip.parent != null)
                {
                    clip.parent.removeChild(clip);
                }
                return;
            }//end
            );
            return;
        }//end

        public static MovieClip  makeOneShotClip (Class param1 ,DisplayObjectContainer param2 ,int param3 =1)
        {
            _loc_4 = new param1 ;
            MovieClip _loc_5 =(MovieClip)new param1;
            if (!(new (MovieClip)param1 instanceof MovieClip))
            {
                throw new Error("Expected a movie clip, got: " + _loc_4);
            }
            param2.addChild(_loc_5);
            playAndRemove(_loc_5, param3);
            return _loc_5;
        }//end

        public static void  runWhenOnStage (DisplayObject param1 ,Function param2 )
        {
            clip = param1;
            callback = param2;
            if (clip.stage != null)
            {
                callback();
            }
            else
            {
                clip .addEventListener (Event .ADDED_TO_STAGE ,void  (Event event )
            {
                clip.removeEventListener(Event.ADDED_TO_STAGE, arguments.callee);
                callback();
                return;
            }//end
            );
            }
            return;
        }//end

    }



