package io.codeforall.bootcamp.arkanoid.inputs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoreSaver {
    private final ArrayList<String[]> savedScores;

    public ScoreSaver() {
        savedScores = new ArrayList<>();
    }

    public ArrayList<String[]> getSavedScores(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        for (int i = 0; i < 10; i++) {
            String line = reader.readLine();
            if (line != null) {
                String[] elements = line.split("-");

                savedScores.add(elements);
            }
        }
        reader.close();
        return savedScores;
    }

    public ArrayList<String[]> updateScores(String systemDate, String description, int score) {
        for (int i = 0; !savedScores.isEmpty() && i < savedScores.size(); i++) {

            int savedScore = Integer.parseInt(savedScores.get(i)[2]);

            if (score >= savedScore || savedScores.get(i) == null) {

                if (savedScores.size() == 10){
                    savedScores.remove(9);
                }

                savedScores.add(i, new String[]{systemDate, description, "" + score});
                return savedScores;
            }
        }

        if (savedScores.isEmpty()) {

            savedScores.addFirst(new String[]{systemDate, description, "" + score});

        }
        return savedScores;
    }

    public void saveToFile(String filePath, ArrayList<String[]> savedScores) throws IOException {

        PrintWriter writer = new PrintWriter(new FileWriter(filePath));

        for (String[] savedScore : savedScores) {

            writer.write(Arrays.stream(savedScore).reduce("", (acc, elem) -> acc + elem + "-"));
            writer.write("\n");

        }
        writer.flush();
        writer.close();
    }
}
