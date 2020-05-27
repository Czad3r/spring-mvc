package pl.kowalczuk.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.kowalczuk.springmvc.domain.entities.PasswordResetToken;
import pl.kowalczuk.springmvc.domain.entities.User;
import pl.kowalczuk.springmvc.repository.PasswordResetTokenRepository;

import java.util.Calendar;
import java.util.Date;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.EXPIRED_TOKEN_MESSAGE;
import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.INVALID_TOKEN_MESSAGE;

@Service
public class PasswordService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? INVALID_TOKEN_MESSAGE
                : isTokenExpired(passToken) ? EXPIRED_TOKEN_MESSAGE
                : null;
    }

    public User getUserByPasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        return passToken.getUser();
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, 1);
        myToken.setExpiryDate(c.getTime());

        passwordResetTokenRepository.save(myToken);
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user) {
        String url = contextPath + "/user/changePassword?token=" + token;

        return constructEmail("Resetowanie hasła / Password Recovery - Cytatuje", "Link ważny 24h:" + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
