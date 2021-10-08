import java.io.Serializable;
import java.util.*;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    public Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    public String name; //Поле не может быть null, Строка не может быть пустой
    public Coordinates coordinates; //Поле не может быть null
    public java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    public int studentsCount; //Значение поля должно быть больше 0
    public FormOfEducation formOfEducation; //Поле не может быть null
    public Semester semesterEnum; //Поле может быть null
    public Person groupAdmin; //Поле не может быть null

    public StudyGroup() {
    }
    public StudyGroup(String name, int x, double y,Date creationDate, int studentsCount, FormOfEducation formOfEducation, Semester semesterEnum, String adminName, Date birthday, long height, Long weight, String passportID) {
//        Date timeForID = new Date();
        this.id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = new Person(adminName, birthday, height, weight, passportID);
    }

    public StudyGroup(String name, int x, double y, int studentsCount, FormOfEducation formOfEducation, Semester semesterEnum, String adminName, Date birthday, long height, Long weight, String passportID) {
//        Date timeForID = new Date();
        this.id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = new Date();
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = new Person(adminName, birthday, height, weight, passportID);
    }
    public StudyGroup(Long id,String name, int x, double y, int studentsCount, FormOfEducation formOfEducation, Semester semesterEnum, String adminName, Date birthday, long height, Long weight, String passportID) {
//        Date timeForID = new Date();
        this.id =id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = new Date();
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = new Person(adminName, birthday, height, weight, passportID);
    }

    public void updateName(Scanner scanner) {
        System.out.println("Enter group name \n");
        try {
            this.name = scanner.nextLine();
            if (name.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Empty string");
            updateName(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void updateStudentsCount(Scanner scanner) {
        try {
            System.out.println("Entry students count");
            this.studentsCount = Integer.parseInt(scanner.nextLine());
            if (studentsCount < 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal count\n");
            updateStudentsCount(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void updateFormOfEducationByScan(Scanner scanner) {
        try {
            System.out.println("Chose form of education DISTANCE_EDUCATION FULL_TIME_EDUCATION EVENING_CLASSES ");
            formOfEducation = FormOfEducation.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect form of education");
            setFormOfEducation(formOfEducation);
        } catch (NullPointerException e) {
            System.exit(0);
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
    }

    public void updateSemesterEnum(Scanner scanner) {
        try {
            System.out.println("Chose semester THIRD FIFTH SIXTH");
            semesterEnum = Semester.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect semester");
            updateSemesterEnum(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }


    @Override
    public String toString() {
        return "StudyGroup\n" +
                "ID: " + id + "\n" +
                "Group name: " + name + "\n" +
                "Creation date: " + creationDate + "\n" +
                "Students count: " + studentsCount + "\n" +
                "Form of education: " + formOfEducation + "\n" +
                "Semester: " + semesterEnum + "\n" +

                "Coordinate X: " + coordinates.getX() + "\n" +
                "Coordinate Y: " + coordinates.getY() + "\n" +

                "Group Admin\n" +
                "Name : " + groupAdmin.getName() + "\n" +
                "Birthday : " + groupAdmin.getBirthday() + "\n" +
                "Height: " + groupAdmin.getHeight() + "\n" +
                "Weight: " + groupAdmin.getWeight() + "\n" +
                "Passport ID " + groupAdmin.getPassportID()
                ;
    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return this.studentsCount - studyGroup.studentsCount;
    }


}

class Coordinates implements Serializable{
    private int x;
    private Double y; //Максимальное значение поля: 110, Поле не может быть null

    public Coordinates() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }

    public void updateX(Scanner scanner) {
        Scanner data = new Scanner(System.in);
        try {
            System.out.println("Enter coordinate X");
            this.x = Integer.parseInt(data.nextLine());
        } catch (Exception e) {
            System.out.println("Incorrect coordinate \n");
            updateX(scanner);
        }
    }

    public void updateY(Scanner scanner) {
        try {
            System.out.println("Enter coordinate Y");
            this.y = Double.parseDouble(scanner.nextLine());
            if (y > 110) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect coordinate\n");
            updateY(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }

    }
}

class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday; //Поле не может быть null
    private long height; //Значение поля должно быть больше 0
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null

    public Person() {

    }

    public Person(String name, Date birthday, long height, Long weight, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;

    }


    public void updateName(Scanner scanner) {

        System.out.println("Enter admin name");
        try {
            this.name = scanner.nextLine();
            if (name.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect name");
            updateName(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void updateBirthday(Scanner scanner) {
        System.out.println("Enter date of admin birthday in format  <<DD MM YYYY>>");
        try {
            String[] date = scanner.nextLine().trim().split(" ", 3);
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            birthday = calendar.getTime();
        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Incorrect date");
            updateBirthday(scanner);
        }
    }

    public void updateHeight(Scanner scanner) {
        System.out.println("Enter admin height");
        try {
            this.height = Long.parseLong(scanner.nextLine());
            if (height < 1) {
                throw new IllegalArgumentException();
            }

        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Illegal height");
            updateHeight(scanner);
        }
    }

    public void updateWeight(Scanner scanner) {
        System.out.println("Enter admin weight");
        try {
            this.weight = Long.parseLong(scanner.nextLine());
            if (this.weight < 1) {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Illegal weight");
            updateWeight(scanner);
        }
    }

    public void updatePassportID(Scanner scanner) {
        System.out.println("Passport ID");
        try {
            this.passportID = scanner.nextLine();
            if (passportID.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Empty string");
            updatePassportID(scanner);
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }


    public Date getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }


}

enum Semester implements Serializable{
    THIRD,
    FIFTH,
    SIXTH
}