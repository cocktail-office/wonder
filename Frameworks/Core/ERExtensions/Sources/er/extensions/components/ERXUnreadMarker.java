/*
 * Copyright (C) NetStruxr, Inc. All rights reserved.
 *
 * This software is published under the terms of the NetStruxr
 * Public Software License version 0.5, a copy of which has been
 * included with this distribution in the LICENSE.NPL file.  */
package er.extensions.components;

import org.apache.log4j.Logger;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

/**
 * Nice for denoting that something has not been viewed. Extended in look frameworks.<br />
 * 
 * @binding item
 * @binding list
 * @binding condition A boolean
 * @binding negate A boolean
 */

public class ERXUnreadMarker extends WOComponent {

    public ERXUnreadMarker(WOContext aContext) {
        super(aContext);
    }

    /////////////////////////////////  log4j category  /////////////////////////////////
    public static Logger log = Logger.getLogger(ERXUnreadMarker.class);
    
    public boolean synchronizesVariablesWithBindings() { return false; }
    public boolean isStateless() { return true; }

    public void reset() { super.reset(); initialized=false; }
    
    private boolean initialized=false;
    private boolean result=false;
    public boolean showUnread() {
        if (!initialized) {
            result=false;
            if (hasBinding("condition")) {
                Number n=(Number)valueForBinding("condition");
                result=n!=null && n.intValue()!=0;
            } else {
                NSArray list=(NSArray)valueForBinding("list");
                Object item=valueForBinding("item");
                result=list!=null && item!=null && list.containsObject(item);
            }
            if (hasBinding("negate")) {
                Integer negate=(Integer)valueForBinding("negate");
                if (negate!=null && negate.intValue()!=0) result=!result;
            }
            initialized=true;
        }
        return result;
    }
    
    public boolean doNotShowUnread() { return !showUnread(); }
}
