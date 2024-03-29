package cn.manager.common.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 随机字符串生成器
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
public class RandomNameGenerator {
    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String SMALL_CHARACTERS = "0123456789012345678901234567890123456789abcdefghijklmnopqrstuvwxyz";
    private static final int NAME_LENGTH = 8;
    private static final int MAX_ATTEMPTS = 1000;
    private static final Set<String> existingNames = new HashSet<>();

    /**
     * 生成用户名随机
     * @param length 长度 null 使用默认8位
     * @param charType 字典库 null 使用大小写混合
     * @return 随机名 例子: 用户dm197690
     */
    public static String generateUserName(Integer length, Integer charType) {


        return "用户" + generatePassword(length == null ? NAME_LENGTH : length, charType == null ? CHARACTERS : SMALL_CHARACTERS);
    }

    /**
     * 生成随机字符串 核心功能
     *
     * @param strLength
     * @return
     */
    public static String generatePassword(int strLength, String directory) {
        Random random = new Random();
        String userName;
        int attempts = 0;

        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strLength; i++) {
                int index = random.nextInt(directory.length());
                sb.append(directory.charAt(index));
            }
            userName = sb.toString();
            attempts++;
        } while (existingNames.contains(userName) && attempts < MAX_ATTEMPTS);
        if (attempts == MAX_ATTEMPTS) {
            throw new RuntimeException("Failed to generate a unique username after " + attempts + " attempts.");
        }

        existingNames.add(userName);
        return userName;
    }


    public static void main(String[] args) {
        for (; ; ) {
            String str = RandomNameGenerator.generateUserName(null,1);
            System.out.println(str);
        }
    }

}