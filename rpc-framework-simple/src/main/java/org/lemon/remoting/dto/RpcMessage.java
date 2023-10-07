package org.lemon.remoting.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RpcMessage {
    private byte messageType;

    private byte codec;

    private byte compress;

    private int requestId;

    private Object data;
}
