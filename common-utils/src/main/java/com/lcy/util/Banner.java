package com.lcy.util;

/**
 * 启动提示，替代三个启动类里各自复制的一份 System.out 打印。
 */
public final class Banner {

    private Banner() {
    }

    public static void print(String appName, int port, String contextPath) {
        // 控制台编码在 Windows 下可能是 GBK，这里只用 ASCII，避免中文乱码
        System.out.println("  [" + appName + "] started  ->  http://localhost:" + port + contextPath + "\n" +
                "                    _\n" +
                "            " +
                "      _(_)_                           wWWWw   _\n" +
                "      @@@@       (_)@(_)   vVVVv     _     @@@@  (___) _(_)_\n" +
                "     @@()@@ wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)\n" +
                "      @@@@  (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\\n" +
                "       /      Y       \\|    \\|/    /(_)    \\|      |/      |\n" +
                "    \\ |     \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/\n" +
                "    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|// \n" +
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}
