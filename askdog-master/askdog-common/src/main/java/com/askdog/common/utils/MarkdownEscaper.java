package com.askdog.common.utils;

import static com.google.common.base.CharMatcher.BREAKING_WHITESPACE;

public final class MarkdownEscaper {

    // TODO just comment it because we not implement the full markdown directives.
//    public static String escape(String content) {
//        CharMatcher charMatcher = CharMatcher.anyOf("\\`*-_{}[]()#+>!");
//        StringBuilder buf = new StringBuilder();
//        char[] chars = content.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            char ch = chars[i];
//            if (charMatcher.matches(ch)) {
//                buf.append('\\');
//            } else if (ch == '\n') {
//                buf.append("   ");
//                if (i < chars.length - 1) {
//                    char next;
//                    while (((next = chars[i + 1]) == ' ' || next == '\t')) {
//                        i++;
//                    }
//                }
//            } else if (ch == '\t') {
//                buf.append("\n");
//                continue;
//            }
//            buf.append(ch);
//        }
//
//        return buf.toString();
//    }
//
//    public static String unescape(String content) {
//        CharMatcher charMatcher = CharMatcher.anyOf("\\`*-_{}[]()#+>!");
//        StringBuilder buf = new StringBuilder();
//        char[] chars = content.toCharArray();
//        for (int i = 0; i < chars.length - 1; i++) {
//            char ch = chars[i];
//            if (ch == '\\' && charMatcher.matches(chars[i + 1])) {
//                buf.append(chars[++i]);
//                continue;
//            }
//
//            buf.append(ch);
//        }
//
//        return buf.toString();
//    }

    /**
     * Now we only support the newline mark, in the feature we should support other directives if we want.
     * @param content the content with markdown directives
     * @return the content which the markdown directives has been removed
     */
    public static String remove(String content) {
        return BREAKING_WHITESPACE.replaceFrom(content, ' ');
    }

}
