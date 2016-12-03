package com.bao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private static HanyuPinyinOutputFormat defaultOutputFormat = new HanyuPinyinOutputFormat();
	static {
		defaultOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}
	private static final char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '~', '!', '@', '#', '#', '$', '%', '^', '&', '*' };

	public static String getRandomLoginName(String name) {
		if (!StringUtils.isNullOrEmpty(name)) {
			return getSimplePinYin(name).get(0);
		}
		return null;
	}

	public static String getRandomLoginPassword() {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			builder.append(chars[random.nextInt(chars.length)]);
		}
		return builder.toString();
	}

	public static List<String> getSimplePinYin(String word) {
		StringBuilder pinyinName = new StringBuilder();
		char[] wordChars = word.toCharArray();
		for (int i = 0; i < wordChars.length; i++) {
			if (wordChars[i] > 128) {
				try {
					// 获得汉字的全拼
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(wordChars[i], defaultOutputFormat);
					if (strs != null) {
						// 因为多音字的关系，所以全拼有可能有多个
						for (int j = 0; j < strs.length; j++) {
							pinyinName.append(strs[j].charAt(0));
							if (j != strs.length - 1) {
								pinyinName.append(",");
							}
						}
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					logger.error("error to getSimplerPinYin", e);
				}
			} else {
				pinyinName.append(wordChars[i]);
			}
			pinyinName.append(" ");// 以空格来分割
		}
		// 但是多音字也会有首字母相同的，所以要去除，用Map来去除
		return parseTheChineseByObject(distinctPinyin(pinyinName.toString()));
	}

	private static List<String> parseTheChineseByObject(List<Map<String, Integer>> list) {

		Map<String, Integer> first = null;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Integer> tempMap = new HashMap<>();
			if (first != null) {
				for (String str : first.keySet()) {
					for (String string : list.get(i).keySet()) {
						String tempStr = str + string;
						tempMap.put(tempStr, 1);
					}
				}
				if (tempMap != null && tempMap.size() > 0) {
					first.clear();
				}
			} else {
				for (String str : list.get(i).keySet()) {
					tempMap.put(str, 1);
				}
			}
			if (tempMap != null && tempMap.size() > 0) {
				first = tempMap;
			}
		}
		return new ArrayList<>(first.keySet());

	}

	private static List<Map<String, Integer>> distinctPinyin(String pinyin) {
		String[] strs = pinyin.split(" ");
		List<Map<String, Integer>> list = new ArrayList<>();
		Map<String, Integer> tempMap;
		;
		for (int i = 0; i < strs.length; i++) {
			String[] tempStr = strs[i].split(",");
			tempMap = new HashMap<>();
			for (int j = 0; j < tempStr.length; j++) {
				Integer count = tempMap.get(tempStr[j]);
				if (count == null) {
					tempMap.put(tempStr[j], 1);
				} else {
					tempMap.remove(tempStr[j]);
					count++;
					tempMap.put(tempStr[j], count);
				}
			}
			list.add(tempMap);
		}
		return list;
	}

}
