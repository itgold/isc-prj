package com.iscweb.common.util;

import com.google.common.collect.Lists;
import com.iscweb.common.model.entity.IUser;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.ROLE_DISTRICT_ADMINISTRATOR;
import static com.iscweb.common.security.ApplicationSecurity.ROLE_SYSTEM_ADMINISTRATOR;

/**
 * Utility class for user-related operations.
 *
 * @author skurenkov
 */
@Slf4j
public class UserUtils {

    public static final String DIGIT_PATTERN = ".*\\d+.*";
    public static final String A_Z_LOWER_CASE_PATTERN = ".*[a-z].*";
    public static final String A_Z_CAPITAL_CASE_PATTERN = ".*[A-Z].*";
    public static final String SPECIAL_CHARS_PATTERN = ".*[@#$!%^&+=].*";

    /**
     * Converts a list of roles into list of SimpleGrantedAuthority objects.
     *
     * @param user user for getting authorities for.
     * @return list of user's authorities.
     */
    public static List<GrantedAuthority> grantedAuthoritiesFor(IUser user) {
        return user.getRoles().stream()
                   .map(role -> new SimpleGrantedAuthority(role.getName()))
                   .collect(Collectors.toList());
    }

    /**
     * Used to determine whether a principal has an administrator role.
     *
     * @param principal spring's principal object instance.
     * @return true if given user is an admin.
     */
    public static boolean isAdmin(User principal) {
        return principal.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(ROLE_SYSTEM_ADMINISTRATOR)
                    || auth.getAuthority().equalsIgnoreCase(ROLE_DISTRICT_ADMINISTRATOR));
    }

    public static boolean hasAdminRole(IUser user) {
        return UserUtils.hasAnyRole(user, ROLE_SYSTEM_ADMINISTRATOR, ROLE_DISTRICT_ADMINISTRATOR);
    }

    public static boolean hasRole(IUser user, String roleName) {
        boolean result = false;
        if (Objects.nonNull(user)) {
            result = user.getRoles()
                         .stream()
                         .anyMatch(role -> Objects.equals(role.getName(), roleName));
        }

        return result;
    }

    public static boolean hasAnyRole(IUser user, String... roleNames) {
        List<String> roles = Lists.newArrayList(roleNames);
        return user.getRoles()
                   .stream()
                   .anyMatch(role -> roles.contains(role.getName()));
    }

    /**
     * Generates a password with a default length of 24 characters.
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(24);
    }

    /**
     * Generate a random alphanumeric password string. The passed parameters specify the length of
     * the password and must contain both alphabetic and numeric characters.
     *
     * @param complexity generated password length.
     * @return generated password.
     */
    public static String generateRandomPassword(int complexity) {
        return new PasswordGenerator().generatePassword(complexity,
                                                        new CharacterRule(EnglishCharacterData.Alphabetical),
                                                        new CharacterRule(EnglishCharacterData.Digit));
    }

    /**
     * Checks that the provided password matches the Phantasmic password policy.
     *
     * @param password password to validate.
     * @return whether the new password looks OK.
     */
    public static boolean isPasswordValid(String password) {
        boolean result = true;

        if (password.length() < 12) {
            result = false;
        } else {
            byte validityCounter = 0;

            if (password.matches(DIGIT_PATTERN)) {
                validityCounter++;
            }
            if (password.matches(A_Z_LOWER_CASE_PATTERN)) {
                validityCounter++;
            }
            if (password.matches(A_Z_CAPITAL_CASE_PATTERN)) {
                validityCounter++;
            }
            if (password.matches(SPECIAL_CHARS_PATTERN)) {
                validityCounter++;
            }

            if (validityCounter < 3) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Checks if the principal implementation is assignable from IUser interface, then casts to IUser.
     *
     * @param principal injected user principal.
     * @return IUser cast from the given principal (or an error).
     */
    public static IUser getUser(Principal principal) {
        Object user;
        if (principal instanceof Authentication) {
            user = ((Authentication) principal).getPrincipal();
        } else {
            user = principal;
        }

        if (user instanceof IUser) {
            return (IUser) user;
        } else {
            String message = String.format("Principal %s not supported", user);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
