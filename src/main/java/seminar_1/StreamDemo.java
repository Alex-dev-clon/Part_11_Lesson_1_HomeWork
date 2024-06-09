package seminar_1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class StreamDemo {

    public static void main(String[] args) {
//        List<Integer> ints = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            int randomInt = ThreadLocalRandom.current().nextInt(0, 100);
//            if (ThreadLocalRandom.current().nextBoolean()) {
//                randomInt = -randomInt;
//            }
//            ints.add(randomInt);
//        }
//
//        //Все положительные четные числа умножить на 4 и распечатать их
//        for (Integer x : ints) {
//            if (x > 0 && x % 2 == 0) {
//                System.out.println(x * 4);
//            }
//        }
//        System.out.println();
//        ints.stream()
//                .filter(it -> it > 0)
//                .filter(it -> it % 2 == 0)
//                .map(it -> it * 4)
//                .forEach(it -> System.out.println(it));

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
        System.out.println(findMaxSalary(persons));
    }

    //вывести имена сотрудников старше 40 лет

    static void printPersons (List<Person> persons){
        persons.stream()
                .filter(it -> it.getAge() > 40)
                .map(it -> it.getName())
                .forEach(it -> System.out.println(it));
    }

    //найти сотрудника с самой высокой зарплатой в 5 департаменте
    static Optional<Person> findMaxSalary (List<Person> persons){
        return persons.stream()
                .filter(it -> it.getDepartment().getName().contains("#5"))
                .max(Comparator.comparingDouble(Person::getSalary));
    }

    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSalary() {
            return salary;
        }

        public Department getDepartment() {
            return department;
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

    private static class Department{
        private String name;

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
