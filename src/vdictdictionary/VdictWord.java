/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vdictdictionary;

import htmlprocessor.HTMLProcessor;
import htmlprocessor.PairString;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 *
 * @author ThienDinh
 */
public class VdictWord {

    private String word;
    private String definition;

    /**
     * Construct a word using Vdict dictionary.
     *
     * @param aWord
     */
    public VdictWord(String aWord) {
        word = aWord;
        definition = "null";
        try {
            retrieveWordData();
        } catch (IOException ex) {
            definition = "Cannot retrieve data from Vdict.";
        }
    }

    /**
     * Connect to Vdict.com and pull data.
     */
    public void retrieveWordData() throws IOException {
        LinkedList<String> filterList = new LinkedList<>();
        filterList.add("phanloai");
        filterList.add("class=\"list1\"");
        LinkedList<String> necessaryLines = HTMLProcessor.getFilteredLines("http://vdict.com/" + word + ",1,0,0.html", filterList);
        ListIterator<String> iter = necessaryLines.listIterator();
        // Concatenate all strings
        String data = "";
        while (iter.hasNext()) {
            String line = iter.next();
            if(line.equals("<ul class=\"list1\">")) continue;
            data += line;
        }
        String space = "&nbsp;&nbsp;";
        LinkedList<PairString> modifierList = new LinkedList<>();
        modifierList.add(new PairString("class=\"phanloai\"", "\n<b><i>[ "));
        modifierList.add(new PairString("class=\"idioms\"", "\n<b><i>[ "));
        modifierList.add(new PairString("class=\"list1\"", "\n<b>" + space + "+ "));
        modifierList.add(new PairString("class=\"list2\"", "\n<i>" + space + space + space + "- "));
        modifierList.add(new PairString("class=\"list3\"", "\n<i>" + space + space + space + space + space + "~ "));
        modifierList.add(new PairString("br/", ": "));

        definition = HTMLProcessor.emptyTags(data, modifierList).trim();
        if (definition.equals("<u><b>[")) {
            definition = "Không tìm thấy từ \"" + this.word + "\"";
            return;
        }

        String formatedText = "<body>";
        Scanner in = new Scanner(definition);
        while (in.hasNextLine()) {
            String text = in.nextLine();
            if (text.contains("<b><i>")) {
                formatedText += "<p>" + text + " ]</i></b>";
                continue;
            }
            if (text.contains("<b>")) {
                formatedText += "<br/>" + text + "</b>";
                continue;
            }
            if (text.contains("<i>")) {
                formatedText += "<br/>" + text + "</i>";
                continue;
            }
        }
        definition = formatedText + "</body>";
        definition += "<center><h6>Program written by Thien Dinh<br/>"
                + "Email: tdinhcs@gmail.com<br/>"
                + "This program is used as a small web browser for vdict.com</h6></center>";
    }

    /**
     * Get the word.
     *
     * @return
     */
    public String getWord() {
        return word;
    }

    /**
     * Get the definition of the word.
     *
     * @return
     */
    public String getDefinition() {
        return definition;
    }

}
