package it.sysman.chefbook.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferRequestStatusEnum {
    ACTIVE("ACTIVE"),
    USED("USED"),
    DECLINED("DECLINED"),
    REVOKED("REVOKED"),
    EXPIRED("EXPIRED");

    private final String value;
}

