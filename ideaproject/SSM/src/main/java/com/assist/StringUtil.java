package com.assist;

public class StringUtil {
    /**
     * 检测某个字符变量是否为空；<br>
     * 为空的情况，包括：null，空串或只包含可以被 trim() 的字符；
     */
    public static final boolean isEmpty(String value) {
        return (value == null || value.trim().length() == 0);
    }
}

