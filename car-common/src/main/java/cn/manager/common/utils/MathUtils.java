package cn.manager.common.utils;

import cn.manager.common.core.Lists;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * 数学转化工具类
 *
 * @Author ljc
 * @Description: ok
 * @Version 0.1.0
 */
public class MathUtils {

    /**
     * ljc
     * 百分比转化
     * [活动]
     *
     * @return
     */
    public static String changePercent(BigDecimal bigDecimal) {
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(bigDecimal);
    }


    /**
     * ljc
     * map数量模板<初级版本>固定数据</初级版本-->
     * [施工中使用: 预约数量]
     *
     * @param zero
     * @param one
     * @param two
     * @return
     */
    public static Map<Integer, Integer> countTemplate(Integer zero, Integer one, Integer two, Integer four) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, zero);
        map.put(1, one);
        map.put(2, two);
        map.put(3, four);
        return map;
    }

    /**
     * ljc
     * map数量模板<初级版本>固定数据</初级版本-->
     * [施工中使用: 预约数量]
     *
     * @param locationCounts 可变参数 前端显示位置的数量
     * @return
     */
    public static Map<Integer, Integer> countTemplate(Integer... locationCounts) {
        if (locationCounts == null) {
            throw new RuntimeException("Error method at com.aisce.common.utils.Maths.countTemplate()");
        }
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = Arrays.asList(locationCounts);
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
        }
        return map;
    }

    /**
     * ljc
     * 计算总数(数量相加, 如果是null跳过)
     * <p>
     * example:
     * <p>
     * Integer a = null;
     * Integer b = 1;
     * Integer c = null;
     * Integer d = 2;
     * System.out.println(MathUtils.sumNumAll(b, c, d)); //3
     * System.out.println(MathUtils.sumNumAll());        //0
     *
     * @param counts
     * @return
     */
    public static Integer sumNumAll(Integer... counts) {
        List<Integer> list = Arrays.asList(counts);

        if (CollectionUtils.isEmpty(list)) return 0;

        return list.stream().filter(Objects::nonNull).reduce(Integer::sum).orElse(0);
    }

    /**
     * ljc 订单号的自增
     *
     * @param orderId
     * @return
     */
    public static String orderIncr(String orderId) {

        if (StringUtils.isBlank(orderId)) {
            return StringUtils.leftPad(String.valueOf(1), 6, "0");
        }

        return StringUtils.leftPad(String.valueOf(Long.parseLong(orderId.substring(2)) + 1), 6, "0");//从第三位开始取
    }

    /**
     * ljc 数组缩容
     *
     * @param conditions
     * @return
     */
    public static String[] removeFirstElement(String... conditions) {

        return Arrays.copyOfRange(conditions, 1, conditions.length);
    }


    /**
     * ljc list 转化为String 字符串
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String listToString(List<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(planId -> {
            stringBuilder.append(planId);
            stringBuilder.append(",");
        });

        String str = stringBuilder.toString();
        return str.substring(0, str.length() - 1);
    }


    /**
     * ljc String用,拼接的字符串 转化为 list
     *
     * @param
     * @param
     * @return
     */
    public static List<Integer> stringToIntegerList(String str, String splitArgs) {

        if (StringUtils.isNotBlank(str)) {
            return Arrays.stream(str.split(splitArgs)).map(Integer::valueOf).collect(Collectors.toList());
        }

        return Lists.newArrayList();
    }


    /**
     * ljc String用,拼接的字符串 转化为 list
     *
     * @param
     * @param
     * @return
     */
    public static List<String> stringToStrList(String str, String splitArgs) {

        if (StringUtils.isNotBlank(str)) {
            return Arrays.stream(str.split(splitArgs)).collect(Collectors.toList());
        }

        return Lists.newArrayList();
    }


    /**
     * ljc 判断list<T> 是否包含这个 T类型
     *
     * @param commonManList 集合
     * @param args          公共参数
     * @return
     */
    public static <T> boolean isContains(List<T> commonManList, T args) {

        /*校验: 查询数据是否为空*/
        if (CollectionUtils.isEmpty(commonManList)) {
            return false;
        }

        return commonManList.contains(args);
    }

    /**
     * ljc list非空校验并转化为0
     * <p>
     * example:
     * list 是空的 返回0
     * list 不是空 返回list.size()
     *
     * @param list 传入list集合
     * @param <T>  任意类型
     * @return 数字类型
     */
    public static <T> Integer isEmptyToZero(List<T> list) {
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    /**
     * ljc 数字相乘
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static String atWillNumMultiply(String arg1, String arg2) {

        if (arg1 == null || arg2 == null) {
            throw new RuntimeException("Error,the exception passed parameter cannot be null[arg1,arg2]");
        }

        return new BigDecimal(arg1).multiply(new BigDecimal(arg2)).toString();
    }


    /**
     * ljc 可变参数校验
     * <p>
     * example
     * true: checkArgs中包含arg1
     * false: checkArgs中不包含arg1
     * 解决多个或者的问题
     *
     * @param arg1      传入参数值[需要比较的]
     * @param checkArgs 比较参数code(多个值集合)
     * @return true/false
     */
    public static boolean argsCheckContains(Integer arg1, Integer... checkArgs) {

        if (checkArgs.length <= 0) return false;

        return Arrays.asList(checkArgs).contains(arg1);
    }

    /**
     * 判断arg1参数是否在 传入的list范围中
     * <p>
     * example:  argsCheckContains(5,1,12)
     * 返回值是true 表示 5在1-12范围中
     * <p>
     * true: checkArgs中包含arg1
     * false: checkArgs中不包含arg1
     *
     * @param arg1  传入参数值[需要比较的]
     * @param start 集合开始
     * @param end   集合关闭
     * @return true/false
     */
    public static boolean argsCheckContains(Long arg1, int start, int end) {

        return MathUtils.getRangeToList(start, end).contains(arg1);
    }

    /**
     * 校验: 是否是默认数据
     *
     * @param id 主键id
     * @return true是默认数据
     * @return false 不是默认数据
     */
    private boolean isDefault(Long id) {

        return MathUtils.getRangeToList(1, 12).contains(id);
    }

    /**
     * 去掉重复的key
     *
     * @param keyExtractor function方法
     * @param <T>          泛型
     * @return 泛型
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return (object) -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }

    /**
     * 获取范围内的list集合
     * <p>
     * example: 输入两个1,12 参数 生成一个两个参数范围(1-12)内的list
     *
     * @param start 开始数字
     * @param end   结束数字
     * @return list数字集合
     */
    public static List<Long> getRangeToList(int start, int end) {
        List<Long> list = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getIntRangeToList(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    public static <T> List<T> getRangeToList(T... values) {

        return new ArrayList<>(Arrays.asList(values));
    }

    /**
     * 使用list中的containsAll方法
     * <p>
     * 此方法是判断list2是否是list的子集，即1ist2包合
     *
     * @param list  参数一
     * @param list2 参数二
     * @return true相同  false不同
     */
    public static boolean compareList(List<String> list, List<String> list2) {
        boolean flag = false;
        if (list.size() == list2.size()) {
            if (list.containsAll(list2)) {
                flag = true;
            }
        }
        System.out.println("执行结果:" + flag);
        return flag;
    }

    /**
     * String字符串比较(可进行符号拆分比较)
     * <p>
     * 例子:
     * 1,2,3 与 1,2,3 比较 则为true
     * 2,1,3 与 1,3,2 比较 则为true
     * 1,3,4 与 1,2,3 比较 则为false
     *
     * @param arg1      比较参数一
     * @param arg2      比较参数二
     * @param splitArgs 拆分符号
     * @return
     */
    public static boolean compareString(String arg1, String arg2, String splitArgs) {

        if (StringUtils.isBlank(arg1) || StringUtils.isBlank(arg2)) {
            return false;
        }

        if (Objects.equals(arg1, arg2)) {
            return true;
        }

        return compareList(stringToStrList(arg1, splitArgs), stringToStrList(arg2, splitArgs));
    }

    /**
     * list操作:  存入的String拆分在存入list 转换:
     * <p>
     * 示例: [1_3_2, 1_2_3] -> [1, 3, 2, 1, 2, 3]
     *
     * @param sourceList 源集合
     * @param splitArgs  拆分标识 如 "," "_" 等
     * @return 目标集合
     */
    public static List<String> listStrToStrList(List<String> sourceList, String splitArgs) {
        List<String> targetList = Lists.newArrayList();

        sourceList.forEach(sourceStr -> targetList.addAll(Arrays.asList(sourceStr.split(splitArgs))));

        return targetList;
    }


    /**
     * 测试区域
     *
     * @param args
     */
    public static void main(String[] args) {

        String str1 = "1,3,2";
        String str2 = "1,2,3";
        String str3 = "1,2,3";
        String str4 = "1,2,4";

        System.out.println(compareString(str1, str2, ","));
        System.out.println(compareString(str2, str3, ","));
        System.out.println(compareString(str2, str4, ","));


        List<String> list = new ArrayList<>();
        list.add(str1);
        list.add(str2);
        list.add(str3);
        list.add(str4);
        System.out.println(list);
        System.out.println(list.size());

        System.out.println(listStrToStrList(list, ","));

    }


}
