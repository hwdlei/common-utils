package zx.soft.utils.threads;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用对象工厂，可限制产生的对象，并提供对象池
 * @author donglei
 * @param <T>
 */
public abstract class ObjectFactory<T> {

	private static Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
	// 超时时间
	private long expirationTime;
	// 限制产生的对象数
	private final int NUM;
	// 获取对象等待时间
	private final int TRY;
	// 可用的对象
	private HashMap<T, Long> available = new HashMap<>();
	// 正在使用的对象
	private HashMap<T, Long> inUse = new HashMap<>();

	protected abstract T create();

	public abstract boolean validate(T o);

	public abstract void expire(T o);

	/**
	 * 最多创建num个对象，请求tryNum次
	 * @param num
	 * @param tryNum
	 */
	public ObjectFactory(int num, int tryNum, long expieationTime) {
		this.NUM = num;
		this.TRY = tryNum;
		this.expirationTime = expieationTime;
	}

	public ObjectFactory(int num, int tryNum) {
		this.NUM = num;
		this.TRY = tryNum;
		this.expirationTime = Long.MAX_VALUE;
	}

	public ObjectFactory() {
		this.expirationTime = Long.MAX_VALUE;
		this.NUM = Integer.MAX_VALUE;
		this.TRY = 1;
	}

	public synchronized T checkOut() throws InterruptedException {
		long now = System.currentTimeMillis();
		int numTry = 0;
		T get = null;
		while (get == null && numTry <= TRY) {
			if (available.size() > 0) {
				Iterator<Map.Entry<T, Long>> ite = available.entrySet().iterator();
				// remove invalid Object instance
				while (ite.hasNext()) {
					Map.Entry<T, Long> t = ite.next();
					if ((now - t.getValue()) > expirationTime) {
						ite.remove();
						expire(t.getKey());
						logger.info("expire an object because of expirationTime");
					} else {
						if (validate(t.getKey())) {
							ite.remove();
							inUse.put(t.getKey(), now);
							get = t.getKey();
							logger.info("Get an instance from the pool");
							break;
						} else {
							// object failed validation
							ite.remove();
							expire(t.getKey());
							logger.info("object failed validation");
						}
					}
				}

				continue;
			}
			if (inUse.size() < NUM) {
				T t = create();
				get = t;
				inUse.put(t, now);
				logger.info("Create a new instance !");
			} else {
				logger.info("All instances have bean used, wait 1 second!");
				wait(1000);
				numTry++;
			}
		}
		if (get == null) {
			logger.info("No instance available after waiting {} seconds!", TRY);
			return null;
		}
		return get;
	}

	public synchronized void checkIn(T instance) {
		inUse.remove(instance);
		available.put(instance, System.currentTimeMillis());
		notifyAll();
	}

	@Override
	public String toString() {
		return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
	}

	public synchronized void clear() {
		available.clear();
		inUse.clear();
	}
}
