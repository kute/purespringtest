package com.kute.util;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * created by kute on 2018/04/07 12:56
 */
public class StringUtil {

    public static List<String> toList(String sequence) {
        return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(sequence);
    }

}
