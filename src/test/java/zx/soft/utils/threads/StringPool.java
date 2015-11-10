package zx.soft.utils.threads;

public class StringPool extends ObjectFactory<String> {
	private int i = 0;

	public StringPool() {
		super();
	}

	public StringPool(int num, int tryNum) {
		super(num, tryNum);
	}

	public StringPool(int num, int tryNum, long expira) {
		super(num, tryNum, expira);
	}

	@Override
	protected String create() {
		return "" + (i++);
	}

	@Override
	public boolean validate(String o) {
		return true;
	}

	@Override
	public void expire(String o) {
	}

}
