package br.com.produzz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**.
 * Classe utilitaria para lidar com token.
 */
public class TokenUtils {
    private static final int TAMANHO_SENHA = 64;
    private static final String DIGESTER_ALGORITHM = "MD5";

    private TokenUtils() { }

    public static String gerarSenha() {
        int start = 'a';
        int end   = 'z';

        char[] password = new char[TAMANHO_SENHA];

        for (int i = 0; i < TAMANHO_SENHA; i++) {
            password[i] = (char) (Math.floor(Math.random() * (end - start)) + start);
        }

        return new String(password);
    }

    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGESTER_ALGORITHM);
            digest.update(password.getBytes());
            byte[] hash = digest.digest();

            return HexCodec.encode(hash);

        } catch (final NoSuchAlgorithmException e) {
            IllegalStateException ise = new IllegalStateException("Digest " + DIGESTER_ALGORITHM + " nao encontrado.");
            ise.initCause(e);
            throw ise;
        }
    }
}
