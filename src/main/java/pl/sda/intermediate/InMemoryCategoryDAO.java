package pl.sda.intermediate;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// robimy DAO , które jest Singletonem, budujemy Singletona !
public class InMemoryCategoryDAO {

    private static InMemoryCategoryDAO instance;

    private List<Category> categoryList;

    private InMemoryCategoryDAO() {
        categoryList = initializeCategories();
    }

    private List<Category> initializeCategories() {
        // otwieramy i czytamy plik z informacjami - czyli plik skopiowany do folderu RESOURCES
        // getResource zwraca URLa, czyli ścieżkę
        try {
            List<String> strings = Files.readAllLines(Paths.get(
                    this.getClass().getClassLoader().getResource("kategorie.txt").toURI()));
            List<Category> categories = new ArrayList<>();

            int counter = 1;
            for (String line : strings) {
                categories.add(Category.builder()
                        .name(line.trim())
                        .id(counter++)
                        .depth(calculateDepth(line))
                        .build());  // duże S w wyrażeniu regex
            }

            Map<Integer, List<Category>> categoryMap = new HashMap<>();
            for (Category category : categories) {
                if (categoryMap.containsKey(category.getDepth())) {
                    categoryMap.get(category.getDepth()).add(category);
                } else {
                    List<Category> cats = new ArrayList<>();
                    cats.add(category);
                    categoryMap.put(category.getDepth(), cats);
                }
            }
            populateParentID(categoryMap, 0);
            return categories;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void populateParentID(Map<Integer, List<Category>> categoryMap, Integer depth) {
        if (!categoryMap.containsKey(depth)) {
            return;
        }
        List<Category> children = categoryMap.get(depth);
        for (Category child : children) {
            if (depth != 0) {
//                category.setParentId(null);
                List<Category> potentialParents = categoryMap.get(depth - 1);
                int parentID = 0;
                for (Category potentialParent : potentialParents) {
                    if (potentialParent.getId() < child.getId() && parentID < potentialParent.getId()) {
                        parentID = potentialParent.getId();
                    }
                }
                child.setParentId(parentID);
            }
        }
        populateParentID(categoryMap, ++depth);
    }

    private int calculateDepth(String line) {
        return line.split("\\S")[0].length();
    }

    public static InMemoryCategoryDAO getInstance() {
        if (instance == null) {
            synchronized (InMemoryCategoryDAO.class) {   //  tu jest Semafor
                if (instance == null) {
                    instance = new InMemoryCategoryDAO();
                }
            }
        }
        return instance;
    }
}