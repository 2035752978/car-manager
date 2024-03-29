package cn.manager.system.generator;

/**
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
public class ATest {

    public static void main(String[] args) {
        int seconds = 100000; // 示例秒数，你可以根据需要修改
        int hours = convertSecondsToHours(seconds);
        System.out.println("Hours: " + hours);
    }

    public static int convertSecondsToHours(int seconds) {
        int hours = seconds / 3600; // 计算基本小时数

        // 如果秒数超过一小时但未达到两小时，则按两小时计算
        if (seconds % 3600 > 0) {
            hours++;
        }

        return hours;
    }




}
