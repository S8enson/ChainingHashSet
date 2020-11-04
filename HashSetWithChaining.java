/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashsetwithchaining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Sam
 */
public class HashSetWithChaining<E> implements Set<E>{

    /**
     * @param args the command line arguments
     */
    
    private HashTable<E> table;
    private int loadFactor;
    private int capacity;
    
    public HashSetWithChaining(){
        loadFactor = 75;
    capacity = 10;
    table = new HashTable(capacity, loadFactor);
    
    
    }
    
    public HashSetWithChaining(int initialCapacity, int loadFactor) {
        if(loadFactor>100 || loadFactor <= 0){
        throw new IllegalArgumentException();
        }
        this.loadFactor = loadFactor;
        table = new HashTable<>(initialCapacity, loadFactor);
        
        capacity = initialCapacity;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        HashSetWithChaining<String> set = new HashSetWithChaining<>(6, 75);
        System.out.println("Creating Set, initial capacity = "+set.capacity);
        System.out.println("Adding Seth, Bob, Adam, Ian");
        set.add("Seth");
        set.add("Bob");
        set.add("Adam");
        set.add("Ian");
        System.out.println(set);
        
        System.out.println("Size is "+set.size());
        
        System.out.println("Adding Jill, Amy, Nat, Seth, Bob, Simon");
        set.add("Jill");
        set.add("Amy");
        set.add("Nat");
        set.add("Seth");
        set.add("Bob");
        set.add("Simon");
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
        System.out.println("Contains Seth? "+set.contains("Seth"));
        System.out.println("Contains Nat? "+set.contains("Nat"));
        System.out.println("Contains Gary? "+set.contains("Gary"));
        
        System.out.println("Iterating!");
        Iterator it = set.iterator();
        
        while (it.hasNext()) {
            System.out.println(it.next());
            
        }
        System.out.println("REMOVING: Seth, Adam, Bob");
        set.remove("Seth");
        set.remove("Adam");
        set.remove("Bob");
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
        //clear
        set.clear();
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
        //add all
        ArrayList<String> addAll = new ArrayList<>();
        addAll.add("Jim");
        addAll.add("Bob");
        addAll.add("Steve");
        addAll.add("Lebron");
        addAll.add("Kobe");
        addAll.add("Rocky");
        System.out.println("Adding all "+addAll+" from array list");
        set.addAll(addAll);
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
        //to array
        System.out.println("Testing toArray function ");
        Object[] arr = set.toArray();
        for (int i = 0; i < arr.length; i++) {
            Object object = arr[i];
            System.out.print(object+" ");
            
        }
        
        
        //2nd to array
        String[] arrayString = new String[set.size()];
        arrayString = set.toArray(arrayString);
        System.out.println("\nTesting 2nd toArray function ");
        for (int i = 0; i < arrayString.length; i++) {
            Object object = arrayString[i];
            System.out.print(object+" ");
            
        }
        
        //containsall
        ArrayList<String> containsAll = new ArrayList<>();
        containsAll.add("Jim");
        containsAll.add("Bob");
        containsAll.add("Steve");
       
        System.out.println("\nContains "+containsAll+" "+set.containsAll(containsAll));
        containsAll.add("Gonzales");
        
        System.out.println("Contains "+containsAll+" "+set.containsAll(containsAll));
        //retain all
        System.out.println("Retain "+containsAll+" ");
        set.retainAll(containsAll);
        System.out.println("Size is "+set.size());
        System.out.println(set);
        // remove all
        
        addAll.add("Jim");
        addAll.add("Bob");
        addAll.add("Steve");
        addAll.add("Lebron");
        addAll.add("Kobe");
        addAll.add("Rocky");
        System.out.println("Adding all "+addAll+" from array list");
        set.addAll(addAll);
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
        System.out.println("Remove "+containsAll+" ");
        set.removeAll(containsAll);
        System.out.println("Size is "+set.size());
        System.out.println(set);
        
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public boolean isEmpty() {
        return table.size() == 0;
    }

    @Override
    public boolean contains(Object arg0) {
        return table.contains((E)arg0);
    }

    @Override
    public Iterator<E> iterator() {
        return table.iterator();
    }

    @Override
    public Object[] toArray() {
        Iterator it = iterator();
        Object[] array = new Object[size()];
        int i = 0;
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        if(!(arg0.length>= this.size())){
        arg0 = (T[])new Object[this.size()];
        }
        Iterator it = iterator();
        
        int i = 0;
        while (it.hasNext()) {
            arg0[i] = (T)it.next();
            i++;
        }
        return arg0;
    }

    @Override
    public boolean add(E arg0) {
        if (contains(arg0)){
        return false;
        }
        else{
            if (table.numTakenHash >= (capacity * (double)loadFactor/100)) {
            expandCapacity();
        }
            table.add(arg0);
        return true;
                }
    }

    @Override
    public boolean remove(Object arg0) {
        if(contains(arg0)){
        table.remove((E)arg0);
        return true;
        }
        else{
        return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        Object next;
        for (Iterator<? extends Object> iterator = arg0.iterator(); iterator.hasNext();) {
            next = iterator.next();
            if(!contains(next)){
            return false;
            }
            
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        Object next;
        for (Iterator<? extends Object> iterator = arg0.iterator(); iterator.hasNext();) {
            next = iterator.next();
            add((E)next);
            
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        //copy all to new table
        Object next;
        HashTable newTable = new HashTable(capacity, loadFactor);
        for (Iterator<? extends Object> iterator = arg0.iterator(); iterator.hasNext();) {
            next = iterator.next();
            if (contains(next)) {
                newTable.add(next);
            }
            
        }
        table = newTable;
        
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        //loop through collection
        //get and remove all
        Object next;
        for (Iterator<? extends Object> iterator = arg0.iterator(); iterator.hasNext();) {
            next = iterator.next();
            
            remove((E)next);
            
        }
        return true;
    }

    @Override
    public void clear() {
        table = new HashTable<>(capacity, loadFactor);
    }
    
    public void expandCapacity(){
         HashTable newTable = new HashTable<E>(2*capacity, loadFactor);
        //Node[] oldArray = array;
        Iterator it = table.iterator();
        E element;
        
        
        while (it.hasNext()) {
            element = (E)it.next();
            newTable.add(element);
        }
        table = newTable;
        capacity = 2 * capacity;
    }
    
    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i < this.capacity; i++) {
            s += ("row "+i+": "+table.indexString(i)+"\n");
            
        }
    
    return s;
    }
    
}
