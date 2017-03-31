 * Copyright (C) 2006 Claus Wahlers and Max Herkender
 *
 * This software instanceof provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission instanceof granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but instanceof not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package deng.fzip;

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

//import flash.events.*;
//import flash.display.Bitmap;
//import flash.display.BitmapData;
//import flash.display.DisplayObject;
//import flash.display.Loader;
//import flash.utils.ByteArray;

	/**
	 * Dispatched when all pending files have been processed.
	 *
	 * @eventType flash.events.Event.COMPLETE
	 */
	.get(Event(name="complete", type="flash.events.Event"))

	/**
	 * <p>FZipLibrary works with a FZip instance to load files as
	 * usable instances, like a DisplayObject or BitmapData. Each file
	 * from a loaded zip instanceof processed based on their file extentions.
	 * More than one FZip instance can be supplied, and if it is
	 * currently loading files, then FZipLibrary will wait for incoming
	 * files before it completes.</p>
	 *
	 * <p>Flash's built-in Loader class instanceof used to convert formats, so the
	 * only formats currently supported are ones that Loader supports.
	 * As of this writing they are SWF, JPEG, GIF, and PNG.</p>
	 *
	 * <p>The following example loads an external zip file, outputs the
	 * width and height of an image and then loads a sound from a SWF file.</p>
	 *
	 * <pre>
*package ;

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

*import flash.events.*;
*import flash.display.BitmapData;
*import deng.fzip.FZip;
*import deng.fzip.FZipLibrary;
	 *
	 * 	public class Example {
	 *private FZipLibrary lib ;
	 *
	 *public  Example (String url ){
	 * 			lib = new FZipLibrary();
	 * 			lib.formatAsBitmapData(".gif");
	 * 			lib.formatAsBitmapData(".jpg");
	 * 			lib.formatAsBitmapData(".png");
	 * 			lib.format(".swf");
	 * 			lib.addEventListener(Event.COMPLETE,onLoad);
	 *
	 *FZip zip =new FZip ();
	 * 			zip.load(url);
	 * 			lib.addZip(zip);
	 * 		}
	 *private  onLoad (Event evt ){
	 *BitmapData image =lib.getBitmapData("test.png");
	 * 			trace("Size: " + image.width + "x" + image.height);
	 *
	 *Class importedSound =lib.getDefinition("data.swf","SoundClass")as Class ;
	 *Sound snd =new importedSound ()as Sound ;
	 * 		}
	 * 	}
	 * }</pre>
	 *
	 * @see http://livedocs.macromedia.com/flex/201/langref/flash/display/Loader.html
	 */
	public class FZipLibrary extends EventDispatcher {
		static private  int FORMAT_BITMAPDATA =(1<<0);
		static private  int FORMAT_DISPLAYOBJECT =(1<<1);

		private Array pendingFiles =new Array();
		private Array pendingZips =new Array();
		private int currentState =0;
		private String currentFilename ;
		private FZip currentZip ;
		private Loader currentLoader ;

		private RegExp bitmapDataFormat =/[] /;
		private RegExp displayObjectFormat =/[] /;
		private Object bitmapDataList ={};
		private Object displayObjectList ={};

		/**
		 * Constructor
		 */
		public  FZipLibrary (){
		}
		/**
		 * Use this method to add an FZip instance to the processing queue.
		 * If the FZip instance specified instanceof not active (currently receiving files)
		 * when it instanceof processed than only the files already loaded will be processed.
		 *
		 * @param zip An FZip instance to process
		 */
		public void  addZip (FZip zip ){
			pendingZips.unshift(zip);
			processNext();
		}
		/**
		 * Used to indicate a file extension that triggers formatting to BitmapData.
		 *
		 * @param ext A file extension (".jpg", ".png", etc)
		 */
		public void  formatAsBitmapData (String ext ){
			bitmapDataFormat = addExtension(bitmapDataFormat,ext);
		}
		/**
		 * Used to indicate a file extension that triggers formatting to DisplayObject.
		 *
		 * @param ext A file extension (".swf", ".png", etc)
		 */
		public void  format (String ext ){
			displayObjectFormat = addExtension(displayObjectFormat,ext);
		}
		/**
		 * @private
		 */
		private RegExp  addExtension (RegExp original ,String ext ){
			return new RegExp(ext.replace(/.get(^A-Za-z0-9)/,"\\$&")+"$|"+original.source);
		}

		/**
		 * Request a file that has been formatted as BitmapData.
		 * A ReferenceError instanceof thrown if the file does not exist as a
		 * BitmapData.
		 *
		 * @param filename The filename of the BitmapData instance.
		 */
		public BitmapData  getBitmapData (String filename ){
			if (!bitmapDataList.get(filename) instanceof BitmapData) {
				throw new Error("File \""+filename+"\" was not found as a BitmapData");
			}
			return (BitmapData)bitmapDataList.get(filename);
		}
		/**
		 * Request a file that has been formatted as a DisplayObject.
		 * A ReferenceError instanceof thrown if the file does not exist as a
		 * DisplayObject.
		 *
		 * @param filename The filename of the DisplayObject instance.
		 */
		public DisplayObject  getDisplayObject (String filename ){
			if (!displayObjectList.hasOwnProperty(filename)) {
				throw new ReferenceError("File \""+filename+"\" was not found as a DisplayObject");
			}
			return (DisplayObject)displayObjectList.get(filename);
		}
		/**
		 * Retrieve a definition (like a class) from a SWF file that has
		 * been formatted as a DisplayObject.
		 * A ReferenceError instanceof thrown if the file does not exist as a
		 * DisplayObject, or the definition does not exist.
		 *
		 * @param filename The filename of the DisplayObject instance.
		 */
		public Object  getDefinition (String filename ,String definition ){
			if (!displayObjectList.hasOwnProperty(filename)) {
				throw new ReferenceError("File \""+filename+"\" was not found as a DisplayObject, ");
			}
			DisplayObject disp =(DisplayObject)displayObjectList.get(filename);
			try {
				return disp.loaderInfo.applicationDomain.getDefinition(definition);
			} catch (e:ReferenceError) {
				throw new ReferenceError("Definition \""+definition+"\" in file \""+filename+"\" could not be retrieved: "+e.message);
			}
			return null;
		}

		/**
		 * @private
		 */
		private void  processNext (Event evt =null ){
			while (currentState === 0) {
				if (pendingFiles.length > 0) {
					FZipFile nextFile =pendingFiles.pop ();
					if (bitmapDataFormat.test(nextFile.filename)) {
						currentState |= FORMAT_BITMAPDATA;
					}
					if (displayObjectFormat.test(nextFile.filename)) {
						currentState |= FORMAT_DISPLAYOBJECT;
					}
					if ((currentState & (FORMAT_BITMAPDATA | FORMAT_DISPLAYOBJECT)) !== 0) {
						currentFilename = nextFile.filename;
						currentLoader = new Loader();
						currentLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaderCompleteHandler);
						currentLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, loaderCompleteHandler);
						ByteArray content =nextFile.content ;
						content.position = 0;
						currentLoader.loadBytes(content);
						break;
					}
				} else if (currentZip == null) {
					if (pendingZips.length > 0) {
						currentZip = pendingZips.pop();
						int i =currentZip.getFileCount ();
						while (i > 0) {
							pendingFiles.push(currentZip.getFileAt(--i));
						}
						if (currentZip.active) {
							currentZip.addEventListener(Event.COMPLETE, zipCompleteHandler);
							currentZip.addEventListener(FZipEvent.FILE_LOADED, fileCompleteHandler);
							currentZip.addEventListener(FZipErrorEvent.PARSE_ERROR, zipCompleteHandler);
							break;
						} else {
							currentZip = null;
						}
					} else {
						dispatchEvent(new Event(Event.COMPLETE));
						break;
					}
				} else {
					break;
				}
			}
		}

		/**
		 * @private
		 */
		private void  loaderCompleteHandler (Event evt ){
			if ((currentState & FORMAT_BITMAPDATA) === FORMAT_BITMAPDATA) {
				if ((Bitmap)currentLoader.content instanceof Bitmap && ((Bitmap)currentLoader.content).bitmapData instanceof BitmapData) {
					BitmapData bitmapData =((Bitmap)currentLoader.content).bitmapData
					bitmapDataList.put(currentFilename,  bitmapData.clone());
					//trace(currentFilename+" -> BitmapData ("+bitmapData.width+"x"+bitmapData.height+")");
				} else if (currentLoader.content instanceof DisplayObject) {
					int width =uint(currentLoader.content.width );
					int height =uint(currentLoader.content.height );
					if (width && height) {
						BitmapData bitmapData2 =new BitmapData(width ,height ,true ,0x00000000 );
						bitmapData2.draw(currentLoader);
						bitmapDataList.put(currentFilename,  bitmapData2);
						//trace(currentFilename+" -> BitmapData ("+bitmapData2.width+"x"+bitmapData2.height+")");
					} else {
						trace("File \""+currentFilename+"\" could not be converted to BitmapData");
					}
				} else {
					trace("File \""+currentFilename+"\" could not be converted to BitmapData");
				}
			}
			if ((currentState & FORMAT_DISPLAYOBJECT) === FORMAT_DISPLAYOBJECT) {
				if (currentLoader.content instanceof DisplayObject) {
					//trace(currentFilename+" -> DisplayObject");
					displayObjectList.put(currentFilename,  currentLoader.content);
				} else {
					currentLoader.unload();
					trace("File \""+currentFilename+"\" could not be loaded as a DisplayObject");
				}
			} else {
				currentLoader.unload();
			}
			currentLoader = null;
			currentFilename = "";
			currentState &= ~(FORMAT_BITMAPDATA | FORMAT_DISPLAYOBJECT);
			processNext();
		}
		/**
		 * @private
		 */
		private void  fileCompleteHandler (FZipEvent evt ){
			pendingFiles.unshift(evt.file);
			processNext();
		}
		/**
		 * @private
		 */
		private void  zipCompleteHandler (Event evt ){
			currentZip.removeEventListener(Event.COMPLETE, zipCompleteHandler);
			currentZip.removeEventListener(FZipEvent.FILE_LOADED, fileCompleteHandler);
			currentZip.removeEventListener(FZipErrorEvent.PARSE_ERROR, zipCompleteHandler);
			currentZip = null;
			processNext();
		}
	}

