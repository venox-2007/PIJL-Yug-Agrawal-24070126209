public class Person {
    public String name;
    public int age;
    public double height;
    public double weight;
    public char gender;
    public String occupation;
    public String nationality;
    public void walk() {
        System.out.println("I'm walking.");
    }
    public void eat() {
        System.out.println("I'm eating.");
    }
    public void speak(int age) {
        if (age < 2)
            System.out.println("I'm still learning to speak.");
        else
            System.out.println("I'm speaking.");
    }
    public void work(String occupation) {
        if ("Plumber".equals(occupation))
            System.out.println("I fix plumbing issues.");
        else if ("Doctor".equals(occupation))
            System.out.println("I cure people's diseases.");
        else
            System.out.println("I'm unemployed.");
    }
    public void details() {
        System.out.println("My name is " + name);
        System.out.println("My age is " + age);
        System.out.println("My height is " + height);
        System.out.println("My weight is " + weight);
        System.out.println("My gender is " + gender);
        System.out.println("My occupation is " + occupation);
        System.out.println("My nationality is " + nationality);
    }
    public static void main(String[] args) {
        Person p = new Person();
        p.name = "Yug";
        p.gender = 'M';
        p.age = 18;
        p.height = 170;
        p.weight = 66.5;
        p.nationality = "Indian";
        p.occupation = "Doctor";
        p.details();
        p.speak(p.age);
        p.work(p.occupation);
        p.walk();
        p.eat();

        Person p2 = new Person();
        p2.name = "Kashif";
        p2.gender = 'M';
        p2.age = 19;
        p2.height = 170;
        p2.weight = 52.5;
        p2.nationality = "Indian";
        p2.occupation = "Plumber";
        p2.details();
        p2.speak(p2.age);
        p2.work(p2.occupation);
        p2.walk();
        p2.eat();
    }
}
