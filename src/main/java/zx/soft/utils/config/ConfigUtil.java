package zx.soft.utils.config;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.ExceptionHelper;

/**
 * 配置文件读取类
 *
 * @author wanggang
 *
 */
public class ConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	public static Properties getProps(String confFileName) {
		Properties result = new Properties();
		logger.info("Load resource: " + confFileName);
		try (InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(confFileName);) {
			result.load(in);
			return result;
		} catch (Exception e) {
			logger.error("Exception:{}", ExceptionHelper.stackTrace(e));
			throw new RuntimeException(e);
		}
	}

}
