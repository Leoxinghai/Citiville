package pixelShader;

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
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;
import pbjAS.*;
import pbjAS.ops.*;
import pbjAS.params.*;
import pbjAS.regs.*;

    public class Processor
    {
        private Rectangle imgRect ;
        private Rectangle rect ;
        private Rectangle filterRect ;
        private Point mpt ;
        private int arc ;
        private int m_highlightFilterPassCount =0;
        private int bProcess =0;
        private int index =0;
        private boolean highlit =false ;
        private int animCtr =1;
        private int walkingFrames =220;
        private int pausedFrames =5;
        private int totalFrames =12;
        private int walkStyle =0;
        private double m_scaler =1;
        private Point loc ;
        private Point dest ;
        private static boolean sync =false ;
        private static ShaderJob shaderJob ;
        private static Vector shaderFilters.<ShaderFilter >=new Vector<ShaderFilter >(5,true );
        private static Processor _instance ;

        public  Processor ()
        {
            this.filterRect = new Rectangle();
            this.mpt = new Point();
            this.arc = int(Math.random() * 360);
            this.loc = new Point();
            this.dest = new Point();
            this.initShaderFilter();
            return;
        }//end

        public BitmapData  doShaderJob (BitmapData param1 ,BitmapData param2 )
        {
            _loc_3 = shaderFilters.get(0);
            BitmapData _loc_4 =new BitmapData(param1.width ,param1.height ,true ,4294967040);
            _loc_3.shader.data.texture.input = param2;
            _loc_3.shader.data.trash.input = param2;
            _loc_3.shader.data.map.input = param1;

            shaderJob = new ShaderJob(_loc_3.shader, _loc_4);
            shaderJob.addEventListener(ShaderEvent.COMPLETE, onShaderComplete, false, 0, true);



            shaderJob.start(true);
            return _loc_4;
        }//end

        private void  initShaderFilter ()
        {
            ShaderFilter _loc_1 =new ShaderFilter ();
            PBJ _loc_2 =new PBJ ();
            _loc_2.version = 1.1;
            _loc_2.name = "PixelMapper";
            _loc_2.parameters = [new PBJParam("_OutCoord", new Parameter(PBJType.TFloat2, false, new RFloat(0, [PBJChannel.R, PBJChannel.G]))), new PBJParam("constants", new Parameter(PBJType.TFloat4, false, new RFloat(3))), new PBJParam("constants2", new Parameter(PBJType.TFloat4, false, new RFloat(5))), new PBJParam("trash", new Texture(4, 0)), new PBJParam("texture", new Texture(4, 1)), new PBJParam("map", new Texture(4, 2)), new PBJParam("result", new Parameter(PBJType.TFloat4, true, new RFloat(1)))];
            _loc_2.code = [new OpSampleNearest(new RFloat(2), new RFloat(0, [PBJChannel.R, PBJChannel.G]), 2), new OpMul(new RFloat(2, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(3, [PBJChannel.B, PBJChannel.B, PBJChannel.B])), new OpEqual(new RFloat(2, [PBJChannel.R]), new RFloat(3, [PBJChannel.A])), new OpMov(new RInt(0, [PBJChannel.G]), new RInt(0, [PBJChannel.R])), new OpEqual(new RFloat(2, [PBJChannel.G]), new RFloat(3, [PBJChannel.A])), new OpMov(new RInt(0, [PBJChannel.B]), new RInt(0, [PBJChannel.R])), new OpLogicalAnd(new RInt(0, [PBJChannel.G]), new RInt(0, [PBJChannel.B])), new OpIf(new RInt(0, [PBJChannel.R])), new OpMov(new RFloat(1), new RFloat(3, [PBJChannel.A, PBJChannel.A, PBJChannel.A, PBJChannel.A])), new OpElse(), new OpSampleLinear(new RFloat(1), new RFloat(2, [PBJChannel.R, PBJChannel.G]), 1), new OpEndIf(), new OpMov(new RFloat(4, [PBJChannel.R]), new RFloat(2, [PBJChannel.B])), new OpMod(new RFloat(4, [PBJChannel.R]), new RFloat(3, [PBJChannel.R])), new OpDiv(new RFloat(4, [PBJChannel.R]), new RFloat(3, [PBJChannel.G])), new OpLessThan(new RFloat(4, [PBJChannel.R]), new RFloat(5, [PBJChannel.B])), new OpIf(new RInt(0, [PBJChannel.R])), new OpMul(new RFloat(1, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(4, [PBJChannel.R, PBJChannel.R, PBJChannel.R])), new OpMul(new RFloat(1, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(5, [PBJChannel.A, PBJChannel.A, PBJChannel.A])), new OpElse(), new OpSub(new RFloat(4, [PBJChannel.R]), new RFloat(5, [PBJChannel.B])), new OpMov(new RFloat(4, [PBJChannel.G, PBJChannel.B, PBJChannel.A]), new RFloat(5, [PBJChannel.G, PBJChannel.G, PBJChannel.G])), new OpSub(new RFloat(4, [PBJChannel.A]), new RFloat(4, [PBJChannel.R])), new OpMov(new RFloat(4, [PBJChannel.R]), new RFloat(5, [PBJChannel.G])), new OpSub(new RFloat(4, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(1, [PBJChannel.R, PBJChannel.G, PBJChannel.B])), new OpMul(new RFloat(4, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(4, [PBJChannel.A, PBJChannel.A, PBJChannel.A])), new OpMov(new RFloat(1, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(5, [PBJChannel.G, PBJChannel.G, PBJChannel.G])), new OpSub(new RFloat(1, [PBJChannel.R, PBJChannel.G, PBJChannel.B]), new RFloat(4, [PBJChannel.R, PBJChannel.G, PBJChannel.B])), new OpEndIf(), new OpMov(new RFloat(4, [PBJChannel.R]), new RFloat(2, [PBJChannel.B])), new OpDiv(new RFloat(4, [PBJChannel.R]), new RFloat(3, [PBJChannel.R])), new OpFloor(new RFloat(4, [PBJChannel.G]), new RFloat(4, [PBJChannel.R])), new OpDiv(new RFloat(4, [PBJChannel.G]), new RFloat(3, [PBJChannel.G])), new OpMov(new RFloat(1, [PBJChannel.A]), new RFloat(4, [PBJChannel.G]))];
            ByteArray _loc_3 =PBJAssembler.assemble(_loc_2 );
            Shader _loc_4 =new Shader(_loc_3 );
            _loc_1.shader = _loc_4;
            _loc_1.shader.data.constants.value = .get(16, 15, 255, 0);
            _loc_1.shader.data.constants2.value = .get(-1, 1, 0.5, 2);
            shaderFilters.put(0,  _loc_1);
            return;
        }//end

        public static Processor  instance ()
        {
            if (!_instance)
            {
                _instance = new Processor;
            }
            return _instance;
        }//end

        private static void  onShaderComplete (ShaderEvent event )
        {
            sync = false;
            return;
        }//end

    }



