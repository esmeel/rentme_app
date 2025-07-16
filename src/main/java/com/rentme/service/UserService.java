package com.rentme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentme.model.User;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public User getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String userEmail = jwtUtil.extractUsername(token);
        if (userEmail == null) {
            System.err.println("User not found");
            return null;
        }
        return userRepository.findByEmail(userEmail);


    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUserRating(User user, int newRating) {
        // حساب المجموع الجديد للتقييمات
        int totalRatingSum = user.getStars() + newRating;

        // زيادة عدد التقييمات
        int newRatingCount = user.getRatingCount() + 1;

        // حساب المتوسط الجديد
        double newAvg = (double) totalRatingSum / newRatingCount;

        // تحديث القيم في الكائن
        user.setStars(totalRatingSum);
        user.setRatingCount(newRatingCount);
        user.setRatingAvg(newAvg);

        // حفظ في قاعدة البيانات
        userRepository.save(user);
    }

}
