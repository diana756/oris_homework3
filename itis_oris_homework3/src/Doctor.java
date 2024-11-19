public class Doctor {
    private Long id;
    private String name;
    private String surname;
    private Integer experience;
    private Integer age;
    private String specialty;
    private String phone;

    public Doctor(Long id, String name, String surname, Integer experience, Integer age, String specialty, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.experience = experience;
        this.age = age;
        this.specialty = specialty;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getAge() {
        return age;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhone() {
        return phone;
    }
}
