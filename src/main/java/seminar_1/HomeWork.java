package seminar_1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class HomeWork {
    public static void main(String[] args) {

        /*
        Домашнее задание указанное на странице после семинара.
        Напишите программу, которая использует Stream API для обработки списка чисел.
        Программа должна вывести на экран среднее значение всех четных чисел в списке.
         */
        List<Integer> listIntegers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println("Среднее арифметическое четных чисел в массиве " +
                listIntegers.stream()
                        .filter(it -> it %2 == 0)
                        .mapToDouble(it -> it)
                        .average()
                        .getAsDouble());
        System.out.println();
        /*
        Домашнее задание указанное в семинаре
         */
        List<Department> departments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Department department = departments.get(randomDepartmentIndex);
            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
            person.setDepartment(department);
            persons.add(person);
        }

        /**
         * Тест методов
         */

        System.out.println("Количество сотрудников старше 22 лет, с зарплатой больше 50 т.р.");
        System.out.println(countPersons(persons, 22, 50000));
        System.out.println();

        System.out.println("Средняя зарплата в департаменте 1");
        System.out.println(averageSalary(persons, 1).getAsDouble());
        System.out.println();

        System.out.println("Группировка работников по департаментам");
        for (Map.Entry<Department, List<Person>> entry : groupByDepartment(persons).entrySet()) {
            System.out.println(entry.getKey().getName() + ": количество сотрудников " + entry.getValue().size());
        }
        System.out.println();

        System.out.println("Максимальная зарплата в каждом департаменте");
        for (Map.Entry<Department, Double> entry : maxSalaryByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }
        System.out.println();

        System.out.println("Список работников по департаментам");
        for (var entry : groupPersonNamesByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }
        System.out.println();

        System.out.println("Сотрудник с минимальной зарплатой в каждом департаменте");
        minSalaryPersons(persons)
                .forEach(p -> System.out.println(p.getDepartment().getName() + ": " + p));
    }

    /**
     * Используя классы Person и Department, реализовать методы ниже:
     */

    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    static int countPersons(List<Person> persons, int x, double d) {
        return (int) persons.stream()
                .filter(it -> it.age > x)
                .filter(it -> it.salary > d)
                .count();
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    static OptionalDouble averageSalary(List<Person> persons, int x) {
        return persons.stream()
                .filter(it -> it.getDepartment().getName().contains("#" + x))
                .mapToDouble(Person::getSalary)
                .average();
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment, Person::getSalary, Math::max));
    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(
                        Collectors.groupingBy(Person::getDepartment,
                                Collectors.mapping(
                                        Person::getName,
                                        Collectors.toList())));
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    static List<Person> minSalaryPersons(List<Person> persons) {

        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment, p -> p,
                        (p1, p2) -> p1.getSalary() <= p2.getSalary() ? p1 : p2))
                .values()
                .stream()
                .toList();
    }

    static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    static class Department {
        private final String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
