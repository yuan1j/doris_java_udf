package org.apache.doris.udf;

import java.sql.Timestamp;


/**
 * WorkhoursBetween 类的简单测试程序
 */
public class WorkhoursBetweenTest2 {

    public static void main(String[] args) {
        // 创建 WorkhoursBetween 实例
        WorkhoursBetween WorkhoursBetween = new WorkhoursBetween();

        try {
//            // 测试本周六到下下周一
            Timestamp start = Timestamp.valueOf("2023-06-17 11:33:00");
            Timestamp end = Timestamp.valueOf("2023-06-26 09:21:00");
            String dateExclude = null;
            String dateInclude = null;
//            Object result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(),null,null);
//            System.out.println("测试1 - 本周六到下下周一: " + result);
//
//            // 测试本周六到下下周一，剔除端午
//            start = Timestamp.valueOf("2023-06-17 11:33:00");
//            end = Timestamp.valueOf("2023-06-26 09:21:00");
//            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
//            dateInclude = null;
//            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude);
//            System.out.println("测试2 - 本周六到下下周一，剔除端午: " + result);
//
//            // 测试本周六到下下周一，剔除端午，包含下周日
//            start = Timestamp.valueOf("2023-06-17 11:33:00");
//            end = Timestamp.valueOf("2023-06-26 09:21:00");
//            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
//            dateInclude = String.valueOf("2023-06-25");
//            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude, dateInclude);
//            System.out.println("测试3 - 本周六到下下周一，剔除端午，包含下周日: " + result);

            // -- 本周六到下下周一，剔除端午，包含下周日和本周六
            start = Timestamp.valueOf("2023-06-17 11:33:00");
            end = Timestamp.valueOf("2023-06-25 09:21:00");
            System.out.println("start: " + start);
            System.out.println("end: " + end);
            dateExclude = String.valueOf("2023-06-22,2023-06-23,2023-06-24");
            dateInclude = String.valueOf("2023-06-17,2023-06-25");
            Object result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime(), dateExclude, dateInclude);
            System.out.println("测4 - 本周六到下下周一，剔除端午，包含下周日和本周六: " + result);

//
//            // 测试本周一到本周日
//            start = Timestamp.valueOf("2023-06-19 11:33:00");
//            end = Timestamp.valueOf("2023-06-25 09:21:00");
//            dateExclude = null;
//            dateInclude = null;
//            result = WorkhoursBetween.evaluate(start.toLocalDateTime(), end.toLocalDateTime());
//            System.out.println("测试5 - 本周一到本周日: " + result);
//
//
//            // 测试无效输入
//            result = WorkhoursBetween.evaluate(null, Timestamp.valueOf("2023-10-01 17:00:00").toLocalDateTime());
//            System.out.println("测试6 - 无效输入: " + result);
//
//            System.out.println("所有测试完成");
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



//
//        // 计算完整的小时数
//        long totalHours = ChronoUnit.HOURS.between(dateFrom, dateTo) + 1;
//        System.out.println("totalHours: " + totalHours);
//        // 其中周末的小时数
//        long weekendHours = 0;
//        if (dateFrom.getDayOfWeek().getValue() >= 6){
//            weekendHours += ChronoUnit.HOURS.between(dateFrom, dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                    .withHour(0).withMinute(0).withSecond(0));
//        }
//        System.out.println("end_date: "+ dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                .withHour(0).withMinute(0).withSecond(0));
//        System.out.println("weekendHours1: " + weekendHours);
//        if (dateTo.getDayOfWeek().getValue() >= 6){
//            weekendHours += ChronoUnit.HOURS.between(dateTo.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY))
//                    .withHour(23).withMinute(59).withSecond(59), dateTo);
//        }
//        System.out.println("end_date: "+ dateTo.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY))
//                .withHour(23).withMinute(59).withSecond(59));
//        System.out.println("weekendHours2: " + weekendHours);
//        weekendHours = weekendHours + totalHours / (24 * 7) * 24 * 2;
//        System.out.println("weekendHours3: " + weekendHours);
//        // 计算dateExclude中应该减去的日期
//        if (dateExclude != null) {
//            String[] excludedDates = dateExclude.split(",");
//        }
//        // 计算dateInclude中应该加上的日期
//        if (dateInclude != null) {
//            String[] includedDates = dateInclude.split(",");
//        }
//        return 1;
//        long fullWeeks = totalHours / (24 * 7);
//        System.out.println("fullWeeks: " + fullWeeks);
//        int workhours = (int) (fullWeeks * 5 * 24); // 每周5个工作日
//        System.out.println("workhours: " + workhours);
//        // 处理剩余天数
//        LocalDateTime current = startDate.plusDays(fullWeeks * 7 );
//
//
//
//
//        String fromDateString = dateFrom.toLocalDate().toString();
//        LocalDateTime startDate;
//
//        if (dateInclude != null && dateInclude.contains(fromDateString)) {
//            startDate = dateFrom;
//        } else if (dateFrom.getDayOfWeek().getValue() >= 6) {
//            startDate = dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                    .withHour(0).withMinute(0).withSecond(0);
//        } else {
//            startDate = dateFrom;
//        }
//
//        // 计算完整的周数
//        long totalDays = ChronoUnit.DAYS.between(startDate, dateTo) + 1;
//        long fullWeeks = totalDays / 7;
//        System.out.println("fullWeeks: " + fullWeeks);
//        int workhours = (int) (fullWeeks * 5 * 24); // 每周5个工作日
//        System.out.println("workhours: " + workhours);
//        // 处理剩余天数
//        LocalDateTime current = startDate.plusDays(fullWeeks * 7 );
//
//        // 处理dateExclude中的日期
//        if (dateExclude != null) {
//            String[] excludedDates = dateExclude.split(",");
//            for (String excludedDate : excludedDates) {
//                excludedDate = excludedDate.trim();
//                LocalDate excludedLocalDate = LocalDate.parse(excludedDate);
//                LocalDate dateFromLocalDate = dateFrom.toLocalDate();
//                LocalDate dateToLocalDate = dateTo.toLocalDate();
//                // 检查排除的日期是否在范围内
//                if (!excludedLocalDate.isBefore(dateFromLocalDate) && !excludedLocalDate.isAfter(dateToLocalDate)) {
//                    DayOfWeek dayOfWeek = excludedLocalDate.getDayOfWeek();
//                    // 如果排除的日期是工作日（周一到周五）
//                    if (dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 5) {
//                        long hoursBetween = 0;
//                        if (excludedLocalDate.equals(dateFromLocalDate) && excludedLocalDate.equals(dateToLocalDate)) {
//                            // 情况1: excludeLocalDate等于datefrom且等于dateTo
//                            hoursBetween = ChronoUnit.HOURS.between(dateFrom, dateTo);
//                        } else if (excludedLocalDate.equals(dateFromLocalDate)) {
//                            // 情况2: excludeLocalDate等于datefrom但不等于dateTo
//                            LocalDateTime endOfExcludeDay = excludedLocalDate.plusDays(1).atStartOfDay();
//                            hoursBetween = ChronoUnit.HOURS.between(dateFrom, endOfExcludeDay);
//                        } else if (excludedLocalDate.equals(dateToLocalDate)) {
//                            // 情况3: excludeLocalDate不等于datefrom但等于dateTo
//                            LocalDateTime startOfExcludeDay = excludedLocalDate.atStartOfDay();
//                            hoursBetween = ChronoUnit.HOURS.between(startOfExcludeDay, dateTo);
//                        } else {
//                            // 情况4: excludeLocalDate在datefrom和dateTo之间但不等于任何一端
//                            hoursBetween = 24;
//                        }
//                        workhours -= (int) hoursBetween; // 从工作日总数中减去这几个小时
//                        // 如果在剩余天数处理范围内
//                        if (!excludedLocalDate.isBefore(current.toLocalDate())) {
//                            workhours -= (int) hoursBetween; // 再多减一次，因为后续计算会将其加上
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("workhours: " + workhours);
//        // 处理dateInclude中的日期
//        if (dateInclude != null) {
//            String[] includedDates = dateInclude.split(",");
//            for (String includedDate : includedDates) {
//                includedDate = includedDate.trim();
//                LocalDate includedLocalDate = LocalDate.parse(includedDate);
//                LocalDate dateFromLocalDate = dateFrom.toLocalDate();
//                LocalDate dateToLocalDate = dateTo.toLocalDate();
//                // 检查包含的日期是否在范围内
//                if (!includedLocalDate.isBefore(dateFromLocalDate) && !includedLocalDate.isAfter(dateToLocalDate)) {
//                    DayOfWeek dayOfWeek = includedLocalDate.getDayOfWeek();
//                    // 如果包含的日期是周末（周六到周日）
//                    if (dayOfWeek.getValue() >= 6 && dayOfWeek.getValue() <= 7) {
//                        long hoursBetween = 0;
//                        if (includedLocalDate.equals(dateFromLocalDate) && includedLocalDate.equals(dateToLocalDate)) {
//                            // 情况1: includedLocalDate等于datefrom且等于dateTo
//                            hoursBetween = ChronoUnit.HOURS.between(dateFrom, dateTo);
//                        } else if (includedLocalDate.equals(dateFromLocalDate)) {
//                            // 情况2: includedLocalDate等于datefrom但不等于dateTo
//                            LocalDateTime endOfIncludeDay = includedLocalDate.plusDays(1).atStartOfDay();
//                            hoursBetween = ChronoUnit.HOURS.between(dateFrom, endOfIncludeDay);
//                        } else if (includedLocalDate.equals(dateToLocalDate)) {
//                            // 情况3: includedLocalDate不等于datefrom但等于dateTo
//                            LocalDateTime startOfIncludeDay = includedLocalDate.atStartOfDay();
//                            hoursBetween = ChronoUnit.HOURS.between(startOfIncludeDay, dateTo);
//                        } else {
//                            // 情况4: includedLocalDate在datefrom和dateTo之间但不等于任何一端
//                            hoursBetween = 24;
//                        }
//                        workhours += (int) hoursBetween; // 从工作日总数中减去这几个小时
//                        // 如果在剩余天数处理范围内
//                        if (!includedLocalDate.isBefore(current.toLocalDate())) {
//                            workhours += (int) hoursBetween; // 再多减一次，因为后续计算会将其加上
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("workhours: " + workhours);
//        while (!current.isAfter(dateTo)) {
//            int dayOfWeek = current.getDayOfWeek().getValue();
//            if (dayOfWeek >= 1 && dayOfWeek <= 5) {
//                workhours++;
//            }
//            current = current.plusHours(1);
//        }
//
//        return workhours;