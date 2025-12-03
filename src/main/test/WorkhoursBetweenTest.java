import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.doris.udf.WorkhoursBetween;


public class WorkhoursBetweenTest {
    public static void main(String[] args) {
        WorkhoursBetween workhoursBetween = new WorkhoursBetween();

        // 测试用例1：基本工作日计算
        LocalDateTime dateFrom1 = LocalDateTime.of(2025, 12, 1, 9, 0, 0); // 周一 9点
        LocalDateTime dateTo1 = LocalDateTime.of(2025, 12, 5, 17, 20, 0);  // 周五 17点
        Integer result1 = workhoursBetween.evaluate(dateFrom1, dateTo1, null, null);
        System.out.println("基础工作日计算: " + result1); // 应该是105

        // 测试用例2：跨周末计算
        LocalDateTime dateFrom21 = LocalDateTime.of(2025, 12, 1, 0, 0, 0); // 周一
        LocalDateTime dateTo21 = LocalDateTime.of(2025, 12, 7, 23, 59, 59); // 周日
        Integer result21 = workhoursBetween.evaluate(dateFrom21, dateTo21, null, null);
        System.out.println("跨周末计算1: " + result21); // 应该是120

        LocalDateTime dateFrom22 = LocalDateTime.of(2025, 12, 1, 0, 0, 0); // 周一
        LocalDateTime dateTo22 = LocalDateTime.of(2025, 12, 15, 23, 59, 59); // 下下周一
        Integer result22 = workhoursBetween.evaluate(dateFrom22, dateTo22, null, null);
        System.out.println("跨周末计算2: " + result22); // 应该是120 + 120 + 24 = 264

        // 测试用例3：排除日期测试
        LocalDateTime dateFrom31 = LocalDateTime.of(2025, 12, 1, 0, 0, 0); // 周一
        LocalDateTime dateTo31 = LocalDateTime.of(2025, 12, 5, 23, 59, 59); // 周五
        String excludeDates31 = "2025-12-02"; // 排除周二
        Integer result31 = workhoursBetween.evaluate(dateFrom31, dateTo31, excludeDates31, null);
        System.out.println("排除日期测试1: " + result31); // 96

        LocalDateTime dateFrom32 = LocalDateTime.of(2025, 12, 1, 0, 0, 0); // 周一
        LocalDateTime dateTo32 = LocalDateTime.of(2025, 12, 5, 23, 59, 59); // 周日
        String excludeDates32 = "2025-12-06"; // 排除周六
        Integer result32 = workhoursBetween.evaluate(dateFrom32, dateTo32, excludeDates32, null);
        System.out.println("排除日期测试2: " + result32); // 120

        LocalDateTime dateFrom33 = LocalDateTime.of(2025, 12, 4, 3, 0, 0); // 周四
        LocalDateTime dateTo33 = LocalDateTime.of(2025, 12, 5, 6, 0, 0); // 周五
        String excludeDates33 = "2025-12-05"; // 排除周五
        Integer result33 = workhoursBetween.evaluate(dateFrom33, dateTo33, excludeDates33, null);
        System.out.println("排除日期测试3: " + result33); // 21


        // 测试用例4：包含日期测试（周末）
        LocalDateTime dateFrom41 = LocalDateTime.of(2025, 12, 6, 3, 0, 0); // 周六
        LocalDateTime dateTo41 = LocalDateTime.of(2025, 12, 8, 23, 59, 59); // 下周一
        String includeDates41 = "2025-12-06"; // 包含周六
        Integer result41 = workhoursBetween.evaluate(dateFrom41, dateTo41, null, includeDates41);
        System.out.println("包含周末测试1: " + result41); // 45

        LocalDateTime dateFrom42 = LocalDateTime.of(2025, 12, 6, 3, 0, 0); // 周六
        LocalDateTime dateTo42 = LocalDateTime.of(2025, 12, 8, 10, 59, 59); // 下周一
        String includeDates42 = "2025-12-08"; // 包含周一
        Integer result42 = workhoursBetween.evaluate(dateFrom42, dateTo42, null, includeDates42);
        System.out.println("包含周末测试2: " + result42); // 11

        LocalDateTime dateFrom43 = LocalDateTime.of(2025, 12, 5, 3, 0, 0); // 周五
        LocalDateTime dateTo43 = LocalDateTime.of(2025, 12, 6, 6, 10, 0); // 周六
        String includeDates43 = "2025-12-06"; // 包含周六
        Integer result43 = workhoursBetween.evaluate(dateFrom43, dateTo43, null, includeDates43);
        System.out.println("包含周末测试3: " + result43); // 28

        // 测试用例5：空值测试
        Integer result5 = workhoursBetween.evaluate(null, dateTo1, null, null);
        System.out.println("空值测试: " + result5); // 应该返回null

        // 测试用例6：开始时间晚于结束时间
        LocalDateTime dateFrom6 = LocalDateTime.of(2025, 12, 5, 0, 0, 0); // 周五
        LocalDateTime dateTo6 = LocalDateTime.of(2025, 12, 1, 0, 0, 0); // 周一
        Integer result6 = workhoursBetween.evaluate(dateFrom6, dateTo6, null, null);
        System.out.println("时间倒序测试: " + result6); // 应该返回-1

    }
}