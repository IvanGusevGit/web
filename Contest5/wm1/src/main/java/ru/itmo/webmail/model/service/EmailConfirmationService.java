package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.EmailConfirmation;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.AttributePair;
import ru.itmo.webmail.model.repository.Repository;
import ru.itmo.webmail.model.repository.impl.RepositoryImpl;

public class EmailConfirmationService {

    private Repository<EmailConfirmation> emailConfirmationRepository = new RepositoryImpl<>(EmailConfirmation.class);

    public void createEmailConfirmation(User user) {
        EmailConfirmation emailConfirmation = new EmailConfirmation();
        emailConfirmation.setUserId(user.getId());
        emailConfirmation.setSecret(user.getLogin() + "privatelink");
        emailConfirmationRepository.save(emailConfirmation);
    }

    public long passEmailConfirmation(String secret) {
        EmailConfirmation emailConfirmation = emailConfirmationRepository.findByAttributes(new AttributePair<>("secret", secret));
        if (emailConfirmation != null) {
            return emailConfirmation.getUserId();
        } else {
            return -1;
        }
    }

}
