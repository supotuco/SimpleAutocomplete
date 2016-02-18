/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleautocomplete;

/**
 *
 * @author Diego
 */
public class SimpleAutocomplete {

    /**
     * @param args the command line arguments
     */
    
    java.util.HashSet<String> words = new java.util.HashSet<>();
    
    java.util.ArrayList<String> sortedWords = new java.util.ArrayList<>();
    boolean updateSorted = false;
    
    public SimpleAutocomplete(){
        
    }
    
    public boolean add(String word){
        if( words.add(word) ){
            updateSorted = true;
        }
        return false;
    }
    
    public boolean remove(String word){
        if( words.remove(word) ){
            updateSorted = true;
        }
        return false;
    }
    
    private int wNorm(String w1, String w2){
        int length = Math.min(w1.length(), w2.length());
        int distance = 0;
        for(int i = 0; i < length; i = i + 1){
            distance = distance + (w1.charAt(i) - w2.charAt(i));
        }
        
        return Math.abs(distance);
    }
    
    public java.util.ArrayList<String> closeWords(String prefix){
        java.util.ArrayList<String> suggest = new java.util.ArrayList<>();
        
        if(updateSorted){
            updateSorted = false;
            sortedWords = new java.util.ArrayList<>(words);
            java.util.Collections.<String>sort(sortedWords);
        }
        
        int index = java.util.Collections.<String>binarySearch(sortedWords, prefix);
        
        if(index < 0){
            index = -1 - index;
        }
        
        for(int j = 0; j < 20 && j < sortedWords.size() - index; j = j + 1){
            if( wNorm( sortedWords.get(j + index) , prefix) < 10){
                suggest.add( sortedWords.get(j + index) );
            }else{
                break;
            }
        }
        
        return suggest;
    }
    
    public String toString(){
        if(updateSorted){
            updateSorted = false;
            
            sortedWords = new java.util.ArrayList<>(words);
            java.util.Collections.<String>sort(sortedWords);
        }
        
        return sortedWords.toString();
    }
    
    public static void main(String[] args){
        SimpleAutocomplete test = new SimpleAutocomplete();
        test.add("san fran");
        test.add("fresno");
        test.add("san anton");
        test.add("san die");
        test.add("san bern");
        test.add("los angel");
        test.add("los santo");
        test.add("los cabos");
        test.add("berkely");
        test.add("el dorado");
        test.add("michigan");
        test.add("california");
        test.add("zapo");
        test.add("yellow stone");
        test.add("yosemite");
        test.add("bay area");
        System.out.println(test);
        System.out.println(test.closeWords("l"));
        
    }
}
