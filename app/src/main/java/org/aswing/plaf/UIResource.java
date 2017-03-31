package org.aswing.plaf;

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

	
/**
 * This interface is used to mark objects created by ComponentUI delegates.
 * The <code>ComponentUI.installUI()</code> and 
 * <code>ComponentUI.uninstallUI()</code> methods can use this interface
 * to decide if a properties value has been overridden.  For example, the
 * JPanel border property is initialized by BasicPanelUI.installUI(),
 * only if it's initial value is an UIResource instance:
 * <pre>
 * if (panel.getBorder() is UIResource) {
 *     panel.setBorder(UIManager.getBorder("Panel.border"));
 * }
 * </pre>
 * At uninstallUI() time we will not reset the property, because it will 
 * be replaced by next UI installing.
 * 
 * Some other type value like Numbers, Booleans, there will be method in the Component 
 * indicated that if is it set by user or LAFs, for example:
 * <pre>
 * if (!panel.isOpaqueSet()) {
 *     panel.setOpaque(UIManager.getBoolean("Panel.opaque"));
 *     panel.setOpaqueSet(false);
 * }
 * </pre>
 * @see EmptyUIResources
 * @see ComponentUI
 * @author iiley
 */	
public interface UIResource
	

}


