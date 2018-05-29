package com.nhnent.tdd.service;

import com.nhnent.tdd.entity.User;
import com.nhnent.tdd.exception.DuplicatedUserException;
import com.nhnent.tdd.exception.NonMatchingPasswordConfirmException;
import com.nhnent.tdd.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author hanjin lee
 */
public class UserServiceTest {

    public static final String PASSWORD_CONCFIRM = "passwordConcfirm";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    private UserService userService;
    private UserRepository mockUserRepository;

    @Before
    public void setUp() throws Exception {
        mockUserRepository = mock(UserRepository.class);
        userService = new UserService();
        userService.setUserRepository(mockUserRepository);
    }

    @Test
    public void givenIllegalArgument_thenThrowIllegalArgEx() {
        assertIllegalArgEx(null, PASSWORD, PASSWORD_CONCFIRM);
        assertIllegalArgEx("", PASSWORD, PASSWORD_CONCFIRM);
        assertIllegalArgEx(USER_ID, null, PASSWORD_CONCFIRM);
        assertIllegalArgEx(USER_ID, "", PASSWORD_CONCFIRM);
        assertIllegalArgEx(USER_ID, PASSWORD, null);
        assertIllegalArgEx(USER_ID, PASSWORD, "");
    }

    @Test
    public void whenNotMatchPasswordConfirm_thenthrowNonMatchingPasswordConfirmEx() {
        assertExceptionThrown(USER_ID, PASSWORD, PASSWORD_CONCFIRM, NonMatchingPasswordConfirmException.class);
    }

    @Test
    public void whenExistingDuplicatedId_thenThrowDuplicateUserEx() {
        when(mockUserRepository.findById(USER_ID)).thenReturn(Optional.of(new User(USER_ID, PASSWORD)));
        assertExceptionThrown(USER_ID, PASSWORD, PASSWORD, DuplicatedUserException.class);
    }

    @Test
    public void whenValidParameter_thenCreateUser() {
        User user = new User(USER_ID,PASSWORD);
        when(mockUserRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.register(USER_ID, PASSWORD, PASSWORD);
        assertThat(createdUser.getId(), is(user.getId()));
    }

    private void assertIllegalArgEx(String id, String password, String passwordConcfirm) {
        assertExceptionThrown(id, password, passwordConcfirm, IllegalArgumentException.class);
    }

    private void assertExceptionThrown(String id, String password, String passwordConcfirm,
                                       Class<? extends RuntimeException> exception) {
        Exception thrownEx = null;
        try {
            userService.register(id, password, passwordConcfirm);
        } catch (Exception e) {
            thrownEx = e;
        }
        assertThat(thrownEx  , instanceOf(exception));
    }

}
