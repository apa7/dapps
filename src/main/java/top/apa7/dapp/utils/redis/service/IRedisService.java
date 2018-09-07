package top.apa7.dapp.utils.redis.service;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by liyi on 2018/8/25.
 * Maintainer:
 */
public interface IRedisService {
    Set<Serializable> keys(String pattern) throws Exception;

    boolean exists(Serializable key) throws Exception;

    boolean flushDb() throws Exception;

    long dbSize() throws Exception;

    boolean ping() throws Exception;
}
