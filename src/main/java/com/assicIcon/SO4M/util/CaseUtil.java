package com.assicIcon.SO4M.util;

import com.google.common.base.CaseFormat;

public class CaseUtil {

	public static String caseToLowerUnderscore(String str) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
	}
}
