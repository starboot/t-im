package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;

/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractServerCmdHandler extends AbstractCmdHandler {


	/**
	 * 主键：判断各类socket请求的指令
	 * @return 主键对象
	 */
	public abstract ReqServerCommandType command();

}
