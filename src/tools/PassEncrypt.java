/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author nikit
 */
public class PassEncrypt {
     public String getEncryptPassword(String password, String salt){
        KeySpec spec = new PBEKeySpec(password.toCharArray(),salt.getBytes(),65536,128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new BigInteger(hash).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PassEncrypt.class.getName()).log(Level.SEVERE, "Нет такого алгоритма", ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PassEncrypt.class.getName()).log(Level.SEVERE, "InvalidKeySpecException", ex);
        }
        return null;
    }
    public String getSalt(){
        return "Это наша секретная фраза для затруднения подбора пароля взломщиками";
    }
}
