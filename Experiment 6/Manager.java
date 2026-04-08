public class Manager extends FullTimeEmployee {
    private double ta;
    private double eduAllowance;
    private double giftCardAllowance;
    private double cardRewardPointsValue;

    public Manager(int empId, String name, String panNo, String joiningDate,
                   String designation, String department, String email,
                   String role, double baseSalary, double perfBonus,
                   double hiringCommission, double medicalAllowance,
                   double internetAllowance, double ta, double eduAllowance,
                   double giftCardAllowance, double cardRewardPointsValue) {

        super(empId, name, panNo, joiningDate, designation, department, email,
              role, baseSalary, perfBonus, hiringCommission, medicalAllowance, internetAllowance);

        this.ta = ta;
        this.eduAllowance = eduAllowance;
        this.giftCardAllowance = giftCardAllowance;
        this.cardRewardPointsValue = cardRewardPointsValue;
    }

    @Override
    public double calcCTC() {
        return baseSalary + perfBonus + ta + eduAllowance
                + medicalAllowance + internetAllowance
                + giftCardAllowance + cardRewardPointsValue;
    }

    public void displayManagerBenefits() {
        System.out.println("Travel Allow. : " + ta);
        System.out.println("Edu Allowance : " + eduAllowance);
        System.out.println("Gift Cards    : " + giftCardAllowance);
        System.out.println("Card Points   : " + cardRewardPointsValue);
    }
}