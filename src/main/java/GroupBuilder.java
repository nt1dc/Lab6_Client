import java.util.*;

public class GroupBuilder {
    String name;
    int x;
    double y;
    int studentsCount;
    FormOfEducation formOfEducation;
    Semester semester;
    String adminName;
    Date birthday;
    long height;
    Long weight;
    String passportID;
    Scanner data;

    public GroupBuilder(Scanner scanner) {
        data = scanner;

    }


    public void setFields() {
        try {
            setName();
            setX();
            setY();
            setStudentsCount();
            setFormOfEducation();
            setSemester();
            setAdminName();
            setBirthday();
            setHeight();
            setWeight();
            setPassportID();
            studyGropCreator();
        } catch (NoSuchElementException e) {
            System.out.println("Creation Failed");

        }
    }

    public void setName() {

        System.out.println("Enter group name \n");
        try {
            this.name = data.nextLine();
            if (name.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Empty string");
            setName();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void setX() {
        try {
            System.out.println("Enter coordinate X");
            x = Integer.parseInt(data.nextLine());
        } catch (Exception e) {
            System.out.println("Incorrect coordinate \n");
            setX();
        }
    }

    public void setY() {
        try {
            System.out.println("Enter coordinateY");
            y = Double.parseDouble(data.nextLine());
            if (y > 110) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect coordinate\n");
            setY();
        } catch (NullPointerException e) {
            System.exit(0);
        }

    }

    public void setStudentsCount() {
        try {
            System.out.println("Entry students count");
            studentsCount = Integer.parseInt(data.nextLine());
            if (studentsCount < 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal count\n");
            setStudentsCount();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void setFormOfEducation() {
        try {
            System.out.println("Chose form of education DISTANCE_EDUCATION FULL_TIME_EDUCATION EVENING_CLASSESChose form of education DISTANCE_EDUCATION FULL_TIME_EDUCATION EVENING_CLASSES ");
            formOfEducation = FormOfEducation.valueOf(data.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect form of education");
            setFormOfEducation();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void setSemester() {
        try {
            System.out.println("Chose semester THIRD FIFTH SIXTH");
            semester=Semester.valueOf(data.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect semester");
            setSemester();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void setAdminName() {

        System.out.println("Enter admin name");
        try {
            adminName = data.nextLine();
            if (adminName.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect name");
            setAdminName();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    public void setBirthday() {
        System.out.println("Enter date of admin birthday in format  <<DD MM YYYY>>");
        try {
            String[] date = data.nextLine().trim().split(" ", 3);
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            birthday = calendar.getTime();
        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Incorrect date");
            setBirthday();
        }
    }

    public void setHeight() {
        System.out.println("Enter admin height");
        try {
            height = Long.parseLong(data.nextLine());
            if (height < 1) {
                throw new IllegalArgumentException();
            }

        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Illegal height");
            setHeight();
        }

    }

    public void setWeight() {
        System.out.println("Enter admin weight");
        try {
            weight = Long.parseLong(data.nextLine());
            if (weight < 1) {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Illegal weight");
            setWeight();
        }
    }

    public void setPassportID() {

        System.out.println("Passport ID");
        try {
            passportID = data.nextLine();
            if (passportID.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Empty string");
            setPassportID();
        } catch (NullPointerException e) {
            System.exit(0);
        }
    }

    /**
     * Возвращает объект StudyGroup
     */
    public StudyGroup studyGropCreator() {
        return new StudyGroup(name, x, y, studentsCount, formOfEducation, semester, adminName, birthday, height, weight, passportID);
    }

}