package Engine.Classes.EasterEggs;

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

import Engine.Classes.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.net.*;
//import flash.text.*;
import Engine.Interfaces.*;

    public class CreditsEasterEgg implements IEasterEgg
    {
        private static  double TEXT_WIDTH =500;
        private static  double TEXT_HEIGHT =340;

        public  CreditsEasterEgg ()
        {
            return;
        }//end

        public String  code ()
        {
            return "credits";
        }//end

        public void  execute ()
        {
            URLLoader _loc_1 =new URLLoader ();
            _loc_1.addEventListener(Event.COMPLETE, this.onCreditsLoadComplete);
            _loc_1.load(new URLRequest(Config.BASE_PATH + "credits.txt"));
            return;
        }//end

        private void  onCreditsLoadComplete (Event event )
        {
            Sprite credits ;
            event = event;
            Point position =new Point(GlobalEngine.stage.stageWidth /2-TEXT_WIDTH /2,GlobalEngine.stage.stageHeight /2-TEXT_HEIGHT /2);
            textFormat = GlobalEngine.fontMapper.mapTextFormat("Credits",newTextFormat(FontNames.UNSTYLED_UI_FONT,24,16777215,false));
            textFormat.align = TextFormatAlign.CENTER;
            textFormat.leftMargin = 5;
            textFormat.rightMargin = 5;
            TextField creditsText =new TextField ();
            creditsText.defaultTextFormat = textFormat;
            creditsText.selectable = false;
            creditsText.x = position.x;
            creditsText.y = position.y;
            creditsText.wordWrap = true;
            creditsText.multiline = true;
            creditsText.htmlText =(String) URLLoader(event.currentTarget).data;
            creditsText.width = TEXT_WIDTH;
            creditsText.height = 24 * creditsText.numLines;
            BitmapData bitmapData =new BitmapData(creditsText.width ,creditsText.height ,true ,0);
            bitmapData.draw(creditsText);
            Bitmap bitmap =new Bitmap(bitmapData );
            credits = new Sprite();
            Sprite background =new Sprite ();
            credits.addChild(background);
            background.graphics.beginFill(0, 0.5);
            background.graphics.drawRoundRect(0, 0, TEXT_WIDTH, TEXT_HEIGHT, 20, 20);
            background.graphics.endFill();
            credits.addChild(bitmap);
            credits.x = position.x;
            credits.y = position.y;
            Sprite mask =new Sprite ();
            mask.x = position.x;
            mask.y = position.y;
            mask.graphics.beginFill(16777215, 1);
            mask.graphics.drawRect(0, 0, TEXT_WIDTH, TEXT_HEIGHT);
            mask.graphics.endFill();
            credits.mask = mask;
            GlobalEngine.stage.addChild(credits);
void             TweenLite .to (bitmap ,creditsText .numLines *0.75,{-y 1*creditsText .height +TEXT_HEIGHT +position .y -10,Linear ease .easeNone ,3delay , onComplete ()
            {
                if (credits != null)
                {
                    GlobalEngine.stage.removeChild(credits);
                    credits = null;
                }
                return;
            }//end
            });
            return;
        }//end

    }



