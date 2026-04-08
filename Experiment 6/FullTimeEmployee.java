public class FullTimeEmployee extends Employee {
    protected String role;
    protected double baseSalary;
    protected double perfBonus;
    protected double hiringCommission;
    protected double medicalAllowance;
    protected double internetAllowance;

    public FullTimeEmployee(int empId, String name, String panNo, String joiningDate,
                            String designation, String department, String email,
                            String role, double baseSalary, double perfBonus,
                            double hiringCommission, double medicalAllowance,
                            double internetAllowance) {
        super(empId, name, panNo, joiningDate, designation, department, email);
        this.role = role;
        this.baseSalary = baseSalary;
        this.perfBonus = perfBonus;
        this.hiringCommission = hiringCommission;
        this.medicalAllowance = medicalAllowance;
        this.internetAllowance = internetAllowance;
    }

    @Override
    public double calcCTC() {
        double ctc;

        if (role.equalsIgnoreCase("SWE")) {
            ctc = baseSalary + perfBonus;
        } else if (role.equalsIgnoreCase("HR")) {
            ctc = baseSalary + hiringCommission;
        } else {
            ctc = baseSalary;
        }

        ctc += medicalAllowance + internetAllowance;
        return ctc;
    }

    public void displaySalaryBreakup() {
        System.out.println("Role          : " + role);
        System.out.println("Base Salary   : " + baseSalary);
        System.out.println("Perf Bonus    : " + perfBonus);
        System.out.println("Hiring Comm.  : " + hiringCommission);
        System.out.println("Medical Allow.: " + medicalAllowance);
        System.out.println("Internet All. : " + internetAllowance);
    }
}