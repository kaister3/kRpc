package org.lemon.remoting.dto;

import lombok.*;
import org.lemon.enums.RpcResponseCodeEnum;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionID = 715745410605631233L;

    private String requestId;

    private Integer code;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }
}
