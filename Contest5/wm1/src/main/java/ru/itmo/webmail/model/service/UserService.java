package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.AttributePair;
import ru.itmo.webmail.model.repository.Repository;
import ru.itmo.webmail.model.repository.impl.RepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private enum Mode {REGISTRATION, AUTHORIZE}
    private enum HandleType {EMAIL, LOGIN}
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";
    private final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    private Repository<User> userRepository = new RepositoryImpl<>(User.class);


    public User validateRegistration(User user, String password) throws ValidationException {
        validateByLogin(user.getLogin(), Mode.REGISTRATION);
        validateByPassword(password);
        validateByEmail(user.getEmail(), Mode.REGISTRATION);
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, new AttributePair<>("passwordSha", passwordSha));
        return user;
    }


    public User validateEnter(String handle, String password) throws ValidationException {
        HandleType handle_mode = validateByHandle(handle);
        validateByPassword(password);
        String passwordSha = getPasswordSha(password);
        User user;
        if (handle_mode == HandleType.LOGIN) {
            user = userRepository.findByAttributes(new AttributePair<>("login", handle),new AttributePair<>("passwordSha", passwordSha));
        } else {
            user = userRepository.findByAttributes(new AttributePair<>("email", handle),new AttributePair<>("passwordSha", passwordSha));
        }
        if (user == null) {
            throw new ValidationException("Invalid login or password");
        }
        validateConfirmation(user);
        return user;
    }


    public void setConfirmed(long id) {
        userRepository.update(id, new AttributePair<>("confirmed", true));
    }


    public User find(long userId) {
        return userRepository.findByAttributes(new AttributePair<>("id", userId));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }



    private HandleType validateByHandle(String handle) throws ValidationException{
        if (!handle.matches(emailRegex)) {
            validateByLogin(handle, Mode.AUTHORIZE);
            return HandleType.LOGIN;
        } else {
            validateByEmail(handle, Mode.AUTHORIZE);
            return HandleType.EMAIL;
        }
    }

    private void validateByLogin(String login, Mode mode) throws ValidationException {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!login.matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (login.length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (mode == Mode.REGISTRATION && userRepository.findByAttributes(new AttributePair<>("login", login)) != null) {
            throw new ValidationException("Login is already in use");
        }
    }

    private void validateByPassword(String password) throws ValidationException {

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

    }

    private void validateByEmail(String email, Mode mode) throws ValidationException {
        if (!email.matches(emailRegex)) {
            throw new ValidationException("Incorrect email address");
        }
        if (mode == Mode.REGISTRATION && userRepository.findByAttributes(new AttributePair<>("email", email)) != null) {
            throw new ValidationException("Email is already in use");
        }
    }

    private void validateConfirmation(User user) throws ValidationException{
        if (!user.getConfirmed()) {
            throw new ValidationException("User hasn't passed email verification");
        }
    }


    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

}
