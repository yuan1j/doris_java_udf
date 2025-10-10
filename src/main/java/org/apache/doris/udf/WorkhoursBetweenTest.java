package org.apache.doris.udf;
import org.apache.doris.udf.WorkhoursBetween;

import java.sql.Timestamp;


/**
 * WorkhoursBetween 类的简单测试程序
 */
public class WorkhoursBetweenTest {

    public static void main(String[] args) {
        // 创建 WorkhoursBetween 实例
        WorkhoursBetween WorkhoursBetween = new WorkhoursBetween();

        // TODO: 根据 WorkhoursBetween 的具体实现添加测试逻辑
        // 示例测试逻辑（假设该类有 evaluate 方法计算两个日期间的工作小时数）

        try {
            // 测试本周六到下下周一
            Timestamp start = Timestamp.valueOf("2023-06-17 11:33:00");
            Timestamp end = Timestamp.valueOf("2023-06-26 09:21:00");
            String dateExclude = null;
            String dateInclude = null;
            Object result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime());
            System.out.println("测试1 - 本周六到下下周一: " + result);

            // 测试本周六到下下周一，剔除端午
            start = Timestamp.valueOf("2023-06-17 11:33:00");
            end = Timestamp.valueOf("2023-06-26 09:21:00");
            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
            dateInclude = null;
            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude);
            System.out.println("测试2 - 本周六到下下周一，剔除端午: " + result);

            // 测试本周六到下下周一，剔除端午，包含下周日
            start = Timestamp.valueOf("2023-06-17 11:33:00");
            end = Timestamp.valueOf("2023-06-26 09:21:00");
            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
            dateInclude = String.valueOf("2023-06-25");
            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude, dateInclude);
            System.out.println("测试3 - 本周六到下下周一，剔除端午，包含下周日: " + result);

            // -- 本周六到下下周一，剔除端午，包含下周日和本周六
            start = Timestamp.valueOf("2023-06-17 11:33:00");
            end = Timestamp.valueOf("2023-06-26 09:21:00");
            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
            dateInclude = String.valueOf("2023-06-17,2023-06-25");
            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude, dateInclude);
            System.out.println("测4 - 本周六到下下周一，剔除端午，包含下周日和本周六: " + result);


            // 测试本周一到本周日
            start = Timestamp.valueOf("2023-06-19 11:33:00");
            end = Timestamp.valueOf("2023-06-25 09:21:00");
            dateExclude = null;
            dateInclude = null;
            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime());
            System.out.println("测试5 - 本周一到本周日: " + result);


            // 测试无效输入
            result = WorkhoursBetween.evaluate(null, Timestamp.valueOf("2023-10-01 17:00:00").toLocalDateTime());
            System.out.println("测试6 - 无效输入: " + result);

            System.out.println("所有测试完成");
        } catch (Exception e) {
            System.err.println("测试过程中发生异常: " + e.getMessage());

        }

    }
}


//测试1 - 本周六到下下周一: 130
//测试2 - 本周六到下下周一，剔除端午: 82
//测试3 - 本周六到下下周一，剔除端午，包含下周日: 106
//测4 - 本周六到下下周一，剔除端午，包含下周日和本周六: 118
//测试5 - 本周一到本周日: 109
//测试6 - 无效输入: null
//所有测试完成