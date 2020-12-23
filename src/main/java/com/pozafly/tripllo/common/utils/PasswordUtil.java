package com.pozafly.tripllo.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswordUtil {

    public String encryptSHA256(String pw) {
        String sha = "";

        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(pw.getBytes());

            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            sha = sb.toString();

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.warn("Encry Error - NoSuchAlgorithmException");
            sha = null;
        }
        return sha;
    }
}
