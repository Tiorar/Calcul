import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Calcul {

    // Словарь для соответствия римских чисел арабским
    private static final Map<String, Integer> ROMAN_TO_ARABIC = new HashMap<>();

    static {
        ROMAN_TO_ARABIC.put("I", 1);
        ROMAN_TO_ARABIC.put("II", 2);
        ROMAN_TO_ARABIC.put("III", 3);
        ROMAN_TO_ARABIC.put("IV", 4);
        ROMAN_TO_ARABIC.put("V", 5);
        ROMAN_TO_ARABIC.put("VI", 6);
        ROMAN_TO_ARABIC.put("VII", 7);
        ROMAN_TO_ARABIC.put("VIII", 8);
        ROMAN_TO_ARABIC.put("IX", 9);
        ROMAN_TO_ARABIC.put("X", 10);
    }

    // Функция для преобразования арабских чисел в римские
    private static String arabicToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Число не может быть преобразовано в римскую цифру.");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (num > 0) {
            if (num >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                num -= arabicValues[i];
            } else {
                i++;
            }
        }

        return result.toString();
    }

    // Функция для выполнения арифметических операций
    public static String calculate(String input) {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода. Ожидалось: a operator b");
        }

        String aStr = parts[0];
        String operator = parts[1];
        String bStr = parts[2];

        int a, b;

        // Проверка и преобразование чисел к арабскому виду
        try {
            a = Integer.parseInt(aStr);
            b = Integer.parseInt(bStr);

            if (a < 1 || a > 10 || b < 1 || b > 10) {
                throw new IllegalArgumentException("Некорректные числа. Допустимы числа от 1 до 10.");
            }
        } catch (NumberFormatException e) {
            a = ROMAN_TO_ARABIC.getOrDefault(aStr, -1);
            b = ROMAN_TO_ARABIC.getOrDefault(bStr, -1);

            if (a == -1 || b == -1) {
                throw new IllegalArgumentException("Некорректные числа. Допустимы числа от 1 до 10 или их римские аналоги.");
            }
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль недопустимо.");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Некорректный оператор. Поддерживаются: +, -, *, /");
        }

        // Преобразование результата к римскому виду, если входные числа были римскими
        if (ROMAN_TO_ARABIC.containsKey(aStr) || ROMAN_TO_ARABIC.containsKey(bStr)) {
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    public static void main(String[] args) {
        // Ввод выражения с клавиатуры
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение (например, '9 + 9'): ");
        String userInput = scanner.nextLine();

        try {
            String result = calculate(userInput);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}
