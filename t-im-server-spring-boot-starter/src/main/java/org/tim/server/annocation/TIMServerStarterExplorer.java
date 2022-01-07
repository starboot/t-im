package org.tim.server.annocation;

import cn.hutool.core.lang.Singleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tim.server.TIMServerStarter;
import org.tim.server.start.TIMServerStarterSpringBoot;

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
