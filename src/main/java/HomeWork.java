import java.sql.*;

public class HomeWork {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {

            // Создать базу данных
            createDB(connection);

            // Записать данные о студентах
            insertDataGroups(connection);

            // Получить и вывести данные о всех студентах в БД
            selectAllStudents(connection);
            System.out.println();

            // Выбрать данные о студентах по имени группы
            selectStudentsByGroup(connection, "'Tester'");

        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void createDB(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table Student(
                    id bigint,
                    firstName varchar(256),
                    secondName varchar(256),
                    groupName varchar(128)
                    )
                    """);
        }
    }

    static void insertDataGroups(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    insert into Student(id, firstName, secondName, groupName) values
                    (1, 'Igor', 'Igorevich', 'Tester'),
                    (2, 'John', 'Jonovich', 'Programmer'),
                    (3, 'Peter', 'Petrovich', 'Tester')
                    """);
        }
    }

    static void selectAllStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    select id, firstName, secondName, groupName
                    from Student
                    """);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                String groupName = resultSet.getString("groupName");
                System.out.println("Студент: " + String.format("%s, %s, %s, %s", id, firstName, secondName, groupName));
            }

        }
    }

    static void selectStudentsByGroup(Connection connection, String groupName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, firstName, secondName, groupName from Student where groupName = " + groupName);

            System.out.println("Студенты группы " + groupName + ":");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                String group_Name = resultSet.getString("groupName");

                System.out.println(String.format("%s, %s, %s, %s", id, firstName, secondName, group_Name));
            }

        }
    }
}
