/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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


import org.aswing.event.*;
import org.aswing.plaf.*;
import flash.events.Event;
import flash.display.InteractiveObject;
import org.aswing.plaf.basic.BasicComboBoxUI;

/**
 * Dispatched when the combobox act, when value set or selection changed.
 * @eventType org.aswing.event.AWEvent.ACT
 * @see org.aswing.JComboBox#addActionListener()
 */
.get(Event(name="act", type="org.aswing.event.AWEvent"))

/**
 *  Dispatched when the combobox's selection changed.
 *
 *  @eventType org.aswing.event.InteractiveEvent.SELECTION_CHANGED
 */
.get(Event(name="selectionChanged", type="org.aswing.event.InteractiveEvent"))

/**
 * A component that combines a button or editable field and a drop-down list.
 * The user can select a value from the drop-down list, which appears at the
 * user's request. If you make the combo box editable, then the combo box
 * includes an editable field into which the user can type a value.
 * <p>
 * <code>JComboBox</code> use a <code>JList</code> to be the drop-down list,
 * so of course you can operate list to do some thing.
 * </p>
 * <p>
 * By default <code>JComboBox</code> can't count its preffered width accurately
 * like default JList, you have to set its preffered size if you want.
 * Or you make a not shared cell factory to it. see <code>ListCellFactory</code>
 * and <code>JList</code> for details.
 * </p>
 * @see JList
 * @see ComboBoxEditor
 * @see DefaultComboBoxEditor
 * @author iiley
 */
public class JComboBox extends Component implements EditableComponent{

	private boolean editable ;
	private int maximumRowCount ;
	protected ComboBoxEditor editor ;
	protected JList popupList ;

	/**
	 * Create a combobox with specified data.
	 */
	( = JComboBoxlistDatanull){
		super();

		setName("JComboBox");
		maximumRowCount = 7;
		editable = false;
		setEditor(new DefaultComboBoxEditor());
		if(listData != null){
			if(listData is ListModel){
				setModel(ListModel(listData));
			}else if(listData is Array){
				setListData((Array)listData);
			}else{
				setListData(null); //create new
			}
		}

		updateUI();
	}

	/**
	 * Sets the ui.
	 * <p>
	 * JComboBox ui should implemented <code>ComboBoxUI</code> interface!
	 * </p>
	 * @param newUI the newUI
	 * @throws ArgumentError when the newUI is not an <code>ComboBoxUI</code> instance.
	 */
	 public void  setUI (ComponentUI newUI ){
		if(newUI is ComboBoxUI){
			super.setUI(newUI);
			getEditor().getEditorComponent().setFont(getFont());
			getEditor().getEditorComponent().setForeground(getForeground());
		}else{
			throw new ArgumentError("JComboBox ui should implemented ComboBoxUI interface!");
		}
	}

