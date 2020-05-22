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
import pl.kowalczuk.springmvc.domain.exceptions.UserAlreadyExistException;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;
import pl.kowalczuk.springmvc.repository.PrivilegeRepository;
import pl.kowalczuk.springmvc.repository.RoleRepository;
import pl.kowalczuk.springmvc.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Nie istnieje użytkownik z loginem: " + username);
        } else
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), true, true, true,
                    true, getAuthorities(user.getRoles()));
    }

    public User registerNewUserAccount(RegisterForm registerForm) throws UserAlreadyExistException {

        if (emailExist(registerForm.getEmail())) {
            throw new UserAlreadyExistException("Istnieje użytkownik z podanym emailem: " + registerForm.getEmail());
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

    public org.springframework.security.core.userdetails.User userToPrincipal(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
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
}
