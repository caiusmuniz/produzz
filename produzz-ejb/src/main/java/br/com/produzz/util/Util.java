package br.com.produzz.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class Util {
	
	private Util() {
	}

	public static boolean isNull(Object o) {
		return (o == null);
	}
	
	public static boolean isBlankOrNull(final Integer value) {
		return (isNull(value) || value.intValue() == 0);
	}
	
	public static boolean isBlankOrNull(final Long value) {
		return (isNull(value) || value.longValue() == 0L);
	}
	
	public static boolean isBlankOrNull(final BigDecimal value) {
		return (isNull(value) || value.longValue() == 0L);
	}
	
	public static boolean isBlankOrNull(final String value) {
		return (isNull(value) || value.trim().length() == 0);
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	public static String sha1(String value) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(Constantes.SHA1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = md.digest(value.getBytes());

		return stringHexa(hash);
	}

	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			
			int parteBaixa = bytes[i] & 0xf;
			
			if (parteAlta == 0) {
				s.append('0');
			}
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		
		return s.toString();
	}

	public static String GenerateRandomString(int minLength, int maxLength, int minLCaseCount, 
			int minUCaseCount, int minNumCount, int minSpecialCount) {
	    char[] randomString;

	    String LCaseChars = "abcdefgijkmnopqrstwxyz";
	    String UCaseChars = "ABCDEFGHJKLMNPQRSTWXYZ";
	    String NumericChars = "23456789";
	    String SpecialChars = "*$-+_&=!{}/";

	    Map<String,Integer> charGroupsUsed = new HashMap<String,Integer>();
	    charGroupsUsed.put("lcase", minLCaseCount);
	    charGroupsUsed.put("ucase", minUCaseCount);
	    charGroupsUsed.put("num", minNumCount);
	    charGroupsUsed.put("special", minSpecialCount);

	    // Because we cannot use the default randomizer, which is based on the
	    // current time (it will produce the same "random" number within a
	    // second), we will use a random number generator to seed the
	    // randomizer.

	    // Use a 4-byte array to fill it with random bytes and convert it then
	    // to an integer value.
	    byte[] randomBytes = new byte[4];

	    // Generate 4 random bytes.
	    new Random().nextBytes(randomBytes);

	    // Convert 4 bytes into a 32-bit integer value.
	    int seed = (randomBytes[0] & 0x7f) << 24 |
	                randomBytes[1] << 16 |
	                randomBytes[2] << 8 |
	                randomBytes[3];

	    // Create a randomizer from the seed.
	    Random random = new Random(seed);

	    // Allocate appropriate memory for the password.
	    int randomIndex = -1;
	    if (minLength < maxLength) {
	        randomIndex = random.nextInt((maxLength-minLength)+1)+minLength;
	        randomString = new char[randomIndex];
	    } else {
	        randomString = new char[minLength];
	    }

	    int requiredCharactersLeft = minLCaseCount + minUCaseCount + minNumCount + minSpecialCount;

	    // Build the password.
	    for (int i = 0; i < randomString.length; i++) {
	        String selectableChars = "";

	        // if we still have plenty of characters left to acheive our minimum requirements.
	        if (requiredCharactersLeft < randomString.length - i) {
	            // choose from any group at random
	            selectableChars = LCaseChars + UCaseChars + NumericChars + SpecialChars;
	        } else { // we are out of wiggle room, choose from a random group that still needs to have a minimum required.
	            // choose only from a group that we need to satisfy a minimum for.
	            for(Map.Entry<String, Integer> charGroup : charGroupsUsed.entrySet()) {
	                if ((int)charGroup.getValue() > 0) {
	                    if("lcase".equals(charGroup.getKey()) ){
	                        selectableChars += LCaseChars;
	                    } else if("ucase".equals(charGroup.getKey())){
	                        selectableChars += UCaseChars;
	                    } else if("num".equals(charGroup.getKey())){
	                        selectableChars += NumericChars;
	                    } else if("special".equals(charGroup.getKey())){
	                        selectableChars += SpecialChars;
	                    } 
	                }
	            }
	        }

	        // Now that the string is built, get the next random character.
	        randomIndex = random.nextInt((selectableChars.length())-1);
	        char nextChar = selectableChars.charAt(randomIndex);

	        // Tac it onto our password.
	        randomString[i] = nextChar;

	        // Now figure out where it came from, and decrement the appropriate minimum value.
	        if (LCaseChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("lcase",charGroupsUsed.get("lcase") - 1);
	            if (charGroupsUsed.get("lcase") >= 0) {
	                requiredCharactersLeft--;
	            }
	        } else if (UCaseChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("ucase",charGroupsUsed.get("ucase") - 1);
	            if (charGroupsUsed.get("ucase") >= 0) {
	                requiredCharactersLeft--;
	            }
	        } else if (NumericChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("num", charGroupsUsed.get("num") - 1);
	            if (charGroupsUsed.get("num") >= 0) {
	                requiredCharactersLeft--;
	            }
	        } else if (SpecialChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("special",charGroupsUsed.get("special") - 1);
	            if (charGroupsUsed.get("special") >= 0) {
	                requiredCharactersLeft--;
	            }
	        }
	    }
	    return new String(randomString);
	}
}
