package zx.soft.utils.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	/**
	 * 判断是否全部为数字
	 */
	public static boolean isAllNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 判断是否全部为数字或字母
	 */
	public static boolean isAllNumAndLetter(String str) {
		return str.matches("^[-+]?(([0-9A-Za-z]+)([.]([0-9A-Za-z]+))?|([.]([0-9A-Za-z]+))?)$");
	}

	/**
	 * 判断是否全部为中文
	 */
	public static boolean isAllChinese(String str) {
		return str.matches("[\u4E00-\u9FA5]+");
	}

	/**
	 * 从字符串中找出所有符合pattern模式的子串，group控制整个子串还是子组
	 * @param str
	 * @param pattern
	 *   贪婪: 最长匹配 .* : 输出: <biao><>c<b>
	 *   不知是否非贪婪 .*? : 输出: <biao>, <>, <b>
	 *   使用组, 输出<>里的内容, 输出: 'biao', ' ', 'b' group(1)
	 *   0组代表整个表达式, 子组从1开始 group(0)
	 * @param group
	 * @return
	 */
	public static List<String> findMatchStrs(String str, String pattern, boolean group) {
		List<String> strs = new ArrayList<>();
		if (StringUtils.isEmpty(str)) {
			return strs;
		}
		Pattern pat = Pattern.compile(pattern);
		Matcher matcher = pat.matcher(str);
		while (matcher.find()) {
			if (group) {
				strs.add(matcher.group(0));
			} else {
				strs.add(matcher.group(1));
			}
		}
		return strs;
	}

	/**
	 *
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceMatchStrs(String str, String pattern, String replace) {
		if (str != null) {
			return str.replaceAll(pattern, replace);
		}
		return null;
	}

}