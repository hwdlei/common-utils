package zx.soft.utils.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类，主要用于：
 * 1、从JSON字符串中获取指定字段的信息；
 * 2、从JSON字符串中，获取父节点中指定的子节点信息；
 *
 * @author wgybzb
 * @since 2013-04-09
 */
public class JsonNodeUtils {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 日志输出
	 */
	private static Logger logger = LoggerFactory.getLogger(JsonNodeUtils.class);

	/**
	 * 从Json字符串中，
	 * 获取Jackson格式的JsonNode
	 * JsonNode:把json映射为树形结构，此结构由各个节点组成
	 *
	 * @param jsonStr	Json字符串
	 * @return			Jackson格式的JsonNode
	 */
	public static JsonNode getJsonNode(String jsonStr) {
		JsonNode jsonNode = null;
		if (StringUtils.isEmpty(jsonStr)) {
			return jsonNode;
		}

		try {
			jsonNode = OBJECT_MAPPER.readTree(jsonStr);
		} catch (JsonParseException e) {
			logger.error("When JsonUtils Process getJsonNode JsonParseException", e);
		} catch (JsonMappingException e) {
			logger.error("When JsonUtils Process getJsonNode JsonMappingException", e);
		} catch (IOException e) {
			logger.error("When JsonUtils Process getJsonNode IOException", e);
		} catch (Exception e) {
			logger.error("When JsonUtils Process getJsonNode Exception", e);
		}
		return jsonNode;
	}

	/**
	 * 从Json字符串中，
	 * 获取指定字段的Jackson格式的JsonNode
	 * JsonNode:把json映射为树形结构中的一个节点
	 *
	 * @param jsonStr	Json字符串
	 * @return			Jackson格式的JsonNode
	 */
	public static JsonNode getJsonNode(String jsonStr, String fieldName) {
		JsonNode node = null;
		JsonNode jsonNode = getJsonNode(jsonStr);
		if (null != jsonNode) {
			node = jsonNode.get(fieldName);
		}
		return node;
	}

	/**
	 * 从指定的JsonNode中，获取指定字段的子JsonNode
	 *
	 * @param parentNode	父JsonNode
	 * @return			    父JsonNode的指定字段的子JsonNode
	 */
	public static JsonNode getJsonNode(JsonNode parentNode, String fieldName) {
		JsonNode node = null;
		if (StringUtils.isNotEmpty(fieldName) && null != parentNode) {
			node = parentNode.get(fieldName);
		}
		return node;
	}

}