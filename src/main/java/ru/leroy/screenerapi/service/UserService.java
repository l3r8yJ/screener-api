package ru.leroy.screenerapi.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.repository.UserRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity userById(final Long id) throws UserNotFoundException {
        return userBy(id);
    }

    public UserEntity registration(final UserEntity user) throws EmailExistException {
        AtomicReference<UserEntity> ref = new AtomicReference<>();
        user.setRate("free");
        this.repository
            .findByEmail(user.getEmail())
            .ifPresentOrElse(
                usr -> {
                    throw new EmailExistException();
                }, () -> ref.set(this.repository.save(user))
            );
        return ref.get();
    }

    public UserEntity updateUserPasswordById(final Long id, final String pass) throws UserNotFoundException {
        UserEntity updated = userBy(id);
        updated.setPassword(pass);
        return repository.save(updated);
    }

    public UserEntity switchUserRateById(final Long id, final String rate) throws UserNotFoundException {
        UserEntity updated = userBy(id);
        updated.setRate(rate);
        return repository.save(updated);
    }


    private UserEntity userBy(Long id) throws UserNotFoundException {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
