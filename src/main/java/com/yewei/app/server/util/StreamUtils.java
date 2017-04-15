package com.yewei.app.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Created by lenovo on 2017/4/14.
 */
public class StreamUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                LOGGER.warn("关闭流出错" + stream, e);
            }
        }
    }
}
