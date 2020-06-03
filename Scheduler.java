import java.util.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Scheduler
{
    public static final Pattern INT = Pattern.compile("\\d+");
    
    public static void main(String[] args) {
        try{
        BufferedReader inputStream = new BufferedReader(new FileReader("input.data"));
        Scanner input;
        String next;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        String line = inputStream.readLine();

        while (line != null) {
            input = new Scanner(line);
            while (input.hasNext()){
            if (input.hasNext(INT))
            {
                next = input.findInLine(INT);
                numbers.add(new Integer(next));
            }  
            }
            line = inputStream.readLine();
        }

        ArrayList<Integer> arrivalTime = new ArrayList<Integer>();
        ArrayList<Integer> burstTime = new ArrayList<Integer>();
        ArrayList<Integer> priority = new ArrayList<Integer>();
        int totalTime = 0;

        for(int i=3; i < numbers.size() ; i = i + 3){
            arrivalTime.add(numbers.get(i));
        }
        
        for(int i=4; i < numbers.size() ; i = i + 3){
            burstTime.add(numbers.get(i));
            totalTime = totalTime + numbers.get(i);
        }

        for(int i=5; i < numbers.size() ; i = i + 3){
            priority.add(numbers.get(i));
        }

        ArrayList<Integer> active = new ArrayList<Integer>();
        ArrayList<Integer> startTimes = new ArrayList<Integer>();
        int currentSecond = 0;
        int index = 0;
        File file = new File("output.data");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        int temp = 0;

        if (!file.exists()) {
            file.createNewFile();
        }

        startTimes.add(currentSecond);

        for (currentSecond = 0; currentSecond < totalTime + numbers.get(2); currentSecond = currentSecond + numbers.get(2)){
            for(int i=0; i < numbers.get(0); i++){
                if (currentSecond == arrivalTime.get(i) || (arrivalTime.get(i) > currentSecond - numbers.get(2) && arrivalTime.get(i) < currentSecond)){
                    active.add(i);
                    if (priority.get(active.get(0)) > priority.get(i)) {
                        System.out.println(startTimes.get(index) + " " + (currentSecond) + " " + (active.get(0) + 1));
                        bw.write(startTimes.get(index) + " " + (currentSecond) + " " + (active.get(0) + 1));
                        bw.newLine();
                        startTimes.add((currentSecond));
                        index++;
                        temp = active.get(0);
                        active.set(0, i);
                        active.set(active.size()-1, temp);      
                    }
                }
            } 
        if (active.size() > 1){
        for(int i=0; i < active.size(); i++){
            for(int j = 1; j < active.size(); j++){
                if (priority.get(active.get(i)) > priority.get(active.get(j))) {
                    temp = active.get(i);
                    active.set(i, active.get(j));
                    active.set(j, temp);

                }
            }   
        }
        }
        if(active.size() > 0){
        burstTime.set((active.get(0)), (burstTime.get((active.get(0))) - numbers.get(2)));
        if(burstTime.get(active.get(0)) <= 0){
            System.out.println(startTimes.get(index) + " " + (currentSecond + numbers.get(2)) + " " + (active.get(0) + 1));
            bw.write(startTimes.get(index) + " " + (currentSecond + numbers.get(2)) + " " + (active.get(0) + 1));
            bw.newLine();
            startTimes.add((currentSecond + numbers.get(2)));
            index++;
            active.remove(0);
        }
    }
}
    bw.close();
}
catch (IOException e) {
    e.printStackTrace();
}
}
}