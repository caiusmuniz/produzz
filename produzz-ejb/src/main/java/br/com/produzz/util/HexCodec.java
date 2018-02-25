package br.com.produzz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**.
 * Classe utilitaria para encodificar uma sequencia de bytes como uma string.
 */
public class HexCodec {
    private static final char[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private HexCodec() { }

    /**.
     * Codifica uma sequencia de bytes como uma string em hexadecimal.
     * @param data A sequencia de bytes a ser codificada.
     * @return Uma string representando a sequ�ncia de bytes informada, em hexadecimal.
     */
    public static String encode(byte[] data) {
        int    length = data.length;
        char[] result = new char[length << 1];

        for (int i = 0; i < length; i++) {
            char high = DIGITS[(0xf0 & data[i]) >>> 4];
            char low  = DIGITS[(0x0f & data[i])];

            result[(i << 1)] = high;
            result[(i << 1) + 1] = low;
        }

        return new String(result);
    }

    /**.
     * Decodifica uma string em hexadecimal como uma sequencia de bytes.
     * @param hex A string representando a sequencia de bytes em hexadecimal.
     * @return A sequencia de bytes representada pela string informada.
     * @throws IllegalArgumentException Se a string informada tiver um  numero impar de caracteres, ou contiver um caractere fora da faixa de 0-f.
     */
    public static byte[] decode(String hex) {
        if ((hex.length() & 0x01) != 0) {
            throw new IllegalArgumentException("Numero impar de caracteres");
        }

        byte[] result = new byte[hex.length() >> 1];

        for (int i = 0; i < hex.length(); i = i + 2) {
            int high = digit(hex.charAt(i)) << 4;
            int low  = digit(hex.charAt(i + 1));

            result[i >> 1] = (byte) (high | low);
        }

        return result;
    }

    private static int digit(char ch) {
        int digit = Character.digit(ch, 16);

        if (digit == -1) {
            throw new IllegalArgumentException("Caractere hexadecimal ilegal: " + ch);
        }

        return digit;
    }

	/**.
	 * Metodo que recebe um texto codificado e retorna se possivel o objeto que eh representado neste texto.
     * @param texto
     * @return
     */
	public static Object decodeToObject(String texto) {
		try {
			if (texto != null && texto.trim().length() > 0) {
				byte[] buffer = decode(texto);
				ByteArrayInputStream input = new ByteArrayInputStream(buffer);
				ObjectInputStream objIn = new ObjectInputStream(input);
				return objIn.readObject();
			}

			return null;

		} catch (final Exception ignored) {
			ignored.printStackTrace();
			return null;
		}
	}

	/**.
	 * Codifica um objeto serializavel em um texto.
	 * @param objeto
	 * @return
	 */
	public static String encode(Object objeto) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(objeto);

			return encode(output.toByteArray());

		} catch (final IOException ignored) {
			ignored.printStackTrace();
			return null;
		}
	}
}
