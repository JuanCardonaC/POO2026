public class Student {

    private static final double MINIMUM_GRADE = 0.0;
    private static final double MAXIMUM_GRADE = 5.0;

    private final String studentId;
    private String name;
    private double firstGrade;
    private double secondGrade;
    private double thirdGrade;

    public Student(String studentId, String name, double firstGrade, double secondGrade, double thirdGrade) {
        this.studentId = studentId;
        this.name = isValidName(name) ? name : "Unnamed";
        this.firstGrade = isValidGrade(firstGrade) ? firstGrade : 0.0;
        this.secondGrade = isValidGrade(secondGrade) ? secondGrade : 0.0;
        this.thirdGrade = isValidGrade(thirdGrade) ? thirdGrade : 0.0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public double getFirstGrade() {
        return firstGrade;
    }

    public double getSecondGrade() {
        return secondGrade;
    }

    public double getThirdGrade() {
        return thirdGrade;
    }

    public double getAverage() {
        return (firstGrade + secondGrade + thirdGrade) / 3;
    }

    public boolean updateName(String newName) {
        if (isValidName(newName)) {
            this.name = newName;
            return true;
        }
        return false;
    }

    public boolean updateFirstGrade(double newGrade) {
        if (isValidGrade(newGrade)) {
            this.firstGrade = newGrade;
            return true;
        }
        return false;
    }

    public boolean updateSecondGrade(double newGrade) {
        if (isValidGrade(newGrade)) {
            this.secondGrade = newGrade;
            return true;
        }
        return false;
    }

    public boolean updateThirdGrade(double newGrade) {
        if (isValidGrade(newGrade)) {
            this.thirdGrade = newGrade;
            return true;
        }
        return false;
    }

    private boolean isValidName(String candidateName) {
        return candidateName != null && !candidateName.trim().isEmpty();
    }

    private boolean isValidGrade(double candidateGrade) {
        return candidateGrade >= MINIMUM_GRADE && candidateGrade <= MAXIMUM_GRADE;
    }
}