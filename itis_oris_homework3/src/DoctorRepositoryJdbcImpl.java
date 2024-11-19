import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorRepositoryJdbcImpl implements DoctorRepository {
    private Connection connection;
    private static final String SQL_SELECT_ALL_FROM_DOCTOR = "SELECT * FROM doctor";

    public DoctorRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> result = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DOCTOR)) {
            while (resultSet.next()) {
                Doctor doctor = new Doctor(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("experience"),
                        resultSet.getInt("age"),
                        resultSet.getString("specialty"),
                        resultSet.getString("phone")
                );
                result.add(doctor);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving all doctors", e);
        }
        return result;
    }

    @Override
    public Optional<Doctor> findById() {
        return Optional.empty();
    }

    public Optional<Doctor> findById(Long id) {
        String sql = "SELECT * FROM doctor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Doctor doctor = new Doctor(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getInt("experience"),
                            resultSet.getInt("age"),
                            resultSet.getString("specialty"),
                            resultSet.getString("phone")
                    );
                    return Optional.of(doctor);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving doctor by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Doctor entity) {
        String sql = "INSERT INTO doctor (name, surname, experience, age, specialty, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getExperience());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setString(5, entity.getSpecialty());
            preparedStatement.setString(6, entity.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error saving doctor", e);
        }
    }

    @Override
    public void update(Doctor entity) {
        String sql = "UPDATE doctor SET name = ?, surname = ?, experience = ?, age = ?, specialty = ?, phone = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getExperience());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setString(5, entity.getSpecialty());
            preparedStatement.setString(6, entity.getPhone());
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error updating doctor", e);
        }
    }

    @Override
    public void remove(Doctor entity) {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM doctor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error deleting doctor", e);
        }
    }

    @Override
    public List<Doctor> findAllByExperience(Long experience) {
        return filterByField("experience", experience);
    }

    @Override
    public List<Doctor> findAllByAge(Integer age) {
        return filterByField("age", age);
    }

    @Override
    public List<Doctor> findAllBySpecialty(String specialty) {
        return filterByField("specialty", specialty);
    }

    @Override
    public List<Doctor> findAllByPhone(String phone) {
        return filterByField("phone", phone);
    }

    private <T> List<Doctor> filterByField(String fieldName, T value) {
        String sql = "SELECT * FROM doctor WHERE " + fieldName + " = ?";
        List<Doctor> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = new Doctor(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getInt("experience"),
                            resultSet.getInt("age"),
                            resultSet.getString("specialty"),
                            resultSet.getString("phone")
                    );
                    result.add(doctor);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error filtering doctors by " + fieldName, e);
        }
        return result;
    }
}
