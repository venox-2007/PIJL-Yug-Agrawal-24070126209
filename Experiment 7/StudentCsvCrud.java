import java.io.*;
import java.util.*;

public class StudentCsvCrud {
    private static final String FILE_NAME = "Students.csv";
    private static final String HEADER = "studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage";

    static class Student {
        int studentId;
        String name;
        String branch;
        int marks1, marks2, marks3, marks4, marks5;
        double percentage;

        Student(int studentId, String name, String branch, int marks1, int marks2, int marks3, int marks4, int marks5) {
            this.studentId = studentId;
            this.name = name;
            this.branch = branch;
            this.marks1 = marks1;
            this.marks2 = marks2;
            this.marks3 = marks3;
            this.marks4 = marks4;
            this.marks5 = marks5;
            this.percentage = 0.0;
        }

        String toCsv() {
            return studentId + "," + name + "," + branch + "," + marks1 + "," + marks2 + "," + marks3 + "," + marks4 + "," + marks5 + "," + String.format("%.2f", percentage);
        }
    }

    public static void main(String[] args) {
        createInitialFile();
        displayFile("After creating file with initial rows");

        addStudent(new Student(103, "Rohan", "ENTC", 76, 74, 80, 0, 0));
        addStudent(new Student(104, "Sneha", "AIDS", 88, 84, 86, 0, 0));
        addStudent(new Student(105, "Kabir", "MECH", 69, 73, 71, 0, 0));
        displayFile("After adding 3 more rows with marks4 and marks5 as 0");

        updateStudentMarks(103, 76, 74, 80, 79, 83);
        updateStudentMarks(104, 88, 84, 86, 90, 91);
        updateStudentMarks(105, 69, 73, 71, 75, 78);
        updateStudentMarks(101, 78, 82, 85, 88, 90);
        updateStudentMarks(102, 91, 87, 89, 92, 90);
        calculateAndUpdatePercentages();
        displayFile("After updating all rows with correct marks and percentage");

        deleteStudent(104);
        displayFile("After deleting student with ID 104");

        showExceptionCondition();
    }

    static void createInitialFile() {
        List<Student> students = new ArrayList<>();
        Student s1 = new Student(101, "Aarav", "CS", 78, 82, 85, 88, 90);
        Student s2 = new Student(102, "Diya", "IT", 91, 87, 89, 92, 90);
        s1.percentage = calculatePercentage(s1);
        s2.percentage = calculatePercentage(s2);
        students.add(s1);
        students.add(s2);
        writeAllStudents(students);
    }

    static void addStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.newLine();
            bw.write(student.toCsv());
            System.out.println("Added student: " + student.studentId + " - " + student.name);
        } catch (IOException e) {
            System.out.println("IOException while adding student: " + e.getMessage());
        }
    }

    static void updateStudentMarks(int id, int m1, int m2, int m3, int m4, int m5) {
        List<Student> students = readAllStudents();
        for (Student s : students) {
            if (s.studentId == id) {
                s.marks1 = m1;
                s.marks2 = m2;
                s.marks3 = m3;
                s.marks4 = m4;
                s.marks5 = m5;
                System.out.println("Updated marks for student ID: " + id);
                break;
            }
        }
        writeAllStudents(students);
    }

    static double calculatePercentage(Student s) {
        return (s.marks1 + s.marks2 + s.marks3 + s.marks4 + s.marks5) / 5.0;
    }

    static void calculateAndUpdatePercentages() {
        List<Student> students = readAllStudents();
        for (Student s : students) {
            s.percentage = calculatePercentage(s);
        }
        writeAllStudents(students);
        System.out.println("Calculated and updated percentage for all students.");
    }

    static void deleteStudent(int id) {
        List<Student> students = readAllStudents();
        boolean removed = students.removeIf(s -> s.studentId == id);
        writeAllStudents(students);
        if (removed) {
            System.out.println("Deleted student with ID: " + id);
        } else {
            System.out.println("Student ID not found for deletion: " + id);
        }
    }

    static List<Student> readAllStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 9) {
                    Student s = new Student(
                        Integer.parseInt(p[0]), p[1], p[2],
                        Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]),
                        Integer.parseInt(p[6]), Integer.parseInt(p[7])
                    );
                    s.percentage = Double.parseDouble(p[8]);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException while reading file: " + e.getMessage());
        }
        return students;
    }

    static void writeAllStudents(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(HEADER);
            for (Student s : students) {
                bw.newLine();
                bw.write(s.toCsv());
            }
        } catch (IOException e) {
            System.out.println("IOException while writing file: " + e.getMessage());
        }
    }

    static void displayFile(String message) {
        System.out.println("\n--- " + message + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("IOException while displaying file: " + e.getMessage());
        }
    }

    static void showExceptionCondition() {
        System.out.println("\n--- Exception condition demonstration ---");
        try (BufferedReader br = new BufferedReader(new FileReader("MissingStudents.csv"))) {
            while (br.readLine() != null) {}
        } catch (IOException e) {
            System.out.println("Caught IOException successfully: " + e.getMessage());
        }
    }
}
