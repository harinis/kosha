package com.thoughtworks.kosha;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.skype.SkypeException;
import com.thoughtworks.kosha.data.Session;
import com.thoughtworks.kosha.dropbox.Dropbox;
import com.thoughtworks.kosha.skype.SkypeDelegate;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then
 * you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of
 * Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO
 * JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class View extends ViewPart {
    public static final String ID = "Kosha.view";
    private Composite composite1;
    private Button button1;
    private Group group1;
    private Label statusLbl;
    private Text tags;
    private SkypeDelegate skypeDelegate;

    @Override
    public void createPartControl(Composite parent) {
	{
	    GridLayout parentLayout = new GridLayout();
	    parentLayout.makeColumnsEqualWidth = true;
	    parent.setLayout(parentLayout);
	    parent.setSize(557, 321);
	    {
		composite1 = new Composite(parent, SWT.NONE);
		RowLayout composite1Layout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
		GridData composite1LData = new GridData();
		composite1LData.grabExcessHorizontalSpace = true;
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1.setLayoutData(composite1LData);
		composite1.setLayout(composite1Layout);
		{
			statusLbl = new Label(composite1, SWT.NONE);
			RowData statusLblLData = new RowData();
			statusLbl.setLayoutData(statusLblLData);
		}
	    }
	    {
		group1 = new Group(parent, SWT.NONE);
		GridLayout group1Layout = new GridLayout();
		group1Layout.makeColumnsEqualWidth = true;
		group1.setLayout(group1Layout);
		GridData group1LData = new GridData();
		group1LData.verticalAlignment = GridData.FILL;
		group1LData.horizontalAlignment = GridData.FILL;
		group1LData.grabExcessHorizontalSpace = true;
		group1LData.grabExcessVerticalSpace = true;
		group1.setLayoutData(group1LData);
		group1.setText("Notes");
		{
		    tags = new Text(group1, SWT.MULTI | SWT.WRAP | SWT.BORDER);
		    GridData text1LData = new GridData();
		    text1LData.grabExcessHorizontalSpace = true;
		    text1LData.horizontalAlignment = GridData.FILL;
		    text1LData.verticalAlignment = GridData.FILL;
		    text1LData.grabExcessVerticalSpace = true;
		    tags.setLayoutData(text1LData);
		}
	    }
	    {
	    	button1 = new Button(parent, SWT.PUSH | SWT.CENTER);
	    	GridData button1LData = new GridData();
	    	button1LData.horizontalAlignment = GridData.FILL;
	    	button1LData.grabExcessHorizontalSpace = true;
	    	button1.setLayoutData(button1LData);
	    	button1.setText("Archive");
	    	button1.addSelectionListener(new SelectionAdapter() {
	    		public void widgetSelected(SelectionEvent e) {
	    			Session currSession = skypeDelegate.getConsolidatedSession();
	    			currSession.setTagsFile(tags.getText().trim());
	    			new Dropbox().recordHistory(currSession);
	    			tags.setText("");
	    		}
	    	});
	    }
	}

	initSkype();
    }

    private void initSkype() {
	try {
	    skypeDelegate = new SkypeDelegate();
	} catch (SkypeException e1) {
	    e1.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void setFocus() {
	// TODO Auto-generated method stub

    }
    
}