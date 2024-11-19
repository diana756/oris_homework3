import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class MainRepository {
    private static final String DB_USERNAME = "itis";
    private static final String DB_PASSWORD = "itis";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/itis_oris_homework3";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_USERNAME, DB_PASSWORD,DB_URL);

        DoctorRepository doctorRepository = new DoctorRepositoryJdbcImpl(connection);

        List<Doctor> doctors = doctorRepository.findAll();

        doctors.forEach(doctor -> System.out.println(doctor.getName()));
    }
}
