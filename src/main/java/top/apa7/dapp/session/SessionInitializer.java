package top.apa7.dapp.session;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * Created by liyi on 2018/8/25.
 */
//初始化Session配置
public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {
    public SessionInitializer() {
        super(SessionConfig.class);
    }
}