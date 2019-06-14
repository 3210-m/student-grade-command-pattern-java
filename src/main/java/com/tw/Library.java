package com.tw;

import com.tw.entity.Grade;
import com.tw.entity.Student;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class Library {
    private static Map<Integer, Student> studentMap = new HashMap<>();



    public boolean someLibraryMethod() {
        addTestData();
        Scanner sc = new Scanner(System.in);
        String imputMsg;

        String tip = "1. 添加学生\n" + "2. 生成成绩单\n" + "3. 退出请输入你的选择（1～3）：";
        System.out.println(tip);
        int key = sc.nextInt();
        boolean result = false;
        while (key != 3) {
            switch (key) {
                case 1:
                    System.out.println("请输入学生信息（格式：姓名, 学号, 民族, 班级, 学科: 成绩, ...）），按回车提交：");
                    imputMsg = sc.next();
                    try {
                        result = addStudent(imputMsg);
                    } catch (Exception e) {
                        result=false;
                    }
                    while(!result){
                        System.out.println("请按正确的格式输入（格式：姓名, 学号, 民族, 班级, 学科: 成绩, ...））：");
                        imputMsg = sc.next();
                        try {
                            result = addStudent(imputMsg);
                        } catch (Exception e) {
                            result=false;
                        }
                    }
                    break;
                case 2:
                    System.out.println("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
                    imputMsg = sc.next();
                    try {
                        result = printStudentMsg(imputMsg);
                    } catch (Exception e) {
                        result=false;
                    }
                    while (!result){
                        System.out.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
                        imputMsg = sc.next();
                        try {
                            result = printStudentMsg(imputMsg);
                        } catch (Exception e) {
                            result=false;
                        }
                    }
                    break;
            }
            System.out.println(tip);
            key = sc.nextInt();
        }
        return true;
    }


    public boolean addStudent(String s) throws Exception{
        Student student = new Student();
        Grade grade = new Grade();
        Map<String, Double> subject = new HashMap<>();

        String str[] = s.split(",|:");
        student.setName(str[0]);
        student.setNumber(Integer.parseInt(str[1]));
        student.setNation(str[2]);
        student.setKclass(Integer.parseInt(str[3]));


        for (int i = 4; i < str.length - 1; i += 2) {
            subject.put(str[i], Double.parseDouble(str[i + 1]));
        }
        grade.setSubject(subject);

        student.setGrade(grade);
        studentMap.put(student.getNumber(), student);
        System.out.println("学生" + student.getName() + "的成绩被添加");
        return true;
    }

    /**
     * 成绩单
     * 姓名|数学|语文|英语|编程|平均分|格外加分|总分
     * ========================
     * 张三|75|95|80|80|82.5|20|350
     * 李四|85|80|70|90|81.25|10|335
     * 王二|85|80|70|90|81.25|25|350
     * ========================
     * 全班总分平均数：xxx
     * 全班总分中位数：xxx
     **/
    public boolean printStudentMsg(String number) throws Exception{
        String[] numbers = number.split(",");
        String title = "平均分|格外加分|总分\n";
        String decollator = "================================================";
        double countAll = 0;
        double[] countMiddle = new double[numbers.length];
        int n = 0;
        double key;
        //获取全部学科
        List<String> subList = new ArrayList<>();
        for (String s : numbers) {
            if(studentMap.containsKey(Integer.parseInt(s))){
                studentMap.get(Integer.parseInt(s)).getGrade().getSubject().forEach((k,v)->subList.add(k));
            }
        }
        List<String> collect = subList.stream().distinct().collect(Collectors.toList());

        //逐个输出学生信息
        for (int i = 0; i < numbers.length; i++) {
            if (studentMap.containsKey(Integer.parseInt(numbers[i]))) {
                if (n == 0) {
                    System.out.print("成绩单\n姓名|");
                    collect.stream().forEach(x-> System.out.print(x+"|"));
                    System.out.println(title + decollator);
                }
                key = soutGrade(studentMap.get(Integer.parseInt(numbers[i])),collect);
                countAll += key;
                countMiddle[n] = key;
                n++;
            }
        }
        System.out.println(decollator);
        System.out.print("全班总分平均数："+(n>0?String.format("%.2f",countAll / n):0));
        System.out.println("全班总分中位数：" + (n<1?"0":(n % 2 == 0 ? (countMiddle[(n / 2) - 1] + countMiddle[n / 2]) / 2 : countMiddle[n / 2])));
        return true;
    }

    //根据科目输出成绩，并计算每个学生成绩
    public double soutGrade(Student student,List<String> subList) {
        System.out.print(student.getName());
        Map<String, Double> subject = student.getGrade().getSubject();
        double count = 0;
        int n = 0;
        for (int i = 0; i < subList.size(); i++) {
            if (subject.containsKey(subList.get(i))) {
                System.out.print("|" + subject.get(subList.get(i)));
                count += subject.get(subList.get(i));
                n++;
            }else{
                System.out.print("|" + "    ");
            }
        }

        double awardedMarks = 0;
        //计算加分
        if(!student.getNation().equals("汉族")){
            awardedMarks+=10;
        }
        if(student.getGrade().getSubject().containsKey("体育")){
            awardedMarks+=20;
        }
        if(student.getGrade().getSubject().containsKey("艺术")){
            awardedMarks+=15;
        }

        count+=awardedMarks;
        double avg = count / n;
        //输出计算
        System.out.println("|" + String.format("%.2f",avg) +"|"+awardedMarks+ "|" + count);
        return count;
    }

    public void addTestData(){
        Student student1 = new Student();
        student1.setNumber(1);
        student1.setName("test1");
        student1.setNation("汉族");
        student1.setKclass(1);
        Grade grade1 = new Grade();
        Map<String,Double> subject1 = new HashMap<>();
        subject1.put("语文",80.0);
        subject1.put("数学",60.0);
        subject1.put("英语",90.0);
        subject1.put("编程",100.0);
        subject1.put("体育",100.0);
        grade1.setSubject(subject1);
        student1.setGrade(grade1);
        studentMap.put(1,student1);

        Student student2 = new Student();
        student2.setNumber(2);
        student2.setName("test2");
        student2.setNation("苗族");
        student2.setKclass(1);
        Grade grade2 = new Grade();
        Map<String,Double> subject2 = new HashMap<>();
        subject2.put("语文",90.0);
        subject2.put("数学",70.0);
        subject2.put("英语",100.0);
        subject2.put("编程",100.0);
        subject2.put("艺术",100.0);
        grade2.setSubject(subject2);
        student2.setGrade(grade2);
        studentMap.put(2,student2);

        Student student3 = new Student();
        student3.setNumber(3);
        student3.setName("test3");
        student3.setNation("土家族");
        student3.setKclass(2);
        Grade grade3 = new Grade();
        Map<String,Double> subject3 = new HashMap<>();
        subject3.put("语文",60.0);
        subject3.put("数学",60.0);
        subject3.put("英语",60.0);
        subject3.put("编程",100.0);
        grade3.setSubject(subject3);
        student3.setGrade(grade3);
        studentMap.put(3,student3);

    }

    public static void main(String [] args){
        Library classUnderTest = new Library();
        classUnderTest.someLibraryMethod();
    }
}