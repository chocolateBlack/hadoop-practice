package cn.edu.buaa.practice.util;

import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author BurningIce
 *
 */
public class StringUtils {
    private static final byte PATH_MAX_LENGTH = 32;
    private static final byte HEX_MIN_LENGTH = 6;
    private static final char[] ignoreChars = {';', '_'};
	/**
	 * 规范化URL
	 * 1. 去掉?后的参数部分，如 /users?userId=12345&topic=5 -> /users
	 * 2. 将路径中的非规范token聚合，如/users/12345/topic/5  ->  /users/ * / topic/ *
	 * @param url
	 * @return
	 */
	public static String normalizeUri(String url) {
		if(url == null || url.length() == 0)
			return url;
		
		// ignore queryStr
		String normalizedUrl;
		int k;
		if((k = url.indexOf('?')) != -1) {
			normalizedUrl = substring(url, 0, k);
			if((k = normalizedUrl.indexOf(';')) != -1) {
				normalizedUrl = substring(normalizedUrl, 0, k);
			}
		} else if((k = url.indexOf('&')) != -1) {
			//some URLs' params string do not proceed with '?'
			int l = url.lastIndexOf('/');
			if(k > l && l > 0 && url.indexOf('=', k) != -1) {
				boolean withoutPath = false;
				for(int ci = l + 1; ci < k; ++ci) {
					if(url.charAt(ci) == '=') {
						withoutPath = true;
						break;
					}
				}
				normalizedUrl = (withoutPath ? substring(url, 0, l + 1) : substring(url, 0, k));
			} else {							
				normalizedUrl = url;
			}
			if((k = normalizedUrl.indexOf(';')) != -1) {
				normalizedUrl = substring(normalizedUrl, 0, k);
			}
		} else if((k = url.indexOf(';')) != -1) {
			normalizedUrl = substring(url, 0, k);
		} else if((k = url.indexOf('#')) != -1) {
			// e.g.: http://www.networkbench.com/index.html#top
			normalizedUrl = substring(url, 0, k);
		} else {
			///TODO should or not permit some url like "http://a.b.com/c/d=f"?
			normalizedUrl = url;
		}
		
		return normalizeUrlPathTokens(normalizedUrl);
	}
	
