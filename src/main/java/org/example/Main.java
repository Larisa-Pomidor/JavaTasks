package org.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    public static long ipsBetween(String start, String end) {
        int[] startArray = Arrays.stream(start.split("\\.")).mapToInt(Integer::parseInt).toArray();
        int[] endArray = Arrays.stream(end.split("\\.")).mapToInt(Integer::parseInt).toArray();
        int result = 0;
        for (int i = 0; i < startArray.length; i++) {
            result += startArray[i] >= endArray[i] ? (startArray[i] - endArray[i]) : (255 + (startArray[i] - endArray[i]));
        }
        return result;
    }

    // Average Salary Excluding the Minimum and Maximum Salary
    public static double average(int[] salary) {
        int max = Arrays.stream(salary).max().orElse(-1);
        int min = Arrays.stream(salary).min().orElse(-1);
        return Arrays.stream(salary).filter(i -> i != max && i != min).average().orElse(-1);
    }

    // In this kata, we will make a function to test whether a period is late.
    public static boolean periodIsLate(LocalDate last, LocalDate today, int cycleLength) {
        System.out.println(ChronoUnit.DAYS.between(today, last));
        return ChronoUnit.DAYS.between(last, today) > cycleLength;
    }

    // Complete the function scramble(str1, str2) that returns true if a portion of str1
    // characters can be rearranged to match str2, otherwise returns false
    public static boolean scramble(String str1, String str2) {
        StringBuilder sb = new StringBuilder(str1);
        for (int i = 0; i < str2.length(); i++) {
            int index = sb.indexOf(String.valueOf(str2.charAt(i)));
            if (index != -1) {
                sb.deleteCharAt(index);
            } else return false;
        }
        return true;
    }

    public static boolean scramble2(String str1, String str2) {
        Map<Integer, Integer> cnt = new HashMap<>();
        str2.chars().forEach(i -> cnt.put(i, cnt.getOrDefault(i, 0) + 1));
        str1.chars().filter(cnt::containsKey).forEach(i -> cnt.put(i, cnt.get(i) - 1));
        return cnt.values().stream().noneMatch(i -> i > 0);
    }

    // If a user input string is alphanumeric.
    public static boolean alphanumeric(String s) {
        if (s.length() == 0) return false;
        String pattern = "(.*\\W.*)|(^nil$)|(^null$)|(^NULL$)|(^None$)|(_)+";
        return !s.matches(pattern);
    }

    public static int lengthOfLongestSubstring2(String s) {
        String separator = "ї";
        Map<Integer, Integer> map = new HashMap<>();
        s.chars().forEach(i -> map.put(i, map.getOrDefault(i, 0) + 1));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                s = s.replaceAll(String.valueOf((char) (int) entry.getKey()), separator);
            }
        }
        s = s.replaceAll(separator + "+", separator);
        return Arrays.stream(s.split(separator)).map(i -> i.length()).max(Integer::compareTo).orElse(-1);
    }

    public static int lengthOfLongestSubstring(String s) {
        StringBuilder sb = new StringBuilder();
        int longest = 0;
        StringBuilder main = new StringBuilder(s);
        for (int i = 0; i < main.length(); i++) {
            for (int j = i; j < main.length(); j++) {
                if (sb.indexOf(String.valueOf(main.charAt(j))) == -1) {
                    sb.append(main.charAt(j));
                } else {
                    if (sb.length() > longest) longest = sb.length();
                    sb.setLength(0);
                    break;
                }
            }
        }
        if (sb.length() > longest) longest = sb.length();
        return longest;
    }

    public static int arraySign(int[] nums) {
        int product = Arrays.stream(nums).reduce(1, (prod, element) -> {
            if (element > 0) return prod * 1;
            else if (element == 0) return prod * 0;
            else {
                return prod * -1;
            }
        });
        return product;
    }

    // Find the Difference of Two Arrays
    public static List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        List<Integer> difference1 = new ArrayList<>(Arrays.stream(nums1).boxed().toList());
        List<Integer> difference2 = new ArrayList<>(Arrays.stream(nums2).boxed().toList());
        difference1.removeAll(Arrays.stream(nums2).boxed().toList());
        difference2.removeAll(Arrays.stream(nums1).boxed().toList());

        List<List<Integer>> result = new ArrayList<>();
        result.add(difference1.stream().distinct().toList());
        result.add(difference2.stream().distinct().toList());

        return result;
    }

    // Given a string with the weights of FFC members in normal order
    // can you give this string ordered by "weights" of these numbers?
    public static String orderWeight(String strng) {
        strng = strng.replaceAll("\\D+", " ");
        strng = strng.replaceAll("^\\D+", "");
        strng = strng.replaceAll("\\D+$", "");

        List<String> weight = new ArrayList<>(List.of(strng.split(" ")));

        weight.sort((a, b) -> sum(a) < sum(b) ? -1 : sum(a) == sum(b) ? a.compareTo(b) : 1);

        return String.join(" ", weight);
    }

    public static int sum(String a) {
        return Arrays.stream(a.split(""))
                .mapToInt(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        //long res = ipsBetween("10.0.0.0", "10.0.0.50");
        //System.out.println(average(new int []{1,2,3,4,6}));
        //periodIsLate(LocalDate.of(2016, 6, 13), LocalDate.of(2016, 7, 16), 35);
        //System.out.println(scramble("rkqodlw","world"));
        // System.out.println(scramble2("rkqodlw","world"));
        //System.out.println(alphanumeric("bcwuevu"));
        //  System.out.println(lengthOfLongestSubstring("dvdf"));
        //System.out.println(arraySign(new int[] {1,5,0,2,-3}));
        // findDifference(new int[] {1,2,3}, new int[] {2,4,6});
        // System.out.println(orderWeight("56 65 74 100 99 68 86 180 90"));
    }
}