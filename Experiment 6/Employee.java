public abstract class Employee {
    protected int empId;
    protected String name;
    protected String panNo;
    protected String joiningDate;
    protected String designation;
    protected String department;
    protected String email;

    public Employee(int empId, String name, String panNo, String joiningDate,
                    String designation, String department, String email) {
        this.empId = empId;
        this.name = name;
        this.panNo = panNo;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.department = department;
        this.email = email;
    }

    public abstract double calcCTC();

    public void displayBasicInfo() {
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + name);
        System.out.println("PAN No        : " + panNo);
        System.out.println("Joining Date  : " + joiningDate);
        System.out.println("Designation   : " + designation);
        System.out.println("Department    : " + department);
        System.out.println("Email         : " + email);
    }
}