package Engine.Classes;

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

import Engine.Interfaces.*;
import Engine.Managers.*;


    public class ZEngineOptions
    {
        public Class viewportClass ;
        public Class zaspManagerClass ;
        public Class tileMapClass =null ;
        public Class socialNetworkUserClass ;
        public Class idleTaskQueue ;
        public Class loadingManagerClass ;
        public boolean sendLoadingManagerStats =false ;
        public int tileWidth =24;
        public int tileHeight =12;
        public double tileScale =1;
        public String baseService ="BaseService.dispatchBatch";
        public int numShards =16;
        public Vector<IEngineComponent> engineComponents;
        public Class easterEggManager ;

        public  ZEngineOptions ()
        {
            this.viewportClass = Viewport;
            this.zaspManagerClass = ZaspManager;
            this.socialNetworkUserClass = SocialNetworkUser;
            this.idleTaskQueue = IdleTaskQueue;
            this.loadingManagerClass = LoadingManager;
            this.engineComponents = new Vector<IEngineComponent>();
            this.easterEggManager = EasterEggManager;
            return;
        }//end

        public void  addComponent (IEngineComponent param1 )
        {
            this.engineComponents.push(param1);
            return;
        }//end

    }