	 public void  updateUI (){
		getPopupList().updateUI();
		editor.getEditorComponent().updateUI();
		setUI(UIManager.getUI(this));
	}

     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicComboBoxUI;
    }

	 public String  getUIClassID (){
		return "ComboBoxUI";
	}

    /**
     * Returns the ui for this combobox with <code>ComboBoxUI</code> instance
     * @return the combobox ui.
     */
    public ComboBoxUI  getComboBoxUI (){
    	return getUI() as ComboBoxUI;
    }

	/**
	 * The ActionListener will receive an ActionEvent when a selection has been made.
	 * If the combo box is editable, then an ActionEvent will be fired when editing has stopped.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	public void  addActionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(AWEvent.ACT, listener, false, priority, useWeakReference);
	}

	/**
	 * Removes a action listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.AWEvent#ACT
	 */
	public void  removeActionListener (Function listener ){
		removeEventListener(AWEvent.ACT, listener, false);
	}


	/**
	 * Add a listener to listen the combobox's selection change event.
	 * When the combobox's selection changed or a different value inputed or set programmiclly.
	 * @param listener the listener
	 * @param priority the priority
	 * @param useWeakReference Determines whether the reference to the listener is strong or weak.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */
	public void  addSelectionListener (Function listener ,int priority =0,boolean useWeakReference =false ){
		addEventListener(InteractiveEvent.SELECTION_CHANGED, listener, false, priority);
	}

	/**
	 * Removes a selection listener.
	 * @param listener the listener to be removed.
	 * @see org.aswing.event.InteractiveEvent#SELECTION_CHANGED
	 */
	public void  removeSelectionListener (Function listener ){
		removeEventListener(InteractiveEvent.SELECTION_CHANGED, listener);
	}

	/**
	 * Returns the popup list that display the items.
	 */
	public JList  getPopupList (){
		if(popupList == null){
			popupList = new JList(null, new DefaultComboBoxListCellFactory());
			popupList.setSelectionMode(JList.SINGLE_SELECTION);
			popupList.addSelectionListener(__listSelectionChanged);
		}
		return popupList;
	}

	/**
     * Sets the maximum number of rows the <code>JComboBox</code> displays.
     * If the number of objects in the model is greater than count,
     * the combo box uses a scrollbar.
     * @param count an integer specifying the maximum number of items to
     *              display in the list before using a scrollbar
     */
	public void  setMaximumRowCount (int count ){
		maximumRowCount = count;
	}

	/**
     * Returns the maximum number of items the combo box can display
     * without a scrollbar
     * @return an integer specifying the maximum number of items that are
     *         displayed in the list before using a scrollbar
     */
	public int  getMaximumRowCount (){
		return maximumRowCount;
	}

	/**
	 * @return the cellFactory for the popup List
	 */
	public ListCellFactory  getListCellFactory (){
		return getPopupList().getCellFactory();
	}

	/**
	 * This will cause all cells recreating by new factory.
	 * @param newFactory the new cell factory for the popup List
	 */
	public void  setListCellFactory (ListCellFactory newFactory ){
		getPopupList().setCellFactory(newFactory);
	}

	/**
     * Sets the editor used to paint and edit the selected item in the
     * <code>JComboBox</code> field.  The editor is used both if the
     * receiving <code>JComboBox</code> is editable and not editable.
     * @param anEditor  the <code>ComboBoxEditor</code> that
     *			displays the selected item
     */
	public void  setEditor (ComboBoxEditor anEditor ){
		if(anEditor == null) return;

		ComboBoxEditor oldEditor =editor ;
		if (oldEditor != null){
			oldEditor.removeActionListener(__editorActed);
			oldEditor.getEditorComponent().removeFromContainer();
		}
		editor = anEditor;
		editor.setEditable(isEditable());
		addChild(editor.getEditorComponent());
		if(ui != null){//means ui installed
			editor.getEditorComponent().setFont(getFont());
			editor.getEditorComponent().setForeground(getForeground());
			editor.getEditorComponent().setBackground(getBackground());
		}
		editor.addActionListener(__editorActed);
		revalidate();
	}

	/**
     * Returns the editor used to paint and edit the selected item in the
     * <code>JComboBox</code> field.
     * @return the <code>ComboBoxEditor</code> that displays the selected item
     */
	public ComboBoxEditor  getEditor (){
		return editor;
	}

	/**
	 * Returns the editor component internal focus object.
	 */
	 public InteractiveObject  getInternalFocusObject (){
		if(isEditable()){
			return getEditor().getEditorComponent().getInternalFocusObject();
		}else{
			return this;
		}
	}

	/**
	 * Apply a new font to combobox and its editor and its popup list.
	 */
	 public void  setFont (ASFont newFont ){
		super.setFont(newFont);
		getPopupList().setFont(newFont);
		getEditor().getEditorComponent().setFont(newFont);
	}

	/**
	 * Apply a new foreground to combobox and its editor and its popup list.
	 */
	 public void  setForeground (ASColor c ){
		super.setForeground(c);
		getPopupList().setForeground(c);
		getEditor().getEditorComponent().setForeground(c);
	}

	/**
	 * Apply a new background to combobox and its editor and its popup list.
	 */
	 public void  setBackground (ASColor c ){
		super.setBackground(c);
		getPopupList().setBackground(c);
		getEditor().getEditorComponent().setBackground(c);
	}

	/**
     * Determines whether the <code>JComboBox</code> field is editable.
     * An editable <code>JComboBox</code> allows the user to type into the
     * field or selected an item from the list to initialize the field,
     * after which it can be edited. (The editing affects only the field,
     * the list item remains intact.) A non editable <code>JComboBox</code>
     * displays the selected item in the field,
     * but the selection cannot be modified.
     *
     * @param b a boolean value, where true indicates that the
     *			field is editable
     */
	public void  setEditable (boolean b ){
		if(editable != b){
			editable = b;
			getEditor().setEditable(b);
			//editable changed, internal focus object will change too, so change the focus
			if(isFocusable() && isFocusOwner() && stage != null){
				if(stage.focus != getInternalFocusObject()){
					stage.focus = getInternalFocusObject();
				}
			}
		}
	}

	/**
     * Returns true if the <code>JComboBox</code> is editable.
     * By default, a combo box is not editable.
     * @return true if the <code>JComboBox</code> is editable, else false
     */
	public boolean  isEditable (){
		return editable;
	}

	/**
     * Enables the combo box so that items can be selected. When the
     * combo box is disabled, items cannot be selected and values
     * cannot be typed into its field (if it is editable).
     *
     * @param b a boolean value, where true enables the component and
     *          false disables it
     */
	 public void  setEnabled (boolean b ){
		super.setEnabled(b);
		if(!b && isPopupVisible()){
			setPopupVisible(false);
		}
		getEditor().setEditable(b && isEditable());
	}

	/**
	 * set a array to be the list data, but array is not a List Mode.
	 * So when the array content was changed, you should call updateListView
	 * to update the JList(the list for combo box).But this is not a good way, its slow.
	 * So suggest you to create a ListMode eg. VectorListMode,
	 * When you modify ListMode, it will automatic update JList.
	 * @see #setMode()
	 * @see org.aswing.ListModel
	 */
	public void  setListData (Array ld ){
		getPopupList().setListData(ld);
	}

	/**
	 * Set the list mode to provide the data to JList.
	 * @see org.aswing.ListModel
	 */
	public void  setModel (ListModel m ){
		getPopupList().setModel(m);
	}

	/**
	 * @return the model of this List
	 */
	public ListModel  getModel (){
		return getPopupList().getModel();
	}
	/**
     * Causes the combo box to display its popup window.
     * @see #setPopupVisible()
     */
	public void  showPopup (){
		setPopupVisible(true);
	}
	/**
     * Causes the combo box to close its popup window.
     * @see #setPopupVisible()
     */
	public void  hidePopup (){
		setPopupVisible(false);
	}
	/**
     * Sets the visibility of the popup, open or close.
     */
	public void  setPopupVisible (boolean v ){
		getComboBoxUI().setPopupVisible(this, v);
	}

	/**
     * Determines the visibility of the popup.
     *
     * @return true if the popup is visible, otherwise returns false
     */
	public boolean  isPopupVisible (){
		return getComboBoxUI().isPopupVisible(this);
	}

	 /**
     * Sets the selected item in the combo box display area to the object in
     * the argument.
     * If <code>item</code> is in the list, the display area shows
     * <code>item</code> selected.
     * <p>
     * If <code>item</code> is <i>not</i> in the list and the combo box is
     * uneditable, it will not change the current selection. For editable
     * combo boxes, the selection will change to <code>item</code>.
     * </p>
     * <code>AWEvent.ACT</code> (<code>addActionListener()</code>)events added to the combo box will be notified
     * when this method is called.
     * <br>
     * <code>InteractiveEvent.SELECTION_CHANGED</code> (<code>addSelectionListener()</code>)events added to the combo box will be notified
     * when this method is called only when the item is different from current selected item,
     * it means that only when the selected item changed.
     *
     * @param item  the list item to select; use <code>null</code> to
     *              clear the selection.
     * @param programmatic indicate if this is a programmatic change.
     */
	public void  setSelectedItem (*item ,boolean programmatic =true ){
		boolean fireChanged =false ;
		if(item !== getSelectedItem()){
			fireChanged = true;
		}
		getEditor().setValue(item);
		int index =indexInModel(item );
		if(index >= 0){
			if(getPopupList().getSelectedIndex() != index){
				getPopupList().setSelectedIndex(index, programmatic);
				fireChanged = false;
			}
			getPopupList().ensureIndexIsVisible(index);
		}
		if(isFocusOwner()){
			getEditor().selectAll();
		}
		dispatchEvent(new AWEvent(AWEvent.ACT));
		if(fireChanged){
			dispatchEvent(new InteractiveEvent(InteractiveEvent.SELECTION_CHANGED, programmatic));
		}
	}

	/**
     * Returns the current selected item.
     * <p>
     * If the combo box is editable, then this value may not have been in
     * the list model.
     * @return the current selected item
     * @see #setSelectedItem()
     */
	public Object getSelectedItem () {
		return getEditor().getValue();
	}

	/**
     * Selects the item at index <code>anIndex</code>.
     * <p>
     * <code>ON_ACT</code> (<code>addActionListener()</code>)events added to the combo box will be notified
     * when this method is called.
     *
     * @param anIndex an integer specifying the list item to select,
     *			where 0 specifies the first item in the list and -1 or greater than max index
     *			 indicates empty selection.
     * @param programmatic indicate if this is a programmatic change.
     */
	public void  setSelectedIndex (int anIndex ,boolean programmatic =true ){
		int size =getModel ().getSize ();
		if(anIndex < 0 || anIndex >= size){
			if(getSelectedItem() != null){
				getEditor().setValue(null);
				getPopupList().clearSelection();
			}
		}else{
			getEditor().setValue(getModel().getElementAt(anIndex));
			getPopupList().setSelectedIndex(anIndex, programmatic);
			getPopupList().ensureIndexIsVisible(anIndex);
		}
		dispatchEvent(new AWEvent(AWEvent.ACT));
	}

	/**
     * Returns the first item in the list that matches the given item.
     * The result is not always defined if the <code>JComboBox</code>
     * allows selected items that are not in the list.
     * Returns -1 if there is no selected item or if the user specified
     * an item which is not in the list.
     * @return an integer specifying the currently selected list item,
     *			where 0 specifies
     *                	the first item in the list;
     *			or -1 if no item is selected or if
     *                	the currently selected item is not in the list
     */
	public int  getSelectedIndex (){
		return indexInModel(getEditor().getValue());
	}

	/**
     * Returns the number of items in the list.
     * @return an integer equal to the number of items in the list
     */
	public int  getItemCount (){
		return getModel().getSize();
	}

	/**
     * Returns the list item at the specified index.  If <code>index</code>
     * is out of range (less than zero or greater than or equal to size)
     * it will return <code>undefined</code>.
     *
     * @param index  an integer indicating the list position, where the first
     *               item starts at zero
     * @return the <code>Object</code> at that list position; or
     *			<code>undefined</code> if out of range
     */
	public Object getItemAt (int index ) {
		return getModel().getElementAt(index);
	}

	//----------------------------------------------------------
	protected void  __editorActed (Event e ){
		if(!isPopupVisible()){
			setSelectedItem(getEditor().getValue());
		}
	}

	protected void  __listSelectionChanged (SelectionEvent e ){
		dispatchEvent(new InteractiveEvent(InteractiveEvent.SELECTION_CHANGED, e.isProgrammatic()));
	}

	protected int  indexInModel (Object value){
		ListModel model =getModel ();
		int n =model.getSize ();
		for(int i =0;i <n ;i ++){
			if(model.getElementAt(i) === value){
				return i;
			}
		}
		return -1;
	}

}


