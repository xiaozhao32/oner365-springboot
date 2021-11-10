package com.oner365.common.sequence.sequence;

import com.oner365.common.sequence.exception.SeqException;

/**
 * 分布式生成UUID
 *
 * @author zhaoyong
 *
 */
public interface Sequence {

    /**
     * 生成下一个值
     * 
     * @return long
     * @throws SeqException 异常
     */
    long nextValue() throws SeqException;

    /**
     * 生成下一个值字符串
     * 
     * @return String
     * @throws SeqException 异常
     */
    String nextNo() throws SeqException;

    /**
     * 生成下一个id
     * 
     * @return int
     */
    int nextId();
}
