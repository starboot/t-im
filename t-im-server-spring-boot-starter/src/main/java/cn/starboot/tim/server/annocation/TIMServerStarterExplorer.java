package cn.starboot.tim.server.annocation;

import cn.hutool.core.lang.Singleton;
import cn.starboot.tim.server.TIMServerStarter;
import cn.starboot.tim.server.start.TIMServerStarterSpringBoot;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by DELL(mxd) on 2022/1/1 21:34
 */
@ConditionalOnMissingBean(TIMServerStarter.class)
@Configuration
public class TIMServerStarterExplorer {

    @Bean
    public TIMServerStarterSpringBoot serverEndpointExporter() {
        return Singleton.get(TIMServerStarterSpringBoot.class);
    }
}
