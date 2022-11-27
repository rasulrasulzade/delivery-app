package com.company.authms.service;

import com.company.authms.model.Courier;
import com.company.authms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService{
    private final UserRepository userRepository;

    @Override
    public List<Courier> getCouriers() {
        return userRepository.findAll().stream()
                .filter(u -> Objects.nonNull(u.getCourierInfo()))
                .map(u ->
                        Courier.builder()
                                .id(u.getId())
                                .email(u.getEmail())
                                .firstName(u.getFirstName())
                                .lastName(u.getLastName())
                                .status(u.getCourierInfo().getStatus())
                                .build())
                .collect(Collectors.toList());
    }
}
