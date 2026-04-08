public class ContractEmployee extends Employee {
    private int noOfHrs;
    private double hourlyRate;
    private double projectIncentive;

    public ContractEmployee(int empId, String name, String panNo, String joiningDate,
                            String designation, String department, String email,
                            int noOfHrs, double hourlyRate, double projectIncentive) {
        super(empId, name, panNo, joiningDate, designation, department, email);
        this.noOfHrs = noOfHrs;
        this.hourlyRate = hourlyRate;
        this.projectIncentive = projectIncentive;
    }

    @Override
    public double calcCTC() {
        return (noOfHrs * hourlyRate) + projectIncentive;
    }

    public void displayContractDetails() {
        System.out.println("Hours Worked  : " + noOfHrs);
        System.out.println("Hourly Rate   : " + hourlyRate);
        System.out.println("Project Incent: " + projectIncentive);
    }
}