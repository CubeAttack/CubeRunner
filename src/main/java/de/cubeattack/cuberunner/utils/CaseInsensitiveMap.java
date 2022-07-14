package de.cubeattack.cuberunner.utils;

import java.util.HashMap;

public class CaseInsensitiveMap extends HashMap<String, String> {
   private static final long serialVersionUID = -1010024940042453390L;

   public String put(String key, String value) {
      return (String)super.put(key.toLowerCase(), value);
   }

   public String get(String key) {
      return (String)super.get(key.toLowerCase());
   }
}
