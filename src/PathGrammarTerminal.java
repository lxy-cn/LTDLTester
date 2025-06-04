import java.util.Objects;

public class PathGrammarTerminal<T> {
    T terminal;

    public T getTerminal() {
        return terminal;
    }

    public void setTerminal(T terminal) {
        this.terminal = terminal;
    }

    public String getText() {
        return terminal.toString();
    }

    //对于以PathGrammarTerminal类为元素的Set类，定义如何判断类实例元素是否相同的机制
    @Override
    public int hashCode() { //hash函数定义
        return Objects.hash(terminal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        PathGrammarTerminal termObj = (PathGrammarTerminal) obj;
        return this.getText() == termObj.getText();
    }
}