	private static String normalizeUrlPathTokens(String uri) {
		if(uri == null || uri.length() == 0)
			return uri;
		
		String[] paths = tokenizeToStringArray(uri, "/");
		if (uri.endsWith("/")) {
			String[] pathsTemp = new String[paths.length + 1];
			System.arraycopy(paths, 0, pathsTemp, 0, paths.length);
			pathsTemp[pathsTemp.length - 1] = "";
			paths = pathsTemp;
		}
		StringBuilder resutltBuffer = new StringBuilder("/");
		int length = paths.length;
		boolean isApply = false;

		for (int i = 0; i < length; ++i) {

			if (i < length - 1) {

				if (!isAggregationPath(paths[i])) {
					resutltBuffer.append(paths[i]).append("/");
				} else {
					resutltBuffer.append("*/");
					isApply = true;
				}
			} else {
				if (paths[i].length() == 0) {
					continue;
				}
				int suffixIndex = paths[i].indexOf('.');
				String suffix = "";
				String path = paths[i];
				if (suffixIndex > 0) {
					suffix = paths[i].substring(suffixIndex);
					path = paths[i].substring(0, suffixIndex);
				}

				if (!isAggregationPath(path)) {
					resutltBuffer.append(path).append(suffix);
				} else {
					resutltBuffer.append("*").append(suffix);
					isApply = true;
				}
			}
		}
		
		return isApply ? resutltBuffer.toString() : uri;
	}
	
	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter)
	 * @param trimTokens trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens omit empty tokens from the result array
	 * (only applies to tokens that are empty after trimming; StringTokenizer
	 * will not consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(
			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}
	
	/**
	 * Copy the given Collection into a String array.
	 * The Collection must contain String elements only.
	 * @param collection the Collection to copy
	 * @return the String array (<code>null</code> if the Collection
	 * was <code>null</code> as well)
	 */
	public static String[] toStringArray(Collection collection) {
		if (collection == null) {
			return null;
		}
		return (String[]) collection.toArray(new String[collection.size()]);
	}
	
	
	private final static char[] HOST_URI_DELIMITERS = new char[] {'/', '?', '&', '#', ';'};
    private final static BitSet dontNeedEncoding;
    private final static BitSet dontNeedEncodingAscii;
    private final static int caseDiff = ('a' - 'A');
    
    static {
    	/* The list of characters that are not encoded has been
    	 * determined as follows:
    	 *
    	 * RFC 2396 states:
    	 * -----
    	 * Data characters that are allowed in a URI but do not have a
    	 * reserved purpose are called unreserved.  These include upper
    	 * and lower case letters, decimal digits, and a limited set of
    	 * punctuation marks and symbols. 
    	 *
    	 * unreserved  = alphanum | mark
    	 *
    	 * mark        = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
    	 *
    	 * Unreserved characters can be escaped without changing the
    	 * semantics of the URI, but this should not be done unless the
    	 * URI is being used in a context that does not allow the
    	 * unescaped character to appear.
    	 * -----
    	 *
    	 * It appears that both Netscape and Internet Explorer escape
    	 * all special characters from this list with the exception
    	 * of "-", "_", ".", "*". While it is not clear why they are
    	 * escaping the other characters, perhaps it is safest to
    	 * assume that there might be contexts in which the others
    	 * are unsafe if not escaped. Therefore, we will use the same
    	 * list. It is also noteworthy that this is consistent with
    	 * O'Reilly's "HTML: The Definitive Guide" (page 164).
    	 *
    	 * As a last note, Intenet Explorer does not encode the "@"
    	 * character which is clearly not unreserved according to the
    	 * RFC. We are being consistent with the RFC in this matter,
    	 * as is Netscape.
    	 *
    	 */

		dontNeedEncoding = new BitSet(256);
		int i;
		for (i = 'a'; i <= 'z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = 'A'; i <= 'Z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = '0'; i <= '9'; i++) {
			dontNeedEncoding.set(i);
		}
		dontNeedEncoding.set(' '); /*
									 * encoding a space to a + is done in the
									 * encode() method
									 */
		dontNeedEncoding.set('-');
		dontNeedEncoding.set('_');
		dontNeedEncoding.set('.');
		dontNeedEncoding.set('*');
		
		dontNeedEncodingAscii = new BitSet(256);
		for(int j = 32; j<= 126; ++j) {
			dontNeedEncodingAscii.set(j);
		}
    }
	
	/**
	 * 将url分隔成host, uri数组
	 * @param url
	 * @return
	 */
	public static String[] splitHostAndUri(String url) {
		if(url == null || url.length() == 0)
			return null;
		
		int hostStartIndex = -1;
		if(url.startsWith("http://")) {
			hostStartIndex = 7;
		} else if(url.startsWith("https://")) {
			hostStartIndex = 8;
		}
		
		if(hostStartIndex != -1) {
			int delimiter = -1;
			for(int ci = hostStartIndex; ci < url.length() && delimiter == -1; ++ ci) {
				char c = url.charAt(ci);
				for(int i = 0; i < HOST_URI_DELIMITERS.length; ++i) {
					if(HOST_URI_DELIMITERS[i] == c) {
						delimiter = ci;
						break;
					}
				}
			}
			
			if(delimiter == -1) {
				// like www.networkbench.com
				return new String[] {url.substring(hostStartIndex), null};
			} else {
				return new String[] {url.substring(hostStartIndex, delimiter), url.substring(delimiter)};
			}
		} else {
			// non http or https
			return new String[] {null, url};
		}
	}
	
	public static String getNextToken(String str, char token, int beginIndex, int maxLength) {
		int endIndex = Math.min(str.length(), beginIndex + maxLength);
		for(int i = beginIndex; i < endIndex; ++i) {
			char c = str.charAt(i);
			if(c == token) {
				return beginIndex == i ? null : substring(str, beginIndex, i);
			}
		}
		
		return null;
	}
	
	public static String getNextToken(String str, char token, int beginIndex, int maxLength, boolean includeEOF) {
		int endIndex = Math.min(str.length(), beginIndex + maxLength);
		int i;
		for(i = beginIndex; i < endIndex; ++i) {
			char c = str.charAt(i);
			if(c == token) {
				return beginIndex == i ? null : substring(str, beginIndex, i);
			}
		}
		
		if(includeEOF && i == str.length()) {
			return substring(str, beginIndex);
		}
		
		return null;
	}
	
	public static String getNextToken(String str, char[] tokens, int beginIndex, int maxLength) {
		int endIndex = Math.min(str.length(), beginIndex + maxLength);
		for(int i = beginIndex; i < endIndex; ++i) {
			char c = str.charAt(i);
			for(int j = 0; j < tokens.length; ++j) {
				if(c == tokens[j]) {
					return beginIndex == i ? null : substring(str, beginIndex, i);
				}
			}
		}
		
		return null;
	}
	
	public static String getNextToken(String str, char[] tokens, int beginIndex, int maxLength, boolean includeEOF) {
		int endIndex = Math.min(str.length(), beginIndex + maxLength);
		int i;
		for(i = beginIndex; i < endIndex; ++i) {
			char c = str.charAt(i);
			for(int j = 0; j < tokens.length; ++j) {
				if(c == tokens[j]) {
					return beginIndex == i ? null : substring(str, beginIndex, i);
				}
			}
		}
		
		if(includeEOF && i == str.length()) {
			return substring(str, beginIndex);
		}
		
		return null;
	}
	
	public static boolean followWith(String str, char[] followingCharacters, int beginIndex) {
		int endIndex = beginIndex + followingCharacters.length;
		if(endIndex > str.length())
			return false;
		
		int k = 0;
		for(int i = beginIndex; i < endIndex; ++i, ++k) {
			if(str.charAt(i) != followingCharacters[k])
				return false;
		}
		
		return true;
	}
	
	public static int indexOfAny(String str, String[] needles) {
		for(int i = 0; i < needles.length; ++i) {
        	int k;
        	if((k = str.indexOf(needles[i])) != -1) {
        		return k;
        	}
        }
        
        return -1;
    }

    public static int indexOfAny(String str, String[] needles, int startIndex) {
        for(int i = 0; i < needles.length; ++i) {
        	int k;
        	if((k = str.indexOf(needles[i], startIndex)) != -1) {
        		return k;
        	}
        }
        
        return -1;
    }
    
	public static boolean startsWithAny(String str, String[] needles) {
		for(int i = 0; i < needles.length; ++i) {
        	if(str.startsWith(needles[i])) {
        		return true;
        	}
        }
        
        return false;
    }

	public static boolean startsWithAny(String str, String[] needles, int startIndex) {
		for(int i = 0; i < needles.length; ++i) {
        	if(str.length() > startIndex + needles[i].length()) {
        		boolean startsWith = true;
        		int k = startIndex;
        		for(int j = 0; j < needles[i].length(); ++j, ++k) {
        			if(needles[j].charAt(j) != str.charAt(k)) {
        				startsWith = false;
        				break;
        			}
        		}
        		
        		if(startsWith)
        			return true;
        	}
        }
        
        return false;
    }
    
    public static boolean startsWith(String str, char[] chars) {
    	if(str.length() < chars.length)
    		return false;
    	
    	for(int i = 0; i < chars.length; ++i) {
    		if(chars[i] != str.charAt(i))
    			return false;
    	}
    	
    	return true;
    }
    
    public static boolean endsWith(String str, char[] chars) {
    	if(str.length() < chars.length)
    		return false;
    	
    	for(int i = 0, j = str.length() - chars.length; i < chars.length; ++i, ++j) {
    		if(chars[i] != str.charAt(j))
    			return false;
    	}
    	
    	return true;
    }
	
    public static String substring(String str, int beginIndex) {
    	if(str == null)
    		return null;
    	
    	return substring(str, beginIndex, str.length());
    }
    
    public static String substring(String str, int beginIndex, int endIndex) {
    	if(str == null)
    		return null;
    	
    	if(endIndex == -1) {
    		endIndex = str.length();
    	}
    	
    	char[] substrChars = new char[endIndex - beginIndex];
    	str.getChars(beginIndex, endIndex, substrChars, 0);
    	return new String(substrChars);
    }
    
	public static String encodeURL(String s){
		return encodeURL(s, "UTF-8");
	}
	
	public static String encodeURL(String s, String encoding) {
		return encodeURL(s, encoding, false);
	}
	
	public static String encodeURL(String s, String encoding, boolean dontEncodeAsciiChars){
		if(s == null || s.equals("")){
			return s;
		}
		
		/*
		try {
			return URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		*/
		// 以下代码拷贝自  java.net.URLEncoder#encode()
		// 增加 dontEncodeAsciiChars 选项的支持，即只encode非ASCII字符
		final BitSet dontNeedEncoding = (dontEncodeAsciiChars ? StringUtils.dontNeedEncodingAscii : StringUtils.dontNeedEncoding);
		boolean needToChange = false;
		StringBuffer out = new StringBuffer(s.length());
		Charset charset;
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		if (encoding == null)
			encoding = "UTF-8";

		try {
			charset = Charset.forName(encoding);
		} catch (IllegalCharsetNameException e) {
			return null;
		} catch (UnsupportedCharsetException e) {
			return null;
		}

		for (int i = 0; i < s.length();) {
			int c = (int) s.charAt(i);
			// System.out.println("Examining character: " + c);
			if (dontNeedEncoding.get(c)) {
				if (!dontEncodeAsciiChars && c == ' ') {
					c = '+';
					needToChange = true;
				}
				// System.out.println("Storing: " + c);
				out.append((char) c);
				i++;
			} else {
				// convert to external encoding before hex conversion
				do {
					charArrayWriter.write(c);
					/*
					 * If this character represents the start of a Unicode
					 * surrogate pair, then pass in two characters. It's not
					 * clear what should be done if a bytes reserved in the
					 * surrogate pairs range occurs outside of a legal surrogate
					 * pair. For now, just treat it as if it were any other
					 * character.
					 */
					if (c >= 0xD800 && c <= 0xDBFF) {
						/*
						 * System.out.println(Integer.toHexString(c) +
						 * " is high surrogate");
						 */
						if ((i + 1) < s.length()) {
							int d = (int) s.charAt(i + 1);
							/*
							 * System.out.println("\tExamining " +
							 * Integer.toHexString(d));
							 */
							if (d >= 0xDC00 && d <= 0xDFFF) {
								/*
								 * System.out.println("\t" +
								 * Integer.toHexString(d) +
								 * " is low surrogate");
								 */
								charArrayWriter.write(d);
								i++;
							}
						}
					}
					i++;
				} while (i < s.length() && !dontNeedEncoding.get((c = (int) s.charAt(i))));

				charArrayWriter.flush();
				String str = new String(charArrayWriter.toCharArray());
				byte[] ba = str.getBytes(charset);
				for (int j = 0; j < ba.length; j++) {
					out.append('%');
					char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
					// converting to use uppercase letter as part of
					// the hex value if ch is a letter.
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					out.append(ch);
					ch = Character.forDigit(ba[j] & 0xF, 16);
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					out.append(ch);
				}
				charArrayWriter.reset();
				needToChange = true;
			}
		}

		return (needToChange ? out.toString() : s);
	}
	
	public static String escapeOutputText(String text) {
		if(text == null || text.length() == 0)
			return text;
		
		StringBuilder escaped = new StringBuilder();
		int length = text.length();
		int start = 0;
		for(int i = 0; i < length; ++i) {
			char c = text.charAt(i);
			switch(c) {
			case '\t' :
				escaped.append(text.substring(start, i)).append("\\t");
				start = i + 1;
				break;
			case '\n' :
				escaped.append(text.substring(start, i)).append("\\n");
				start = i + 1;
				break;
			case '\r' :
				escaped.append(text.substring(start, i)).append("\\r");
				start = i + 1;
				break;
			case '"' :
				escaped.append(text.substring(start, i)).append("\\\"");
				start = i + 1;
				break;
			}
		}
		
		if(start == 0) {
			// nothing escaped
			return text;
		}
		
		if(start < length) {
			escaped.append(text.substring(start));
		}
		
		return escaped.toString();
	}
	
	public static String unescapeOutputText(String text) {
		if(text == null || text.length() == 0)
			return text;
		
		StringBuilder escaped = new StringBuilder();
		int length = text.length();
		int start = 0;
		for(int i = 0; i < length; ++i) {
			char c = text.charAt(i);
			if(c == '\\') {
				if(i + 1 < length) {
					char nextChar = text.charAt(i + 1);
					switch (nextChar) {
					case 't' :
						escaped.append(text.substring(start, i)).append('\t');
						start = i + 2;
						break;
					case 'n' :
						escaped.append(text.substring(start, i)).append('\n');
						start = i + 2;
						break;
					case 'r' :
						escaped.append(text.substring(start, i)).append('\r');
						start = i + 2;
						break;
					case '"' :
						escaped.append(text.substring(start, i)).append('"');
						start = i + 2;
						break;
					}
				}
			}
		}
		
		if(start == 0) {
			// nothing escaped
			return text;
		}
		
		if(start < length) {
			escaped.append(text.substring(start));
		}
		
		return escaped.toString();
	}
	
	private static boolean isAggregationPath(String path) {
        //1: 路径超过 PATH_MAX_LENGTH 则聚合
        if (path.length() >= PATH_MAX_LENGTH) {
            return true;
        }

        char[] chars = path.toCharArray();
        //2: 16进制或特殊字符，长度要大于HEX_MIN_LENGTH
        if (chars.length > HEX_MIN_LENGTH) {
            for (char c : chars) {
                if (!isHexOrIgnoreChar(c)) {
                    return false;
                }
            }
        } else {
            //3 数字或特殊字符
            for (char c : chars) {
                if (!isDigitOrIgnoreChar(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isHexOrIgnoreChar(char c) {
        if (c >= 'a' && c <= 'f')
            return true;
        if (c >= 'A' && c <= 'F')
            return true;
        if (c >= '0' && c <= '9')
            return true;
        if (isIgnoreChar(c))
            return true;
        return false;
    }

    private static boolean isDigitOrIgnoreChar(char c) {
        if (c >= '0' && c <= '9')
            return true;
        if (isIgnoreChar(c))
            return true;
        return false;
    }

    private static boolean isIgnoreChar(char c) {
        if (c >= 42 && c <= 46) //* + , - .
            return true;
        for (char t : ignoreChars) {
            if (t == c)
                return true;
        }
        return false;
    }

    public static List<String> iterableToList(Iterable<String> iterable) {
        List<String> result = new ArrayList<String>();

        Iterator<String> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return Collections.unmodifiableList(result);

    }
}
