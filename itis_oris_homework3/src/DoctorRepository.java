import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor> {
    List<Doctor> findAllByExperience(Long experience);

    List<Doctor> findAllByAge(Integer age);

    List<Doctor> findAllBySpecialty(String specialty);

    List<Doctor> findAllByPhone(String phone);
}
