package la.serendipity.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.function.Supplier;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Component
@RestController
public class Test {
	private final @Autowired ServletRequest request = null;
	private final @Autowired ServletResponse response = null;

	static public class T {
		public int i = 0;
	}

	static ThreadLocal<T> inte = ThreadLocal.withInitial(() -> {
		return new T();
	});

	@RequestMapping(value = "/")
	public void get() throws IOException {
		ThreadMXBean tmb = ManagementFactory.getThreadMXBean();

		// JVM が CPU 時間の取得をサポートしているかどうかは以下のメソッドで確認可能
		// System.out.println(tmb.isCurrentThreadCpuTimeSupported());

		System.out.println(++inte.get().i);

		PrintWriter out = response.getWriter();
		out.println("<html><body><table style='text-align:left;'>");

		long[] liveThreadID = tmb.getAllThreadIds();
		for (long l : liveThreadID) {
			ThreadInfo ti = tmb.getThreadInfo(l);
			// System.out.println(ti.getThreadName() + " [" + l + "] " +
			// tmb.getThreadCpuTime(l));
			out.println(String.format("<tr><th>%s<td>%d<td>%f ms",
					ti.getThreadName(), l, 0.000001 * tmb.getThreadCpuTime(l)));
		}

		out.println("</body></html>");
	}

	static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

	private static void print() {
		System.out.println(String.format(
				"thread : %s, cpu: %f ms, user: %f ms, sys: %f ms", Thread
						.currentThread().getName(), threadMXBean
						.getCurrentThreadCpuTime() / 1000000.0, threadMXBean
						.getCurrentThreadUserTime() / 1000000.0,
				threadMXBean.getCurrentThreadCpuTime() / 1000000.0
						- threadMXBean.getCurrentThreadUserTime() / 1000000.0

		));
	}
}
