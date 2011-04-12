package com.thoughtworks.kosha;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Test extends ViewPart {
	private Composite composite1;
	private Label label1;

    public Test() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public void createPartControl(Composite parent) {
    	{
	    	parent.setSize(541, 302);
	    	{
	    		composite1 = new Composite(parent, SWT.NONE);
	    		RowLayout composite1Layout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
	    		composite1.setLayout(composite1Layout);
	    		{
	    			label1 = new Label(composite1, SWT.NONE);
	    			RowData label1LData = new RowData();
	    			label1.setLayoutData(label1LData);
	    			label1.setText("label1");
	    		}
	    	}
    	}
	// TODO Auto-generated method stub

    }

    @Override
    public void setFocus() {
	// TODO Auto-generated method stub

    }

}
