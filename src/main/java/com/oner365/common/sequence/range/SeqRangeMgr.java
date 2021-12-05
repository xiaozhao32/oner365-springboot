package com.oner365.common.sequence.range;

/**
 * sequence range
 *
 * @author zhaoyong
 */
public interface SeqRangeMgr {

	/**
	 * next range
	 * 
	 * @param paramString 参数
	 * @return SeqRange
	 */
	SeqRange nextRange(String paramString);

	/**
	 * init
	 */
	void init();
}
