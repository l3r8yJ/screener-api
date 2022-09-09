package ru.leroy.screenerapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.*;
import ru.leroy.screenerapi.message.RateNames;
import ru.leroy.screenerapi.repository.UserRepository;

import java.util.Objects;

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

    public UserEntity authentication(final UserEntity auth)
        throws AuthenticationException, EmailNotFoundException {
        final UserEntity user = this.repository
            .findByEmail(auth.getEmail())
            .orElseThrow(() -> new EmailNotFoundException(auth.getEmail()));
        if (!Objects.equals(user.getPassword(), auth.getPassword())) {
            throw new AuthenticationException();
        }
        return user;
    }

    public UserEntity registration(final UserEntity user) throws EmailExistException {
        user.setRate(RateNames.FREE_RATE);
        this.repository
            .findByEmail(user.getEmail())
            .ifPresent((usr) -> { throw new EmailExistException(user.getEmail()); });
        return this.repository.save(user);
    }

    @Transactional
    public UserEntity updateUserPasswordById(final Long id, final String pass)
        throws UserNotFoundException, SamePasswordException {
        final UserEntity updated = this.userBy(id);
        if (Objects.equals(pass, updated.getPassword())) {
            throw new SamePasswordException();
        }
        updated.setPassword(pass);
        return this.userBy(id);
    }

    @Transactional
    public UserEntity updateRateById(final Long id, final String rate) throws UserNotFoundException {
        final UserEntity updated = this.userBy(id);
        updated.setRate(rate);
        return this.repository.save(updated);
    }


    private UserEntity userBy(final Long id) throws UserNotFoundException {
        return this.repository
            .findById(id)
            .orElseThrow(UserNotFoundException::new);
    }
}
