package com.oner365.common.sequence.range;

import com.oner365.common.sequence.exception.SeqException;

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
     * @throws SeqException 异常
     */
    SeqRange nextRange(String paramString) throws SeqException;

    /**
     * init
     */
    void init();
}
