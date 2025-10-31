package io.codeforall.bootcamp.arkanoid;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class ScoreSaver {
    private int score;
    private LinkedList<String[]> savedScores;

    public ScoreSaver(int score) {
        this.score = score;
        savedScores = new LinkedList<>();
    }

    public void getSavedScores(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        for (int i = 0; i < 10; i++) {
            String[] lines = reader.readLine().split("-");
            if (Arrays.stream(lines).noneMatch(null)) {
                savedScores.add(lines);
            } else {
                savedScores.add(new String[]{"","",""});
            }
        }
        reader.close();
    }

    public void updateScores (String systemDate, String description){
        for(int i = 0; i < savedScores.size(); i++){
            if (score > Integer.parseInt(savedScores.get(i)[2])){
                savedScores.add(i, new String[]{systemDate, description, "" + score});
                savedScores.remove(9);
            } else if (score == Integer.parseInt(savedScores.get(i)[2]) && i + 1 < 10){
                savedScores.add(i + 1, new String[]{systemDate, description, "" + score}  );
                savedScores.remove(9);
            }
        }
    }

    public void saveToFile(String filePath) throws IOException {
        PrintWriter writer = new PrintWriter( new FileWriter(filePath));
        for (String[] savedScore : savedScores) {
            writer.write(Arrays.stream(savedScore).reduce("", String::concat));
        }
        writer.flush();
        writer.close();
    }

    public void execute (String filePath, String systemDate, String description) throws IOException {
        getSavedScores(filePath);
        updateScores(systemDate, description);
        saveToFile(filePath);
    }

}
