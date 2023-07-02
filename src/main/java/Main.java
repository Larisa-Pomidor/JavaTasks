import com.sun.jdi.IntegerValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
            while (i >= offset && i + offset < sLength && s.charAt(i - offset) == s.charAt(i + offset)) {
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

        if (test > Math.pow(2, 31) - 1 || test < -(Math.pow(2, 31))) {
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
        if (longDividend < 0) {
            negative = !negative;
            longDividend = -longDividend;
        }
        if (longDivisor < 0) {
            negative = !negative;
            longDivisor = -longDivisor;
        }

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
            sb.insert(0, ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index)))) + (Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain) % 10);
            remain = ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index)))) + (Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain) / 10;
            index++;
        }
        while (index < a.length()) {
            sb.insert(0, ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index)))) + remain) % 10);
            remain = ((Integer.parseInt(String.valueOf(a.charAt(a.length() - 1 - index)))) + remain) / 10;
            index++;
        }

        while (index < b.length()) {
            sb.insert(0, ((Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain) % 10);
            remain = (((Integer.parseInt(String.valueOf(b.charAt(b.length() - 1 - index)))) + remain) / 10);
            index++;
        }

        if (remain != 0) {
            sb.insert(0, remain);
        }

        return sb.toString().replaceAll("^0*", "");
    }

    public static void exceptionTest() {
        try {
            throw new IllegalStateException("Exception!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
    //
    //You must implement a solution with a linear runtime complexity and use only constant extra space.
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }

    // You have n coins and you want to build a staircase with these coins.
    // The staircase consists of k rows where the ith row has exactly i coins.
    // The last row of the staircase may be incomplete.
    //
    //Given the integer n, return the number of complete rows of the staircase you will build.
    public static int arrangeCoins(int n) {
        int line = 1;
        while (n - line >= 0) {
            n -= line;
            line++;
        }
        return line - 1;
    }

    // Write an algorithm to determine if a number n is happy.
    public static boolean isHappy(int n) {
        String[] array;
        while (n % 10 != n) {
            array = String.valueOf(n).split("");
            n = Arrays.stream(array).map(i -> Integer.parseInt(i) * Integer.parseInt(i)).reduce(0, Integer::sum);
        }
        return n == 1 || n == 7;
    }

    public static int removeElement(int[] nums, int val) {
        int clean = 0;
        int i;
        boolean stop = false;
        for (i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != val) {
                if (stop) break;
                for (int j = clean; j < nums.length; j++) {
                    if (nums[clean] == val) {
                        int temp = nums[clean];
                        nums[clean] = nums[i];
                        nums[i] = temp;
                        break;
                    }
                    clean = j;
                    if (i <= clean) {
                        stop = true;
                        break;
                    }
                }
            }
        }

        return !stop ? 0 : (clean + 1);
    }

    // Given an integer array nums of size n, return the number with the value closest to 0 in nums.
    // If there are multiple answers, return the number with the largest value.
    public static int findClosestNumber(int[] nums) {
        int min = nums[0];
        for (int n : nums) {
            min = Math.abs(min) == Math.abs(n) ? (min > n ? min : n) : (Math.abs(min) > Math.abs(n) ? n : min);
        }

        return min;
    }

    public static boolean strongPasswordCheckerII(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*\\d.*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()\\-+].*")) return false;
        for (int i = 0; i < password.length() - 1; i++)
            if (password.charAt(i) == password.charAt(i + 1)) return false;
        return true;
    }

    // An array is monotonic if it is either monotone increasing or monotone decreasing.
    //Given an integer array nums, return true if the given array is monotonic, or false otherwise.
    public static boolean isMonotonic(int[] nums) {
        if (nums.length <= 1) return true;
        int compared = Integer.compare(nums[0], nums[1]);
        int curCompared;
        for (int i = 1; i < nums.length - 1; i++) {
            curCompared = Integer.compare(nums[i], nums[i + 1]);
            if (curCompared != 0) {
                if (compared != 0) {
                    if (curCompared != compared) return false;
                } else compared = curCompared;
            }

        }
        return true;
    }

    // Given an integer array bills where bills[i] is the bill the ith customer pays,
    // return true if you can provide every customer with the correct change, or false otherwise.
    public static boolean lemonadeChange(int[] bills) {
        List<Integer> change = new LinkedList<>();

        for (int b : bills) {
            if (b == 5) change.add(b);
            if (b == 10) {
                if (!removeElement(change, 5)) return false;
                change.add(10);
            }
            if (b == 20) {
                int indexFirst = change.indexOf(10);
                int indexSecond = change.indexOf(5);
                if (indexFirst != -1 && indexSecond != -1) {
                    removeElement(change, 5);
                    removeElement(change, 10);
                } else {
                    if (!removeElement(change, 5)) return false;
                    if (!removeElement(change, 5)) return false;
                    if (!removeElement(change, 5)) return false;
                }
            }
        }
        return true;
    }

    // Given two strings s and goal,
    // return true if you can swap two letters in s so the result is equal to goal,
    // otherwise, return false.
    public static boolean buddyStrings(String s, String goal) {
        if (s.length() < 2 || s.length() != goal.length()) return false;
        Map<Character, Integer> duplicates = new HashMap();
        ArrayList<Integer> swap = new ArrayList<>(2);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != goal.charAt(i)) swap.add(i);
            else duplicates.put(s.charAt(i), duplicates.get(s.charAt(i)) != null ? duplicates.get(s.charAt(i)) + 1 : 1);
            if (swap.size() > 2) return false;
        }
        if (swap.size() == 0) {
            for (Integer value : duplicates.values()) {
                if (value >= 2) {
                    return true;
                }
            }
        }
        if (swap.size() != 2) return false;
        if (s.charAt(swap.get(0)) != goal.charAt(swap.get(1))) return false;
        if (s.charAt(swap.get(1)) != goal.charAt(swap.get(0))) return false;

        return true;
    }

    public static boolean removeElement(List<Integer> change, int index) {
        index = change.indexOf(index);
        if (index != -1) {
            change.remove(index);
            return true;
        }
        return false;
    }

    public static int countOperations(int num1, int num2) {
        int counter = 0;
        while (num1 != 0 && num2 != 0) {
            if (num1 < num2) num2 -= num1;
            else num1 -= num2;
            counter++;
        }
        return counter;
    }

    // Given the string s, the size of each group k and the character fill,
    // return a string array denoting the composition of every group s has been divided into,
    // using the above procedure.
    public static String[] divideString(String s, int k, char fill) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < s.length() / k; i++) {
            result.add(s.substring(i * k, i * k + k));
        }
        int rest = s.length() % k;
        if (rest != 0) result.add(s.substring(s.length() - rest - 1) + String.valueOf(fill).repeat((k - rest)));
        return result.toArray(new String[0]);
    }

    // Given an array of strings words, return the first palindromic string in the array.
    // If there is no such string, return an empty string "".
    public static String firstPalindrome(String[] words) {
        for (String w : words) {
            if (isPalindrom(w)) return w;
        }
        return "";
    }

    public static boolean isPalindrom(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }
        return true;
    }

    // Given a 0-indexed string word and a character ch,
    // reverse the segment of word that starts at index 0 and ends
    // at the index of the first occurrence of ch (inclusive).
    // If the character ch does not exist in word, do nothing.
    public static String reversePrefix(String word, char ch) {
        if (word.indexOf(ch) == -1) return word;
        return new StringBuilder(word.substring(0, word.indexOf(ch) + 1)).reverse() + word.substring(word.indexOf(ch) + 1);
    }

    // Given three integer arrays nums1, nums2, and nums3, return a distinct array containing all the values that are present
    // in at least two out of the three arrays. You may return the values in any order.
    public static List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> commonMap = new HashMap<>();
        commonElements(new HashSet<>(Arrays.stream(nums1).boxed().toList()), commonMap);
        commonElements(new HashSet<>(Arrays.stream(nums2).boxed().toList()), commonMap);
        commonElements(new HashSet<>(Arrays.stream(nums3).boxed().toList()), commonMap);

        for (Map.Entry<Integer, Integer> commonEl : commonMap.entrySet()) {
            if (commonEl.getValue() >= 2) result.add(commonEl.getKey());
        }
        return result;
    }

    public static Map<Integer, Integer> commonElements(HashSet<Integer> nums, Map<Integer, Integer> commonMap) {
        for (int n : nums) {
            commonMap.put(n, commonMap.get(n) != null ? commonMap.get(n) + 1 : 1);
        }
        return commonMap;
    }

    // Given a positive integer n, return the smallest positive integer that is a multiple of both 2 and n.
    public int smallestEvenMultiple(int n) {
        return n % 2 == 0 ? n : n * 2;
    }

    public static boolean judgeCircle(String moves) {
        int l = 0;
        int r = 0;
        int d = 0;
        int u = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'U') u++;
            else if (moves.charAt(i) == 'D') d--;
            else if (moves.charAt(i) == 'L') l++;
            else if (moves.charAt(i) == 'R') r--;
        }

        return (l + r == 0) && (d + u == 0);
    }

    // Given an integer num, return the number of digits in num that divide num.
    //
    //An integer val divides nums if nums % val == 0.
    public static int countDigits(int num) {
        String[] numsArray = String.valueOf(num).split("");
        int result = 0;
        for (String numA : numsArray) {
            if (num % Integer.parseInt(numA) == 0) result++;
        }
        return result;
    }

    // Given a positive integer num, return the number of positive integers less than or equal to num whose digit sums are even.
    //
    //The digit sum of a positive integer is the sum of all its digits.
    public static int countEven(int num) {
        int result = 0;
        for (int i = 1; i <= num; i++) {
            if (Arrays.stream(String.valueOf(i).split("")).mapToInt(Integer::valueOf).sum() % 2 == 0) {
                result++;
            }
        }
        return result;
    }

    // Return the capitalized title.
    public static String capitalizeTitle(String title) {
        StringBuilder sb = new StringBuilder();
        String[] titleArray = title.split(" ");
        for (String t : titleArray) {
            if (t.length() < 3) sb.append(" ").append(t.toLowerCase());
            else {
                sb.append(" ").append(Character.toUpperCase(t.charAt(0))).append(t.substring(1).toLowerCase());
            }
        }
        return sb.toString().trim();
    }

    // Given a string paragraph and a string array of the banned words banned,
    // return the most frequent word that is not banned.
    // It is guaranteed there is at least one word that is not banned, and that the answer is unique.
    //
    //The words in paragraph are case-insensitive and the answer should be returned in lowercase.
    public static String mostCommonWord(String paragraph, String[] banned) {
        paragraph = paragraph.replaceAll("[!?',;.]", " ");
        paragraph = paragraph.replaceAll("\s+", " ");
        String[] parArray = paragraph.split(" ");
        Map<String, Integer> maxMap = new HashMap<>();
        HashSet<String> unique = new HashSet<>(List.of(banned));
        for (String par : parArray) {
            par = par.toLowerCase();
            if (!unique.contains(par)) {
                if (maxMap.get(par) == null) {
                    maxMap.put(par, 1);
                } else {
                    maxMap.put(par, maxMap.get(par) + 1);
                }
            }
        }

        return maxMap.keySet().stream().max(Comparator.comparing(maxMap::get)).orElse(null);
    }

    // Given an integer n, return true if it is a power of two. Otherwise, return false.
    //
    //An integer n is a power of two, if there exists an integer x such that n == 2x.
    public static boolean isPowerOfTwo(int n) {
        if (n == 0) return false;
        while (n % 2 == 0) {
            n /= 2;
        }

        return n == 1;
    }

    // Given an integer array nums, return true if any value appears at least twice in the array,
    // and return false if every element is distinct.
    public static boolean containsDuplicate(int[] nums) {
        return nums.length == IntStream.of(nums).collect(HashSet::new, HashSet::add, HashSet::addAll).size();
    }

    // You are given an integer array prices representing the prices of various chocolates in a store.
    // You are also given a single integer money, which represents your initial amount of money.
    //
    //You must buy exactly two chocolates in such a way that you still have some non-negative leftover money.
    // You would like to minimize the sum of the prices of the two chocolates you buy.
    public static int buyChoco(int[] prices, int money) {
        int i;
        int j = 0;
        for (i = 0; i < prices.length; i++) {
            for (j = i + 1; j < prices.length; j++) {
                if (prices[i] + prices[j] <= money) return money;
            }
        }
        return money - prices[i] - prices[j];
    }

    // Return the number of passengers who are strictly more than 60 years old.
    public static int countSeniors(String[] details) {
        int result = 0;
        for (String person : details) {
            if (Integer.parseInt(person.substring(11, 13)) > 60) result++;
        }
        return result;
    }

    // You should convert Celsius into Kelvin and Fahrenheit and return it as an array ans = [kelvin, fahrenheit].
    public static double[] convertTemperature(double celsius) {
        return new double[]{celsius + 273.15, celsius * 1.80 + 32.00};
    }

    // Given an integer array nums, return the most frequent even element.
    //
    //If there is a tie, return the smallest one. If there is no such element, return -1.
    public static int mostFrequentEven(int[] nums) {
        if (nums.length == 1) return nums[0] % 2 == 0 ? nums[0] : -1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (num % 2 == 0) map.put(num, map.get(num) == null ? 1 : map.get(num) + 1);
        }

        int min = -1;
        int max = 0;

        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if (pair.getValue() == max && pair.getKey() < min) {
                min = pair.getKey();
                max = pair.getValue();
            } else if (pair.getValue() > max) {
                min = pair.getKey();
                max = pair.getValue();
            }
        }
        return min;
    }

    // You are given an array of strings names, and an array heights that consists of distinct positive integers.
    // Both arrays are of length n.
    //
    //For each index i, names[i] and heights[i] denote the name and height of the ith person.
    //
    //Return names sorted in descending order by the people's heights.
    public static String[] sortPeople(String[] names, int[] heights) {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(heights[i], names[i]);
        }
        Arrays.sort(heights);

        String[] sortedNames = new String[names.length];
        for (int i = sortedNames.length - 1; i >= 0; i--) {
            sortedNames[i] = map.get(heights[sortedNames.length - 1 - i]);
        }
        return sortedNames;
    }

    // A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
    //
    //You are given an array of strings sentences, where each sentences[i] represents a single sentence.
    //
    //Return the maximum number of words that appear in a single sentence.
    public static int mostWordsFound(String[] sentences) {
        return Arrays.stream(sentences).mapToInt(i -> i.split(" ").length).max().orElse(-1);
    }

    // Given a 0-indexed integer array nums, return the smallest index i of nums such that i mod 10 == nums[i],
    // or -1 if such index does not exist.
    public static int smallestEqual(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i % 10 == nums[i]) return i;
        }
        return -1;
    }

    // Given an integer n, add a dot (".") as the thousands separator and return it in string format.
    public static String thousandSeparator(int n) {
        if (n < 1000) return String.valueOf(n);
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            String test = String.valueOf(n % 1000);
            if (test.length() == 1) test = "00" + test;
            else if (test.length() == 0) test = "000";
            else if (test.length() == 2) test = "0" + test;
            sb.insert(0, test + '.');
            n = n / 1000;
        }
        return sb.substring(0, sb.length() - 1).replaceAll("^0*", "");
    }

    // You are given an integer n and an integer start.
    //
    //Define an array nums where nums[i] = start + 2 * i (0-indexed) and n == nums.length.
    //
    //Return the bitwise XOR of all elements of nums.
    public static int xorOperation(int n, int start) {
        int result = 0;

        for (int i = 0; i < n; i++) {
            result ^= start + 2 * i;
        }
        return result;
    }

    // You have to find a permutation of the string where no letter is followed by another letter
    // and no digit is followed by another digit.
    // That is, no two adjacent characters have the same type.
    //
    //Return the reformatted string or return an empty string if it is impossible to reformat the string.
    public static String reformat(String s) {
        if (s.length() == 1) return s;
        Deque<Character> letters = new ArrayDeque<>();
        Deque<Character> digits = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        boolean digit = true;

        for (int i = 0; i < s.length(); i++) {
            if (digit) {
                if (Character.isAlphabetic(s.charAt(i))) {
                    if (!digits.isEmpty()) {
                        sb.append(digits.pop());
                        sb.append(s.charAt(i));
                    } else letters.push(s.charAt(i));
                } else {
                    sb.append(s.charAt(i));
                    digit = false;
                }
            } else {
                if (!Character.isAlphabetic(s.charAt(i))) {
                    if (!letters.isEmpty()) {
                        sb.append(letters.pop());
                        sb.append(s.charAt(i));
                    } else digits.push(s.charAt(i));
                } else {
                    sb.append(s.charAt(i));
                    digit = true;
                }
            }
        }

        while (!letters.isEmpty() || !digits.isEmpty()) {
            if (digit && digits.isEmpty()) {
                if (letters.size() == 1 && !Character.isAlphabetic(sb.charAt(0))) {
                    sb.insert(0, letters.pop());
                    break;
                } else return "";
            }
            if (!digit && letters.isEmpty()) {
                if (digits.size() == 1 && Character.isAlphabetic(sb.charAt(0))) {
                    sb.insert(0, digits.pop());
                    break;
                } else return "";
            }
            sb.append(!digit ? letters.pop() : digits.pop());
            digit = !digit;
        }
        return (!letters.isEmpty() || !digits.isEmpty()) ? "" : sb.toString();
    }

    // Given an array of integers arr, a lucky integer is an integer that has a frequency in the array equal to its value.
    //
    //Return the largest lucky integer in the array. If there is no lucky integer return -1.
    public static int findLucky(int[] arr) {
        Map<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        for (int num : arr) {
            map.put(num, map.get(num) == null ? 1 : map.get(num) + 1);
        }

        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if (pair.getKey() == pair.getValue()) return pair.getKey();
        }

        return -1;
    }

    // Write a program to count the number of days between two dates.
    //
    //The two dates are given as strings, their format is YYYY-MM-DD as shown in the examples.
    public static int daysBetweenDates(String date1, String date2) {
        LocalDate ldt1 = LocalDate.parse(date1);
        LocalDate ldt2 = LocalDate.parse(date2);
        return Math.abs((int) ChronoUnit.DAYS.between(ldt1, ldt2));
    }

    // Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it.
    // That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].
    //
    //Return the answer in an array.
    public static int[] smallerNumbersThanCurrent(int[] nums) {
        int[] result = new int[nums.length];
        int count;
        for (int i = 0; i < nums.length; i++) {
            count = 0;
            if (nums[i] == 0) result[i] = nums.length - i - 1;
            else {
                for (int num : nums) {
                    if (nums[i] > num) count++;
                }
            }
            result[i] = count;
        }
        return result;
    }

    // Given an array arr of integers, check if there exist two indices i and j such that :
    //
    //i != j
    //0 <= i, j < arr.length
    //arr[i] == 2 * arr[j]
    public static boolean checkIfExist(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == 2 * arr[j] || arr[j] == 2 * arr[i]) return true;
            }
        }
        return false;
    }

    // Given an array of strings words,
    // return the words that can be typed using letters of the alphabet on only one row of American keyboard like the image below.
    public static String[] findWords(String[] words) {
        String check;
        boolean flag;
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            flag = true;
            if ("asdfghjkl".contains(words[i].substring(0, 1).toLowerCase())) check = "asdfghjkl";
            else if ("zxcvbnm".contains(words[i].substring(0, 1).toLowerCase())) check = "zxcvbnm";
            else check = "qwertyuiop";

            for (int j = 1; j < words[i].length() - 1; j++) {
                if (!check.contains(words[i].substring(j, j + 1).toLowerCase())) {
                    flag = false;
                    break;
                }
            }
            if (flag) result.add(words[i]);
        }

        return result.toArray(new String[0]);
    }

    // You are given an integer n that consists of exactly 3 digits.
    //
    //We call the number n fascinating if, after the following modification, the resulting number contains all the digits from 1 to 9 exactly once and does not contain any 0's:
    //
    //Concatenate n with the numbers 2 * n and 3 * n.
    public static boolean isFascinating(int n) {
        String str = n + String.valueOf(n * 2) + n * 3;
        if (str.contains("0")) return false;
        if (str.length() != 9) return false;
        return str.contains("1") && str.contains("2") && str.contains("3") && str.contains("4") && str.contains("5") && str.contains("6") && str.contains("7") && str.contains("8") && str.contains("9");
    }

    public static int numJewelsInStones(String jewels, String stones) {
        int result = 0;
        for (int i = 0; i < jewels.length(); i++) {
            for (int j = 0; j < stones.length(); j++) {
                if (jewels.charAt(i) == stones.charAt(j)) result++;
            }
        }

        return result;
    }

    // You have a long flowerbed in which some of the plots are planted, and some are not.
    // However, flowers cannot be planted in adjacent plots.
    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed.length == 1 && flowerbed[0] == 0 && n == 1) return true;
        int empty = 0;
        boolean start = true;
        boolean last = false;

        for (int place = 0; place < flowerbed.length; place++) {
            if (place == flowerbed.length - 1 && flowerbed[place] == 0) {
                last = true;
                empty++;
            }
            if (flowerbed[place] == 0 && !last) empty++;
            else {
                if (start && last) n = n - (empty + 1) / 2;
                else if (start || last) n = n - empty / 2;
                else n = n - Integer.valueOf((empty - 1) / 2);
                if (n <= 0) return true;
                empty = 0;
                start = false;
            }
        }

        return n <= 0;
    }

    // Given an array points where points[i] = [xi, yi] represents a point on the X-Y plane, return true if these points are a boomerang.
    //
    //A boomerang is a set of three points that are all distinct and not in a straight line.
    public static boolean isBoomerang(int[][] points) {
        if (points.length >= 2) {
            if (points[0][0] == points[1][0] && points[0][1] == points[1][1]) return false;
        }

        if (points.length == 2) return true;

        for (int i = 2; i < points.length; i++) {
            if ((Double.valueOf(points[i][0] - points[0][0]) * Double.valueOf(points[1][1] - points[0][1]) == Double.valueOf(points[1][0] - points[0][0]) * Double.valueOf((points[i][1] - points[0][1]))))
                return false;
        }
        return true;
    }

    // Given two strings s and t, return true if t is an anagram of s, and false otherwise.
    //
    //An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
    // typically using all the original letters exactly once.
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        StringBuilder first = new StringBuilder(s);
        StringBuilder second = new StringBuilder(t);
        for (int i = 0; i < first.length(); i++) {
            int index = second.indexOf(String.valueOf(first.charAt(i)));
            if (index != -1) second.deleteCharAt(index);
            else return false;
        }
        return true;
    }

    // Given the root of a binary tree and an integer targetSum,
    // return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.
    //
    //A leaf is a node with no children.
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null && targetSum - root.val == 0) return true;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public static int sumOfFibonacciNumbers(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        int result = 1;
        int last = 1;
        int currentSumm;
        int lastLast = 0;
        for (int i = 2; i < n + 1; i++) {
            currentSumm = last + lastLast;
            lastLast = last;
            last = currentSumm;
            result += currentSumm;
        }
        return result;
    }

    public static int sumOfFibonacciNumbersRecursion(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return sumOfFibonacciNumbersRecursion(n - 1) + sumOfFibonacciNumbersRecursion(n - 2) + 1;
    }

    // Given a positive integer n, find the sum of all integers in the range [1, n] inclusive that are divisible by 3, 5, or 7.
    //
    //Return an integer denoting the sum of all numbers in the given range satisfying the constraint.
    public static int sumOfMultiples(int n) {
        if (n < 3) return 0;
        int sum = 0;
        for (int i = 3; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) sum += i;
        }
        return sum;
    }

    // Return the integer divisors[i] with the maximum divisibility score.
    // If there is more than one integer with the maximum score, return the minimum of them.
    public static int maxDivScore(int[] nums, int[] divisors) {
        ArrayList<Integer> div = new ArrayList<>();
        int max = 0;
        int maxCur;
        for (int divisor : divisors) {
            maxCur = 0;
            for (int num : nums) {
                if (num % divisor == 0) maxCur++;
            }
            if (maxCur > max) {
                max = maxCur;
                div.clear();
                div.add(divisor);
            } else if (maxCur == max) {
                div.add(divisor);
            }
        }
        return Collections.min(div);
    }

    // Return the number of strings in words that are a prefix of s.
    public static int countPrefixes(String[] words, String s) {
        return (int) Arrays.stream(words).filter(s::startsWith).count();
    }

    // Given an integer num, reverse num to get reversed1, then reverse reversed1 to get reversed2.
    // Return true if reversed2 equals num. Otherwise return false.
    public static boolean isSameAfterReversals(int num) {
        if (num == 0) return true;
        return num % 10 != 0;
    }

    // Given a 0-indexed integer array nums of length n and an integer k, return the number of pairs (i, j) where 0 <= i < j < n,
    // such that nums[i] == nums[j] and (i * j) is divisible by k.
    public static int countPairs(int[] nums, int k) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j] && i * j % k == 0) result++;
            }
        }
        return result;
    }

    // Given a string array words, return an array of all characters that show up in all strings within the words (including duplicates).
    // You may return the answer in any order.
    public static List<String> commonChars(String[] words) {
        if (words.length == 1) return Arrays.asList(words[0].split(""));
        ArrayList<Map<Character, Integer>> letters = new ArrayList<>();
        for (String w : words) {
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < w.length(); i++) {
                map.put(w.charAt(i), map.get(w.charAt(i)) == null ? 1 : map.get(w.charAt(i)) + 1);
            }
            letters.add(map);
        }
        List<String> result = new ArrayList<>();
        int k;
        for (Map.Entry<Character, Integer> l : letters.get(0).entrySet()) {
            k = l.getValue();
            for (int j = 1; j < letters.size(); j++) {
                Integer value = letters.get(j).get(l.getKey());
                if (value != null) {
                    if (value < k)
                        k = value;
                } else {
                    k = 0;
                    break;
                }
            }
            for (int m = 0; m < k; m++) {
                result.add(String.valueOf(l.getKey()));
            }
        }
        return result;
    }

    public static double doubles2(int maxk, int maxn) {
        BigDecimal result = BigDecimal.valueOf(0);
        for (int k = 1; k <= maxk; k++) {
            for (int n = 1; n <= maxn; n++) {
                result.add(BigDecimal.valueOf(1.0 / (k * Math.pow(n + 1, 2 * k))));
            }
        }
        return result.doubleValue();
    }

    public static double doubles(int maxk, int maxn) {
        double result = 0;
        for (int k = 1; k <= maxk; k++) {
            result += u(k, maxn);
        }
        return result;
    }

    public static double v(int k, int n) {
        return 1.0 / (k * Math.pow(n + 1, 2 * k));
    }

    public static double u(int k, int N) {
        double result = 0;
        for (int n = 1; n <= N; n++) {
            result += v(k, n);
        }
        return result;
    }

    public static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        return (int) (Math.ceil(Double.valueOf((desiredHeight - upSpeed) / Double.valueOf(upSpeed - downSpeed))) + 1);
    }

    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.get(n) == null ? 1 : map.get(n) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > nums.length / 2) return entry.getKey();
        }
        return 0;
    }

    public static String addBinary(String a, String b) {
        BigInteger aBig = new BigInteger(a, 2);
        BigInteger bBig = new BigInteger(b, 2);
        aBig = aBig.add(bBig);
        return aBig.toString(2);

    }

    public static int lengthOfLastWord(String s) {
        s = s.strip();
        int result = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!String.valueOf(s.charAt(i)).isBlank()) {
                result++;
            } else return result;
        }
        return result;
    }

    public static int searchInsert(int[] nums, int target) {
        int position = Arrays.binarySearch(nums, target);
        return position > 0 ? position : -position - 1;
    }

    public static boolean isPalindrome(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        s = s.toLowerCase();
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }
        return true;
    }

    public static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public static int addDigits(int num) {
        int result = num;

        while (result > 9) {
            result = 0;
            while (num != 0) {
                result += num % 10;
                num /= 10;
            }
            num = result;
        }
        return result;
    }

    public static String reverseVowels(String s) {
        String vowels = s.replaceAll("[^auioeAUIOE]", "");
        StringBuilder sb = new StringBuilder(s);
        int index = vowels.length() - 1;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == 'a' || sb.charAt(i) == 'u' || sb.charAt(i) == 'i' ||
                    sb.charAt(i) == 'o' || sb.charAt(i) == 'e' ||
                    sb.charAt(i) == 'A' || sb.charAt(i) == 'U' || sb.charAt(i) == 'I' ||
                    sb.charAt(i) == 'O' || sb.charAt(i) == 'E') {
                sb.setCharAt(i, vowels.charAt(index));
                index--;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // long res = ipsBetween("10.0.0.0", "10.0.0.50");
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
        // Arrays.stream(moveZeroes(new int[]{0, 1, 0, 3, 12})).forEach(System.out::print);
//        swapNodes(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(6)))), 2);
//        ListNode jj = addTwoNumbers(new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))),
//                new ListNode(9, new ListNode(9)));
        // System.out.println(longestPalindrome("bb"));
        // System.out.println(reverse(9646324351));
        // System.out.println(divide(2147483647, -1));
        //  System.out.println(add("1372", "69"));
        // exceptionTest();
        // System.out.println(singleNumber(new int[] {2,2,1}));
        // System.out.println(arrangeCoins(8));
        // System.out.println(removeElement(new int[] {2}, 1));
        // System.out.println(findClosestNumber(new int[] {-4,-2,1,4,8}));
        // System.out.println(strongPasswordCheckerII("-Aa1a1a1"));
        // System.out.println(isMonotonic(new int[]{1,2,2,3}));
        // System.out.println(lemonadeChange(new int[] {5,5,5,10,5,5,10,20,20,20}));
        //  System.out.println(buddyStrings("aa", "aa"));
        // System.out.println(mostCommonWord("Bob. hIt, baLl", new String[] {"bob", "hit"}));
        // System.out.println(mostFrequentEven(new int[]{0, 1, 2, 2, 4, 4, 1}));
//        System.out.println(thousandSeparator(51040));
//        int a = 10;
//        long b = a;
//        a = (int) b;

        // System.out.println(reformat("covid2019"));
        // findLucky(new int[]{1, 4, 2, 1, 2});
        // checkIfExist(new int[]{7, 1, 14, 11});
        // findWords(new String[] {"Aasdfghjkl","Qwertyuiop","zZxcvbnm"});
        // isFascinating(783);
        // canPlaceFlowers(new int[]{0, 0}, 1);
        // isBoomerang(new int[][]{{0, 1}, {1, 1}, {2, 1}});

//        hasPathSum(new TreeNode(5,
//                        new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
//                        new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1)))),
//                22);
//
//         System.out.println(sumOfFibonacciNumbers(11));
        //System.out.println(sumOfFibonacciNumbersRecursion(7));
        // System.out.println(10 + 33 % 5 * 7 / 3 % 4 + 3 );

        // commonChars(new String[] {"dadaabaa","bdaaabcc","abccddbb","bbaacdba","ababbbab","ccddbbba","bbdabbda","bdabaacb"});
        // System.out.println(doubles(1, 10000));
        // growingPlant(68, 33, 96);
        // lengthOfLastWord("Hello World");

        // isPalindrome("A man, a plan, a canal: Panama");
        addDigits(111);
    }
}