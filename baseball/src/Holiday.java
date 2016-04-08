import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Holiday {
    private static final String Map = null;

    public Holiday() {
        List<Integer> inputArray = Arrays.asList(3, 5, 6, 6, 3, 1, 6, 3, 6, 2, 1, 1, 7, 5);
        List<Integer> distinct = inputArray.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinct);
        
        int[] minDays = { -1, -1, Integer.MAX_VALUE };
        
        HashMap<Integer, Pair> map = new HashMap<Integer, Pair>();
        for (int i = 0; i < distinct.size(); i++)
            map.put(distinct.get(i), new Pair(-1, i));
        
        int count = 0;
        int lastL = 0; 
        for (int i = 0; i < inputArray.size(); i++) {
                Pair current = map.get(inputArray.get(i));
                if (current == null)
                        continue;
                count++;
                if (current.R == 0) {
                        current.L = i;
                        continue;
                }
                current.L = map.get(distinct.get(current.R - 1)).L;
                lastL = current.L;
                if (current.R == distinct.size() - 1 && current.L != -1
                                && i - current.L < minDays[2]) {
                    minDays[0] = current.L;
                    minDays[1] = i;
                    minDays[2] = i - current.L;
                }
        }
        System.out.println("[" + minDays[0] + ".." + minDays[1] + "] - " + minDays[2]);
    }
    
    public Holiday(int a) {
        int inputArray[] = new int [] { 3, 5, 6, 6, 3, 1, 6, 3, 6, 2, 1, 1, 7, 5 };
        Map<Integer, Boolean> distinct = Arrays.stream(inputArray)
                .distinct()
                .boxed()
                .collect(Collectors.toMap(v->v, v-> Boolean.TRUE));
        System.out.println(distinct);
        
        Map<Integer, Integer> count = distinct.keySet().stream()
                .collect(Collectors.toMap(v->v, v-> 0));
        int start = 0, end = 0, minRange = Integer.MAX_VALUE, currentIndex = 0, counter = 0;
        
        for(int idx = 0; idx < inputArray.length; idx++) {
            if (count.get(inputArray[idx]) == 0 && distinct.get(inputArray[idx])) {
                counter++;
            }
            count.put(inputArray[idx], count.get(inputArray[idx]) + 1);
            if (counter == distinct.keySet().size()) {
                while(count.get(inputArray[currentIndex]) > 1 || !distinct.get(inputArray[idx])) {
                    count.put(inputArray[currentIndex], count.get(inputArray[currentIndex]) - 1);
                    currentIndex++;
                }
                if (idx - currentIndex < minRange) {
                    start = currentIndex;
                    end = idx;
                    minRange = idx - currentIndex;
                }
            }
        }
        System.out.println(String.format("[%d:%d] - %d", start, end, minRange));
    }

    public static void main(String[] args) {
        new Holiday(1);
    }

    public class Pair {
            private int L, R;

            public Pair(int L, int R) {
                    this.L = L;
                    this.R = R;
            }
    }
}