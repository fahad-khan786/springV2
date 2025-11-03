package CoreJava.firstweek;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Student {
    private String name;
    private int age;
    private double marks;
    private String department;

    public Student(String name, int age, double marks, String department) {
        this.name = name;
        this.age = age;
        this.marks = marks;
        this.department = department;
    }

    public Student() {

    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getMarks() { return marks; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", marks=" + marks +
                ", department='" + department + '\'' +
                '}';
    }

    public static void main(String[] args) {
//        CoreJava.firstweek.Student st = new Student();
//        List<Student> listOfStudent =  studentDataGenerator(20);
//        listOfStudent.forEach(System.out::println);
//        Student topperStudent = st.getStudentWithHighestMarksByDepartment("IT",listOfStudent);
//        System.out.println("topper student name "+topperStudent.getName() + " "  + " topper student age "+  topperStudent.getAge() + " ");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(2000);
                List<Student> listOfStudent =  studentDataGenerator(20);
                listOfStudent.forEach(System.out::println);
                Student topperStudent = getStudentWithHighestMarksByDepartment("IT",listOfStudent);
                System.out.println("topper student name "+topperStudent.getName() + " "  + " topper student age "+  topperStudent.getAge() + " ");
            }catch (Exception e){
                e.printStackTrace();
            }
            return "all process done";
        });

        System.out.println("main thread is going to run");

        future.join();

        future.thenAccept(res -> System.out.println(res));

        System.out.println("all task has done");

    }

    public static List<Student> studentDataGenerator(int numberOfStudent){
        Random rndm = new Random();
        String[] departments = {"CS","IT","MECH","ECE","CIVIL"};
        List<Student> listOfStudent =  IntStream.range(0,numberOfStudent).
                mapToObj(i -> new Student(
                        "Stu-"+i,18 + rndm.nextInt(5),50 + rndm.nextDouble() * 50,departments[rndm.nextInt(departments.length)]
                )).collect(Collectors.toList());
        return listOfStudent;
    }

    //data processing
    public static Student getStudentWithHighestMarksByDepartment(String departmentName,List<Student> listOfStudent){

        Map<String, List<Student>> groupByDept = listOfStudent.stream()
                .collect(Collectors.groupingBy(s -> s.getDepartment()));

        List<Student> listOfStudentForSpecificDepartment =  groupByDept.get(departmentName);

        System.out.println("||||||||||||||||||||||||||Data for students with specific department||||||||||||||||||||||||||||||||||");

        listOfStudentForSpecificDepartment.stream().forEach(System.out::println);

        System.out.println("||||||||||||||||||||||||||Data for students with specific department||||||||||||||||||||||||||||||||||");


        return listOfStudent.stream().filter(s->s.getDepartment().equalsIgnoreCase(departmentName)).max(Comparator.comparingDouble(Student::getMarks)).get();
    }
}

