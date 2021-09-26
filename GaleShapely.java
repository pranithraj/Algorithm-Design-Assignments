package ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GaleShapely {
 public static void main(String[] args) {
  final GaleShapely galeShapely = new GaleShapely();

  // Taking the inputs
  final Scanner input = new Scanner(System.in);

  System.out.println("Enter the total number of men: ");
  final int numberOfMen = input.nextInt();

  // Part A
  galeShapely.createPreferenceList(numberOfMen);

 }

 private void createPreferenceList(int numberOfMen) {
  final Scanner input = new Scanner(System.in);
  final Map<String, List<String>> menPreferenceList = new HashMap<>();
  final Map<String, List<String>> womenPreferenceList = new HashMap<>();

  // For testing purpose. Delete later.
//  menPreferenceList.put("v", Arrays.asList("b", "a", "d", "e", "c"));
//  menPreferenceList.put("w", Arrays.asList("d", "b", "a", "c", "e"));
//  menPreferenceList.put("x", Arrays.asList("b", "e", "c", "d", "a"));
//  menPreferenceList.put("y", Arrays.asList("a", "d", "c", "b", "e"));
//  menPreferenceList.put("z", Arrays.asList("b", "d", "a", "e", "c"));
//
//  womenPreferenceList.put("a", Arrays.asList("z", "v", "w", "y", "x"));
//  womenPreferenceList.put("b", Arrays.asList("x", "w", "y", "v", "z"));
//  womenPreferenceList.put("c", Arrays.asList("w", "x", "y", "z", "v"));
//  womenPreferenceList.put("d", Arrays.asList("v", "z", "y", "x", "w"));
//  womenPreferenceList.put("e", Arrays.asList("y", "w", "z", "x", "v"));

  System.out.println("Enter men's preference list: ");
  for (int i = 0; i < numberOfMen; i++) {
   System.out.println("Preference list of man #" + (i + 1) + ": ");
   menPreferenceList.put(input.next(), addPreferences(numberOfMen));
  }

  System.out.println("Enter women's preference list: ");
  for (int i = 0; i < numberOfMen; i++) {
   System.out.println("Preference list of woman #" + (i + 1) + ": ");
   womenPreferenceList.put(input.next(), addPreferences(numberOfMen));
  }

  System.out.println(menPreferenceList);
  System.out.println(womenPreferenceList);

  // Part B
  final Map<String, String> match = checkForStableMatch(menPreferenceList, womenPreferenceList);
  System.out.println(match);
 }

 private Map<String, String> checkForStableMatch(Map<String, List<String>> menPreferenceList, Map<String, List<String>> womenPreferenceList) {
  final Map<String, String> stableMatch = new HashMap<>();
  final Set<String> freeMen = new HashSet<>(menPreferenceList.keySet());
  final Set<String> freeWomen = new HashSet<>(womenPreferenceList.keySet());

  while (!freeMen.isEmpty()) {
   final String man = freeMen.stream().findFirst().get();
   final List<String> preferenceList = menPreferenceList.get(man);
   for (String woman : preferenceList) {
    // When the woman is not engaged to any man
    if (freeWomen.contains(woman)) {
     stableMatch.put(man, woman);
     freeMen.remove(man);
     freeWomen.remove(woman);
     break;
    }
    // When the woman is already engaged to any man
    else {
     final String presentMatchOfWoman = stableMatch.entrySet().stream()
             .filter(e -> e.getValue().contains(woman)).map(Map.Entry::getKey)
             .findFirst().get();

     final List<String> presentPreferenceList = womenPreferenceList.get(woman);

     if (presentPreferenceList.indexOf(presentMatchOfWoman) > presentPreferenceList.indexOf(man)) {
      stableMatch.remove(presentMatchOfWoman);
      stableMatch.put(man, woman);
      freeMen.remove(man);
      freeMen.add(presentMatchOfWoman);
      break;
     }
    }
   }
  }
  return stableMatch;
 }

 // Helper method to add preferences for a man/woman
 private List<String> addPreferences(int numberOfMen) {
  final Scanner input = new Scanner(System.in);
  final List<String> preferences = new ArrayList<>();
  for (int i = 0; i < numberOfMen; i++) {
   preferences.add(input.next());
  }
  return preferences;
 }
}
