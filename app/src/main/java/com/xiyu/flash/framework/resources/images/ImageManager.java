package com.xiyu.flash.framework.resources.images;
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

import com.pgh.mahjong.resource.MahJongImages;
import com.xiyu.util.*;

import android.view.SurfaceView;

import java.util.Hashtable;

import com.xiyu.flash.framework.AppBase;
    public class ImageManager {
    	Hashtable images;
        public ImageInst  getImageInst (String id ){
        	int moffset = id.indexOf("MAHJONG_");
        	if(moffset >=0) {
        		id = id.substring("MAHJONG_".length());
        		Bitmap bitmap0 = MahJongImages.getInstance(this.mApp.getAssets(),null).getMahJongImage(id);
        		ImageInst imageinst0 = new ImageInst(new ImageData(new BitmapData(bitmap0)));
        		images.put(id, imageinst0);
        		return imageinst0;
        	}

        	int offset = id.indexOf("IMAGE_REANIM_");
        	if(offset >=0) {
        		id = id.substring("IMAGE_REANIM_".length());
        	}
        	if(offset <0) {
    	    	offset = id.indexOf("IMAGE_");
    	    	if(offset >=0) {
    	    		id = id.substring("IMAGE_".length());
    	    	}
        	}
        	ImageInst imageinst = null;//(ImageInst)images.get(id);
        	if(imageinst == null) {
        		Bitmap bitmap = mApp.getResourcesImage(id);
        		imageinst = new ImageInst(new ImageData(new BitmapData(bitmap)));
        		//images.put(id, imageinst);
        	}

        	return imageinst;
        	//return (ImageInst)images.get(id);
/*
            Object obj =this.mApp.resourceManager().getResource(id );
            ImageDescriptor desc =(ImageDescriptor)obj;
            if (desc != null)
            {
                obj = desc.createData();
                this.mApp.resourceManager().setResource(id, obj);
            };
            ImageData data =(ImageData)obj;
            if (data == null)
            {
                return null;
//				//throw (new Error((("Image '" + id) + "' instanceof not loaded.")));
            };
            ImageInst img =new ImageInst(data );
*/

//            return (img);
        }

        private AppBase mApp ;



        public void  addDescriptor (String id ,ImageDescriptor desc ){
            this.mApp.resourceManager().setResource(id, desc);
        }

        public void setImageInst(String id, ImageInst image) {
        	images.put(id, image);
        }

        public  ImageManager (AppBase app ){
            this.mApp = app;
            images = new Hashtable();
        }
    }


