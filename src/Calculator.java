import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    public static void resultCalc(JTable jtab) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        DefaultTableModel dtm = (DefaultTableModel) jtab.getModel();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            String mat = getMaterialByElementIndex(i, dtm);
            if (!mat.isEmpty()) {
                if (hashMap.containsKey(mat)) {
                    hashMap.put(mat, hashMap.get(mat) + getAreaByElementIndex(i, dtm));
                } else {
                    hashMap.put(mat, getAreaByElementIndex(i, dtm));
                }
            } else {
                int n = findSurfaceByName((String) dtm.getValueAt(i, 4), dtm);
                hashMap.put(getMaterialByElementIndex(n, dtm), hashMap.get(getMaterialByElementIndex(n, dtm)) - getAreaByElementIndex(i, dtm));

            }
        }
        for (Map.Entry<String, Integer> o : hashMap.entrySet()) {
            System.out.println(o.getKey() + " - " + o.getValue());
        }
    }

    public static int findSurfaceByName(String name, DefaultTableModel dtm) {
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (dtm.getValueAt(i, 0).toString().equals(name)) return i;
        }
        return -1;
    }

    public static int getAreaByElementIndex(int a, DefaultTableModel dtm) {
        int width = Integer.parseInt(dtm.getValueAt(a, 1).toString());
        int height = Integer.parseInt(dtm.getValueAt(a, 2).toString());
        return width * height;
    }

    public static String getMaterialByElementIndex(int a, DefaultTableModel dtm) {
        String m = (String) dtm.getValueAt(a, 3);
        if (m != null) {
            return m;
        } else {
            return "";
        }
    }

    // Не используется
    public static void calculteSecondVar(DefaultTableModel paintsDefaultTableModel) {
        ArrayList<Float> arrayList = new ArrayList<>();
        float area = 0;
        for (int j = 0; j < paintsDefaultTableModel.getRowCount(); j++) {
            String s = (String) paintsDefaultTableModel.getValueAt(j, 0);
            float float1 = Integer.parseInt((String) paintsDefaultTableModel.getValueAt(j, 1));
            float float2 = Integer.parseInt((String) paintsDefaultTableModel.getValueAt(j, 2));
            float res = float1 * float2 / 10000;
            if (s.startsWith("Окно") || s.startsWith("Дверь")) {
                area -= res;
            } else {
                area += res;
            }
            arrayList.add(res);
        }
        System.out.println("Area: " + area + " м2");
        System.out.println("ArrayList: " + arrayList);
        System.out.println("ArrayList size: " + arrayList.size());
    }
}
