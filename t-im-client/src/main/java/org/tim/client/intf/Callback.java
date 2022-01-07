package org.tim.client.intf;

import org.tim.client.TIMClient;

/**
 * Created by DELL(mxd) on 2022/1/6 15:41
 */
public interface Callback {
    void func(boolean isSuccess, TIMClient timClient);
}
