package com.fsdbackend.Login;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:3000")
public class LoginController {
	private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "FSDAppMassMutual";
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
//        User user = userRepository.findByUsername(loginRequest.getUsername());
//        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
//        	
//            String token = generateToken(user.getUsername(), user.getPassword(), user.getRole());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("token", token);
//            response.put("role", user.getRole());
//
//            return ResponseEntity.ok(response);
//        } else {
//            // Authentication failed
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }
//    public static String generateToken(String username, String password, String role) {
//        String token = username + ":" + password + ":" + role;
//
//        try {
//            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
//            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
//            mac.init(secretKeySpec);
//            byte[] macBytes = mac.doFinal(token.getBytes(StandardCharsets.UTF_8));
//
//            return Base64.getEncoder().encodeToString(macBytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            e.printStackTrace(); // Handle the exception as per your application's requirements
//        }
//
//        return null;
//    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
            User user = userRepository.findByUsername(loginRequest.getUsername());

            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            	
                String token = generateToken(user.getUsername(), user.getPassword(), user.getRole());

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("token", token);
                response.put("role", user.getRole());

                return ResponseEntity.ok(response);
            } else {
                // Authentication failed
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

    public static String generateToken(String username, String password, String role) {
        String token = username + ":" + password + ":" + role;

        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, HMAC_ALGORITHM);

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] macBytes = mac.doFinal(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(macBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace(); // Handle the exception as per your application's requirements
        }

        return null;
    }

    // Other methods and logic
}

