package ru.leroy.screenerapi.service;

import org.springframework.stereotype.Service;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.message.RateNames;
import ru.leroy.screenerapi.repository.UserRepository;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public Iterable<UserEntity> index() {
        return this.repository.findAll();
    }

    public UserEntity userById(final Long id) throws UserNotFoundException {
        return this.userBy(id);
    }

    public UserEntity authentication(final String email,  final String password)
        throws AuthenticationException, EmailNotFoundException {
        final AtomicReference<UserEntity> atomic = new AtomicReference<>();
        this.repository
            .findByEmail(email)
            .ifPresentOrElse(
                user -> {
                    if (Objects.equals(user.getPassword(), password)) {
                        atomic.set(user);
                    } else {
                        throw new AuthenticationException();
                    }
                },
                () -> {
                    throw new EmailNotFoundException(email);
                }
            );
        return atomic.get();
    }

    public UserEntity registration(final UserEntity user) throws EmailExistException {
        final AtomicReference<UserEntity> ref = new AtomicReference<>();
        user.setRate(RateNames.FREE_RATE);
        this.repository
            .findByEmail(user.getEmail())
            .ifPresentOrElse(
                usr -> {
                    throw new EmailExistException();
                },
                () -> ref.set(this.repository.save(user))
            );
        return ref.get();
    }

    public UserEntity updateUserPasswordById(final Long id, final String pass) throws UserNotFoundException {
        final UserEntity updated = this.userBy(id);
        updated.setPassword(pass);
        return this.repository.save(updated);
    }

    public UserEntity switchUserRateById(final Long id, final String rate) throws UserNotFoundException {
        final UserEntity updated = this.userBy(id);
        updated.setRate(rate);
        return this.repository.save(updated);
    }


    private UserEntity userBy(final Long id) throws UserNotFoundException {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
