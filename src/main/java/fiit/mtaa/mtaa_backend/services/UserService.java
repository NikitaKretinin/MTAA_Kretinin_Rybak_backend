package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.artifacts_data.HibernateUtil;
import fiit.mtaa.mtaa_backend.artifacts_data.UserRole;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getUserByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT a FROM User a WHERE a.login=:login", User.class).setParameter("login", login).getSingleResult();
    }

    public User updateUser(User user) { return userRepository.save(user); }

    public User saveUser(User user) {
        if (UserRole.contains(user.getUser_role())){
            return userRepository.save(user);
        } else {
            return null;
        }
    }
}
