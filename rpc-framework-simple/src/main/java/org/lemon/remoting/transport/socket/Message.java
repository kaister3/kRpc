package org.lemon.remoting.transport.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Kaister3
 * @createTime 2023/10/03
 */

@Data
@AllArgsConstructor
public class Message implements Serializable {
    private String message;
}
