package org.tim.server.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.GroupListener;

/**
 * Created by DELL(mxd) on 2021/12/25 10:40
 */
public class TimGroupListener implements GroupListener {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(TimGroupListener.class);

    public static final TimGroupListener me = new TimGroupListener();

    private TimGroupListener() {
    }

    @Override
    public void onAfterBind(ChannelContext channelContext, String s) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("onAfterBind: " + s);
        }
    }

    @Override
    public void onAfterUnbind(ChannelContext channelContext, String s) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("onAfterUnbind: " + s);
        }
    }
}
