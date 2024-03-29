package cn.starboot.tim.server.start;

import cn.starboot.tim.server.TIMServerStarter;
import cn.starboot.tim.server.intf.TIMServerProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by DELL(mxd) on 2022/1/6 11:11
 */
public class TIMServerStarterSpringBoot extends TIMServerStarter implements SmartInitializingSingleton, BeanFactoryAware, ResourceLoaderAware {
	protected TIMServerStarterSpringBoot(TIMServerProcessor serverProcessor) {
		super(serverProcessor);
	}

	@Override
    public void afterSingletonsInstantiated() {
        start();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }
}
