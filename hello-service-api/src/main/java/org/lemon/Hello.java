package org.lemon;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
@Builder
@ToString
public class Hello implements Serializable {
    private String message;
    private String description;
}
