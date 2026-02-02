package com.cts.elearn.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.elearn.domain.event.PasswordResetRequestedEvent;
import com.cts.elearn.domain.event.UserRegisteredEvent;
import com.cts.elearn.dto.ForgotPasswordRequest;
import com.cts.elearn.dto.LoginRequest;
import com.cts.elearn.dto.LoginResponse;
import com.cts.elearn.dto.UserResponse;
import com.cts.elearn.entity.User;
import com.cts.elearn.event.DomainEventPublisher;
import com.cts.elearn.exception.UserNotFoundException;
import com.cts.elearn.repository.UserRepository;
import com.cts.elearn.security.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       DomainEventPublisher eventPublisher,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public User registerUser(User user) {

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        eventPublisher.publish(
                new UserRegisteredEvent(saved.getId(), saved.getEmail())
        );

        return saved;
    }

    // LOGIN
    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));

        // 🔐 Match password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }

        // 🔑 Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),           // subject
                user.getRole()             // role (LEARNER / ADMIN)
        );

        return new LoginResponse(token, "ROLE_" + user.getRole(), user.getStatus().name());
    }

    // GET USER
    public UserResponse getUserById(int id) {
        User user = userRepository.findById((long) id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToResponse(user);
    }

    public List<User> getUsers(int page, int size) {
        return userRepository.findAll();
    }

    // UPDATE
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Integer id) {
        userRepository.deleteById(id.longValue());
    }

    // RESET PASSWORD
    public String resetPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        eventPublisher.publish(
                new PasswordResetRequestedEvent(user.getId(), user.getEmail(), token)
        );

        return "Password reset link sent successfully";
    }

    // MAPPER
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId().intValue(),
                user.getName(),
                user.getContactNumber(),
                user.getEmail()
        );
    }
}


/** 29.01.2026 - this is working code but added security in the above code
@Service
public class UserService {

	private final UserRepository userRepository;
	private final DomainEventPublisher eventPublisher;

	public UserService(UserRepository userRepository, DomainEventPublisher eventPublisher) {
		this.userRepository = userRepository;
		this.eventPublisher = eventPublisher;
	}

	// REGISTER
	public User registerUser(User user) {
		User saved = userRepository.save(user);

		eventPublisher.publish(new UserRegisteredEvent(saved.getId(), saved.getEmail()));

		return saved;
	}

	// LOGIN
	public LoginResponse loginUser(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("Invalid credentials"));

		return new LoginResponse("DUMMY_TOKEN", user.getRole(), user.getStatus().name());
	}

	// GET USER
	public UserResponse getUserById(int id) {
		User user = userRepository.findById((long) id).orElseThrow(() -> new UserNotFoundException("User not found"));

		return mapToResponse(user);
	}

	public List<User> getUsers(int page, int size) {
		return userRepository.findAll();
	}

	// UPDATE
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	// DELETE
	public void deleteUser(Integer id) {
		userRepository.deleteById(id.longValue());
	}

	// RESET PASSWORD
	public String resetPassword(ForgotPasswordRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		userRepository.save(user);

		eventPublisher.publish(new PasswordResetRequestedEvent(user.getId(), user.getEmail(), token));

		return "Password reset link sent successfully";
	}

	// MAPPER
	private UserResponse mapToResponse(User user) {
		return new UserResponse(user.getId().intValue(), user.getName(), user.getContactNumber(), user.getEmail());
	}
}
**/