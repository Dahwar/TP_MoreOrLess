package france.uha.ensisa.fl.moreorless;

import static java.lang.Math.random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Florent
 */
public class MoreOrLessModel {
    private int secretNumber=0;
    private int currentNumber=0;
    private int max=0;
    private int limit=10000000;

    private ObservableList<String> listOfValue;
    
    public enum State {
        MORE,
        LESS,
        EQUAL
    }
    
    public MoreOrLessModel() {
        this.secretNumber=0;
        this.currentNumber=0;
        this.max=0;
        this.listOfValue=FXCollections.observableArrayList();
    }
    
    public MoreOrLessModel(int secretNumber, int currentNumber, int counter, int max) {
        this.secretNumber=secretNumber;
        this.currentNumber=currentNumber;
        this.max=max;
        this.listOfValue=FXCollections.observableArrayList();
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber=secretNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber=currentNumber;
        if(currentNumber>=0 && currentNumber<=this.getMax()){
            this.addValueToList(Integer.toString(currentNumber));
        }
    }

    public int getMax() {
        return max;
    }
    
    public void setMax(int max) {
        if(max<=getLimit())
            this.max=max;
        else
            this.max=getLimit();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public ObservableList<String> getListOfValue() {
        return this.listOfValue;
    }
    
    public boolean win() {
        return (this.secretNumber==this.currentNumber)?true:false;
    }
    
    public State isMoreOrLess() {
        return (this.currentNumber>this.secretNumber)?State.LESS:State.MORE;
    }
    
    public void addValueToList(String value) {
        this.listOfValue.add(value);
    }
    
    public void init(int max) {
        this.setMax(max);
        this.setSecretNumber((int) (random()*max));
        this.setCurrentNumber(0);
        this.listOfValue.clear();
    }
}
