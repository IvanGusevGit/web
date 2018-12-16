package trials;

import ru.itmo.webmail.model.repository.AttributePair;

public class Try {
    public static void main(String[] args) {
        AttributePair<?> attributePair = new AttributePair<Integer>("aaa", 10);
        System.out.println(attributePair.getValue().toString());
    }

}
