import com.sun.jdi.IntegerValue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


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
        return Arrays.stream(a.split("")).mapToInt(Integer::parseInt).reduce(0, Integer::sum);
    }

    // Longest Common Prefix
    public static String longestCommonPrefix(String[] strs) {
        String check = strs[0];
        int curLen;
        int diff = 0;
        for (int i = 1; i < strs.length; i++) {
            curLen = strs[i].length();

            while (check.length() > 0 && diff == 0) {
                if (curLen > check.length()) diff = curLen - strs[i].replaceAll("^(" + check + ")", "").length();
                else diff = check.length() - check.replaceAll("^(" + strs[i] + ")", "").length();

                if (diff == 0) check = check.substring(0, check.length() - 1);
                else check = check.substring(0, diff);
            }
            diff = 0;
        }
        return check;
    }

    // Snail travel
    public static int[] snail1(int[][] array) {
        ArrayList<Integer> result = new ArrayList<>();
        int N = array.length;
        for (int i = 0; i < N; i++) {
            if (i % 2 == 1) {
                for (int j = N - 1; j > 0; j--) {
                    result.add(array[i][j]);
                }
            } else {
                for (int j = 0; j < N; j++) {
                    result.add(array[i][j]);
                }
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    public static int[] snail2(int[][] array) {
        ArrayList<Integer> result = new ArrayList<>();
        int N = array.length;
        if (array[0].length == 0) return new int[]{};
        for (int level = 0; level < N; level++) {
            for (int j = level; j < N - level; j++) {
                result.add(array[level][j]);
            }
            for (int j = level + 1; j < N - level; j++) {
                result.add(array[j][N - level - 1]);
            }
            for (int j = N - level - 2; j >= level; j--) {
                result.add(array[N - level - 1][j]);
            }
            for (int j = N - level - 2; j > level; j--) {
                result.add(array[j][level]);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    // Maximum Number of Vowels in a Substring of Given Length
    // Time Limit Exceeded
    public static int maxVowels2(String s, int k) {
        int max = 0;
        for (int i = 0; i < s.length() - k + 1; i++) {
            String substring = s.substring(i, i + k);
            int currentLen = substring.length() - substring.replaceAll("[aeiou]*", "").length();
            if (currentLen > max) max = currentLen;
        }
        return max;
    }

    // Maximum Number of Vowels in a Substring of Given Length
    public static int maxVowels(String s, int k) {
        String substring = s.substring(0, k);
        int count = substring.length() - substring.replaceAll("[aeiou]*", "").length();

        int currentCount = count;
        for (int i = k; i < s.length(); i++) {
            if (checkVovel(s.charAt(i))) currentCount++;
            if (checkVovel(s.charAt(i - k))) currentCount--;
            if (currentCount > count) count = currentCount;
        }
        return count;
    }

    static boolean checkVovel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') return true;
        return false;
    }

    //    You are given the heads of two sorted linked lists list1 and list2.
//    Merge the two lists in a one sorted list.
//    The list should be made by splicing together the nodes of the first two lists.
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode merged = new ListNode(0);
        ListNode mergedNext = new ListNode(0, merged);
        boolean lemoned1 = list1 == null;
        boolean lemoned2 = list2 == null;
        while ((list1 != null || list2 != null) && !lemoned1 && !lemoned2) {
            if (list1.val < list2.val) {
                merged.next = new ListNode(list1.val);
                if (list1.next != null) list1 = list1.next;
                else lemoned1 = true;
            } else {
                merged.next = new ListNode(list2.val);
                if (list2.next != null) list2 = list2.next;
                else lemoned2 = true;
            }
            merged = merged.next;
        }
        if (lemoned1) {
            while (list2 != null) {
                merged.next = new ListNode(list2.val);
                list2 = list2.next;
                merged = merged.next;
            }
        }
        if (lemoned2) {
            while (list1 != null) {
                merged.next = new ListNode(list1.val);
                list1 = list1.next;
                merged = merged.next;
            }
        }
        return mergedNext.next.next;
    }

    // RGB To Hex Conversion
    public static String rgb(int r, int g, int b) {
        StringBuilder sb = new StringBuilder();
        toHex(sb, r);
        toHex(sb, g);
        toHex(sb, b);
        return sb.toString().toUpperCase();
    }

    public static StringBuilder toHex(StringBuilder sb, int c) {
        if (c < 0) return sb.append("00");
        else if (c > 255) return sb.append("FF");
        sb.append(Integer.toHexString(c / 16));
        sb.append(Integer.toHexString(c % 16));
        return sb;
    }

    // In DNA strings, symbols "A" and "T" are complements of each other, as "C" and "G".
    // Your function receives one side of the DNA; you need to return the other complementary side.
    // DNA strand is never empty or there is no DNA at all.
    public static String makeComplement(String dna) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dna.length(); i++) {
            sb.append(findComplementor(dna.charAt(i)));
        }

        return sb.toString();
    }

    public static char findComplementor(char c) {
        if (c == 'A') return 'T';
        else if (c == 'T') return 'A';
        else if (c == 'C') return 'G';
        else return 'C';
    }

    public static int[] plusOne(int[] digits) {
        ArrayList<Integer> sum = new ArrayList<>();
        int rest = 1;

        for (int i = digits.length - 1; i >= 0; i--) {
            int curSum = digits[i] + rest;
            sum.add(0, curSum % 10);
            rest = curSum < 10 ? 0 : curSum / 10;
        }

        if (rest != 0) sum.add(0, rest % 10);
        return sum.stream().mapToInt(i -> i).toArray();
    }

    public static int thirdMax(int[] nums) {
        List<Integer> distArray = Arrays.stream(nums).boxed().distinct().sorted(Comparator.reverseOrder()).toList();
        if (distArray.size() < 3) return Collections.max(distArray);
        else return distArray.get(2);
    }

    // Given an integer array nums, move all 0's to the end of it while
    // maintaining the relative order of the non-zero elements.
    public static int[] moveZeroes(int[] nums) {
        List<Integer> noZeros = Arrays.stream(nums).boxed().filter(i -> i != 0).toList();
        int zeroLength = nums.length - noZeros.size();
        ArrayList<Integer> list = new ArrayList<>(noZeros);
        list.addAll(Arrays.stream(new int[zeroLength]).boxed().toList());
        return list.stream().mapToInt(i -> i).toArray();
    }

    // Given an integer array nums, move all 0's to the end of it while
    // maintaining the relative order of the non-zero elements.
    public static void moveZeroes2(int[] nums) {
        List<Integer> noZeros = Arrays.stream(nums).boxed().filter(i -> i != 0).toList();
        int zeroLength = nums.length - noZeros.size();
        ArrayList<Integer> list = new ArrayList<>(noZeros);
        list.addAll(Arrays.stream(new int[zeroLength]).boxed().toList());
        for (int i = 0; i < nums.length; i++) {
            nums[i] = list.get(i);
        }
    }

    public static ListNode swapNodes(ListNode head, int k) {
        ListNode tempNode = head;
        ListNode second = head;
        int counter = 1;
        while (counter < k) {
            tempNode = tempNode.next;
            counter++;
        }

        ListNode first = tempNode;

        while (tempNode.next != null) {
            tempNode = tempNode.next;
            second = second.next;
        }

        int tempVal = first.val;
        first.val = second.val;
        second.val = tempVal;

        return head;
    }

    // You are given two non-empty linked lists representing two non-negative integers.
    // The digits are stored in reverse order, and each of their nodes contains a single digit.
    // Add the two numbers and return the sum as a linked list.
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode finalResult = new ListNode(0, result);
        int remain = 0;
        while (l1 != null && l2 != null) {
            result.next = new ListNode((l1.val + l2.val + remain) % 10);
            remain = (l1.val + l2.val + remain) / 10;
            l1 = l1.next;
            l2 = l2.next;
            result = result.next;
        }

        while (l1 != null) {
            result.next = new ListNode((l1.val + remain) % 10);
            remain = (remain + l1.val) / 10;
            l1 = l1.next;
            result = result.next;
        }
        while (l2 != null) {
            result.next = new ListNode((l2.val + remain) % 10);
            remain = (remain + l2.val) / 10;
            l2 = l2.next;
            result = result.next;
        }
        if (remain != 0) {
            result.next = new ListNode(remain);
        }

        return finalResult.next.next;
    }

    // Given an integer n and two other values, build an array of size n filled with these two values alternating.
    public static String[] alternate(int n, String firstValue, String secondValue) {
        ArrayList<String> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) result.add(firstValue);
            else result.add(secondValue);
        }
        return result.toArray(new String[0]);
    }

    //Given a string s, return the longest palindromic substring in s.
    public static String longestPalindrome(String s) {
        int sLength = s.length();
        if (sLength == 1) return s;
        String result = s.substring(0, 1);
        for (int i = 1; i < sLength; i++) {
            int offset = 1;
            while (i >= offset && i + offset < sLength && s.charAt(i - offset) == s.charAt(i + offset) ) {
                if (result.length() < offset * 2 + 1) result = s.substring(i - offset, i + offset + 1);
                offset++;
            }
        }

        for (int i = 0; i < sLength; i++) {
            int offset = 0;
            while (i >= offset && i + offset + 1 < sLength && s.charAt(i - offset) == s.charAt(i + offset + 1)) {
                if (result.length() < (offset + 1) * 2) result = s.substring(i - offset, i + offset + 2);
                offset++;
            }
        }
        return result;
    }

    // Given a signed 32-bit integer x, return x with its digits reversed.
    // If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.
    public static int reverse(int x) {
        if (x == 0) return 0;
        StringBuilder sb = new StringBuilder(Integer.toString(x));
        String minus = "";
        if (sb.charAt(0) == '-') {
            sb.deleteCharAt(0);
            minus = "-";
        }
        sb.reverse();

        long test = Long.parseLong(minus + sb.toString().replaceAll("^0*", ""));

        if (test > Math.pow(2, 31)-1 || test < -(Math.pow(2, 31))) {
            return 0;
        }
        return (int) test;
    }

    // Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.
    public static int divide(int dividend, int divisor) {
        long longDividend = (long) dividend;
        long longDivisor = (long) divisor;

        if (longDividend <= Integer.MIN_VALUE && (longDivisor == -1)) {
            return Integer.MAX_VALUE;
        }

        if (longDividend <= Integer.MIN_VALUE && (longDivisor == 1)) {
            return Integer.MIN_VALUE;
        }

        if (longDividend >= Integer.MAX_VALUE && (longDivisor == -1)) {
            return Integer.MIN_VALUE;
        }

        if (longDividend >= Integer.MAX_VALUE && (longDivisor == 1)) {
            return Integer.MAX_VALUE;
        }

        if (longDividend == 0) return 0;

        boolean negative = false;
        if (longDividend < 0) { negative = !negative; longDividend = -longDividend; }
        if (longDivisor < 0) { negative = !negative; longDivisor = -longDivisor; }

        long result = 0;

        while (longDividend - longDivisor >= 0) {
            longDividend = longDividend - longDivisor;
            result++;
        }

        if (negative) result = -result;

        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }


        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE - 1;
        }

        return (int) result;
    }

    // Write a function that returns the sum of two numbers.
    // The input numbers are strings and the function must return a string.
    public static String add(String a, String b) {
        int remain = 0;
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < a.length() && index < b.length()) {
            sb.insert(0, ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index)))) +
                    (Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain) % 10);
            remain = ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index))))
                    + (Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain)  / 10;
            index++;
        }
        while (index < a.length()) {
            sb.insert(0, ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index))))  + remain) % 10);
            remain = ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index))))  + remain) / 10;
            index++;
        }

        while (index < b.length()) {
            sb.insert(0, ((Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index))))  + remain) % 10);
            remain = (((Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index))))  + remain) / 10);
            index++;
        }

        if (remain != 0) {
            sb.insert(0, remain);
        }

        return sb.toString().replaceAll("^0*", "");
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
        // System.out.println(longestCommonPrefix(new String[]{"abab", "aba", ""}));
        // System.out.println(Arrays.toString(snail2(new int[][]{{}})));

        // System.out.println(maxVowels("weallloveyou", 7));
//        mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(3))),
//                new ListNode(2, new ListNode(4, new ListNode(10))));
        // System.out.println(makeComplement("TACGACT"));
        // System.out.println(plusOne(new int[] {9}));
        // System.out.println(thirdMax(new int[]{1, 2, 2, 3}));
//        Arrays.stream(moveZeroes(new int[]{0, 1, 0, 3, 12})).forEach(System.out::print);
//        swapNodes(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(6)))), 2);
//        ListNode jj = addTwoNumbers(new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))),
//                new ListNode(9, new ListNode(9)));
       // System.out.println(longestPalindrome("bb"));
       // System.out.println(reverse(9646324351));
       // System.out.println(divide(2147483647, -1));
        System.out.println(add("1372", "69"));
    }
}