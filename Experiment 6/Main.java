public class Main {
    public static void main(String[] args) {

        FullTimeEmployee swe = new FullTimeEmployee(
                1001,
                "Aarav Mehta",
                "ABCDE1234F",
                "12-07-2022",
                "Software Engineer",
                "Engineering",
                "aarav@company.com",
                "SWE",
                900000,
                120000,
                0,
                30000,
                18000
        );

        FullTimeEmployee hr = new FullTimeEmployee(
                1002,
                "Riya Sharma",
                "PQRSX5678K",
                "15-03-2021",
                "HR Specialist",
                "Human Resources",
                "riya@company.com",
                "HR",
                650000,
                0,
                90000,
                25000,
                15000
        );

        ContractEmployee consultant = new ContractEmployee(
                2001,
                "Kunal Verma",
                "LMNOP4321Z",
                "01-11-2025",
                "Security Consultant",
                "IT Security",
                "kunal.contract@company.com",
                160,
                1200,
                20000
        );

        Manager manager = new Manager(
                3001,
                "Sneha Iyer",
                "ZXCVB9876P",
                "08-01-2020",
                "Engineering Manager",
                "Engineering",
                "sneha.manager@company.com",
                "SWE",
                1500000,
                250000,
                0,
                50000,
                25000,
                80000,
                40000,
                15000,
                10000
        );

        System.out.println("======== FULL TIME SWE ========");
        swe.displayBasicInfo();
        swe.displaySalaryBreakup();
        System.out.println("Annual CTC    : " + swe.calcCTC());

        System.out.println("\n======== FULL TIME HR ========");
        hr.displayBasicInfo();
        hr.displaySalaryBreakup();
        System.out.println("Annual CTC    : " + hr.calcCTC());

        System.out.println("\n======== CONTRACT EMPLOYEE ========");
        consultant.displayBasicInfo();
        consultant.displayContractDetails();
        System.out.println("Annual/Project CTC : " + consultant.calcCTC());

        System.out.println("\n======== MANAGER ========");
        manager.displayBasicInfo();
        manager.displaySalaryBreakup();
        manager.displayManagerBenefits();
        System.out.println("Annual CTC    : " + manager.calcCTC());
    }
}