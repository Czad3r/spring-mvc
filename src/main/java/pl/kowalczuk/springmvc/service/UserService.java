package pl.kowalczuk.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kowalczuk.springmvc.domain.entities.Privilege;
import pl.kowalczuk.springmvc.domain.entities.Role;
import pl.kowalczuk.springmvc.domain.entities.User;
import pl.kowalczuk.springmvc.domain.exceptions.EmailAlreadyExistException;
import pl.kowalczuk.springmvc.domain.exceptions.UsernameAlreadyExistException;
import pl.kowalczuk.springmvc.domain.forms.EditProfileForm;
import pl.kowalczuk.springmvc.domain.forms.PasswordForm;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;
import pl.kowalczuk.springmvc.repository.PrivilegeRepository;
import pl.kowalczuk.springmvc.repository.RoleRepository;
import pl.kowalczuk.springmvc.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.EMAIL_EXIST_ERROR_MESSAGE;
import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.USERNAME_EXIST_ERROR_MESSAGE;
import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.USERNAME_NOT_EXIST_ERROR_MESSAGE;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextService securityContext;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(USERNAME_NOT_EXIST_ERROR_MESSAGE);
        } else
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), true, true, true,
                    true, getAuthorities(user.getRoles()));
    }

    public User registerNewUserAccount(RegisterForm registerForm) throws EmailAlreadyExistException, UsernameAlreadyExistException {

        if (emailExist(registerForm.getEmail())) {
            throw new EmailAlreadyExistException(EMAIL_EXIST_ERROR_MESSAGE);
        }

        if (usernameExist(registerForm.getUsername())) {
            throw new UsernameAlreadyExistException(USERNAME_EXIST_ERROR_MESSAGE);
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");

        Role userRole = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        User user = new User();
        user.setUsername(registerForm.getUsername());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setCity(registerForm.getCity());
        user.setCountry(registerForm.getCountry());
        user.setGender(registerForm.getGender());
        user.setPhone(registerForm.getPhone());
        user.setPostalCode(registerForm.getPostalCode());
        user.setStreetNo(registerForm.getStreetNo());
        user.setStreetNo2(registerForm.getStreetNo2());
        user.setEmail(registerForm.getEmail());
        user.setStreet(registerForm.getStreet());
        user.setRoles(Arrays.asList(userRole));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }


    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User updateUserAccount(EditProfileForm registerForm) throws EmailAlreadyExistException, UsernameAlreadyExistException, UsernameNotFoundException {
        User currentUser = userRepository.findByUsername(securityContext.getPrincipal().getUsername());

        if ((!currentUser.getEmail().equals(registerForm.getEmail()) && emailExist(registerForm.getEmail()))) {
            throw new EmailAlreadyExistException(EMAIL_EXIST_ERROR_MESSAGE + registerForm.getEmail());
        }

        if ((!currentUser.getUsername().equals(registerForm.getUsername()) && usernameExist(registerForm.getUsername()))) {
            throw new UsernameAlreadyExistException(USERNAME_EXIST_ERROR_MESSAGE + registerForm.getUsername());
        }

        String username = securityContext.getPrincipal().getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(USERNAME_NOT_EXIST_ERROR_MESSAGE);
        } else {
            user.setUsername(registerForm.getUsername());
            user.setCity(registerForm.getCity());
            user.setCountry(registerForm.getCountry());
            user.setGender(registerForm.getGender());
            user.setPhone(registerForm.getPhone());
            user.setPostalCode(registerForm.getPostalCode());
            user.setStreetNo(registerForm.getStreetNo());
            user.setStreetNo2(registerForm.getStreetNo2());
            user.setEmail(registerForm.getEmail());
            user.setStreet(registerForm.getStreet());
            return userRepository.save(user);
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUserPassword(PasswordForm form) {
        String username = securityContext.getPrincipal().getUsername();
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(securityContext.getPrincipal().getUsername());
    }
}
