package zx.soft.utils.log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception 日志输出
 *  @author hwdlei
 *  2016年12月13日
 */
public final class ExceptionHelper {


	public static String detailedMessage(Throwable t) {
		return detailedMessage(t, false, 0);
	}

	public static String detailedMessage(Throwable t, boolean newLines, int initialCounter) {
		if (t == null) {
			return "Unknown";
		}
		int counter = initialCounter + 1;
		if (t.getCause() != null) {
			StringBuilder sb = new StringBuilder();
			while (t != null) {
				sb.append(t.getClass().getSimpleName());
				if (t.getMessage() != null) {
					sb.append("[");
					sb.append(t.getMessage());
					sb.append("]");
				}
				if (!newLines) {
					sb.append("; ");
				}
				t = t.getCause();
				if (t != null) {
					if (newLines) {
						sb.append("\n");
						for (int i = 0; i < counter; i++) {
							sb.append("\t");
						}
					} else {
						sb.append("nested: ");
					}
				}
				counter++;
			}
			return sb.toString();
		} else {
			return t.getClass().getSimpleName() + "[" + t.getMessage() + "]";
		}
	}

	public static String stackTrace(Throwable e) {
		StringWriter stackTraceStringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stackTraceStringWriter);
		e.printStackTrace(printWriter);
		return stackTraceStringWriter.toString();
	}

}
